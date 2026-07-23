#!/usr/bin/env python3
"""
Extract the repository list from the crocs-muni/javacard-curated-list README, enrich each entry with live stats
pulled from shields.io badges (stars, last commit, contributor count) and
write out a CSV plus a markdown statistics report.
"""

import argparse
import csv
import re
import sys
from concurrent.futures import ThreadPoolExecutor, as_completed
from datetime import date
from urllib.request import Request, urlopen
from urllib.error import HTTPError, URLError

README_URL = "https://raw.githubusercontent.com/crocs-muni/javacard-curated-list/master/README.md"
CSV_PATH = "javacard_repos.csv"
REPORT_PATH = "javacard_repos_stats.md"
SANITY_REPORT_PATH = "javacard_repos_sanity.md"

TODAY_YEAR = date.today().year
TODAY_MONTH = date.today().month

USER_AGENT = "Mozilla/5.0 (compatible; javacard-curated-list-processor/1.0)"


def fetch_url(url, timeout=15):
    req = Request(url, headers={"User-Agent": USER_AGENT})
    with urlopen(req, timeout=timeout) as resp:
        return resp.read().decode("utf-8", errors="replace")


# ---------------------------------------------------------------------------
# Step 1: fetch the raw README.md markdown source from GitHub
# ---------------------------------------------------------------------------

def load_markdown_text(source):
    if source.startswith("http://") or source.startswith("https://"):
        text = fetch_url(source)
    else:
        with open(source, encoding="utf-8") as f:
            text = f.read()
    return text.replace("\r\n", "\n").replace("\r", "\n")


# ---------------------------------------------------------------------------
# Step 2: parse the markdown into individual repository entries
# ---------------------------------------------------------------------------

ENTRY_RE = re.compile(r"^- \[(?P<name>.+?)\]\((?P<link>[^)]+)\)(?P<rest>.*)$")
GITHUB_RE = re.compile(r"github\.com/+([A-Za-z0-9_.\-]+)/+([A-Za-z0-9_.\-]+)")
MANUAL_LASTCOMMIT_RE = re.compile(r"last commit\s+(\d{4})", re.IGNORECASE)
HOST_RE = re.compile(r"https?://(?:www\.)?([a-zA-Z0-9.\-]+)")

BADGE_RE = {
    "stars": re.compile(r"!\[stars\]\(([^)]+)\)"),
    "last_commit": re.compile(r"!\[lastcommit\]\(([^)]+)\)"),
    "contributors": re.compile(r"!\[numcontributors\]\(([^)]+)\)"),
}

# shields.io badge URLs look like img.shields.io/github/<kind>/<owner>/<repo>.svg,
# i.e. they never contain a literal "github.com" - a separate pattern from GITHUB_RE.
SHIELDS_GITHUB_RE = re.compile(r"img\.shields\.io/github/[a-zA-Z\-]+/([A-Za-z0-9_.\-]+)/([A-Za-z0-9_.\-]+?)\.svg")


def github_owner_repo(url_or_text):
    m = GITHUB_RE.search(url_or_text)
    if not m:
        return None
    owner, repo = m.group(1), re.sub(r"\.git$", "", m.group(2))
    return (owner, repo)


def shields_badge_owner_repo(url_or_text):
    m = SHIELDS_GITHUB_RE.search(url_or_text)
    if not m:
        return None
    return (m.group(1), m.group(2))


