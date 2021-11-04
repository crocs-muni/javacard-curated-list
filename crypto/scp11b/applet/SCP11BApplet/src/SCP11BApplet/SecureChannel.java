package SCP11BApplet;

import javacard.security.ECPrivateKey;
import javacard.security.AESKey;
import javacard.framework.Util;
import javacardx.crypto.Cipher;
import javacard.framework.ISOException;

public class SecureChannel {

    /**
     * <p>
     * GP-SCP11b support with ECKA.
     * </p>
     * <br>
     * <p>
     * SCP11b: With two ephemeral key pairs and one static key pair. The OCE does
     * not have a static key pair. The ephemeral key pair of the OCE is used twice.
     * </p>
     * <p>
     * Internal Authenticate command is used by Off-Card-Entity (OCE) to submit
     * ephemeral public key to Card which the Card responses with its ephemeral
     * public key and session receipt followed by opening of an SCP-03 symmetric
     * secure channel.
     * </p>
     */

    public static final short SCP11BIDParams = (short) 0x1100;
    public static final byte[] CRTTLCA6 = { (byte) 0xA6, (byte) 0x0D, (byte) 0x90, (byte) 0x02, (byte) 0x11,
            (byte) 0x00, (byte) 0x95, (byte) 0x01, (byte) 0x3C, (byte) 0x80, (byte) 0x01, (byte) 0x88, (byte) 0x81,
            (byte) 0x01, (byte) 0xA0 };
    public static final byte[] ePKOCEECKATAG = { (byte) 0x5F, (byte) 0x49 };
    public static final short receiptMacLen = 16;

