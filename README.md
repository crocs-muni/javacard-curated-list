[![Logo of Enigma Bridge](http://www.ideaspace.cam.ac.uk/images/iswestimages/enigmabridgelogo/image_preview)](https://keychest.net)

# Curated list of JavaCard applications
The goal is to provide curated catalog of all open-source JavaCard applets and applications relevant for JavaCard development. The initial list was compiled by complete search for all GitHub and SourceForge repositories with *'javacard.framework'* keyword.

You are encouraged to contribute - please create a pull request. 

The projects collected in this list were analyzed and published (June 2017) in [Analysis of JavaCard open-source ecosystem](https://medium.com/enigma-shards/analysis-of-javacard-open-source-ecosystem-9be0bfd66398)

### Backup of listed repositories
As repositores are sometimes moved or removed, the fork of each repository under the virtual organization ['javacard-curated-list'](https://github.com/javacard-FOSS-applets) is created. Try to find a repository there if link doesn't work.

### Format and notation
> **Project name _[activity]_** <br> Short description, often taken from a project readme.md
If is the project located on GitHub as primary project (not only source code in some subfolder), last commit date, number of contributors and number of stars received is retrieved directly from GitHub. Otherwise, source like SourceForge or BitBucket is signalized with manual last commit date entry.

## Contents

1. [Applets (standalone applications)](#applets-standalone-applications)
    - [Electronic passports and citizen ID](#electronic-passports-and-citizen-id)
    - [Authentication and access control](#authentication-and-access-control)
    - [Payments and loyalty](#payments-and-loyalty)
    - [Key and password managers](#key-and-password-managers)
    - [Digital signing, OpenPGP and mail security](#digital-signing-openpgp-and-mail-security)
    - [e-Health](e-health)
    - [NDEF tags](#ndef-tags)
    - [CryptoCurrency wallets](#cryptocurrency-wallets)
    - [Emulation of some proprietary cards](#emulation-of-some-proprietary-cards)
    - [Unsorted applications](#unsorted-applications)
2.  [Library code (code which is expected to be used as part of other code)](#library-code-code-which-is-expected-to-be-used-as-part-of-other-code)
3. [Developer tools](#developer-tools)
    - [Applet build, upload and management](#applet-build-upload-and-management)
    - [Card capabilities testing (algorithms support, performance, security issues)](#card-capabilities-testing-algorithms-support-performance-security-issues)
    - [Formal verification and code transformation tools](#formal-verification-and-code-transformation-tools)
4. [JavaCard simulators and emulators](#javacard-simulators-and-emulators)
5. [Learning (various school projects, simple hello world applets, etc)](#learning-various-school-projects-simple-hello-world-applets-etc)
6. [Unsorted](#unsorted)
   - [(needs further inspection)](#needs-further-inspection)
   - [Methodology](#methodology)

_________________________________________________

## Applets (standalone applications)
### Electronic passports and citizen ID
 
- [JMRTD: Machine Readable Travel Documents](https://sourceforge.net/projects/jmrtd/) (**SourceForge**) _[last commit 2017]_ <br>
Free implementation of the MRTD (Machine Readable Travel Documents) standards as set by ICAO used in the ePassport. Consists of an API for card terminal software and a Java Card applet.

- [EstEID compatible JavaCard applets](https://github.com/martinpaljak/esteid-applets)    ![stars](https://img.shields.io/github/stars/martinpaljak/esteid-applets.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/esteid-applets.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/esteid-applets.svg)   <br> 
Various JavaCard applets compatible to EstEID chip protocol: FakeEstEID, MyEstEID

- [Electronic Driving License](https://github.com/martinpaljak/AppletPlayground/tree/master/src/org/isodl/applet) (**GitHub**) _[last commit 2015]_  <br> 
A reference implementation of the ISO18013 standards. Based on the passport applet code developed by the JMRTD team. The project implements the host API for reading out ISO compliant electronic driving licenses and a Java Card applet that implements the standard on a smart card. 

- [JMRTD applet without EAC support](https://github.com/walterschell/jmrtd-noeac)    ![stars](https://img.shields.io/github/stars/walterschell/jmrtd-noeac.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/walterschell/jmrtd-noeac.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/walterschell/jmrtd-noeac.svg)   <br> 
Fork of JMRTD electronic passport applet without EAC support. The target device for this project is G+D SmartCafe Expert 144k Dual.

- [SIC eID card](https://github.com/nversbra/SIC)    ![stars](https://img.shields.io/github/stars/nversbra/SIC.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/nversbra/SIC.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/nversbra/SIC.svg) 
  <br> 
A privacy-friendly alternative for the Belgian eID card. The project aims to improve security of Belgian ID holders by limiting the current extensive exposure of their profiles. To do so, we build an alternative ID card which limits service providers to strickly necessary ID holder profile information. 
- [FedICT Quick-Key Toolset](https://github.com/Twuk/eid-quick-key-toolset/tree/master/eid-quick-key-toolset) (**GitHub**) _[last commit 2011]_
  <br> 
EidCard project
- [IdentityCard applet](https://github.com/JavaCardSpot-dev/IdentityCard-applet/tree/master/workspace/JavaCard) (**GitHub**) _[last commit 2017]_
  <br> 
Vrije University Brussels applet (be.msec.smartcard.IdentityCard.java) with authentication, identity metadata storage and retrieval and time update functionality.
- [belgian-e-id applet](https://github.com/amoerie/belgian-e-id)  ![stars](https://img.shields.io/github/stars/amoerie/belgian-e-id.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/amoerie/belgian-e-id.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/amoerie/belgian-e-id.svg) 
  <br> 
Belgian e-id applet

### Authentication and access control
- [YkOtpApplet - OTP applet](https://github.com/arekinath/YkOtpApplet)    ![stars](https://img.shields.io/github/stars/arekinath/YkOtpApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/arekinath/YkOtpApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/arekinath/YkOtpApplet.svg) 
  <br>
Javacard applet emulating the Yubikey challenge-response interface
- [YubiKey NEO App: OATH](https://github.com/Yubico/ykneo-oath)    ![stars](https://img.shields.io/github/stars/Yubico/ykneo-oath.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Yubico/ykneo-oath.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Yubico/ykneo-oath.svg) 
  <br>
This project implement the HOTP/TOTP card functionality used on the YubiKey NEO device that is sold by Yubico. Its primary use is to use the YubiKey NEO to generate OATH HOTP/TOTP one-time-passwords. GPLv3+

- [ISOApplet PKI](https://github.com/philipWendland/IsoApplet)    ![stars](https://img.shields.io/github/stars/philipWendland/IsoApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/philipWendland/IsoApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/philipWendland/IsoApplet.svg)   <br>
A Java Card PKI Applet aiming to be ISO 7816 compliant. The Applet is capable of saving a PKCS#15 file structure and performing PKI related operations using the private key, such as signing or decrypting. Private keys can be generated directly on the smartcard or imported from the host computer. The import of private keys is disabled in the default security configuration. 

- [SSH support applet](https://github.com/scs/uclinux/blob/eb0cf9617bd22b69ad625575a95cf4fa2c140d55/user/ssh/scard/Ssh.java) (**GitHub**) _[last commit 2007]_  <br>
Old, but widely copied applet performing RSA decrypt on card and used by SSH client 

- [HOTP authenticator via NDEF tag](https://github.com/petrs/hotp_via_ndef)    ![stars](https://img.shields.io/github/stars/petrs/hotp_via_ndef.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/petrs/hotp_via_ndef.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/petrs/hotp_via_ndef.svg)  <br>
JavaCard HMAC-based One Time Password generator which delivers new code via URL tag of NDEF every time the card is put close to NFC-enabled phone. As the Android (and soon also iOS) handles the NDEF tags natively, no additional software is required (after initial card personalization with OTP secret key).

- [CoolKey Applet](https://github.com/NabilNoaman/CoolkeyApplet)    ![stars](https://img.shields.io/github/stars/NabilNoaman/CoolkeyApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/NabilNoaman/CoolkeyApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/NabilNoaman/CoolkeyApplet.svg)  <br>
CoolKey Applet with the idea of making it a fresh JavaCard 2.2.2 applet meant to be revival of CardEdge Muscle card applet.

- [MuscleApplet](https://github.com/martinpaljak/MuscleApplet)    ![stars](https://img.shields.io/github/stars/martinpaljak/MuscleApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/MuscleApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/MuscleApplet.svg)  <br>
Significant, but outdated applet used for OpenSC. Superseeded by PKCS#15 and PIV standards.

- [OpenFIPS201 PIV applet](https://github.com/makinako/OpenFIPS201)    ![stars](https://img.shields.io/github/stars/makinako/OpenFIPS201.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/makinako/OpenFIPS201.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/makinako/OpenFIPS201.svg)  <br>
Personal Identity Verification (PIV) applet. Commissioned and funded by the Australian Department of Defence

- [PIV applet](https://github.com/arekinath/PivApplet)    ![stars](https://img.shields.io/github/stars/arekinath/PivApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/arekinath/PivApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/arekinath/PivApplet.svg) <br>
Personal Identity Verification (PIV) applet (NIST SP 800-73-4). Target is JavaCard 2.2.2, with 2-3k of transient memory.

- [ORWL KeyFob applets](https://github.com/O-R-W-L/KeyFob-applet)    ![stars](https://img.shields.io/github/stars/O-R-W-L/KeyFob-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/O-R-W-L/KeyFob-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/O-R-W-L/KeyFob-applet.svg) 
   <br>
Contains applets for KeyFOB NFC Secure Element for performing association, authentication and identification. Relates to ORWL secure computer.
- [PKCS#15 applet](https://github.com/lupascualex/p15)   _[REMOVED, last commit 2015]_ <br>
Implementation of card according to RSA PKCS#15 specification. (seems like extensive implementation, but fails to convert under ant-javacard so far)  

- [PKI applet](https://github.com/rakeb/PKIApplet) _[REMOVED, last commit 2016]_ <br>
(extensive PKI applet, requires JavaCard 3.0.5) 

- [Generic Identity Device Specification Applet](https://github.com/vletoux/GidsApplet)  ![stars](https://img.shields.io/github/stars/vletoux/GidsApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/vletoux/GidsApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/vletoux/GidsApplet.svg)  <br>
Generic Identity Device Specification (GIDS) smart card is the only PKI smart card whose driver is integrated on each Windows since Windows 7 SP1 and which can be used read and write. No Windows driver installation is required. Based on ISOApplet PKI

- [PIV CryptonitApplet](https://github.com/mbrossard/cryptonit-applet)    ![stars](https://img.shields.io/github/stars/mbrossard/cryptonit-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/mbrossard/cryptonit-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/mbrossard/cryptonit-applet.svg) 
  <br>
Personal Identity Verification (PIV) applet
- [Ledger U2F Applet](https://github.com/LedgerHQ/ledger-u2f-javacard)    ![stars](https://img.shields.io/github/stars/LedgerHQ/ledger-u2f-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/LedgerHQ/ledger-u2f-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/LedgerHQ/ledger-u2f-javacard.svg) 
  <br> 
This applet is a Java Card implementation of the FIDO Alliance U2F standard. It uses no proprietary vendor API and is freely available on Ledger Unplugged and for a small fee on other Fidesmo devices through Fidesmo store.
- [Ledger U2F Applet Fork](https://github.com/darconeous/u2f-javacard/)    ![stars](https://img.shields.io/github/stars/darconeous/ledger-u2f-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/darconeous/ledger-u2f-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/darconeous/ledger-u2f-javacard.svg) 
  <br> 
This applet is a Fork of Ledger U2F repository with some changes. At first, in the releases a compiled source is available along with a script for GpPro tool to both instal and personalise the applet. Secondly, a proprietary APDU was changed from `F0` CLA to `80`. All personalisation APDU on other ledger forks threw `6881`, but the `80` CLA personalisation succeeded. The repository also claims to support iOS.
- [FIDO CCU2F Applet](https://github.com/tsenger/CCU2F)    ![stars](https://img.shields.io/github/stars/tsenger/CCU2F.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/tsenger/CCU2F.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/tsenger/CCU2F.svg) 
  <br> 
This CCU2F JavaCard Applet is based on the Ledger U2F Applet. I imported this applet to Eclipse with installed JCOP Tools and modified the AID of this applet to the standardized AID for FIDO NFC token ( 0xA0000006472F0001). I also provided some example data ([Attestation Certificate and Key](u2f-javacard/U2F Example Attestation Certificate and Key Bytes.txt)) to bring this applet to run. This Applet was succesfully tested on JCOP v2.4.2 R3 cards with KeyAgreementX.ALG_EC_SVDP_DH_PLAIN_XY from NXPs JCOP library for EC Point Multiplication. 
- [U2FToken](https://github.com/fightyz/U2FToken)    ![stars](https://img.shields.io/github/stars/fightyz/U2FToken.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/fightyz/U2FToken.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/fightyz/U2FToken.svg) 
  <br>
An U2F Token implementation based on Ledger U2F Applet which cannot pass the NFC self-conformance test in the phase: "U2F_REGISTER, Short APDU, Change BlockSize", as it doesn't handle the situation which Le(BlockSize) is not 256 bytes.)
- [Yubikey Neo One Time Pad](https://github.com/cayennegraphics/Yubisec)    ![stars](https://img.shields.io/github/stars/cayennegraphics/Yubisec.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/cayennegraphics/Yubisec.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/cayennegraphics/Yubisec.svg) 
  <br>
This project has been submitted to the YubiKing 2015 Hackathon. Yubisec is an implementation of a One Time Pad for secure communication between two Android phones using keys stored and generated on YubiKey Neo tokens.
- [Biometric Authentication](https://github.com/albertocarp/BiometricAuthentification)    ![stars](https://img.shields.io/github/stars/albertocarp/BiometricAuthentification.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/albertocarp/BiometricAuthentification.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/albertocarp/BiometricAuthentification.svg) 
  <br>
Fuzzy extractor to authenticate with biometric data
- [OneCard](https://github.com/SharkyHarky/OneCard)    ![stars](https://img.shields.io/github/stars/SharkyHarky/OneCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/SharkyHarky/OneCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/SharkyHarky/OneCard.svg) 
  <br>
radiius.com Radiius applet, applet seems to be just starting to implement required functionality as per specification 
- [OTP client and server applets](https://github.com/gelvaos/otp)    ![stars](https://img.shields.io/github/stars/gelvaos/otp.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gelvaos/otp.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gelvaos/otp.svg) 
  <br>
This is proof-of-concept implementation of One Time password JavaCard STK applet and authentication server. Load JavaCard applet to SIM card and use STK menu.
- [Trusted Identity Module](https://github.com/Orange-OpenSource/TIM)    ![stars](https://img.shields.io/github/stars/Orange-OpenSource/TIM.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Orange-OpenSource/TIM.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Orange-OpenSource/TIM.svg) 
  <br>
A local smartphone module acting as an OpenID Connect Server proxy and delivers trusted tokens to installed native applications. The TIM improves the user experience with single sign on, security and privacy enhancement. The Trusted Identity Module project is a set of four projects: an Android service (tim_service), a JAVA Card Service (TimCardlet), a modified OpenID Connect Server (phpOpTim) and a basic Android TIM-Client app enabling to test the TIM services (HelloTim). The OIDC-TIM server is based on an open source implementation of OpenID Connect in PHP by Nomura Research Institute, Ltd. Seems to be extensive and well documented. 

### Payments and loyalty
- [OpenEMV](https://github.com/JavaCardOS/OpenEMV)  ![stars](https://img.shields.io/github/stars/JavaCardOS/OpenEMV.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/JavaCardOS/OpenEMV.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/JavaCardOS/OpenEMV.svg)  <br>
The OpenEMV is a Java Card implementation of the EMV standard. This applet is a very basic EMV applet supporting only SDA and plaintext offline PIN.It does not offer personalisation support - everything is hard-coded.
seems like mature project

- [EMV-TOOLS](https://github.com/gabessolo/EMV-TOOLS)    ![stars](https://img.shields.io/github/stars/gabessolo/EMV-TOOLS.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gabessolo/EMV-TOOLS.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gabessolo/EMV-TOOLS.svg) 
  <br>
Collection of source code for many EMV-related projects: SimpleEMVApplet, java-card-openEMV, card-spy, emvdemoBook, emv-bertlv...
- [Simple Wallet](https://github.com/fitpay/javacard-simple-wallet)    ![stars](https://img.shields.io/github/stars/fitpay/javacard-simple-wallet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/fitpay/javacard-simple-wallet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/fitpay/javacard-simple-wallet.svg) 
  <br>
This is a simple wallet simply maintains a running account balance allowing you to credit, debit, or review the current balance of the wallet. This is simply for pure demonstration on various wearable technologies where a javacard secure element is available.
- [E-Purse](https://github.com/tomirio619/hw-epurse)    ![stars](https://img.shields.io/github/stars/tomirio619/hw-epurse.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/tomirio619/hw-epurse.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/tomirio619/hw-epurse.svg) 
  <br>
just started, unifinished (04/2017)
- [AppSecure](https://github.com/deepakprabhakara/appsecure)    ![stars](https://img.shields.io/github/stars/deepakprabhakara/appsecure.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/deepakprabhakara/appsecure.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/deepakprabhakara/appsecure.svg) 
  <br>
AppSecure uses Gemalto's eGate smart card framework to enforce pay as you go services for Win32 applications. This entry made it to the pre-final round of Gemalto's eGate Open Contest in 2004           
- [PBOC3Applet](https://github.com/jiankeliu5/PBOC3Applet)    ![stars](https://img.shields.io/github/stars/jiankeliu5/PBOC3Applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/jiankeliu5/PBOC3Applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/jiankeliu5/PBOC3Applet.svg) 
  <br>
payment applet
- [PayPass applet](https://github.com/jiankeliu5/CardApplet-PayPass)    ![stars](https://img.shields.io/github/stars/jiankeliu5/CardApplet-PayPass.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/jiankeliu5/CardApplet-PayPass.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/jiankeliu5/CardApplet-PayPass.svg) 
  <br>
Javacard Applet for functioning paypass credential. The javacard code included will answer to any reader that requests MasterCard PayPass contactless cards.
- [PayPass, VisaMSD, MMPP applets](https://github.com/SimplyTapp/CardExamples)    ![stars](https://img.shields.io/github/stars/SimplyTapp/CardExamples.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/SimplyTapp/CardExamples.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/SimplyTapp/CardExamples.svg) 
  <br>
This repository contains multiple card applet/card agent examples for using on simplytapp, GPL to v1.2.1.
*probably significant project, needs closer look*
- [JavaCard Wallet](https://github.com/RimGazzeh/JavaCard_Wallet)    ![stars](https://img.shields.io/github/stars/RimGazzeh/JavaCard_Wallet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/RimGazzeh/JavaCard_Wallet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/RimGazzeh/JavaCard_Wallet.svg) 
  <br>
Client/Server application of an electronic wallet (Pin, Storage, control, payment..) 
- [EMVCAP](https://github.com/zoobab/EMVCAP)    ![stars](https://img.shields.io/github/stars/zoobab/EMVCAP.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/zoobab/EMVCAP.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/zoobab/EMVCAP.svg) 
  <br>
This tool emulates an EMV-CAP device, to illustrate the article "Banque en ligne : a la decouverte d'EMV-CAP" published in MISC, issue #56.
- [Mobile banking applet via STK](https://github.com/ubs121/mbank_STK)    ![stars](https://img.shields.io/github/stars/ubs121/mbank_STK.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/ubs121/mbank_STK.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/ubs121/mbank_STK.svg) 
  <br>
Mobile banking solution using SIM Toolkit
- [Loyalty Card Applet](https://github.com/Anthirian/Loyalty-Card)    ![stars](https://img.shields.io/github/stars/Anthirian/Loyalty-Card.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Anthirian/Loyalty-Card.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Anthirian/Loyalty-Card.svg) 
  <br>
Loyalty Card System based on a Java Card featuring a smart card and a terminal. This is a system that allows customers to get credits by shopping in the same store repeatedly. Customers can spend credits to buy products in the store.

### Key and password managers
- [SIM password wallet](https://github.com/bertrandmartel/sim-password-wallet)    ![stars](https://img.shields.io/github/stars/bertrandmartel/sim-password-wallet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/bertrandmartel/sim-password-wallet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/bertrandmartel/sim-password-wallet.svg) 
  <br>
Android application interacting with a JavaCard applet installed on SIM card. 
- [CryptSetup JavaCard Key Manager](https://github.com/WOnder93/cryptsetup-javacard)    ![stars](https://img.shields.io/github/stars/WOnder93/cryptsetup-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/WOnder93/cryptsetup-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/WOnder93/cryptsetup-javacard.svg) 
  <br>
A JavaCard key manager for Cryptsetup. School project, but well documented and extensive 
- [KeepassNFCApplet](https://github.com/JavaCardOS/KeepassNFCApplet)    ![stars](https://img.shields.io/github/stars/JavaCardOS/KeepassNFCApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/JavaCardOS/KeepassNFCApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/JavaCardOS/KeepassNFCApplet.svg) 
  <br>
KeepassNFC is a applet in javacard platform that can protect the secret key of KeePass database.
- [Smartcard crypto applet](https://github.com/nfd/smartcard_crypto_applet)    ![stars](https://img.shields.io/github/stars/nfd/smartcard_crypto_applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/nfd/smartcard_crypto_applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/nfd/smartcard_crypto_applet.svg) 
  <br>
This repository constains the same source code ( -Februrary 2020- ) as the repository listed above ([KeepassNFCApplet](https://github.com/JavaCardOS/KeepassNFCApplet))
- [Java Card OpenPGP Card](https://github.com/jderuiter/javacard-openpgpcard)    ![stars](https://img.shields.io/github/stars/jderuiter/javacard-openpgpcard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/jderuiter/javacard-openpgpcard.svg)  ![numcontributors](https://img.shields.io/github/contributors-anon/jderuiter/javacard-openpgpcard.svg) 
  <br>
This is a Java Card implementation of the OpenPGP smart card specifications.
- [SIM Password Manager](https://github.com/nelenkov/sim-password-manager)    ![stars](https://img.shields.io/github/stars/nelenkov/sim-password-manager.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/nelenkov/sim-password-manager.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/nelenkov/sim-password-manager.svg) 
  <br>
Android password manager app that implements password encryption inside a secure element (SE). Can only run on a device that supports the Open Mobile API (aka SmartCardService). Requires associated Java Card applet to be loaded in the SE (SIM card or embedded SE) in advance.
- [SIM password store](https://github.com/gtomek/sim-password-store)    ![stars](https://img.shields.io/github/stars/gtomek/sim-password-store.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gtomek/sim-password-store.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gtomek/sim-password-store.svg) 
  <br>
SIM toolkit application allowing storage and retrieval of the logins and passwords by SMS in a secure way. It demonstates how to develop a SIM toolkit application that can securely store and manage information on the SIM and communcate with an external world via SMS.
- [TrueCrypt password storage applet](https://github.com/Mercixor/smartcard-truecrypt)    ![stars](https://img.shields.io/github/stars/Mercixor/smartcard-truecrypt.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Mercixor/smartcard-truecrypt.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Mercixor/smartcard-truecrypt.svg) 
  <br>
Using TrueCrypt with a JavaCard. The app stores the user container passwords on a SmartCard and automatically loads the passwords from it to the choosen Containers. The user just remember a four digit PIN to access the SmartCard. Additionally I've implented an password-share methode. The user is able to share passwords with previous stored PublicKeys (RSA 2048 Bit). The private key is generated during the of the card and don't leave this.
- [Password manager](https://github.com/cranixx/Passmg)    ![stars](https://img.shields.io/github/stars/cranixx/Passmg.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/cranixx/Passmg.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/cranixx/Passmg.svg) 
  <br>
Password manager for javacards. Tested on J3A081 card. 

- [JCPasswordManager](https://github.com/bayotop/JCPasswordManager)    ![stars](https://img.shields.io/github/stars/bayotop/JCPasswordManager.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/bayotop/JCPasswordManager.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/bayotop/JCPasswordManager.svg)   <br>
JavaCard Password Manager is a java applet used to create and securely store a strong password used with the steganography app created by. School project, but well documented.

### Digital signing, OpenPGP and mail security
- [Yubico OpenPGP applet](https://github.com/Yubico/ykneo-openpgp)    ![stars](https://img.shields.io/github/stars/Yubico/ykneo-openpgp.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Yubico/ykneo-openpgp.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Yubico/ykneo-openpgp.svg) 
  <br>
This project implement the OpenPGP card functionality used on the YubiKey NEO device. This project is based on the Java Card OpenPGP Card project made by Joeri de Ruiter. The OpenPGP Card applet is typically used through GnuPG.

- [ANSSI-FR SmartPGP applet](https://github.com/ANSSI-FR/SmartPGP)    ![stars](https://img.shields.io/github/stars/ANSSI-FR/SmartPGP.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/ANSSI-FR/SmartPGP.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/ANSSI-FR/SmartPGP.svg) 
  <br>
SmartPGP is a free and open source implementation of the OpenPGP card 3.x specification in JavaCard. The main improvement introduced in OpenPGP card 3.x specification from previous version is the support of elliptic curve cryptography with several existing curves (NIST P-256, NIST P-384, NIST P-521, brainpool p256r1, brainpool p384r1 and brainpool p512r1). The SmartPGP Card applet is typically used through GnuPG.

- [Secure multi-party signatures Myst](https://github.com/OpenCryptoProject/Myst)    ![stars](https://img.shields.io/github/stars/OpenCryptoProject/Myst.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/OpenCryptoProject/Myst.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/OpenCryptoProject/Myst.svg)  <br>
Secure multiparty Schnorr-based EC signatures implemented without proprietary ECPoint API. 

- [SigAnima](https://github.com/tsenger/SigAnima)    ![stars](https://img.shields.io/github/stars/tsenger/SigAnima.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/tsenger/SigAnima.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/tsenger/SigAnima.svg)   <br>
SigAnima is an JavaCard ECDSA signing applet. This applet is based on the [javacardsign applet]((http://sourceforge.net/projects/javacardsign/)) from Wojciech Mostowski . The applet supports the following standardized EC domain parameters: secp224r1, BrainpoolP224r1, secp256r1, BrainpoolP256r1, BrainpoolP320r1. 

- [FluffyPGP applet](https://github.com/JavaCardOS/FluffyPGP-Applet)    ![stars](https://img.shields.io/github/stars/JavaCardOS/FluffyPGP-Applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/JavaCardOS/FluffyPGP-Applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/JavaCardOS/FluffyPGP-Applet.svg)   <br>
The FluffyPGP Applet implements the OpenGPG Card v 2.0.1 specification without using secure channels or Global Platform for portability. GPL3

- [JCOpenPGP](https://sourceforge.net/projects/jcopenpgp) (**SourceForge**) _[last commit 2016]_ <br>
Aim of this project is to create JavaCard applet implementing Functional Specification of the OpenPGP application on ISO Smart Card Operating Systems.

- [Virtual KeyCard applet](https://github.com/eriknellessen/Virtual-Keycard)    ![stars](https://img.shields.io/github/stars/eriknellessen/Virtual-Keycard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/eriknellessen/Virtual-Keycard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/eriknellessen/Virtual-Keycard.svg)   <br>
This software system allows you to decrypt and sign your e-mails with your smartphone instead of using a contactless smartcard. The smartphone communicates with your PC via NFC (as a contactless smartcard would). bachelor's thesis. Warning: This is just proof-of-concept code and should NOT be used in production environments.
  <br>
The repository contains only CardEdge applet but it is not the applet development repository. Main purpose of this repo is to run card simulator inside a smartphone; the applet is just used here.  

### e-Health
- [Electronic health card](https://github.com/gabriellewp/eHealthCard)    ![stars](https://img.shields.io/github/stars/gabriellewp/eHealthCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gabriellewp/eHealthCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gabriellewp/eHealthCard.svg) 
  <br>
eHealth card implementation
- [HealthCard: JavaCard + JML specs](https://sourceforge.net/projects/healthcard/) (**SourceForge**) _[last commit 2014]_ <br>
Prototype of a Java Card application for smart cards and its client application. It was developed with the support of JML (Java Modeling Language) used to formally specify the requirements for developing the HealthCard application in Java Card.
- [EGKfeuer](https://github.com/elnin0815/EGKfeuer)    ![stars](https://img.shields.io/github/stars/elnin0815/EGKfeuer.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/elnin0815/EGKfeuer.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/elnin0815/EGKfeuer.svg) 
  <br>
A project to read the German health insurance card (Elektronische Gesundheitskarte (EGK)), transform the read data to FHIR@copy; ressources, and send them to a choosable FHIR Endpoint. <b>This repository does not contain an applet, it is only client tool.</b>

### NDEF tags
- [JavaCard NDEF Applet](https://github.com/OpenJavaCard/openjavacard-ndef)    ![stars](https://img.shields.io/github/stars/OpenJavaCard/openjavacard-ndef.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/OpenJavaCard/openjavacard-ndef.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/OpenJavaCard/openjavacard-ndef.svg) 
  <br>
This project contains a JavaCard applet acting as an NFC NDEF Tag. It is intended as a convenience applet, allowing storage of an NDEF record on a smartcard to direct the user to a relevant host-device application, such as a smartphone app related to the card or a web page for which the card serves as an authorization token. Data can be preloaded at install time using standards-compliant methods so that this generic applet can be used in different use cases without modification.

- [JavaCard NDEF application](https://github.com/slomo/ndef-javacard)    ![stars](https://img.shields.io/github/stars/slomo/ndef-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/slomo/ndef-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/slomo/ndef-javacard.svg) 
  <br>
JavaCard applet for speaking NDEF. Implementation of the NDEF Nfc-Forum specification for JavaCard 2.2.1. The aim is to support sending of urls to smartphones, and provoiding only compile time writeability.
- [Pico NDEF Applet](https://github.com/MpicoSys/PicoLabel/tree/master/scc_applet/Pico_NDEF/src/org/aispring/javacard/ndef) (**GitHub**) _[last commit 2017]_ 
  <br>


### CryptoCurrency wallets
- [SecureBitcoinWalletJavaCardApplet](https://github.com/acidg/SecureBitcoinWalletJavaCardApplet)    ![stars](https://img.shields.io/github/stars/acidg/SecureBitcoinWalletJavaCardApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/acidg/SecureBitcoinWalletJavaCardApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/acidg/SecureBitcoinWalletJavaCardApplet.svg) 
  <br>
This project is the JavaCard applet for the Secure Bitcoin Wallet App.

- [Ledger Bitcoin Hardware Wallet ](https://github.com/LedgerHQ/ledger-javacard)    ![stars](https://img.shields.io/github/stars/LedgerHQ/ledger-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/LedgerHQ/ledger-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/LedgerHQ/ledger-javacard.svg) 
  <br>
This applet is an implementation of the Ledger Wallet Hardware Wallet specification emulating an NFC Forum Type 4 tag to display the second factor, with specific extensions. 
  <br>
<b>This project is DISCONTINUED according to issues section.</b>. Derived project can be found [here](https://github.com/JavaCardOS/BitcoinWallet).

- [Status KeyCard](https://github.com/status-im/status-keycard)    ![stars](https://img.shields.io/github/stars/status-im/status-keycard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/status-im/status-keycard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/status-im/status-keycard.svg) 
  <br>
Keycard is a an implementation of a BIP-32 HD wallet running on Javacard 3.0.4+. Supports among others key generation, derivation and signing, exporting keys defined in the context of EIP-1581, card duplicationand setting up a NFC NDEF tag.

- [SatoChip Bitcoin applet](https://github.com/Toporin/SatoChipApplet)    ![stars](https://img.shields.io/github/stars/Toporin/SatoChipApplet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Toporin/SatoChipApplet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Toporin/SatoChipApplet.svg) 
  <br>
SatoChip stands for Secure Anonymous Trustless and Open Chip. It is a javacard applet that can be used as a secure hardware wallet running for example on a Yubikey Neo. The SatoChip has full BIP32 supports but due to technical limitations on current javacards, hardened keys (i.e. child keys using indices 2^31 through 2^32-1) are derived much faster than normal keys.
- [Bitcoin wallet](https://github.com/JavaCardOS/BitcoinWallet)    ![stars](https://img.shields.io/github/stars/JavaCardOS/BitcoinWallet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/JavaCardOS/BitcoinWallet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/JavaCardOS/BitcoinWallet.svg) 
  <br>
BitcoinWallet is a Bitcoin Hardware Wallet implementation. It is based on the project Ledger Wallet and can be run on JavaCard platform with JCRE version 3.0.x above.

- [Specter-DIY Bitcoin wallet applets](https://github.com/cryptoadvance/specter-javacard)    ![stars](https://img.shields.io/github/stars/cryptoadvance/specter-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/cryptoadvance/specter-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/cryptoadvance/specter-javacard.svg) 
  <br>
This is a collection of JavaCardOS applets for Specter-DIY secrets storage for use by Specter Bitcoin multisignature wallet.


- [Lamassu card](https://github.com/lamassu/ssu-card/)    ![stars](https://img.shields.io/github/stars/lamassu/ssu-card/.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/lamassu/ssu-card/.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/lamassu/ssu-card/.svg) 
  <br>
Very simple applet used by Lamassu Bitcoin machines to sign transaction with ECDSA. 

### Emulation of some proprietary cards

- [DESFire applet](https://github.com/robsbeat1/Java-Card-Project)    ![stars](https://img.shields.io/github/stars/robsbeat1/Java-Card-Project.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/robsbeat1/Java-Card-Project.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/robsbeat1/Java-Card-Project.svg)  <br>
reimplementation of DESFire card - master's thesis proof of concept

- [JavaCard DESFire emulation](https://github.com/SakaZulu/java-card-desfire-emulation)    ![stars](https://img.shields.io/github/stars/SakaZulu/java-card-desfire-emulation.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/SakaZulu/java-card-desfire-emulation.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/SakaZulu/java-card-desfire-emulation.svg)   <br>
  Emulation of DESFire card

- [MobileEDEPV3](https://github.com/FourTree/EDEPApplet-hengbao/tree/master/MobileEDEPV3) (**GitHub**) _[last commit 2016]_ <br>
JC emulation of some Chinese card?

- [TAG 4 emulation](https://github.com/Tordensky/Tag4)    ![stars](https://img.shields.io/github/stars/Tordensky/Tag4.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Tordensky/Tag4.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Tordensky/Tag4.svg)  <br>
This is an implementation of a TAG 4 for emulation

- [Gauss Key Card](https://github.com/darconeous/gauss-key-card)    ![stars](https://img.shields.io/github/stars/darconeous/gauss-key-card.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/darconeous/gauss-key-card.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/darconeous/gauss-key-card.svg)  <br>
Gauss Key Card is a Java Card applet that implements the minimal working subset of the Tesla Key Card Protocol. Supported Java Card implementations that load this application will be able to be paired with a compatible vehicle and subsequently unlock, start, or lock the vehicle in the same way you would with an official key card.



### Unsorted applications
- [TPM2 applet](https://github.com/mobilesec/tpm2-se-applet)    ![stars](https://img.shields.io/github/stars/mobilesec/tpm2-se-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/mobilesec/tpm2-se-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/mobilesec/tpm2-se-applet.svg) 
  <br>
The project aims to implement basic TPM2 functionalities for smart cards that run Java Card. Currently, this implementation supports :
TPM_Startup, TPM_StartAuthSession, TPM_PCR_Extend, TPM_PCR_Read, TPM_PCR_Reset, TPM_ReadPublic, TPM_GetRandom
- [SmartMeterIQ](https://github.com/adityasawhney/SmartMeterIQ)    ![stars](https://img.shields.io/github/stars/adityasawhney/SmartMeterIQ.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/adityasawhney/SmartMeterIQ.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/adityasawhney/SmartMeterIQ.svg) 
  <br>
The main concern with Smart Meters is the granularity of the data which enables physical and behavioral analysis of the consumer in terms of the brand and make of devices installed and their house hold activities (like when do they wake up, when they are not at home). We propose using Java Card platform as it is a natural fit and is designed to be tamper-proof and secure. In addition, we explore using advanced cryptography techniques such as Zero Knowledge Proof of Knowledge (using Pedersen Commitments) to enable the Utility Supplier to trust the data it is getting from the central device.
- [STKApplet](https://github.com/aliasnash/z-first-applet)    ![stars](https://img.shields.io/github/stars/aliasnash/z-first-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/aliasnash/z-first-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/aliasnash/z-first-applet.svg) 
  <br>
SIM Toolkit Applet
- [javacard-petrol-rationing](https://github.com/alegen/javacard-petrol-rationing) _[REMOVED, last commit 2014]_ <br>
Applet for security of Petrol rationing, including design documents, Radboud University, Hardware Security course, JavaCard project

- [LiteID-SimApp](https://github.com/LiteID/LiteID-SimApp)    ![stars](https://img.shields.io/github/stars/LiteID/LiteID-SimApp.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/LiteID/LiteID-SimApp.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/LiteID/LiteID-SimApp.svg) 
  <br>
A Sim Application client for LiteID, just started, unfinished 
- [LicenseCardApp](https://github.com/FourTree/LicenseCardApp)    ![stars](https://img.shields.io/github/stars/FourTree/LicenseCardApp.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/FourTree/LicenseCardApp.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/FourTree/LicenseCardApp.svg) 
  <br>
applet handling license usage counters and relevant stuff (no documentation)
- [Prototype firmware for the Trusted Execution Module (TEM)](https://github.com/csail/tem_fw)    ![stars](https://img.shields.io/github/stars/csail/tem_fw.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/csail/tem_fw.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/csail/tem_fw.svg) 
  <br>
Prototype firmware for the Trusted Execution Module (TEM). The firmware is a JavaCard applet, and it can turn any capable JavaCard into a TEM.
- [Secure-Storage-and-Erasure](https://github.com/SecurityResearcher/SSE)    ![stars](https://img.shields.io/github/stars/SecurityResearcher/SSE.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/SecurityResearcher/SSE.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/SecurityResearcher/SSE.svg) 
  <br>
This is an open source prototype of Secure Storage and Erasure (SSE) System, including both the JavaCard and host programs. 
- [Mobile-ID USAT applet](https://sourceforge.net/projects/mobile-id-usat-applethealt) _[REMOVED, last commit 2017]_ <br>
The Remarc Mobile-ID USAT applet this is a JavaCard applet with USIM Application Toolkit menu support. 
Basic functions of the Remarc Mobile-ID SAT applet: Authentication function; Signing function; Changing PIN1/PIN2;  Changing PUK; Unblock PIN1/PIN2; View information - in a USAT menu is present a menu item with information of PIN usage

- [E-Voting applet](https://github.com/EVIVoting/EVIV)    ![stars](https://img.shields.io/github/stars/EVIVoting/EVIV.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/EVIVoting/EVIV.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/EVIVoting/EVIV.svg) 
  <br>
EVIV is a highly sound End-to-end Verifiable Internet Voting system, which offers full voter’s mobility and preserves the voter’s privacy from the vote casting PC even if the voter votes from a public PC, such as a PC at a cybercafe ́ or at a public library.
- [UPSC framework](https://github.com/paromix/upsc)    ![stars](https://img.shields.io/github/stars/paromix/upsc.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/paromix/upsc.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/paromix/upsc.svg) 
  <br>
Identity and the security of data transmission is very critical for the success of these e-services. SIM cards might take an important role as a security service provider. They have been used for so many years to preserve the security keys(Ki) and algorithms (A3A8) for authenticating and encrypting the data. Within this project, the international consortium will try to implement a software framework on both the mobile terminal and SIM card that expose the required security functions to popular e-services like Mobile commerce, Financial transactions, Data Encryption, Secure Cloud Storage and Mobile Identity.

## Library code (code which is expected to be used as part of other code)

- [JCMathLib - ECPoint library](https://github.com/OpenCryptoProject/JCMathLib)    ![stars](https://img.shields.io/github/stars/OpenCryptoProject/JCMathLib.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/OpenCryptoProject/JCMathLib.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/OpenCryptoProject/JCMathLib.svg)   <br>
Provides software re-implementation of low-level operations like ECPoint or BigInteger without any use of proprietary API.

- [SHA3, PBKDF2, TWINE, Zorro, ACORN, AEGIS, ASCON, CLOC, and MORUS AEAD ciphers implementation](https://github.com/MiragePV/OptimizedJCAlgs)    ![stars](https://img.shields.io/github/stars/MiragePV/OptimizedJCAlgs.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/MiragePV/OptimizedJCAlgs.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/MiragePV/OptimizedJCAlgs.svg)  <br>
Optimalized implementation of AEGIS, ACORN, ASCON, CLOC, MORUS (Authenticated Encryption), TWINE, Zorro (Block Ciphers), SHA-3 (Message Digest) and PBKDF2 (Key derivation) 

- [LibESE Android verified boot](https://github.com/ADVAN-ELAA-8QM-PRC1/platform-external-libese)    ![stars](https://img.shields.io/github/stars/ADVAN-ELAA-8QM-PRC1/platform-external-libese.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/ADVAN-ELAA-8QM-PRC1/platform-external-libese.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/ADVAN-ELAA-8QM-PRC1/platform-external-libese.svg)   <br>
Minimal transport wrapper for communicating with embedded secure elements on Android

- [JavaCard ChaCha20 implementation](https://github.com/thotheolh/jcChaCha2032)    ![stars](https://img.shields.io/github/stars/thotheolh/jcChaCha2032.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/thotheolh/jcChaCha2032.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/thotheolh/jcChaCha2032.svg)  <br>
32-bit JavaCard based ChaCha20 stream cipher optimized for JavaCard environment with 32-bit Integer support

- [ACORN, AEGIS, ASCON, CLOC, and MORUS AEAD ciphers](https://github.com/palkrajesh/AEonJC)    ![stars](https://img.shields.io/github/stars/palkrajesh/AEonJC.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/palkrajesh/AEonJC.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/palkrajesh/AEonJC.svg)  <br>
Optimalized implementation of 5 selected candidates for authenticated encryption from CAESAR competition

- [Primitives for JavaCard](https://github.com/albertocarp/Primitives_SmartCard)    ![stars](https://img.shields.io/github/stars/albertocarp/Primitives_SmartCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/albertocarp/Primitives_SmartCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/albertocarp/Primitives_SmartCard.svg)   <br>
JBigInteger, JCMath, SHA3, UProve...

- [OpenTLSSec](https://github.com/halemmerich/opentlssc)    ![stars](https://img.shields.io/github/stars/halemmerich/opentlssc.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/halemmerich/opentlssc.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/halemmerich/opentlssc.svg)   <br>
Open source java card library for TLS secured communication under GPL v3. (Seems like significant project)

- [SmartCardTLS](https://github.com/gilb/smart_card_TLS)    ![stars](https://img.shields.io/github/stars/gilb/smart_card_TLS.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gilb/smart_card_TLS.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gilb/smart_card_TLS.svg)   <br>
Client implementation of TLS 1.0 in Java Card (tested with Gmail mobile (lightweight version) with the card G&D SmartCafe 3.2)

- [SRP-6a password-authenticated secure channel](https://github.com/mobilesec/secure-channel-srp6a-applet)    ![stars](https://img.shields.io/github/stars/mobilesec/secure-channel-srp6a-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/mobilesec/secure-channel-srp6a-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/mobilesec/secure-channel-srp6a-applet.svg)  <br>
Java Card applet for SRP-6a password-authenticated secure channel to secure elements/smartcards. This Java Card applet is an implementation of the Secure Remote Password (SRP-6a) password-authenticated secure channel protocol by Wu [1]. In combination with an implementation of an off-card application, such as an Android application using our SRP-6A Android Library, you can establish a secure communication channel that is mutually authenticated with a PIN or password. 

- [EC-SRP-5 password-authenticated secure channel](https://github.com/mobilesec/secure-channel-ec-srp-applet)    ![stars](https://img.shields.io/github/stars/mobilesec/secure-channel-ec-srp-applet.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/mobilesec/secure-channel-ec-srp-applet.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/mobilesec/secure-channel-ec-srp-applet.svg)   <br>
An implementation of the elliptic curve variant of the Secure Remote Password (SRP-5) password-authenticated secure channel protocol from IEEE Std 1363.2-2008 for secure channel to secure elements/smartcards. Utilizes NXP proprietary API.

- [ykneo-curves](https://github.com/Yubico/ykneo-curves)    ![stars](https://img.shields.io/github/stars/Yubico/ykneo-curves.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Yubico/ykneo-curves.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Yubico/ykneo-curves.svg) 
  <br>
This is an applet demonstrating several curves for use in YubiKey NEO.

- [Curve25519 jc implementation](https://github.com/david-oswald/jc_curve25519)    ![stars](https://img.shields.io/github/stars/david-oswald/jc_curve25519.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/david-oswald/jc_curve25519.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/david-oswald/jc_curve25519.svg) 
  <br>
Javacard implementation of Curve25519, JavaCard 3.0.1 or higher (currently developed on J2D081)

- [Java Card Synchronization Framework](https://github.com/jfhren/jc_sync)    ![stars](https://img.shields.io/github/stars/jfhren/jc_sync.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/jfhren/jc_sync.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/jfhren/jc_sync.svg) 
  <br>
The main goal of this framework is to synchronize Java Card 2 applets through the exchange of ciphered APDUs. The current state of the framework is lacking in many ways and requires more works to be fully functional. However it can serve as a proof of concept for the synchronization of applet data in a secure fashion in a pure Java Card 2 setting (i.e.: without tweaking the Java Card VM).

- [Secure Element Evaluation Kit for the Android platform](https://github.com/seek-for-android/pool)    ![stars](https://img.shields.io/github/stars/seek-for-android/pool.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/seek-for-android/pool.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/seek-for-android/pool.svg) 
  <br>
SmartCard API for Android. The SmartCard API adds the necessary modules and API’s to the Android platform. It offers flexible access to secure elements, allowing a secure application solution to make use of any secure form factor, such as a USIM card, a secure µSD card, an embedded secure element.

- [OPACITY auth protocol for JC](https://github.com/shevelevsergey/opacity-for-smartcard)    ![stars](https://img.shields.io/github/stars/shevelevsergey/opacity-for-smartcard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/shevelevsergey/opacity-for-smartcard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/shevelevsergey/opacity-for-smartcard.svg) 
  <br>
This project is designed to authenticate users to the Web service using contactless smart cards. As an authentication protocol was chosen protocol OPACITY. This protocol has been specifically designed for contactless payments and it is officially registered now as an authentication protocol ISO/IEC 24727-6.

- [HMAC and CMAC computation](https://github.com/mll11/jcard/tree/master/TestMAC) (**GitHub**) _[last commit 2014]_ 
  <br>
This Java Card applet support APDUs to test HMAC (SHA-1, SHA-256) and CMAC (AES-128). It uses Java Card 2.2.2.

- [Self-Blindable credentials](https://github.com/credentials/sbcred_javacard)    ![stars](https://img.shields.io/github/stars/credentials/sbcred_javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/credentials/sbcred_javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/credentials/sbcred_javacard.svg) 
  <br>
Java Card implementation of Self-Blindable credentials           

- [TelephonyManager Carrier Privilege granting](https://github.com/sabtmoha/carrier_privilege)    ![stars](https://img.shields.io/github/stars/sabtmoha/carrier_privilege.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/sabtmoha/carrier_privilege.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/sabtmoha/carrier_privilege.svg) 
  <br>
Since Android 5.1, applications are able to communicate with UICC using the class TelephonyManager. However, the concerned functions require a special privilege, that is the carrier privilege. This JavaCard applet grants this privilege to the app whose signature is included inside the applet (the variable SHA256_SIGN)

- [AES, OAEP, SHA2-384 and SHA2-512 JC reimplementation,](https://github.com/petrs/JCSWAlgs)    ![stars](https://img.shields.io/github/stars/petrs/JCSWAlgs.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/petrs/JCSWAlgs.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/petrs/JCSWAlgs.svg) 
  <br>
The Suite of software reimplementations of selected cryptographic algorithms potentially missing on your smartcard with JavaCard platform. Optimized for speed and small memory footprint.

- [Hashchain applet](https://github.com/raminarmanfar/Java-smart-card-cryptographic-protocols)    ![stars](https://img.shields.io/github/stars/raminarmanfar/Java-smart-card-cryptographic-protocols.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/raminarmanfar/Java-smart-card-cryptographic-protocols.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/raminarmanfar/Java-smart-card-cryptographic-protocols.svg) 
  <br>
Implementation of some hash chain

- [ElGamal-based Threshold Scheme for Electronic Elections](https://github.com/CRISES-URV/eVerification-2)    ![stars](https://img.shields.io/github/stars/CRISES-URV/eVerification-2.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/CRISES-URV/eVerification-2.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/CRISES-URV/eVerification-2.svg) 
  <br>
TTP SmartCard-Based ElGamal Cryptosystem Using Threshold Scheme for Electronic Elections. EU Project CRISES group has studied the feasibility of developing ElGamal cryptosystem and Shamir’s secret sharing scheme into JavaCards, whose API gives no support for it. (probably significant applet).

- [Audit TTP SmartCard-Based ElGamal Cryptosystem](https://github.com/AuditURV/Audit)    ![stars](https://img.shields.io/github/stars/AuditURV/Audit.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/AuditURV/Audit.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/AuditURV/Audit.svg) 
  <br>
TTP SmartCard-Based ElGamal Cryptosystem Using Threshold Scheme for Electronic Elections. EU Project.

- [Protocol for Lightweight Authentication of Identity (PLAID)](https://github.com/martinpaljak/AppletPlayground/tree/master/src/plaid804) (**GitHub**) _[last commit 2015]_
  <br>
Protocol for Lightweight Authentication of Identity [PLAID](https://www.humanservices.gov.au/corporate/publications-and-resources/protocol-lightweight-authentication-identity-plaid/plaid-reference-implementation-department-human-services), Australian Government

- [Sec2 cloud security project](https://github.com/RUB-NDS/Sec2)    ![stars](https://img.shields.io/github/stars/RUB-NDS/Sec2.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/RUB-NDS/Sec2.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/RUB-NDS/Sec2.svg) 
  <br>
message-level security must at least be applied to protect those data during and after the storing process. Novel solution for secure data storage in the cloud. It presents a security concept allowing each client to encrypt outgoing data on one’s mobile device and share it among a defined user group while using a seamless service provision. J. Somorovsky, research proof of the concept


## Developer tools 
### Applet build, upload and management
- [Ant-JavaCard](https://github.com/martinpaljak/ant-javacard)    ![stars](https://img.shields.io/github/stars/martinpaljak/ant-javacard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/ant-javacard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/ant-javacard.svg) 
  <br>
Easy to use Ant task for building JavaCard CAP files in a declarative way.

- [GlobalPlatform tools (GPShell)](https://sourceforge.net/p/globalplatform/) (**SourceForge**) _[last commit 2014]_ <br>
The GlobalPlatform card specification provides a standard for the management of the contents on a smart card. Mainly this comprises the installation and the removal of applications. 

- [Sun/Oracle JavaCard SDK binaries](https://github.com/martinpaljak/oracle_javacard_sdks)    ![stars](https://img.shields.io/github/stars/martinpaljak/oracle_javacard_sdks.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/oracle_javacard_sdks.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/oracle_javacard_sdks.svg) 
  <br>
Oracle JavaCard SDK-s for using as a Git submodule for ant-javacard projects.    

- [JCProfiler: JavaCard performance profiler](https://github.com/OpenCryptoProject/JCProfiler)    ![stars](https://img.shields.io/github/stars/OpenCryptoProject/JCProfiler.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/OpenCryptoProject/JCProfiler.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/OpenCryptoProject/JCProfiler.svg)  <br>
Performance profiler for Java Card code. Automatically inserts special execution interruption "traps" into applet code, repeatedly execute target operation and measures time differences between traps. Annotates code with measured time. 

- [OPAL - GlobalPlatform lib](https://bitbucket.org/ssd/opal) (**BitBucket**) _[last commit 2015]_ <br>
A GlobalPlatform Java Library. OPAL implements several authentication, encryption and transfer protocols for
smart card. This tool has been developped by the SSD Research Team (XLIM Labs, University of Limoges, France).

- [CAP File Manipulation](https://bitbucket.org/ssd/capmap-free) (**BitBucket**) _[last commit 2012]_ <br>
A Java Card CAP file parser. 

- [capfile handling tool](https://github.com/martinpaljak/capfile)    ![stars](https://img.shields.io/github/stars/martinpaljak/capfile.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/capfile.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/capfile.svg) 
  <br>
Small library for JavaCard CAP file handling

- [ExpParser](https://sourceforge.net/projects/javacardtools/) (**SourceForge**) _[last commit 2008]_ <br>
Parser for JavaCard export files (\*.exp) - display content in human-readable form

- [Smart-Card-Tool-pyResMan](https://sourceforge.net/projects/pyresman) (**SourceForge**) _[last commit 2017]_ <br>
pyResMan is a free open source smartcard tool for JavaCard and other smart card. It can be used to send APDU(s), execute APDU script(s); It can be used to debug ISO14443 protocol commands and Mifare commands with R502 SPY reader; It can also be used to manage resource of GP card. It is based on pyScard and GlobalPlatform open source projects.

- [JavaCard debugging toolkit](https://github.com/omarbenhamid/jcdebug)    ![stars](https://img.shields.io/github/stars/omarbenhamid/jcdebug.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/omarbenhamid/jcdebug.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/omarbenhamid/jcdebug.svg)  <br>
JCDebug is a JavaCard debugging toolkit. A simple command line tools that instruments JavaCard applets to offer debugging and inspection services directly on the target plateform. Potentially significant project, needs closer look

- [Card2Jar](https://github.com/benjholla/Card2Jar)    ![stars](https://img.shields.io/github/stars/benjholla/Card2Jar.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/benjholla/Card2Jar.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/benjholla/Card2Jar.svg) 
  <br>
A converter for converting Java Card CAP files to JAR files. Currently this is just a handy wrapper around the JCDK3.0.4_ClassicEdition SDK distribution's normalizer utility. It is subject to all the same caveats as the normalizer.bat interface (must have export EXP file and must be a Java Card 2.2.2 or lower applet).

- ["Allow all" ARA-M](https://github.com/seek-for-android/allow-all-ara)    ![stars](https://img.shields.io/github/stars/seek-for-android/allow-all-ara.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/seek-for-android/allow-all-ara.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/seek-for-android/allow-all-ara.svg) 
  <br>
GlobalPlatform dummy ARA applet to grant full access



### Card capabilities testing (algorithms support, performance, security issues)
- [JCAlgTest](https://github.com/crocs-muni/JCAlgTest)    ![stars](https://img.shields.io/github/stars/crocs-muni/JCAlgTest.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/crocs-muni/JCAlgTest.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/crocs-muni/JCAlgTest.svg) 
  <br>
Automated testing tool for algorithms from JavaCard API supported by particular smart card. Performance testing of almost all available methods. The results for more than 60+ cards available at https://jcalgtest.org. 

- [ECTester](https://github.com/petrs/ECTester)    ![stars](https://img.shields.io/github/stars/petrs/ECTester.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/petrs/ECTester.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/petrs/ECTester.svg)  <br>
Tester of Eliptic curves support and behavior (TYPE_EC_FP and TYPE_EC_F2M) on smartcards with JavaCard platform. 

- [PicoLabel AES/DES/RSA speed tester](https://github.com/MpicoSys/PicoLabel)    ![stars](https://img.shields.io/github/stars/MpicoSys/PicoLabel.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/MpicoSys/PicoLabel.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/MpicoSys/PicoLabel.svg) 
  <br>
Performance testing for DES/AES/RSA 

- [Performance Benchmark Applet for Javacard/smartcard](https://github.com/dmdclab/smartcard-benchmark)    ![stars](https://img.shields.io/github/stars/dmdclab/smartcard-benchmark.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/dmdclab/smartcard-benchmark.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/dmdclab/smartcard-benchmark.svg) 
  <br>
Performance testing for various algorithms, similar as JCAlgTest (needs investigation about difference to JCAlgTest) 

- [HandlerTest](https://github.com/LudovicRousseau/HandlerTest)    ![stars](https://img.shields.io/github/stars/LudovicRousseau/HandlerTest.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/LudovicRousseau/HandlerTest.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/LudovicRousseau/HandlerTest.svg) 
  <br>
L. Rousseau PCSCLite reader test : This program send commands to a card through the reader.           

- [Memory profiling tool](https://github.com/maxashwin/JavaCard/tree/master/Wkg_MemoryMeasurementScript) (**GitHub**) _[last commit 2017]_  <br>
Locates an applet constructor and places free memory measurements hooks before and after every allocation command. Summarizes required memory for every allocated object (RAM, EEPROM)   

- [Three applets testing on-card defenses against maliciously modified applets](https://github.com/maxashwin/JavaCard)    ![stars](https://img.shields.io/github/stars/maxashwin/JavaCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/maxashwin/JavaCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/maxashwin/JavaCard.svg)  <br>
Abuse of Shareable interface, type confusion after use of Shareable interface, direct modification of CAP file

### Formal verification and code transformation tools
- [VeriFast](https://github.com/verifast/verifast)    ![stars](https://img.shields.io/github/stars/verifast/verifast.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/verifast/verifast.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/verifast/verifast.svg) 
  <br>
VeriFast is a research prototype of a tool for modular formal verification of correctness properties of single-threaded and multithreaded C and Java programs annotated with preconditions and postconditions written in separation logic. Examples on JavaCard applets: EPurse, EidCard. Very active project.

- [Joana IFC analysis framework](https://github.com/joana-team/joana)    ![stars](https://img.shields.io/github/stars/joana-team/joana.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/joana-team/joana.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/joana-team/joana.svg) 
  <br>
Joana is a static analysis tool that can be used for information flow control (IFC) of Java bytecode. IFC allows to verify the INTEGRITY (no attacker can temper with sensitive information) or CONFIDENTIALITY (no attacker can infer secret information from public outputs) of a Java program. System dependence graphs (SDG) form the basic technology for our analyses. Examples also on JavaCard applets. Very active project.

- [CesTa project](https://github.com/formanek/CesTa)    ![stars](https://img.shields.io/github/stars/formanek/CesTa.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/formanek/CesTa.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/formanek/CesTa.svg)  <br>
Security hardening (duplicate variables, constant branches, transaction detection...) for JavaCard applets based on ANTLR automatic code transformations

- [The KeY project](https://github.com/cirosantilli/key-java-formal-verification-fork)    ![stars](https://img.shields.io/github/stars/cirosantilli/key-java-formal-verification-fork.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/cirosantilli/key-java-formal-verification-fork.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/cirosantilli/key-java-formal-verification-fork.svg) 
  <br>
examples on JavaCard, this github repo is not official (https://www.key-project.org) 

- [KeYmaera 3](https://github.com/LS-Lab/KeYmaera-release)    ![stars](https://img.shields.io/github/stars/LS-Lab/KeYmaera-release.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/LS-Lab/KeYmaera-release.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/LS-Lab/KeYmaera-release.svg) 
  <br>
KeYmaera 3: A Hybrid Theorem Prover for Hybrid Systems with examples on JavaCard applets (among others). http://symbolaris.com/info/KeYmaera.html

## JavaCard simulators and emulators

- [JCardSim:](https://github.com/licel/jcardsim)    ![stars](https://img.shields.io/github/stars/licel/jcardsim.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/licel/jcardsim.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/licel/jcardsim.svg) 
  <br>
Capable JavaCard simulator implemented atop of BouncyCastle. Very good for unit testing, quick prototyping and educational purposes. Allows for multiple simulated cards in parallel.   

- [vJCRE ](https://github.com/martinpaljak/vJCRE)    ![stars](https://img.shields.io/github/stars/martinpaljak/vJCRE.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/vJCRE.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/vJCRE.svg) 
  <br>
vJCRE is a virtual Java Card Runtime Environment

- [Secure Element Emulator](https://github.com/mobilesec/secure-element-emulator)    ![stars](https://img.shields.io/github/stars/mobilesec/secure-element-emulator.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/mobilesec/secure-element-emulator.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/mobilesec/secure-element-emulator.svg) 
  <br>
This project aims at emulating a secure element environment for debugging and rapid-prototyping of secure element applets. It is a fork of the open-source Java Card simulator jCardSim (original source code available here). Within our research we added extensions to emulate an application life-cycle that matches the life-cycle of applications on real smartcard chips.

- [Java Card Simulator via Node.js](https://github.com/adamnoakes/javacard-simulator)    ![stars](https://img.shields.io/github/stars/adamnoakes/javacard-simulator.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/adamnoakes/javacard-simulator.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/adamnoakes/javacard-simulator.svg) 
  <br>
This project provides an implementation of a Java Card Runtime Enviornment (JCRE) produced using Node.js which is capable of creating virtual smart card devices and sending APDU commands to the smart card devices for execution via a RESTful API. The project also provides a web-based interface for interacting with the JCRE. 

- [PythonCard:](https://bitbucket.org/benallard/pythoncard/) (**BitBucket**) _[last commit 2013] <br>
JavaCard API simulated in Python environment. The goal is to provide a classic 3.0.1 version, while maintaining compatiblity with earlier version like 2.1.2.

- [JCardMock](https://github.com/christianhujer/jcardmock)    ![stars](https://img.shields.io/github/stars/christianhujer/jcardmock.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/christianhujer/jcardmock.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/christianhujer/jcardmock.svg) 
  <br>
Mock implementation of the Java Card API 3.0.4 in order to test Java Card applet code without a card or simulator. It runs the Java Card API 3.0.4 in a normal Java Virtual Machine. The purpose is to allow for unit tests for Java Card applets with normal test frameworks like JUnit or TestNG in a normal Java Virtual Machine (unfinished)

- [CAPRunner](https://github.com/benallard/caprunner)    ![stars](https://img.shields.io/github/stars/benallard/caprunner.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/benallard/caprunner.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/benallard/caprunner.svg) 
  <br>
CAPRunner is a javacard bytecode emulator that is able to execute CAP files. It also comes with an handy runcap.py that bind them together and allow you to send some APDUs to a CAP file (without the need for a smartcard).
developement on BitBucket

- [FreeJCVM](https://sourceforge.net/projects/freejcvm) (**SourceForge**) _[last commit 2015]_ <br>
Free javacard vm implementation for AVR MCU.

- [openjcvm](https://sourceforge.net/projects/openjcvm/) (**SourceForge**) _[last commit 2015]_ <br>
A open source java card virtual machine implementation. And also some part of the VM code can be used as part of kinds of tools such as javacard bytecode disassembler.

## Learning (various school projects, simple hello world applets, etc)

- [AppletPlayground](https://github.com/martinpaljak/AppletPlayground)    ![stars](https://img.shields.io/github/stars/martinpaljak/AppletPlayground.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/martinpaljak/AppletPlayground.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/martinpaljak/AppletPlayground.svg) 
  <br>
AppletPlayground is an educational repository for getting to know JavaCard development by learning from existing open source software. It includes various open source applets from the internet, bundled into ready to use package. Everything you need to edit, compile and load the applets to real JavaCard-s or test with an emulator.

- [Simple AES encrypt/decrypt](https://github.com/pauksk/nrf6310-pca10007)    ![stars](https://img.shields.io/github/stars/pauksk/nrf6310-pca10007.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/pauksk/nrf6310-pca10007.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/pauksk/nrf6310-pca10007.svg) 
  <br>
Master thesis - protection of wireless networks in smart homes using secure hardware

- [SecureChat](https://github.com/sharedbits/JavaChat)    ![stars](https://img.shields.io/github/stars/sharedbits/JavaChat.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/sharedbits/JavaChat.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/sharedbits/JavaChat.svg) 
  <br>
Secure chat client/server application 
- [JavaCard Demo](https://github.com/leowenyang/JCDemo)    ![stars](https://img.shields.io/github/stars/leowenyang/JCDemo.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/leowenyang/JCDemo.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/leowenyang/JCDemo.svg) 
  <br>
Examples of various JavaCard functionalities (as separate applets)
- [MultiCard](https://github.com/Dragonexodus/MultiCard)    ![stars](https://img.shields.io/github/stars/Dragonexodus/MultiCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Dragonexodus/MultiCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Dragonexodus/MultiCard.svg) 
  <br>
An school of applied science smartcard project. This project contains offCard and onCard components with various aspects of javacard development.
- [Hotel Buddy](https://github.com/prayzzz/smartcard)    ![stars](https://img.shields.io/github/stars/prayzzz/smartcard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/prayzzz/smartcard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/prayzzz/smartcard.svg) 
  <br>
Various JavaCard projects created for a lecture-series. Some PKI.           
- [Simple calculator on JavaCard](https://github.com/steff7/javacard-calculator)    ![stars](https://img.shields.io/github/stars/steff7/javacard-calculator.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/steff7/javacard-calculator.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/steff7/javacard-calculator.svg) 
  <br>
- [Maze solver on JavaCard](https://github.com/henryhoo/javacard_maze)    ![stars](https://img.shields.io/github/stars/henryhoo/javacard_maze.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/henryhoo/javacard_maze.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/henryhoo/javacard_maze.svg) 
  <br>
A small experiment on memory about java card using jcopv2.2.1           
- [TraninCard applet](https://github.com/TBoonX/sc_traincard)    ![stars](https://img.shields.io/github/stars/TBoonX/sc_traincard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/TBoonX/sc_traincard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/TBoonX/sc_traincard.svg) 
  <br>
SmartCard - Traincard, No Pain No Gain Project at HTWk Leipzig. 
- [Client applet for CDAX Crypto](https://github.com/marlonbaeten/cdax-crypto-cpp/tree/f3329fcdf348b6a3ddc95b993dbc97b806aea335/applet) (**GitHub**) _[last commit 2014]_  <br>
The applet with implementation of basic cryptographic functions offered by JavaCard API

- [CryptedBankCard](https://github.com/Herve-M/UQAM-EMB7015)    ![stars](https://img.shields.io/github/stars/Herve-M/UQAM-EMB7015.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/Herve-M/UQAM-EMB7015.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/Herve-M/UQAM-EMB7015.svg) 
  <br>
The school project with goal to develop a secure banking card.
- [Smartcard-Offline-Lock](https://github.com/GeorgesAlkhouri/Smartcard-Offline-Lock)    ![stars](https://img.shields.io/github/stars/GeorgesAlkhouri/Smartcard-Offline-Lock.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/GeorgesAlkhouri/Smartcard-Offline-Lock.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/GeorgesAlkhouri/Smartcard-Offline-Lock.svg) 
  <br>
Electronic door locks are commonly used at hotels, exhibitions or public facilities. An offline-operational solution for an electronic lock is required. This is a study project, so please don't expect to much comfort (single DES key used).
- [Learning applets repository](https://github.com/gracebear/JavaCard)    ![stars](https://img.shields.io/github/stars/gracebear/JavaCard.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/gracebear/JavaCard.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/gracebear/JavaCard.svg) 
  <br>
This repository will provide you with javacard applet for absolutely beginners\ javacard development tools\ javacard open source applets. 

## Unsorted

### (needs further inspection)
- [Corba](https://github.com/lbarbisan/corba)    ![stars](https://img.shields.io/github/stars/lbarbisan/corba.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/lbarbisan/corba.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/lbarbisan/corba.svg) 
  <br>
old project, CryptoFlex uploader?
- [unifei-smart-cards](https://github.com/tiagorg/unifei-smart-cards)    ![stars](https://img.shields.io/github/stars/tiagorg/unifei-smart-cards.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/tiagorg/unifei-smart-cards.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/tiagorg/unifei-smart-cards.svg) 
  <br>
Material developed in UNIFEI-MG research about Smart Cards          

- [Teleport secret Bitcoin applet](https://github.com/EyeOfPython/teleport_secret)    ![stars](https://img.shields.io/github/stars/EyeOfPython/teleport_secret.svg?style=social) ![lastcommit](https://img.shields.io/github/last-commit/EyeOfPython/teleport_secret.svg) ![numcontributors](https://img.shields.io/github/contributors-anon/EyeOfPython/teleport_secret.svg) 
  <br>
Basic applet implementing simple Bitcoin signature functionality.      



### Methodology
- DONE (2018-02-22) Search all GitHub repositories with "javacard.framework.Applet" string. For repeated searches, sort by _Sort:Recently indexed_ to get new projects first
- DONE (2017-04-15) Analyze applets included in AppletPlayground
- DONE (2017-04-15) Search all SourceForge repositories with "javacard" string
- DONE (2017-04-16) Sort applets into categories according to basic topic
- Inspect other repositories of relevant developers
- Analyze status and maturity of included projects (subsection 'needs further inspection')