def parse_entries(markdown_text):
    lines = markdown_text.split("\n")
    category = None
    subcategory = None
    entries = []

    i = 0
    n = len(lines)
    while i < n:
        line = lines[i]
        if line.startswith("## "):
            category = line[3:].strip()
            subcategory = None
            i += 1
            continue
        if line.startswith("### "):
            subcategory = line[4:].strip()
            i += 1
            continue
        if line.startswith("- ["):
            buf = line
            joined = 1
            m = ENTRY_RE.match(buf)
            while not m and joined < 4 and (i + joined) < n:
                buf = buf + " " + lines[i + joined]
                joined += 1
                m = ENTRY_RE.match(buf)
            if not m:
                i += 1
                continue
            name, link, rest = m.group("name"), m.group("link"), m.group("rest")

            gh = GITHUB_RE.search(buf)
            owner, repo = (gh.group(1), gh.group(2)) if gh else (None, None)
            if repo:
                repo = re.sub(r"\.git$", "", repo)

            host_m = HOST_RE.search(link)
            host = host_m.group(1) if host_m else ""

            manual_m = MANUAL_LASTCOMMIT_RE.search(buf)
            manual_last_commit_year = int(manual_m.group(1)) if manual_m else None

            main_owner_repo = github_owner_repo(link)

            badge_info = {}
            for key, pattern in BADGE_RE.items():
                bm = pattern.search(buf)
                badge_url = bm.group(1) if bm else None
                badge_info[key] = {
                    "present": bm is not None,
                    "url": badge_url,
                    "owner_repo": shields_badge_owner_repo(badge_url) if badge_url else None,
                }

            entries.append({
                "category": category,
                "subcategory": subcategory,
                "name": name,
                "link": link,
                "host": host,
                "owner": owner,
                "repo": repo,
                "manual_last_commit_year": manual_last_commit_year,
                "line_no": i + 1,
                "raw_line": buf,
                "main_owner_repo": main_owner_repo,
                "badges": badge_info,
            })
            i += joined
            continue
        i += 1

    return entries


# ---------------------------------------------------------------------------
# Step 3: fetch the shields.io badges for each repo and parse their SVG text
# ---------------------------------------------------------------------------

def badge_urls(owner, repo):
    return {
        "stars": f"https://img.shields.io/github/stars/{owner}/{repo}.svg?style=social",
        "last_commit": f"https://img.shields.io/github/last-commit/{owner}/{repo}.svg",
        "contributors": f"https://img.shields.io/github/contributors-anon/{owner}/{repo}.svg",
    }


def parse_count_text(val):
    val = val.strip().lower()
    if val.isdigit():
        return int(val)
    m = re.match(r"^(\d+(?:\.\d+)?)([km])$", val)
    if m:
        num = float(m.group(1))
        mult = 1000 if m.group(2) == "k" else 1_000_000
        return int(round(num * mult))
    return None


def parse_stars_svg(svg):
    m = re.search(r'id="rlink"[^>]*>([^<]+)</text>', svg)
    if m:
        return parse_count_text(m.group(1))
    return None


def parse_aria_label_value(svg):
    m = re.search(r'aria-label="[^:]+:\s*([^"]+)"', svg)
    return m.group(1).strip() if m else None


def fetch_with_retries(url, retries=2):
    last_exc = None
    for attempt in range(retries + 1):
        try:
            return fetch_url(url)
        except (HTTPError, URLError, TimeoutError) as e:
            last_exc = e
    raise last_exc


def fetch_repo_badges(owner, repo):
    urls = badge_urls(owner, repo)
    result = {"stars": None, "last_commit_raw": None, "developers": None, "error": None}
    try:
        stars_svg = fetch_with_retries(urls["stars"])
        result["stars"] = parse_stars_svg(stars_svg)
    except (HTTPError, URLError, TimeoutError) as e:
        result["error"] = f"stars:{e}"

    try:
        lc_svg = fetch_with_retries(urls["last_commit"])
        result["last_commit_raw"] = parse_aria_label_value(lc_svg)
    except (HTTPError, URLError, TimeoutError) as e:
        result["error"] = (result["error"] or "") + f" last_commit:{e}"

    try:
        contrib_svg = fetch_with_retries(urls["contributors"])
        val = parse_aria_label_value(contrib_svg)
        if val:
            result["developers"] = parse_count_text(val)
    except (HTTPError, URLError, TimeoutError) as e:
        result["error"] = (result["error"] or "") + f" contributors:{e}"

    return result


# ---------------------------------------------------------------------------
# Step 4: turn the shields "last commit" text into a calendar year
# ---------------------------------------------------------------------------

MONTHS = ["january", "february", "march", "april", "may", "june", "july",
          "august", "september", "october", "november", "december"]


