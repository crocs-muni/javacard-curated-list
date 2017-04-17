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
- [OpenEMV](https://sourceforge.net/projects/javacard-openemv-applet (https://github.com/JavaCardOS/OpenEMV)) _[**mature** : last commit 2016]_ <br>
The OpenEMV is a Java Card implementation of the EMV standard. This applet is a very basic EMV applet supporting only SDA and plaintext offline PIN.It does not offer personalisation support - everything is hard-coded.
seems like mature project

#### (needs further inspection)
- [EMV-TOOLS](https://github.com/gabessolo/EMV-TOOLS) _[**needs inspection** : active]_ <br>
Collection of source code for many EMV-related projects: SimpleEMVApplet, java-card-openEMV, card-spy, emvdemoBook, emv-bertlv...
- [Simple Wallet](https://github.com/fitpay/javacard-simple-wallet) _[**needs inspection** : last commit 2015]_ <br>
This is a simple wallet simply maintains a running account balance allowing you to credit, debit, or review the current balance of the wallet. This is simply for pure demonstration on various wearable technologies where a javacard secure element is available.
- [E-Purse](https://github.com/aredev/hw-epurse) _[**needs inspection** : active]_ <br>
just started, unifinished (04/2017)
- [AppSecure](https://github.com/deepakprabhakara/appsecure) _[**needs inspection** : last commit 2010]_ <br>
AppSecure uses Gemalto's eGate smart card framework to enforce pay as you go services for Win32 applications. This entry made it to the pre-final round of Gemalto's eGate Open Contest in 2004           
- [PBOC3Applet](https://github.com/jiankeliu5/PBOC3Applet) _[**needs inspection** : last commit 2014]_ <br>
payment applet
- [PayPass applet](https://github.com/jiankeliu5/CardApplet-PayPass) _[**needs inspection** : last commit 2014]_ <br>
Javacard Applet for functioning paypass credential. The javacard code included will answer to any reader that requests MasterCard PayPass contactless cards.
- [PayPass, VisaMSD, MMPP applets](https://github.com/SimplyTapp/CardExamples) _[**needs inspection** : last commit 2014]_ <br>
This repository contains multiple card applet/card agent examples for using on simplytapp, GPL to v1.2.1.
*probably significant project, needs closer look*
- [JavaCard Wallet](https://github.com/RimGazzeh/JavaCard_Wallet) _[**needs inspection** : last commit 2016]_ <br>
Client/Server application of an electronic wallet (Pin, Storage, control, payment..) 
- [EMVCAP](https://github.com/zoobab/EMVCAP) _[**needs inspection** : last commit 2017]_ <br>
This tool emulates an EMV-CAP device, to illustrate the article "Banque en ligne : a la decouverte d'EMV-CAP" published in MISC, issue #56.
- [Mobile banking applet via STK](https://github.com/ubs121/mbank_STK) _[**needs inspection** : last commit 2016]_ <br>
Mobile banking solution using SIM Toolkit
- [Loyalty Card Applet](https://github.com/Anthirian/Loyalty-Card) _[**needs inspection** : last commit 2013]_ <br>
Loyalty Card System based on a Java Card featuring a smart card and a terminal. This is a system that allows customers to get credits by shopping in the same store repeatedly. Customers can spend credits to buy products in the store.

### Key and password managers
#### (needs further inspection)
- [CryptSetup JavaCard Key Manager](https://github.com/WOnder93/cryptsetup-javacard) _[**needs inspection** : last commit 2016]_ <br>
A JavaCard key manager for Cryptsetup. School project, but well documented and extensive 
- [SIM Password Manager](https://github.com/nelenkov/sim-password-manager) _[**needs inspection** : last commit 2013]_ <br>
Android password manager app that implements password encryption inside a secure element (SE). Can only run on a device that supports the Open Mobile API (aka SmartCardService). Requires associated Java Card applet to be loaded in the SE (SIM card or embedded SE) in advance.
- [SIM password store](https://github.com/gtomek/sim-password-store) _[**needs inspection** : last commit 2014]_ <br>
SIM toolkit application allowing storage and retrieval of the logins and passwords by SMS in a secure way. It demonstates how to develop a SIM toolkit application that can securely store and manage information on the SIM and communcate with an external world via SMS.
- [TrueCrypt password storage applet](https://github.com/Mercixor/smartcard-truecrypt) _[**needs inspection** : last commit 2015]_ <br>
Using TrueCrypt with a JavaCard. The app stores the user container passwords on a SmartCard and automatically loads the passwords from it to the choosen Containers. The user just remember a four digit PIN to access the SmartCard. Additionally I've implented an password-share methode. The user is able to share passwords with previous stored PublicKeys (RSA 2048 Bit). The private key is generated during the of the card and don't leave this.
- [Password manager](https://github.com/cranixx/Passmg) _[**needs inspection** : last commit 2016]_ <br>
Password manager for javacards. Tested on J3A081 card. 
- [JCPasswordManager](https://github.com/bayotop/JCPasswordManager) _[**needs inspection** : last commit 2016]_ <br>
JavaCard Password Manager is a java applet used to create and securely store a strong password used with the steganography app created by arunenigma (https://github.com/arunenigma/Steganography-Java-GUI). School project, but well documented.

### Digital signing, OpenPGP and mail security
- [Yubico OpenPGP applet](https://github.com/Yubico/ykneo-openpgp) _[**mature** : active]_ <br>
This project implement the OpenPGP card functionality used on the YubiKey NEO device. This project is based on the Java Card OpenPGP Card project made by Joeri de Ruiter. The OpenPGP Card applet is typically used through GnuPG.

#### (needs further inspection)
- [SigAnima](https://github.com/tsenger/SigAnima) _[**needs inspection** : last commit 2014]_ <br>
SigAnima is an JavaCard ECDSA signing applet. This applet is based on the [javacardsign applet]((http://sourceforge.net/projects/javacardsign/)) from Wojciech Mostowski . The applet supports the following standardized EC domain parameters: secp224r1, BrainpoolP224r1, secp256r1, BrainpoolP256r1, BrainpoolP320r1. Seems like interesting applet
- [FluffyPGP applet](https://github.com/JavaCardOS/FluffyPGP-Applet) _[**needs inspection** : last commit 2016]_ <br>
The FluffyPGP Applet implements the OpenGPG Card v 2.0.1 specification without using secure channels or Global Platform for portability. GPL3
- [JCOpenPGP](https://sourceforge.net/projects/jcopenpgp) _[**needs inspection** : last commit 2016]_ <br>
Aim of this project is to create JavaCard applet implementing Functional Specification of the OpenPGP application on ISO Smart Card Operating Systems.
- [Virtual KeyCard applet](https://github.com/eriknellessen/Virtual-Keycard) _[**needs inspection** : last commit 2015]_ <br>
This software system allows you to decrypt and sign your e-mails with your smartphone instead of using a contactless smartcard. The smartphone communicates with your PC via NFC (as a contactless smartcard would). bachelor's thesis. Warning: This is just proof-of-concept code and should NOT be used in production environments.



### e-Health
#### (needs further inspection)
- [Electronic health card](https://github.com/gabriellewp/eHealthCard) _[**needs inspection** : last commit 2016]_ <br>
eHealth card implementation
- [HealthCard: JavaCard + JML specs](https://sourceforge.net/projects/healthcard/) _[**needs inspection** : last commit 2014]_ <br>
Prototype of a Java Card application for smart cards and its client application. It was developed with the support of JML (Java Modeling Language) used to formally specify the requirements for developing the HealthCard application in Java Card.
- [EGKfeuer](https://github.com/elnin0815/EGKfeuer) _[**needs inspection** : last commit 2016]_ <br>
A project to read the German health insurance card (Elektronische Gesundheitskarte (EGK)), transform the read data to FHIR@copy; ressources, and send them to a choosable FHIR Endpoint

### NDEF tags
- [JavaCard NDEF Applet](https://github.com/promovicz/javacard-ndef) _[**mature** : last commit 2015]_ <br>
This project contains a JavaCard applet acting as an NFC NDEF Tag. It is intended as a convenience applet, allowing storage of an NDEF record on a smartcard to direct the user to a relevant host-device application, such as a smartphone app related to the card or a web page for which the card serves as an authorization token. Data can be preloaded at install time using standards-compliant methods so that this generic applet can be used in different use cases without modification.

#### (needs further inspection)
- [JavaCard NDEF application](https://github.com/slomo/ndef-javacard) _[**needs inspection** : last commit 2014]_ <br>
JavaCard applet for speaking NDEF. Implementation of the NDEF Nfc-Forum specification for JavaCard 2.2.1. The aim is to support sending of urls to smartphones, and provoiding only compile time writeability.
- [Pico NDEF Applet](https://github.com/MpicoSys/PicoLabel/tree/master/scc_applet/Pico_NDEF/src/org/aispring/javacard/ndef) _[**needs inspection** : last commit 2017]_ <br>


### Bitcoin wallets
#### (needs further inspection)
- [SecureBitcoinWalletJavaCardApplet](https://github.com/acidg/SecureBitcoinWalletJavaCardApplet) _[**needs inspection** : last commit 2015]_ <br>
This project is the JavaCard applet for the Secure Bitcoin Wallet App.
- [Ledger Bitcoin Hardware Wallet ](https://github.com/LedgerHQ/ledger-javacard) _[**needs inspection** : last commit 2016]_ <br>
This applet is an implementation of the Ledger Wallet Hardware Wallet specification emulating an NFC Forum Type 4 tag to display the second factor, with specific extensions. 
- [SatoChip Bitcoin applet](https://github.com/Toporin/SatoChipApplet) _[**needs inspection** : last commit 2015]_ <br>
SatoChip stands for Secure Anonymous Trustless and Open Chip. It is a javacard applet that can be used as a secure hardware wallet running for example on a Yubikey Neo. The SatoChip has full BIP32 supports but due to technical limitations on current javacards, hardened keys (i.e. child keys using indices 2^31 through 2^32-1) are derived much faster than normal keys.
- [Bitcoin wallet](https://github.com/JavaCardOS/BitcoinWallet) _[**needs inspection** : last commit 2016]_ <br>
BitcoinWallet is a Bitcoin Hardware Wallet implementation. It is based on the project Ledger Wallet and can be run on JavaCard platform with JCRE version 3.0.x above.

### Emulation of some proprietary cards
#### (needs further inspection)
- [DESFire applet](https://github.com/robsbeat1/Java-Card-Project) _[**needs inspection** : last commit 2013]_ <br>
reimplementation of DESFire card - master's thesis proof of concept
- [JavaCard DESFire emulation](https://github.com/SakaZulu/java-card-desfire-emulation) _[**needs inspection** : last commit 2011]_ <br>
Emulation of DESFire card
- [MobileEDEPV3](https://github.com/FourTree/EDEPApplet-hengbao/tree/master/MobileEDEPV3) _[**needs inspection** : last commit 2016]_ <br>
JC emulation of some Chinese card?
- [TAG 4 emulation](https://github.com/Tordensky/Tag4) _[**needs inspection** : last commit 2013]_ <br>
This is an implementation of a TAG 4 for emulation

### Unsorted applications
#### (needs further inspection)
- [SmartMeterIQ](https://github.com/adityasawhney/SmartMeterIQ) _[**needs inspection** : last commit 2013]_ <br>
The main concern with Smart Meters is the granularity of the data which enables physical and behavioral analysis of the consumer in terms of the brand and make of devices installed and their house hold activities (like when do they wake up, when they are not at home). We propose using Java Card platform as it is a natural fit and is designed to be tamper-proof and secure. In addition, we explore using advanced cryptography techniques such as Zero Knowledge Proof of Knowledge (using Pedersen Commitments) to enable the Utility Supplier to trust the data it is getting from the central device.
- [STKApplet](https://github.com/aliasnash/z-first-applet) _[**needs inspection** : last commit 2017]_ <br>
SIM Toolkit Applet
- [javacard-petrol-rationing](https://github.com/alegen/javacard-petrol-rationing) _[**needs inspection** : last commit 2014]_ <br>
Applet for security of Petrol rationing, including design documents, Radboud University, Hardware Security course, JavaCard project
- [LiteID-SimApp](https://github.com/LiteID/LiteID-SimApp) _[**just started** : last commit 2017]_ <br>
A Sim Application client for LiteID, just started, unfinished 
- [LicenseCardApp](https://github.com/FourTree/LicenseCardApp) _[**needs inspection** : last commit 2017]_ <br>
applet handling license usage counters and relevant stuff (no documentation)
- [Prototype firmware for the Trusted Execution Module (TEM)](https://github.com/csail/tem_fw) _[**needs inspection** : last commit 2009]_ <br>
Prototype firmware for the Trusted Execution Module (TEM). The firmware is a JavaCard applet, and it can turn any capable JavaCard into a TEM.
- [Secure-Storage-and-Erasure](https://github.com/SecurityResearcher/SSE) _[**needs inspection** : last commit 2014]_ <br>
This is an open source prototype of Secure Storage and Erasure (SSE) System, including both the JavaCard and host programs. 
- [Mobile-ID USAT applet](https://sourceforge.net/projects/mobile-id-usat-applethealt) _[**needs inspection** : last commit 2017]_ <br>
The Remarc Mobile-ID USAT applet this is a JavaCard applet with USIM Application Toolkit menu support. 
Basic functions of the Remarc Mobile-ID SAT applet: Authentication function; Signing function; Changing PIN1/PIN2;  Changing PUK; Unblock PIN1/PIN2; View information - in a USAT menu is present a menu item with information of PIN usage
- [E-Voting applet](https://github.com/EVIVoting/EVIV) _[**needs inspection** : last commit 2016]_ <br>
EVIV is a highly sound End-to-end Verifiable Internet Voting system, which offers full voter’s mobility and preserves the voter’s privacy from the vote casting PC even if the voter votes from a public PC, such as a PC at a cybercafe ́ or at a public library.
- [UPSC framework](https://github.com/paromix/upsc) _[**needs inspection** : last commit 2017]_ <br>
Identity and the security of data transmission is very critical for the success of these e-services. SIM cards might take an important role as a security service provider. They have been used for so many years to preserve the security keys(Ki) and algorithms (A3A8) for authenticating and encrypting the data. Within this project, the international consortium will try to implement a software framework on both the mobile terminal and SIM card that expose the required security functions to popular e-services like Mobile commerce, Financial transactions, Data Encryption, Secure Cloud Storage and Mobile Identity.


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
