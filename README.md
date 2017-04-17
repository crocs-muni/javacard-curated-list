# javacard-currated-list
Currated list of open-source JavaCard applets and related applications for cryptographic smartcards 

# Currated list of JavaCard applications
The goal is to provide currated catalog of all open-source JavaCard applets and applications relevant for JavaCard development. The initail list was compiled by complete search for all GitHub and SourceForge repositories for *'javacard.framework'* keyword.

You are encouraged to contribute - please read the [contribution guidelines]() first, then create pull request. 

### Methodology
- DONE (2017-04-15) Search all GitHub repositories with "javacard.framework.Applet" string
- DONE (2017-04-15) Analyze applets included in AppletPlayground
- DONE (2017-04-15) Search all SourceForge repositories with "javacard" string
- DONE (2017-04-16) Sort applets into categories according to basic topic
- Inspect other repositories of relevant developers
- Analyze status and maturity of included projects (needs further inspection section)

### Format and notation
> **Project name _[status : activity]_** <br> Short description, often taken from a project readme.md
> - **status**: subjective state of project: *mature* (well developed), *aspiring* (promising, but not yet mature), *playground* (initial stages, just playing/learning), *needs inspection* (not analysed yet)
> - **activity**: *active* (at last one commit during the last year), *last commit* (date of last commit for less active / inactive projects)

## Applets (standalone applications)
### Electronic passports and citizen ID
 