def last_commit_text_to_year(text):
    if not text:
        return None
    t = text.strip().lower()

    if t in ("today", "yesterday"):
        return TODAY_YEAR

    weekdays = ("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday")
    if t == "last week" or any(t == f"last {d}" for d in weekdays):
        return TODAY_YEAR

    m = re.match(r"(\d+)\s+(day|days|week|weeks)\s+ago", t)
    if m:
        return TODAY_YEAR

    m = re.match(r"(\d+)\s+(month|months)\s+ago", t)
    if m:
        months_ago = int(m.group(1))
        y, mo = TODAY_YEAR, TODAY_MONTH
        for _ in range(months_ago):
            mo -= 1
            if mo == 0:
                mo = 12
                y -= 1
        return y

    m = re.match(r"(\d+)\s+(year|years)\s+ago", t)
    if m:
        return TODAY_YEAR - int(m.group(1))

    m = re.match(r"([a-z]+)\s+(\d{4})$", t)
    if m and m.group(1) in MONTHS:
        return int(m.group(2))

    m = re.match(r"^([a-z]+)$", t)
    if m and m.group(1) in MONTHS:
        month_num = MONTHS.index(m.group(1)) + 1
        return TODAY_YEAR if month_num <= TODAY_MONTH else TODAY_YEAR - 1

    return None


def recency_bucket(year):
    if year is None:
        return "Unknown"
    age = TODAY_YEAR - year
    if age <= 0:
        return "This year (2026)"
    if age == 1:
        return "Last year (2025)"
    if age == 2:
        return "2 years ago (2024)"
    if age <= 4:
        return "3-4 years ago"
    if age <= 9:
        return "5-9 years ago"
    return "10+ years ago"


def star_bucket(stars):
    if stars is None:
        return "Unknown"
    if stars == 0:
        return "0"
    if stars <= 4:
        return "1-4"
    if stars <= 9:
        return "5-9"
    if stars <= 24:
        return "10-24"
    if stars <= 49:
        return "25-49"
    if stars <= 99:
        return "50-99"
    return "100+"


def dev_bucket(devs):
    if devs is None:
        return "Unknown"
    if devs == 1:
        return "1"
    if devs <= 3:
        return "2-3"
    if devs <= 10:
        return "4-10"
    return "11+"


# ---------------------------------------------------------------------------
# --sanity: structural + data-availability checks on the README's badges
# ---------------------------------------------------------------------------

BADGE_LABELS = {"stars": "stars", "last_commit": "last-commit", "contributors": "contributors"}

# GitHub org that re-hosts archived copies of repos removed by their original owner.
# A badge pointing here with the same repo name as the entry's main link is intentional,
# not a copy-paste mistake, so it's exempt from the badge_mismatch check.
ARCHIVE_ORG = "javacard-foss-applets"


def owner_repo_str(t):
    return f"{t[0]}/{t[1]}" if t else None


def structural_issues_for_entry(e):
    """Badge-vs-link mismatches and copy/paste artifacts detectable from the markdown alone."""
    issues = []
    main_or = e["main_owner_repo"]
    badges = e["badges"]

    if main_or:
        main_or_lower = (main_or[0].lower(), main_or[1].lower())
        for key, label in BADGE_LABELS.items():
            b = badges[key]
            if not b["present"]:
                issues.append(("missing_badge", f"missing {label} badge (entry links to {owner_repo_str(main_or)})"))
            elif b["owner_repo"] is None:
                issues.append(("malformed_badge", f"{label} badge URL doesn't look like a github.com/owner/repo shields link: {b['url']}"))
            elif (b["owner_repo"][0].lower(), b["owner_repo"][1].lower()) != main_or_lower:
                badge_owner, badge_repo = b["owner_repo"][0].lower(), b["owner_repo"][1].lower()
                if badge_owner == ARCHIVE_ORG and badge_repo == main_or_lower[1]:
                    # Badge points to the archived copy of this exact repo under the
                    # javacard-FOSS-applets org - expected when the original was removed.
                    continue
                issues.append(("badge_mismatch",
                                f"{label} badge points to {owner_repo_str(b['owner_repo'])} but entry links to "
                                f"{owner_repo_str(main_or)} (looks like a copy-pasted badge that wasn't updated)"))
    else:
        # Main link isn't GitHub (SourceForge/GitLab/BitBucket/archive.org/...) - any GitHub
        # badge left in place is very likely a leftover from copy-pasting another entry.
        for key, label in BADGE_LABELS.items():
            b = badges[key]
            if b["present"] and b["owner_repo"]:
                issues.append(("orphan_badge",
                                f"{label} badge references GitHub repo {owner_repo_str(b['owner_repo'])} but the "
                                f"entry's main link is not GitHub ({e['link']})"))

    raw = e["raw_line"]

    # Strip the two markdown idioms that legitimately contain nested/repeated brackets so
    # they don't drown out real copy-paste mistakes: (1) a linked badge image "[![alt](img)](url)",
    # and (2) the manual "_[last commit YYYY, ... [removed](url)]_" annotation.
    cleaned = re.sub(r"\[!\[[^\]]*\]\([^)]*\)\]\([^)]*\)", " ", raw)
    cleaned = re.sub(r"_\[(?:[^\[\]]|\[[^\[\]]*\])*\]_", " ", cleaned)

    if re.search(r"\[[^\]\[]*\[", cleaned):
        issues.append(("nested_brackets", "nested '[' found inside a link's text - possible malformed/duplicated markdown link"))

    if re.search(r"\)\(https?://", cleaned):
        issues.append(("adjacent_links", "a link is immediately followed by another '(...)' with no separator - "
                                          "looks like two links got concatenated (missing space?)"))

    all_urls = [e["link"]] + [b["url"] for b in badges.values() if b["url"]]
    for url in all_urls:
        if url.count("http") > 1 and "origin_url=" not in url:
            issues.append(("concatenated_url", f"URL appears to contain two links glued together: {url}"))

    return issues