    // Init SCP11b secure channel and produce a keystream
    public static short initSecureChannelOpen(byte[] ocePubKey, short ocePKOff, short ocePKLen,
            ECPrivateKey cardLongTermPrivKey, byte[] outputKeyStream, short outKeyStreamOff, byte[] buff, short buffOff,
            byte[] buff1, short buff1Off, AESKey receiptKey, AESKey SENCKey, AESKey SMACKey, AESKey SRMACKey,
            AESKey SDEKKey) {
        // 1.) Gen card ephemeral session keypair
        SCP11BApplet.tmpKP.genKeyPair();

        // 2.) Generate ShSes shared secret via ECDH card long term private key with OCE
        // ephemeral
        // public key
        short kdfOff = buffOff;
        short kdfLen = 0;
        short kdfCtrOff = 0; // Short cut caching of counter
        short kLen = 0;

        // // Offset off the 0x04 ASN.1 header as the current
        // // KeyProfile.computeECDHSharedSecret operates on XY coords
        // kLen = SCP11BApplet.hwProfile.computeECDHSharedSecret(ocePubKey, (short)
        // (ocePKOff + 1),
        // (short) (ocePKLen - 1), buff1, buff1Off, buff, kdfOff);

        // Support ASN1 public key
        kLen = SCP11BApplet.hwProfile.computeECDHSharedSecretASN1(ocePubKey, ocePKOff, ocePKLen, buff, kdfOff);
        kdfOff += kLen;
        kdfLen += kLen;

        // 3.) Generate ShSee shared secret via ECDH card ephemeral private key with OCE
        // ephemeral
        // public key

        // // Offset off the 0x04 ASN.1 header as the current
        // // KeyProfile.computeECDHSharedSecret operates on XY coords
        // kLen = SCP11BApplet.hwProfile.computeECDHSharedSecret(SCP11BApplet.ecTmpPriv,
        // ocePubKey,
        // (short) (ocePKOff + 1), (short) (ocePKLen - 1), buff1, buff1Off, buff,
        // kdfOff);

        // Support ASN1 public key
        kLen = SCP11BApplet.hwProfile.computeECDHSharedSecretASN1(SCP11BApplet.ecTmpPriv, ocePubKey, ocePKOff, ocePKLen,
                buff, kdfOff);
        kdfOff += kLen;
        kdfLen += kLen;

        // 4.) Set aside 32-bit space for KDF counter and set counter to 0x00000001
        Util.arrayFillNonAtomic(buff, kdfOff, (short) 4, (byte) 0x00);
        kdfOff += 4;
        kdfLen += 4;
        kdfCtrOff = (short) (kdfOff - 1);
        buff[kdfCtrOff] = (byte) 0x01;

        // 4.) Derive AES session keys from X9.63-KDF(concat(ShSee || ShSes), counter,
        // SharedInfo=<keyUsage || keyType || keyLength || hostIDLen - If applicable
        // || hostID - If applicable> and 5 keys for Receipt Key, S-ENC, S-MAC, S-RMAC,
        // S-DEK keys where SharedInfo's keyLength shall be L * 5 and L shall be the
        // length of each of the 5 keys.
        buff[kdfOff] = (byte) 0x3C; // Only '3C' (secure messaging with C-MAC, R-MAC, and
                                    // ENCRYPTION) is acceptable
        kdfOff++;
        buff[kdfOff] = (byte) 0x88; // Only 'AES' key type allowed
        kdfOff++;
        buff[kdfOff] = (byte) 0xA0; // Only L key length of 32 bytes / 256-bits allowed with 5 keys
        kdfOff++;
        kdfLen += 3;

        // // DEBUG: Caching of KDF data
        Util.arrayFillNonAtomic(SCP11BApplet.debugBytes1, (short) 0, (short) SCP11BApplet.debugBytes1.length,
                (byte) 0x00);
        Util.arrayCopyNonAtomic(buff, buffOff, SCP11BApplet.debugBytes1, (short) 0, kdfLen);

        // Gen key 1 with counter 1
        SCP11BApplet.sha256.reset();
        SCP11BApplet.sha256.doFinal(buff, buffOff, kdfLen, outputKeyStream, outKeyStreamOff);
        receiptKey.setKey(outputKeyStream, outKeyStreamOff); // Debug, integrate keys into SCP init

        // Increment counter
        buff[kdfCtrOff] = (byte) 0x02;

        // Gen key 2 with counter 2
        SCP11BApplet.sha256.reset();
        SCP11BApplet.sha256.doFinal(buff, buffOff, kdfLen, outputKeyStream, (short) (outKeyStreamOff + 32));
        SENCKey.setKey(outputKeyStream, (short) (outKeyStreamOff + 32)); // Debug, integrate keys into SCP init

        // Increment counter
        buff[kdfCtrOff] = (byte) 0x03;

        // Gen key 3 with counter 3
        SCP11BApplet.sha256.reset();
        SCP11BApplet.sha256.doFinal(buff, buffOff, kdfLen, outputKeyStream, (short) (outKeyStreamOff + 64));
        SMACKey.setKey(outputKeyStream, (short) (outKeyStreamOff + 64)); // Debug, integrate keys into SCP init

        // Increment counter
        buff[kdfCtrOff] = (byte) 0x04;

        // Gen key 4 with counter 4
        SCP11BApplet.sha256.reset();
        SCP11BApplet.sha256.doFinal(buff, buffOff, kdfLen, outputKeyStream, (short) (outKeyStreamOff + 96));
        SRMACKey.setKey(outputKeyStream, (short) (outKeyStreamOff + 96)); // Debug, integrate keys into SCP init

        // Increment counter
        buff[kdfCtrOff] = (byte) 0x05;

        // Gen key 5 with counter 5
        SCP11BApplet.sha256.reset();
        SCP11BApplet.sha256.doFinal(buff, buffOff, kdfLen, outputKeyStream, (short) (outKeyStreamOff + 128));
        SDEKKey.setKey(outputKeyStream, (short) (outKeyStreamOff + 128)); // Debug, integrate keys into SCP init

        // Reset session MAC chain and session IV
        Util.arrayFillNonAtomic(SCP11BApplet.secSessMacChain, (short) 0, (short) SCP11BApplet.secSessMacChain.length,
                (byte) 0x00);
        Util.arrayFillNonAtomic(SCP11BApplet.secSessIV, (short) 0, (short) SCP11BApplet.secSessIV.length, (byte) 0x00);
        SCP11BApplet.secSessIV[15] = (byte) 0x01; // Ctr starts at 1

        return 160;
    }

    // // Set relevant keys from a SCP11b computed keystream
    // public static void keystreamToKeys(byte[] keystream, short keyStreamOff, AESKey receiptKey, AESKey SENCKey,
            // AESKey SMACKey, AESKey SRMACKey, AESKey SDEKKey) {
        // receiptKey.setKey(keystream, keyStreamOff);
        // SENCKey.setKey(keystream, (short) (keyStreamOff + 32));
        // SMACKey.setKey(keystream, (short) (keyStreamOff + 64));
        // SRMACKey.setKey(keystream, (short) (keyStreamOff + 96));
        // SDEKKey.setKey(keystream, (short) (keyStreamOff + 128));
    // }