- [JMRTD: Machine Readable Travel Documents](https://sourceforge.net/projects/jmrtd/) _[**mature project** : active]_ <br> 
Free implementation of the MRTD (Machine Readable Travel Documents) standards as set by ICAO used in the ePassport. Consists of an API for card terminal software and a Java Card applet.
- [EstEID compatible JavaCard applets](https://github.com/martinpaljak/esteid-applets) _[**mature project** : active]_ <br> 
Various JavaCard applets compatible* to EstEID chip protocol: FakeEstEID, MyEstEID

#### (needs further inspection)

- [JMRTD applet without EAC support](https://github.com/walterschell/jmrtd-noeac) _[**needs inspection** : last commit 2014]_ <br> 
Fork of JMRTD electronic passport applet without EAC support. The target device for this project is G+D SmartCafe Expert 144k Dual.
- [SIC eID card](https://github.com/nversbra/SIC) _[**just started** : unknown]_ <br> 
A privacy-friendly alternative for the Belgian eID card. The project aims to improve security of Belgian ID holders by limiting the current extensive exposure of their profiles. To do so, we build an alternative ID card which limits service providers to strickly necessary ID holder profile information. 
- [FedICT Quick-Key Toolset](https://github.com/Twuk/eid-quick-key-toolset/tree/master/eid-quick-key-toolset) _[**needs inspection** : last commit 2011]_ <br> 
EidCard project

### Authentication and access control

- [YubiKey NEO App: OATH](https://github.com/Yubico/ykneo-oath) _[**mature project** : active]_ <br>
This project implement the HOTP/TOTP card functionality used on the YubiKey NEO device that is sold by Yubico. Its primary use is to use the YubiKey NEO to generate OATH HOTP/TOTP one-time-passwords. GPLv3+
- [SSH support applet](https://github.com/scs/uclinux/blob/eb0cf9617bd22b69ad625575a95cf4fa2c140d55/user/ssh/scard/Ssh.java) _[**mature** : inactive]_ <br>
Old, but widely copied applet perforimg RSA decrypt on card and used by SSH client 
- [MuscleApplet](https://github.com/martinpaljak/MuscleApplet) _[**mature, outdated** : last commit 2005]_ <br>
Significant, but outdated applet used for OpenSC. Supreseeded by PKCS#15 and PIV standards.

#### (needs further inspection)
- [PKI applet](https://github.com/rakeb/PKIApplet) _[**needs inspection** : last commit 2016]_ <br>
extensive PKI applet
- [ISOApplet PKI](https://github.com/philipWendland/IsoApplet) _[**needs inspection** : active]_ <br>
A Java Card PKI Applet aiming to be ISO 7816 compliant. The Applet is capable of saving a PKCS#15 file structure and performing PKI related operations using the private key, such as signing or decrypting. Private keys can be generated directly on the smartcard or imported from the host computer. The import of private keys is disabled in the default security configuration. 
mature project
- [Generic Identity Device Specification Applet](https://github.com/vletoux/GidsApplet) _[**needs inspection** : active]_ <br>
Generic Identity Device Specification (GIDS) smart card is the only PKI smart card whose driver is integrated on each Windows since Windows 7 SP1 and which can be used read and write. No Windows driver installation is required and this card can be used instantly.
- [PIV CryptonitApplet](https://github.com/mbrossard/cryptonit-applet) _[**needs inspection** : active]_ <br>
Personal Identity Verification (PIV) applet
- [PKCS#15 applet](https://github.com/lupascualex/p15) _[**needs inspection** : last commit 2015]_ <br>
Implementation of card according to RSA PKCS#15 specification
- [Ledger U2F Applet](https://github.com/LedgerHQ/ledger-u2f-javacard) _[**needs inspection** : last commit 2016]_ <br> 
This applet is a Java Card implementation of the FIDO Alliance U2F standard. It uses no proprietary vendor API and is freely available on Ledger Unplugged and for a small fee on other Fidesmo devices through Fidesmo store.
- [U2FToken](https://github.com/fightyz/U2FToken) _[**needs inspection** : last commit 2016]_ <br>
An U2F Token implementation based on Ledger U2F Applet which cannot pass the NFC self-conformance test in the phase: "U2F_REGISTER, Short APDU, Change BlockSize", as it doesn't handle the situation which Le(BlockSize) is not 256 bytes.)
- [Yubikey Neo One Time Pad](https://github.com/cayennegraphics/Yubisec) _[**needs inspection** : last commit 2015]_ <br>
This project has been submitted to the YubiKing 2015 Hackathon. Yubisec is an implementation of a One Time Pad for secure communication between two Android phones using keys stored and generated on YubiKey Neo tokens.
- [Biometric Authentication](https://github.com/albertocarp/BiometricAuthentification) _[**needs inspection** : last commit 2016]_ <br>
Fuzzy extractor to authenticate with biometric data
- [OneCard](https://github.com/SharkyHarky/OneCard) _[**needs inspection** : last commit 2015]_ <br>
radiius.com Radiius applet, applet seems to be just starting to implement required functionality as per specification 
- [OTP client and server applets](https://github.com/gelvaos/otp) _[**needs inspection** : last commit 2012]_ <br>
This is proof-of-concept implementation of One Time password JavaCard STK applet and authentication server. Load JavaCard applet to SIM card and use STK menu.
- [CoolKey Applet](https://github.com/NabilNoaman/CoolkeyApplet) _[**needs inspection** : last commit 2010]_ <br>
CoolKey Applet with the idea of making it a fresh JavaCard 2.2.2 applet meant to be revival of CardEdge Muscle card applet.
- [Trusted Identity Module](https://github.com/Orange-OpenSource/TIM) _[**needs inspection** : last commit 2015]_ <br>
A local smartphone module acting as an OpenID Connect Server proxy and delivers trusted tokens to installed native applications. The TIM improves the user experience with single sign on, security and privacy enhancement. The Trusted Identity Module project is a set of four projects: an Android service (tim_service), a JAVA Card Service (TimCardlet), a modified OpenID Connect Server (phpOpTim) and a basic Android TIM-Client app enabling to test the TIM services (HelloTim). The OIDC-TIM server is based on an open source implementation of OpenID Connect in PHP by Nomura Research Institute, Ltd. Seems to be extensive and well documented. 

### Payments and loyalty
#### (needs further inspection)

### Key and password managers
#### (needs further inspection)

### Digital signing, OpenPGP and mail security
#### (needs further inspection)

### e-Health
#### (needs further inspection)

### NDEF tags
#### (needs further inspection)

### Bitcoin wallets
#### (needs further inspection)

### Emulation of some proprietary cards
#### (needs further inspection)

### Unsorted applications
#### (needs further inspection)

## Library code (code which is expected to be used as part of other code)

## Developer tools 
### Applet build, upload and management
#### (needs further inspection)

### Card capabilities testing (algorithms support, performance)
#### (needs further inspection)

### Formal verification tools
#### (needs further inspection)

### Applet code transformations
#### (needs further inspection)

## JavaCard simulators and emulators

## Learning (various school projects, simple hello world applets....)

## Unsorted