def data_issues_for_entry(e):
    """Issues only detectable by actually resolving the badge (repo renamed/deleted/private/typo)."""
    issues = []
    main_or = e["main_owner_repo"]
    if not main_or:
        return issues

    result = fetch_repo_badges(*main_or)
    if result["stars"] is None:
        issues.append(("missing_stars", f"stars could not be resolved for {owner_repo_str(main_or)} "
                                         f"({result['last_commit_raw'] or result['error'] or 'no data'})"))
    if result["last_commit_raw"] is None or result["last_commit_raw"] == "repo not found":
        issues.append(("missing_last_commit", f"last commit could not be resolved for {owner_repo_str(main_or)} "
                                               f"({result['last_commit_raw'] or result['error'] or 'no data'})"))
    if result["developers"] is None:
        issues.append(("missing_developers", f"developer/contributor count could not be resolved for "
                                              f"{owner_repo_str(main_or)} ({result['last_commit_raw'] or result['error'] or 'no data'})"))
    return issues


def run_sanity(path, offline=False):
    print(f"Extracting markdown from {path} ...", file=sys.stderr)
    markdown_text = load_markdown_text(path)

    print("Parsing repository entries...", file=sys.stderr)
    entries = parse_entries(markdown_text)
    print(f"  found {len(entries)} entries", file=sys.stderr)

    print("Checking badge links for structural issues...", file=sys.stderr)
    for e in entries:
        e["issues"] = structural_issues_for_entry(e)

    if not offline:
        github_entries = [e for e in entries if e["main_owner_repo"]]
        print(f"Fetching live badge data to check for unresolvable entries ({len(github_entries)} repos)...",
              file=sys.stderr)
        with ThreadPoolExecutor(max_workers=10) as pool:
            future_to_entry = {pool.submit(data_issues_for_entry, e): e for e in github_entries}
            done = 0
            for fut in as_completed(future_to_entry):
                e = future_to_entry[fut]
                try:
                    e["issues"].extend(fut.result())
                except Exception as exc:
                    e["issues"].append(("fetch_error", str(exc)))
                done += 1
                if done % 25 == 0:
                    print(f"  ...{done}/{len(github_entries)}", file=sys.stderr)

    flagged = [e for e in entries if e["issues"]]
    print_sanity_report(entries, flagged)
    write_sanity_report(entries, flagged)
    return flagged


def print_sanity_report(entries, flagged):
    print(f"\n{len(flagged)}/{len(entries)} entries have issues:\n")
    for e in flagged:
        loc = e["subcategory"] or e["category"] or ""
        print(f"- [{e['name']}] (line {e['line_no']}, {loc})")
        print(f"  {e['link']}")
        for kind, detail in e["issues"]:
            print(f"    [{kind}] {detail}")
        print()


