[![Logo of Enigma Bridge](http://www.ideaspace.cam.ac.uk/images/iswestimages/enigmabridgelogo/image_preview)](https://keychest.net)

# Curated list of JavaCard applications
The goal is to provide curated catalog of all open-source JavaCard applets and applications relevant for JavaCard development. The initial list was compiled by complete search for all GitHub and SourceForge repositories with *'javacard.framework'* keyword.

You are encouraged to contribute - please create a pull request. 

The projects collected in this list were analyzed and published in [Analysis of JavaCard open-source ecosystem](https://medium.com/enigma-shards/analysis-of-javacard-open-source-ecosystem-9be0bfd66398)

### Format and notation
> **Project name _[status : activity]_** <br> Short description, often taken from a project readme.md
> - **status**: subjective state of project: *mature* (well developed), *aspiring* (promising, but not yet mature), *playground* (initial stages, just playing/learning), *needs inspection* (not analysed yet)
> - **activity**: *active* (at last one commit during the last year), *last commit* (date of last commit for less active / inactive projects)

_________________________________________________

## Applets (standalone applications)
### Electronic passports and citizen ID
 
- [JMRTD: Machine Readable Travel Documents](https://sourceforge.net/projects/jmrtd/) _[**mature** : last commit 2017]_ <br> 
Free implementation of the MRTD (Machine Readable Travel Documents) standards as set by ICAO used in the ePassport. Consists of an API for card terminal software and a Java Card applet.
- [EstEID compatible JavaCard applets](https://github.com/martinpaljak/esteid-applets) _[**mature** : last commit 2016]_ <br> 
Various JavaCard applets compatible to EstEID chip protocol: FakeEstEID, MyEstEID
- [Electronic Driving License](https://github.com/martinpaljak/AppletPlayground/tree/master/src/org/isodl/applet) _[**mature project** : last commit 2015]_ <br> 
A reference implementation of the ISO18013 standards. Based on the passport applet code developed by the JMRTD team. The project implements the host API for reading out ISO compliant electronic driving licenses and a Java Card applet that implements the standard on a smart card. 


#### (needs further inspection)

- [JMRTD applet without EAC support](https://github.com/walterschell/jmrtd-noeac) _[**needs inspection** : last commit 2014]_ <br> 
Fork of JMRTD electronic passport applet without EAC support. The target device for this project is G+D SmartCafe Expert 144k Dual.
- [SIC eID card](https://github.com/nversbra/SIC) _[**just started** : unknown]_ <br> 
A privacy-friendly alternative for the Belgian eID card. The project aims to improve security of Belgian ID holders by limiting the current extensive exposure of their profiles. To do so, we build an alternative ID card which limits service providers to strickly necessary ID holder profile information. 
- [FedICT Quick-Key Toolset](https://github.com/Twuk/eid-quick-key-toolset/tree/master/eid-quick-key-toolset) _[**needs inspection** : last commit 2011]_ <br> 
EidCard project

### Authentication and access control
- [YubiKey NEO App: OATH](https://github.com/Yubico/ykneo-oath) _[**mature** : active]_ <br>
This project implement the HOTP/TOTP card functionality used on the YubiKey NEO device that is sold by Yubico. Its primary use is to use the YubiKey NEO to generate OATH HOTP/TOTP one-time-passwords. GPLv3+
- [ISOApplet PKI](https://github.com/philipWendland/IsoApplet) _[**mature** : active]_ <br>
A Java Card PKI Applet aiming to be ISO 7816 compliant. The Applet is capable of saving a PKCS#15 file structure and performing PKI related operations using the private key, such as signing or decrypting. Private keys can be generated directly on the smartcard or imported from the host computer. The import of private keys is disabled in the default security configuration. 
- [SSH support applet](https://github.com/scs/uclinux/blob/eb0cf9617bd22b69ad625575a95cf4fa2c140d55/user/ssh/scard/Ssh.java) _[**mature** : inactive]_ <br>
Old, but widely copied applet perforimg RSA decrypt on card and used by SSH client 
- [HOTP authenticator via NDEF tag](https://github.com/petrs/hotp_via_ndef) _[**aspiring** : active]_ <br>
JavaCard HMAC-based One Time Password generator which delivers new code via URL tag of NDEF every time the card is put close to NFC-enabled phone. As the Android (and soon also iOS) handles the NDEF tags natively, no additional software is required (after initial card personalization with OTP secret key).
- [CoolKey Applet](https://github.com/NabilNoaman/CoolkeyApplet) _[**mature, inactive** : last commit 2010]_ <br>
CoolKey Applet with the idea of making it a fresh JavaCard 2.2.2 applet meant to be revival of CardEdge Muscle card applet.
- [MuscleApplet](https://github.com/martinpaljak/MuscleApplet) _[**mature, outdated** : last commit 2005]_ <br>
Significant, but outdated applet used for OpenSC. Superseeded by PKCS#15 and PIV standards.
- [OpenFIPS201 PIV applet] (https://github.com/makinako/OpenFIPS201) _[**mature, active** : last commit 2018]_ <br>
Personal Identity Verification (PIV) applet. Commissioned and funded by the Australian Department of Defence

#### (needs further inspection)
- [ORWL KeyFob applets](https://github.com/O-R-W-L/KeyFob-applet)  _[**needs inspection** : last commit 2017]_ <br>
Contains applets for KeyFOB NFC Secure Element for performing association, authentication and identification. Relates to ORWL secure computer.
- [PKCS#15 applet](https://github.com/lupascualex/p15) _[**needs inspection** : last commit 2015]_ <br>
Implementation of card according to RSA PKCS#15 specification. (seems like extensive implementation, but fails to convert under ant-javacard so far)  
- [PKI applet](https://github.com/rakeb/PKIApplet) _[**needs inspection** : last commit 2016]_ <br>
(extensive PKI applet, requires JavaCard 3.0.5)
- [Generic Identity Device Specification Applet](https://github.com/vletoux/GidsApplet) _[**needs inspection** : active]_ <br>
Generic Identity Device Specification (GIDS) smart card is the only PKI smart card whose driver is integrated on each Windows since Windows 7 SP1 and which can be used read and write. No Windows driver installation is required. Based on [ISOApplet PKI](https://github.com/philipWendland/IsoApplet).
- [PIV CryptonitApplet](https://github.com/mbrossard/cryptonit-applet) _[**needs inspection** : active]_ <br>
Personal Identity Verification (PIV) applet
- [Ledger U2F Applet](https://github.com/LedgerHQ/ledger-u2f-javacard) _[**needs inspection** : last commit 2016]_ <br> 
This applet is a Java Card implementation of the FIDO Alliance U2F standard. It uses no proprietary vendor API and is freely available on Ledger Unplugged and for a small fee on other Fidesmo devices through Fidesmo store.
- [FIDO CCU2F Applet](https://github.com/tsenger/CCU2F) _[**needs inspection** : last commit 2017]_ <br> 
This CCU2F JavaCard Applet is based on the Ledger U2F Applet. I imported this applet to Eclipse with installed JCOP Tools and modified the AID of this applet to the standardized AID for FIDO NFC token ( 0xA0000006472F0001). I also provided some example data ([Attestation Certificate and Key](u2f-javacard/U2F Example Attestation Certificate and Key Bytes.txt)) to bring this applet to run. This Applet was succesfully tested on JCOP v2.4.2 R3 cards with KeyAgreementX.ALG_EC_SVDP_DH_PLAIN_XY from NXPs JCOP library for EC Point Multiplication. 
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
- [E-Purse](https://github.com/tomirio619/hw-epurse) _[**needs inspection** : active]_ <br>
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
- [KeepassNFC](https://github.com/JavaCardOS/KeepassNFC) _[**needs inspection** : last commit 2016]_ <br>
KeepassNFC is a applet in javacard platform that it can protect the secret key of KeePass database. 
- [Smartcard crypto applet](https://github.com/nfd/smartcard_crypto_applet) _[**needs inspection** : last commit 2016]_ <br>
This applet performs secure decryption of secrets via NFC. It was made for KeePassNFC, but could be used for other things.
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

- [ANSSI-FR SmartPGP applet](https://github.com/ANSSI-FR/SmartPGP) _[**mature** : active]_ <br>
SmartPGP is a free and open source implementation of the OpenPGP card 3.x specification in JavaCard. The main improvement introduced in OpenPGP card 3.x specification from previous version is the support of elliptic curve cryptography with several existing curves (NIST P-256, NIST P-384, NIST P-521, brainpool p256r1, brainpool p384r1 and brainpool p512r1). The SmartPGP Card applet is typically used through GnuPG.

- [Secure multi-party signatures Myst](https://github.com/OpenCryptoProject/Myst) _[**aspiring** : active]_ <br>
Secure multiparty Schnorr-based EC signatures implemented without proprietary ECPoint API. 

#### (needs further inspection)
- [SigAnima](https://github.com/tsenger/SigAnima) _[**needs inspection** : last commit 2014]_ <br>
SigAnima is an JavaCard ECDSA signing applet. This applet is based on the [javacardsign applet]((http://sourceforge.net/projects/javacardsign/)) from Wojciech Mostowski . The applet supports the following standardized EC domain parameters: secp224r1, BrainpoolP224r1, secp256r1, BrainpoolP256r1, BrainpoolP320r1. (Seems like interesting applet)
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


### CryptoCurrency wallets
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
#### (needs further inspection)
- [LibESE Android verified boot](https://github.com/ADVAN-ELAA-8QM-PRC1/platform-external-libese) _[**needs inspection** : last commit 2017]_ <br>
Minimal transport wrapper for communicating with embedded secure elements on Android
- [ACORN, AEGIS, ASCON, CLOC, and MORUS AEAD ciphers implementation](https://github.com/palkrajesh/AEonJC) _[**needs inspection** : last commit 2017]_ <br>
Optimalized implementation of 5 selected candidates for authenticated encryption from CAESAR competition
- [Primitives for JavaCard](https://github.com/albertocarp/Primitives_SmartCard) _[**needs inspection** : last commit 2016]_ <br>
JBigInteger, JCMath, SHA3, UProve...
- [OpenTLSSec](https://github.com/halemmerich/opentlssc) _[**needs inspection** : last commit 2013]_ <br>
Open source java card library for TLS secured communication under GPL v3. (Seems like significant project)
- [SmartCardTLS](https://github.com/gilb/smart_card_TLS/) _[**needs inspection** : last commit 2013]_ <br>
Client implementation of TLS 1.0 in Java Card (tested with Gmail mobile (lightweight version) with the card G&D SmartCafe 3.2)
- [SRP-6a password-authenticated secure channel](https://github.com/mobilesec/secure-channel-srp6a-applet) _[**needs inspection** : last commit 2015]_ <br>
Java Card applet for SRP-6a password-authenticated secure channel to secure elements/smartcards. This Java Card applet is an implementation of the Secure Remote Password (SRP-6a) password-authenticated secure channel protocol by Wu [1]. In combination with an implementation of an off-card application, such as an Android application using our SRP-6A Android Library, you can establish a secure communication channel that is mutually authenticated with a PIN or password. 
- [ykneo-curves](https://github.com/Yubico/ykneo-curves) _[**needs inspection** : last commit 2014]_ <br>
This is an applet demonstrating several curves for use in YubiKey NEO.
- [Java Card Synchronization Framework](https://github.com/jfhren/jc_sync) _[**needs inspection** : last commit 2014]_ <br>
The main goal of this framework is to synchronize Java Card 2 applets through the exchange of ciphered APDUs. The current state of the framework is lacking in many ways and requires more works to be fully functional. However it can serve as a proof of concept for the synchronization of applet data in a secure fashion in a pure Java Card 2 setting (i.e.: without tweaking the Java Card VM).
- [Secure Element Evaluation Kit for the Android platform](https://github.com/seek-for-android/pool) _[**needs inspection** : last commit 2015]_ <br>
SmartCard API for Android. The SmartCard API adds the necessary modules and API’s to the Android platform. It offers flexible access to secure elements, allowing a secure application solution to make use of any secure form factor, such as a USIM card, a secure µSD card, an embedded secure element.
- [OPACITY auth protocol for JC](https://github.com/shevelevsergey/opacity-for-smartcard) _[**needs inspection** : last commit 2015]_ <br>
This project is designed to authenticate users to the Web service using contactless smart cards. As an authentication protocol was chosen protocol OPACITY. This protocol has been specifically designed for contactless payments and it is officially registered now as an authentication protocol ISO/IEC 24727-6.
- [HMAC and CMAC computation](https://github.com/mll11/jcard/tree/master/TestMAC) _[**needs inspection** : last commit 2014]_ <br>
This Java Card applet support APDUs to test HMAC (SHA-1, SHA-256) and CMAC (AES-128). It uses Java Card 2.2.2.
- [ Self-Blindable credentials](https://github.com/credentials/sbcred_javacard) _[**needs inspection** : last commit 2014]_ <br>
Java Card implementation of Self-Blindable credentials           
- [TelephonyManager Carrier Privilege granting](https://github.com/sabtmoha/carrier_privilege) _[**needs inspection** : last commit 2015]_ <br>
Since Android 5.1, applications are able to communicate with UICC using the class TelephonyManager. However, the concerned functions require a special privilege, that is the carrier privilege. This JavaCard applet grants this privilege to the app whose signature is included inside the applet (the variable SHA256_SIGN)
- [AES, OAEP, SHA2-384 and SHA2-512 JC reimplementation,](https://github.com/petrs/JCSWAlgs) _[**needs inspection** : last commit 2016]_ <br>
The Suite of software reimplementations of selected cryptographic algorithms potentially missing on your smartcard with JavaCard platform. Optimized for speed and small memory footprint.
- [Hashchain applet](https://github.com/raminarmanfar/Java-smart-card-cryptographic-protocols) _[**needs inspection** : last commit 2016]_ <br>
Implementation of some hash chain
- [ElGamal-based Threshold Scheme for Electronic Elections](https://github.com/CRISES-URV/eVerification-2) _[**needs inspection** : last commit 2013]_ <br>
TTP SmartCard-Based ElGamal Cryptosystem Using Threshold Scheme for Electronic Elections. EU Project CRISES group has studied the feasibility of developing ElGamal cryptosystem and Shamir’s secret sharing scheme into JavaCards, whose API gives no support for it. (probably significant applet).
- [Audit TTP SmartCard-Based ElGamal Cryptosystem](https://github.com/AuditURV/Audit) _[**needs inspection** : last commit 2016]_ <br>
TTP SmartCard-Based ElGamal Cryptosystem Using Threshold Scheme for Electronic Elections. EU Project.
- [Protocol for Lightweight Authentication of Identity (PLAID)](https://github.com/martinpaljak/AppletPlayground/tree/master/src/plaid804) _[**needs inspection** : last commit 2015]_ <br>
Protocol for Lightweight Authentication of Identity [PLAID](https://www.humanservices.gov.au/corporate/publications-and-resources/protocol-lightweight-authentication-identity-plaid/plaid-reference-implementation-department-human-services), Australian Government
- [Sec2 cloud security project](https://github.com/RUB-NDS/Sec2) _[**needs inspection** : last commit 2016]_ <br>
message-level security must at least be applied to protect those data during and after the storing process. Novel solution for secure data storage in the cloud. It presents a security concept allowing each client to encrypt outgoing data on one’s mobile device and share it among a defined user group while using a seamless service provision. J. Somorovsky, research proof of the concept


## Developer tools 
### Applet build, upload and management
- [Ant-JavaCard](https://github.com/martinpaljak/ant-javacard) _[**mature** : last commit 2017]_ <br>
Easy to use Ant task for building JavaCard CAP files in a declarative way.
- [GlobalPlatform tools (GPShell)](https://sourceforge.net/p/globalplatform/) _[**mature** : last commit 2014]_ <br>
The GlobalPlatform card specification provides a standard for the management of the contents on a smart card. Mainly this comprises the installation and the removal of applications. 
- [Sun/Oracle JavaCard SDK binaries](https://github.com/martinpaljak/oracle_javacard_sdks) _[**mature** : last commit 2016]_ <br>
Oracle JavaCard SDK-s for using as a Git submodule for ant-javacard projects.    

#### (needs further inspection)
- [OPAL - GlobalPlatform lib](https://bitbucket.org/ssd/opal) _[**needs inspection** : last commit 2015]_
A GlobalPlatform Java Library. OPAL implements several authentication, encryption and transfer protocols for
smart card. This tool has been developped by the SSD Research Team (XLIM Labs, University of Limoges, France).
- [CAP File Manipulation](https://bitbucket.org/ssd/capmap-free) _[**needs inspection** : last commit 2012]_
A Java Card CAP file parser. 
- [Smart-Card-Tool-pyResMan](https://sourceforge.net/projects/pyresman) _[**needs inspection** : last commit 2015]_ <br>
pyResMan is a free open source smartcard tool for JavaCard and other smart card. It can be used to send APDU(s), execute APDU script(s); It can be used to debug ISO14443 protocol commands and Mifare commands with R502 SPY reader; It can also be used to manage resource of GP card. It is based on pyScard and GlobalPlatform open source projects.
last commit 2017
- [JavaCard debugging toolkit](https://github.com/omarbenhamid/jcdebug) _[**needs inspection** : last commit 2015]_ <br>
JCDebug is a JavaCard debugging toolkit. A simple command line tools that instruments JavaCard applets to offer debugging and inspection services directly on the target plateform.       
potentially significant project, needs closer look
last commit 2016    
- [Card2Jar](https://github.com/benjholla/Card2Jar) _[**needs inspection** : last commit 2014]_ <br>
A converter for converting Java Card CAP files to JAR files. Currently this is just a handy wrapper around the JCDK3.0.4_ClassicEdition SDK distribution's normalizer utility. It is subject to all the same caveats as the normalizer.bat interface (must have export EXP file and must be a Java Card 2.2.2 or lower applet).
- ["Allow all" ARA-M](https://github.com/seek-for-android/allow-all-ara) _[**needs inspection** : last commit 2015]_ <br>
GlobalPlatform dummy ARA applet to grant full access



### Card capabilities testing (algorithms support, performance, security issues)
- [JCAlgTest](https://github.com/crocs-muni/JCAlgTest) _[**mature** : last commit 2017]_ <br>
Automated testing tool for algorithms from JavaCard API supported by particular smart card. Performance testing of almost all available methods. The results for more than 60+ cards available at https://jcalgtest.org. 
- [ECTester](https://github.com/petrs/ECTester) _[**aspiring** : last commit 2016]_ <br>
Tester of Eliptic curves support and behavior (TYPE_EC_FP and TYPE_EC_F2M) on smartcards with JavaCard platform. 

#### (needs further inspection)
- [Performance Benchmark Applet for Javacard/smartcard](https://github.com/dmdclab/smartcard-benchmark) _[**needs inspection** : last commit 2016]_ <br>
Performance testing for various algorithms, similar as JCAlgTest (needs investigation about difference to JCAlgTest) 
- [HandlerTest](https://github.com/LudovicRousseau/HandlerTest) _[**needs inspection** : last commit 2016]_ <br>
L. Rousseau PCSCLite reader test : This program send commands to a card through the reader.           
- [Memory profiling tool] (https://github.com/maxashwin/JavaCard/tree/master/Wkg_MemoryMeasurementScript)_[**needs inspection** : last commit 2017]_ <br>
Locates an applet constructor and places free memory measurements hooks before and after every allocation command. Summarizes required memory for every allocated object (RAM, EEPROM)   
- [Three applets testing on-card defenses against maliciously modified applets](https://github.com/maxashwin/JavaCard)_[**needs inspection** : last commit 2017]_ <br>
Abuse of Shareable interface, type confusion after use of Shareable interface, direct modification of CAP file
### Formal verification and code transformation tools
- [VeriFast](https://github.com/verifast/verifast) _[**mature** : last commit 2017]_ <br>
VeriFast is a research prototype of a tool for modular formal verification of correctness properties of single-threaded and multithreaded C and Java programs annotated with preconditions and postconditions written in separation logic. Examples on JavaCard applets: EPurse, EidCard. Very active project.
- [Joana IFC analysis framework](https://github.com/joana-team/joana) _[**mature** : last commit 2017]_ <br>
Joana is a static analysis tool that can be used for information flow control (IFC) of Java bytecode. IFC allows to verify the INTEGRITY (no attacker can temper with sensitive information) or CONFIDENTIALITY (no attacker can infer secret information from public outputs) of a Java program. System dependence graphs (SDG) form the basic technology for our analyses. Examples also on JavaCard applets. Very active project.
- [CesTa project](https://github.com/formanek/CesTa) _[**aspiring** : last commit 2013]_ <br>
Security hardening (duplicate variables, constant branches, transaction detection...) for JavaCard applets based on ANTLR automatic code transformations

#### (needs further inspection)

- [The KeY project](https://github.com/cirosantilli/key-java-formal-verification-fork) _[**needs inspection** : last commit 2015]_ <br>
examples on JavaCard, this github repo is not official (https://www.key-project.org) 
- [KeYmaera 3](https://github.com/LS-Lab/KeYmaera-release) _[**needs inspection** : last commit 2014]_ <br>
KeYmaera 3: A Hybrid Theorem Prover for Hybrid Systems with examples on JavaCard applets (among others). http://symbolaris.com/info/KeYmaera.html

## JavaCard simulators and emulators

- [JCardSim:](https://github.com/licel/jcardsim) _[**mature** : last commit 2017]_ <br>
Capable JavaCard simulator implemented atop of BouncyCastle. Very good for unit testing, quick prototyping and educational purposes. Allows for multiple simulated cards in parallel.   

#### (needs further inspection)
- [vJCRE ](https://github.com/martinpaljak/vJCRE) _[**needs inspection** : last commit 2016]_ <br>
vJCRE is a virtual Java Card Runtime Environment
- [Secure Element Emulator](https://github.com/mobilesec/secure-element-emulator) _[**needs inspection** : last commit 2015]_ <br>
This project aims at emulating a secure element environment for debugging and rapid-prototyping of secure element applets. It is a fork of the open-source Java Card simulator jCardSim (original source code available here). Within our research we added extensions to emulate an application life-cycle that matches the life-cycle of applications on real smartcard chips.
- [Java Card Simulator via Node.js](https://github.com/adamnoakes/javacard-simulator) _[**needs inspection** : last commit 2016]_ <br>
This project provides an implementation of a Java Card Runtime Enviornment (JCRE) produced using Node.js which is capable of creating virtual smart card devices and sending APDU commands to the smart card devices for execution via a RESTful API. The project also provides a web-based interface for interacting with the JCRE. 
- [PythonCard:](https://bitbucket.org/benallard/pythoncard/) _[**needs inspection** : last commit 2013]_ <br>
JavaCard API simulated in Python environment. The goal is to provide a classic 3.0.1 version, while maintaining compatiblity with earlier version like 2.1.2. Older version on [GitHub also available](https://github.com/benallard/pythoncard)
- [JCardMock](https://github.com/christianhujer/jcardmock) _[**needs inspection** : last commit 2012]_ <br>
Mock implementation of the Java Card API 3.0.4 in order to test Java Card applet code without a card or simulator. It runs the Java Card API 3.0.4 in a normal Java Virtual Machine. The purpose is to allow for unit tests for Java Card applets with normal test frameworks like JUnit or TestNG in a normal Java Virtual Machine (unfinished)
- [CAPRunner  https://bitbucket.org/benallard/caprunner/,](https://github.com/benallard/caprunner) _[**needs inspection** : last commit 2015]_ <br>
CAPRunner is a javacard bytecode emulator that is able to execute CAP files. It also comes with an handy runcap.py that bind them together and allow you to send some APDUs to a CAP file (without the need for a smartcard).
developement on BitBucket
- [FreeJCVM](https://sourceforge.net/projects/freejcvm) _[**needs inspection** : last commit 2015]_ <br>
Free javacard vm implementation for AVR MCU.
- [openjcvm](https://sourceforge.net/projects/openjcvm/) _[**needs inspection** : last commit 2015]_ <br>
A open source java card virtual machine implementation. And also some part of the VM code can be used as part of kinds of tools such as javacard bytecode disassembler.

## Learning (various school projects, simple hello world applets....)

- [AppletPlayground](https://github.com/martinpaljak/AppletPlayground) _[**mature** : last commit 2017]_ <br>
AppletPlayground is an educational repository for getting to know JavaCard development by learning from existing open source software. It includes various open source applets from the internet, bundled into ready to use package. Everything you need to edit, compile and load the applets to real JavaCard-s or test with an emulator.

- [Simple AES encrypt/decrypt](https://github.com/pauksk/nrf6310-pca10007) _[last commit 2017]_ <br>
Master thesis - protection of wireless networks in smart homes using secure hardware

#### (needs further inspection)
- [SecureChat](https://github.com/sharedbits/JavaChat) _[**needs inspection** : last commit 2018]_ <br>
Secure chat client/server application 
- [JavaCard Demo](https://github.com/leowenyang/JCDemo) _[**needs inspection** : last commit 2015]_ <br>
Examples of various JavaCard functionalities (as separate applets)
- [MultiCard](https://github.com/Dragonexodus/MultiCard) _[**needs inspection** : last commit 2016]_ <br>
An school of applied science smartcard project. This project contains offCard and onCard components with various aspects of javacard development.
- [Hotel Buddy](https://github.com/prayzzz/smartcard) _[**needs inspection** : last commit 2015]_ <br>
Various JavaCard projects created for a lecture-series. Some PKI.           
- [Simple calculator on JavaCard](https://github.com/steff7/javacard-calculator) _[**needs inspection** : last commit 2013]_ <br>
- [Maze solver on JavaCard](https://github.com/henryhoo/javacard_maze) _[**needs inspection** : last commit 2015]_ <br>
A small experiment on memory about java card using jcopv2.2.1           
- [TraninCard applet](https://github.com/TBoonX/sc_traincard) _[**needs inspection** : last commit 2014]_ <br>
SmartCard - Traincard, No Pain No Gain Project at HTWk Leipzig. 
- [Client applet for CDAX Crypto](https://github.com/marlonbaeten/cdax-crypto-cpp/tree/f3329fcdf348b6a3ddc95b993dbc97b806aea335/applet) _[**needs inspection** : last commit 2014]_ <br>
The applet with implementation of basic cryptographic functions offered by JavaCard API
- [CryptedBankCard](https://github.com/Herve-M/UQAM-EMB7015) _[**needs inspection** : last commit 2015]_ <br>
The school project with goal to develop a secure banking card.
- [Smartcard-Offline-Lock](https://github.com/GeorgesAlkhouri/Smartcard-Offline-Lock) _[**needs inspection** : last commit 2015]_ <br>
Electronic door locks are commonly used at hotels, exhibitions or public facilities. An offline-operational solution for an electronic lock is required. This is a study project, so please don't expect to much comfort (single DES key used).
- [Learning applets repository](https://github.com/gracebear/JavaCard) _[**needs inspection** : last commit 2015]_ <br>
This repository will provide you with javacard applet for absolutely beginners\ javacard development tools\ javacard open source applets. 

## Unsorted

#### (needs further inspection)
- [Corba](https://github.com/lbarbisan/corba) _[**needs inspection** : last commit 2015]_ <br>
old project, CryptoFlex uploader?
- [unifei-smart-cards](https://github.com/tiagorg/unifei-smart-cards) _[**needs inspection** : last commit 2014]_ <br>
Material developed in UNIFEI-MG research about Smart Cards          


### Methodology
- DONE (2018-02-22) Search all GitHub repositories with "javacard.framework.Applet" string. For repeated searches, sort by _Sort:Recently indexed_ to get new projects first
- DONE (2017-04-15) Analyze applets included in AppletPlayground
- DONE (2017-04-15) Search all SourceForge repositories with "javacard" string
- DONE (2017-04-16) Sort applets into categories according to basic topic
- Inspect other repositories of relevant developers
- Analyze status and maturity of included projects (subsection 'needs further inspection')