    // Generate receipt. Only suitable for SCP11b receipt generation
    public static short generateSCP11BReceipt(AESKey receiptKey, byte[] oceEcPublicKey, short oceECPubKOff,
            short oceECPubKLen, byte[] receipt, short receiptOff, byte[] buff, short buffOff, byte[] buff1,
            short buff1Off) {
        // 1.) Generate CRT-TLV internally a.k.a 'CRT-TLV-A6'
        // CRT-TLV-SCPIDParam - T=0x90, L=0x02, V=0x1100
        // CRT-TLV-KeyUsage - T=0x95, L=0x01, V=0x3C
        // CRT-TLV-KeyType - T=0x80, L=0x01, V=0x88
        // CRT-TLV-KeyLength - T=81, L=0x01, V=A0
        // CRT-TLV-A6 encapsulate all sub-TLVs - T=0xA6, L=0x0D, V=<All the above
        // sub-TLVs
        Util.arrayCopyNonAtomic(CRTTLCA6, (short) 0, buff, buffOff, (short) CRTTLCA6.length);

        // 2.) Concat ePK.OCE.ECKA-TLV - T=0x5F49, L=TLVLen(oceEcPublicKey),
        // V=<oceEcPublicKey> behind CRT-TLV-A6
        Util.arrayCopyNonAtomic(ePKOCEECKATAG, (short) 0, buff, (short) (buffOff + CRTTLCA6.length),
                (short) ePKOCEECKATAG.length);

        short processLen = 0;
        if (oceECPubKLen >= 128) {
            Util.arrayCopyNonAtomic(oceEcPublicKey, oceECPubKOff, buff,
                    (short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 2), oceECPubKLen);
            buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length)] = (byte) 0x81;
            buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 1)] = (byte) (oceECPubKLen & 0xFF);
            processLen = (short) (CRTTLCA6.length + ePKOCEECKATAG.length + oceECPubKLen + 2);
        } else {
            Util.arrayCopyNonAtomic(oceEcPublicKey, oceECPubKOff, buff,
                    (short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 1), oceECPubKLen);
            buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length)] = (byte) (oceECPubKLen & 0xFF);
            processLen = (short) (CRTTLCA6.length + ePKOCEECKATAG.length + oceECPubKLen + 1);
        }

        // 3.) Write return receipt with ePK.SD.ECKA TLV tag first where T=0x5F49,
        // L=Len(ePK.SD.ECKA), V=ePK.SD.ECKA)
        short rOff = receiptOff;
        short ecPubKLen = 0;

        Util.arrayCopyNonAtomic(ePKOCEECKATAG, (short) 0, receipt, rOff, (short) ePKOCEECKATAG.length);
        rOff += ePKOCEECKATAG.length;

        if (SCP11BApplet.ecTmpPub.getW(buff1, buff1Off) >= 128) {
            ecPubKLen = SCP11BApplet.ecTmpPub.getW(receipt, (short) (rOff + 2));
            receipt[rOff] = (byte) 0x81;
            receipt[(short) (rOff + 1)] = (byte) (ecPubKLen & 0xFF);
            rOff += (short) (ecPubKLen + 2);
        } else {
            ecPubKLen = SCP11BApplet.ecTmpPub.getW(receipt, (short) (rOff + 1));
            receipt[rOff] = (byte) (ecPubKLen & 0xFF);
            rOff += (short) (ecPubKLen + 1);

        }

        // Set CMAC T and L values for the CMAC tag.
        receipt[rOff] = (byte) 0x86;
        receipt[(short) (rOff + 1)] = (byte) 0x10;
        rOff += 2;

        // 4.) Create CMACReceipt = AES-CMAC(Key=ReceiptKey, Data= (CRT-TLV-A6 ||
        // ePK.OCE.ECKA-TLV)
        processLen = CMAC.process(SCP11BApplet.aesECB, receiptKey, buff, buffOff, processLen, buff1, buff1Off, receipt,
                rOff, receiptMacLen);

        // 4.) Return receipt = (T=0x5F49, L=Len(ePK.SD.ECKA), V=ePK.SD.ECKA) ||
        // (T=0x86, L=0x16, V=CMACReceipt) if CMAC is successful
        if (processLen == receiptMacLen) {
            return (short) (rOff + processLen - receiptOff);
        }
        return 0;
    }

    // SCP03 i=70 type message wrap and unwrap only. APDU buffer will be overwritten
    // with relevant output data.
    public static short handleMessage(boolean isWrap, AESKey SENCKey, AESKey MACKey, byte[] apduBuf, short apduBufOff,
            short apduLen, byte[] buff, short bOff, byte[] buff1, short b1Off, byte[] buff2, short b2Off, byte[] output,
            short outOff) {
        short macLen = 0;
        short procLen = 0;
        if (isWrap) {
            // Encrypt plaintext data
            if (apduLen > 0) {
                procLen = calcAPDUCrypto(true, SENCKey, apduBuf, apduBufOff, apduLen, buff, bOff, buff1, b1Off, output,
                        outOff);
                if (procLen == -1) {
                    return procLen; // Return error if there is error
                }
            }

            // Calculate RMAC on top of ciphertext data and return combined ciphertext and
            // RMAC data as Response APDU content
            macLen = calcAPDURMAC(MACKey, output, outOff, procLen, buff, bOff, buff1, b1Off, output, outOff);
            if (macLen != 0) {
                return (short) (procLen + macLen);
            } else {
                return -1;
            }
        } else {
            // Calculate CMAC on APDU buffer data minus 8 bytes CMAC
            macLen = calcAPDUCMAC(MACKey, apduBuf, apduBufOff, (short) (apduLen - 8), buff, bOff, buff1, b1Off, buff2,
                    b2Off);
            // Util.arrayCopyNonAtomic(buff2, b2Off, SCP11BApplet.debugBytes, (short) 0,
            // (short) 16);
            if (macLen != 0) {
                // Check MAC
                if (Util.arrayCompare(apduBuf, (short) (apduBufOff + apduLen - 8), buff2, b2Off,
                        (short) 8) == (byte) 0x00) {
                    try {
                        // Decrypt ciphertext data if MAC check passes and return decrypted plaintext
                        return calcAPDUCrypto(false, SENCKey, apduBuf, apduBufOff, apduLen, buff, bOff, buff1, b1Off,
                                output, outOff);
                    } catch (Exception e) {
                        ISOException.throwIt(Util.makeShort((byte) 0x6f, (byte) 0x22)); // <---- Receiving message issue
                    }
                } else {
                    ISOException.throwIt(Util.makeShort((byte) 0x6F, (byte) 0xF1));
                    return -1; // Incorrect MAC
                }
                return -1; // remove once debug is done
            } else {
                ISOException.throwIt(Util.makeShort((byte) 0x6F, (byte) 0xF2));
                return -1;
            }
        }
    }

    private static short calcAPDUCMAC(AESKey SMACKey, byte[] input, short iOff, short iLen, byte[] buff, short bOff,
            byte[] buff1, short b1Off, byte[] output, short oOff) {
        // 1.) Concat (MAC Chaining Value || CLA, INS, P1, P2, LC, Data) as target data
        Util.arrayCopyNonAtomic(SCP11BApplet.secSessMacChain, (short) 0, buff, bOff,
                (short) SCP11BApplet.secSessMacChain.length);
        Util.arrayCopyNonAtomic(input, iOff, buff, (short) (bOff + SCP11BApplet.secSessMacChain.length), iLen);

        // 2.) CMAC (SMAC Key, target data)
        if (CMAC.process(SCP11BApplet.aesECB, SMACKey, buff, bOff, (short) (SCP11BApplet.secSessMacChain.length + iLen),
                buff1, b1Off, output, oOff, (short) 16) != (short) 0) {

            // 3.) CMAC result (entire 16 bytes) is updated as new MAC Chaining Value and
            // the leftmost 8 bytes are CMAC value
            Util.arrayCopyNonAtomic(output, oOff, SCP11BApplet.secSessMacChain, (short) 0, (short) 16);
            return 8;
        }
        return 0;
    }

    private static short calcAPDURMAC(AESKey SRMACKey, byte[] input, short iOff, short iLen, byte[] buff, short bOff,
            byte[] buff1, short b1Off, byte[] output, short oOff) {
        // 1.) Concat (MAC Chaining Value || CLA, INS, P1, P2, LC, Data) as target data
        Util.arrayCopyNonAtomic(SCP11BApplet.secSessMacChain, (short) 0, buff, bOff,
                (short) SCP11BApplet.secSessMacChain.length);
        Util.arrayCopyNonAtomic(input, iOff, buff, (short) (bOff + SCP11BApplet.secSessMacChain.length), iLen);

        // 2.) CMAC (SRMAC Key, target data) and CMAC result has the leftmost 8 bytes
        // set as the CMAC value
        if (CMAC.process(SCP11BApplet.aesECB, SRMACKey, buff, bOff,
                (short) (SCP11BApplet.secSessMacChain.length + iLen), buff1, b1Off, output, (short) (oOff + iLen),
                (short) 16) != 0) {

            // 3.) Response APDU is modified where the Response Data is left and the CMAC
            // Value is right followed finally by the SW
            Util.arrayCopyNonAtomic(input, iOff, output, oOff, iLen);
            return 8;
        }
        return 0;
    }

    private static short calcAPDUCrypto(boolean isWrap, AESKey SENCKey, byte[] input, short iOff, short iLen,
            byte[] buff, short bOff, byte[] buff1, short b1Off, byte[] output, short outOff) {
        short ret = 0;
        if (isWrap) {
            // ----- Response APDU Encryption flow -----
            // 1.) Adjust IV by setting most significant byte of IV to be set to 0x80
            Util.arrayCopyNonAtomic(SCP11BApplet.secSessIV, (short) 0, buff, bOff,
                    (short) SCP11BApplet.secSessIV.length);
            buff[bOff] = (byte) 0x80;

            // 2.) Encrypt newly permutated IV with S-ENC key and AES-ECB to produce the
            // Response IV
            SCP11BApplet.aesECB.init(SENCKey, Cipher.MODE_ENCRYPT);
            short ivLen = SCP11BApplet.aesECB.doFinal(buff, bOff, (short) SCP11BApplet.secSessIV.length, buff, bOff);
            if (ivLen != 16) {
                return -1;
            }

            // 3.) Use the new Response IV to encrypt data with AES-CBC-ISO9797 and S-ENC
            // key and return ciphertext
            SCP11BApplet.aesCBCNoPad.init(SENCKey, Cipher.MODE_ENCRYPT, buff, bOff, ivLen);

            // Copy data from APDU buffer to scratch buffer for caching
            Util.arrayCopyNonAtomic(input, iOff, buff, bOff, iLen);

            // Pad scratch buffer data with padded data into APDU buffer for caching
            short finLen = ISO9797Pad.process(true, buff, bOff, iLen, output, outOff);

            // Copy cached padded data in APDU buffer to scratch buffer
            Util.arrayCopyNonAtomic(output, outOff, buff, bOff, finLen);

            // Encrypt padded data in scratch buffer into APDU buffer
            return SCP11BApplet.aesCBCNoPad.doFinal(buff, bOff, finLen, output, outOff);
        } else {
            // ----- Command APDU Decryption flow -----
            // 1.) IV acts as a counter with first counter at 1 and increment with or
            // without crypto process. Check if there is encrypted data, if no, increment
            // crypto counter and return 0.
            short dataLen = (short) (input[(short) (iOff + 4)] & 0xFF);

            if (dataLen < 8) {
                ret = -1;
            } else if (dataLen == 8) {
                ret = 0;
            } else {
                // 2.) Decrypt using AES-CBC-ISO9797 and return plaintext
                // using S-ENC key taking into account 8 bytes of CMAC
                SCP11BApplet.aesCBCNoPad.init(SENCKey, Cipher.MODE_DECRYPT, SCP11BApplet.secSessIV, (short) 0,
                        (short) SCP11BApplet.secSessIV.length);
                short decLen = SCP11BApplet.aesCBCNoPad.doFinal(input, (short) (iOff + 5), (short) (dataLen - 8), buff,
                        bOff);
                if (decLen > 0) {
                    // Strip off ISO9797 padding and return unpadded plaintext
                    decLen = ISO9797Pad.process(false, buff, bOff, decLen, output, (short) (outOff + 5));

                    // Overwrite the LC byte in the APDU buffer
                    output[(short) (outOff + 4)] = (byte) (decLen & 0xFF);

                    // Copy input 4 bytes APDU header to output
                    Util.arrayCopyNonAtomic(input, iOff, output, outOff, (short) 4);

                    // Return decrypted plaintext length + 5 bytes of APDU headers (CLA, INS, P1,
                    // P2, LC)
                    ret = (short) (decLen + 5);
                } else {
                    ret = -1;
                }
            }
            MathUtil.increment128(SCP11BApplet.secSessIV, (short) 0, buff, bOff, SCP11BApplet.secSessIV, (short) 0);
            return ret;
        }
    }
}