def write_sanity_report(entries, flagged):
    by_kind = {}
    for e in flagged:
        for kind, _ in e["issues"]:
            by_kind[kind] = by_kind.get(kind, 0) + 1

    lines = []
    lines.append("# JavaCard Curated List — Sanity Check Report")
    lines.append("")
    lines.append(f"- Total entries checked: **{len(entries)}**")
    lines.append(f"- Entries with at least one issue: **{len(flagged)}**")
    lines.append("")

    if by_kind:
        lines.append("## Issues by type")
        lines.append("")
        lines.append("| Issue type | Count |")
        lines.append("|---|---|")
        for kind, count in sorted(by_kind.items(), key=lambda kv: -kv[1]):
            lines.append(f"| {kind} | {count} |")
        lines.append("")

    lines.append("## Flagged entries")
    lines.append("")
    for e in flagged:
        loc = e["subcategory"] or e["category"] or ""
        lines.append(f"### [{e['name']}]({e['link']}) — line {e['line_no']} ({loc})")
        lines.append("")
        for kind, detail in e["issues"]:
            lines.append(f"- **{kind}**: {detail}")
        lines.append("")

    with open(SANITY_REPORT_PATH, "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")
    print(f"Wrote {SANITY_REPORT_PATH}", file=sys.stderr)


# ---------------------------------------------------------------------------
# Main pipeline
# ---------------------------------------------------------------------------

def run_pipeline(path):
    print(f"Extracting markdown from {path} ...", file=sys.stderr)
    markdown_text = load_markdown_text(path)

    print("Parsing repository entries...", file=sys.stderr)
    entries = parse_entries(markdown_text)
    print(f"  found {len(entries)} entries", file=sys.stderr)

    github_entries = [e for e in entries if e["owner"] and e["repo"]]
    print(f"  {len(github_entries)} have a github.com owner/repo we can query", file=sys.stderr)

    print("Fetching shields.io badges (stars / last-commit / contributors)...", file=sys.stderr)
    with ThreadPoolExecutor(max_workers=10) as pool:
        future_to_entry = {
            pool.submit(fetch_repo_badges, e["owner"], e["repo"]): e
            for e in github_entries
        }
        done = 0
        for fut in as_completed(future_to_entry):
            e = future_to_entry[fut]
            try:
                badges = fut.result()
            except Exception as exc:
                badges = {"stars": None, "last_commit_raw": None, "developers": None, "error": str(exc)}
            e["stars"] = badges["stars"]
            e["last_commit_raw"] = badges["last_commit_raw"]
            e["developers"] = badges["developers"]
            e["fetch_error"] = badges["error"]
            done += 1
            if done % 25 == 0:
                print(f"  ...{done}/{len(github_entries)}", file=sys.stderr)

    for e in entries:
        e.setdefault("stars", None)
        e.setdefault("last_commit_raw", None)
        e.setdefault("developers", None)
        e.setdefault("fetch_error", None)

        year = last_commit_text_to_year(e["last_commit_raw"])
        if year is None:
            year = e["manual_last_commit_year"]
        e["last_commit_year"] = year

    print(f"Writing {CSV_PATH} ...", file=sys.stderr)
    with open(CSV_PATH, "w", newline="", encoding="utf-8") as f:
        writer = csv.writer(f)
        writer.writerow([
            "Category", "Subcategory", "Repository", "GitHub Link",
            "Stars", "Last Commit (badge text)", "Last Commit Year (est.)",
            "Developers",
        ])
        for e in entries:
            writer.writerow([
                e["category"] or "",
                e["subcategory"] or "",
                e["name"],
                e["link"],
                e["stars"] if e["stars"] is not None else "",
                e["last_commit_raw"] or (f"last commit {e['manual_last_commit_year']}" if e["manual_last_commit_year"] else ""),
                e["last_commit_year"] if e["last_commit_year"] is not None else "",
                e["developers"] if e["developers"] is not None else "",
            ])

    write_report(entries)
    print("Done.", file=sys.stderr)


def write_report(entries):
    total = len(entries)
    with_stars = [e for e in entries if e["stars"] is not None]
    with_year = [e for e in entries if e["last_commit_year"] is not None]
    with_devs = [e for e in entries if e["developers"] is not None]

    star_order = ["0", "1-4", "5-9", "10-24", "25-49", "50-99", "100+", "Unknown"]
    star_counts = {k: 0 for k in star_order}
    for e in entries:
        star_counts[star_bucket(e["stars"])] += 1

    recency_order = ["This year (2026)", "Last year (2025)", "2 years ago (2024)",
                      "3-4 years ago", "5-9 years ago", "10+ years ago", "Unknown"]
    recency_counts = {k: 0 for k in recency_order}
    for e in entries:
        recency_counts[recency_bucket(e["last_commit_year"])] += 1

    dev_order = ["1", "2-3", "4-10", "11+", "Unknown"]
    dev_counts = {k: 0 for k in dev_order}
    for e in entries:
        dev_counts[dev_bucket(e["developers"])] += 1

    cat_order = []
    cat_counts = {}
    for e in entries:
        c = e["category"] or "Uncategorized"
        if c not in cat_counts:
            cat_order.append(c)
            cat_counts[c] = 0
        cat_counts[c] += 1

    top_starred = sorted(with_stars, key=lambda e: e["stars"], reverse=True)[:10]

    lines = []
    lines.append("# JavaCard Curated List — Statistics")
    lines.append("")
    lines.append("Source: [crocs-muni/javacard-curated-list](https://github.com/crocs-muni/javacard-curated-list) README, "
                  "enriched with live data pulled from shields.io GitHub badges (stars, last-commit, contributors).")
    lines.append("")
    lines.append(f"- Total repository entries found: **{total}**")
    lines.append(f"- Entries with a resolvable GitHub star count: **{len(with_stars)}**")
    lines.append(f"- Entries with a resolvable last-commit date: **{len(with_year)}**")
    lines.append(f"- Entries with a resolvable contributor count: **{len(with_devs)}**")
    lines.append("")

    lines.append("## Entries per section")
    lines.append("")
    lines.append("| Section | Count |")
    lines.append("|---|---|")
    for c in cat_order:
        lines.append(f"| {c} | {cat_counts[c]} |")
    lines.append("")

    lines.append("## Star distribution")
    lines.append("")
    lines.append("| Stars | Repositories | % of total |")
    lines.append("|---|---|---|")
    for k in star_order:
        if star_counts[k]:
            lines.append(f"| {k} | {star_counts[k]} | {100 * star_counts[k] / total:.1f}% |")
    lines.append("")

    lines.append("## Last commit recency")
    lines.append("")
    lines.append("| Last commit | Repositories | % of total |")
    lines.append("|---|---|---|")
    for k in recency_order:
        if recency_counts[k]:
            lines.append(f"| {k} | {recency_counts[k]} | {100 * recency_counts[k] / total:.1f}% |")
    lines.append("")

    lines.append("## Number of developers (contributors)")
    lines.append("")
    lines.append("| Developers | Repositories | % of total |")
    lines.append("|---|---|---|")
    for k in dev_order:
        if dev_counts[k]:
            lines.append(f"| {k} | {dev_counts[k]} | {100 * dev_counts[k] / total:.1f}% |")
    lines.append("")

    lines.append("## Top 10 most starred repositories")
    lines.append("")
    lines.append("| Repository | Stars | Last commit | Developers |")
    lines.append("|---|---|---|---|")
    for e in top_starred:
        lines.append(f"| [{e['name']}]({e['link']}) | {e['stars']} | "
                      f"{e['last_commit_raw'] or ''} | {e['developers'] if e['developers'] is not None else ''} |")
    lines.append("")

    with open(REPORT_PATH, "w", encoding="utf-8") as f:
        f.write("\n".join(lines) + "\n")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("source", nargs="?", default=README_URL,
                         help="Curated list source: a URL (default: the live crocs-muni/javacard-curated-list "
                              "README on GitHub) or a local markdown file")
    parser.add_argument("--sanity", action="store_true",
                         help="Check the file for badge issues (missing badges, badge/link mismatches, "
                              "unresolvable stars/last-commit/developers) instead of building the CSV/report")
    parser.add_argument("--offline", action="store_true",
                         help="With --sanity, skip live shields.io lookups and only run the structural checks")
    args = parser.parse_args()

    if args.sanity:
        run_sanity(args.source, offline=args.offline)
    else:
        run_pipeline(args.source)


if __name__ == "__main__":
    main()
