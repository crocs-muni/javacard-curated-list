package SCP11BApplet;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.security.AESKey;
import javacard.security.ECPrivateKey;
import javacard.security.ECPublicKey;
import javacard.security.KeyAgreement;
import javacard.security.KeyPair;
import javacard.security.KeyBuilder;
import javacard.security.MessageDigest;
import javacard.security.RandomData;
import javacardx.crypto.Cipher;
import javacard.framework.Util;

public class SCP11BApplet extends Applet {

    public static KeyProfile hwProfile = null;
    public static ECPrivateKey ecTmpPriv = null;
    public static ECPublicKey ecTmpPub = null;
    public static KeyPair tmpKP = null;
    public static AESKey tmpCryptKey = null;
    public static AESKey SMACKey = null;
    public static AESKey SRMACKey = null;
    public static AESKey SENCKey = null;
    public static AESKey SDECKey = null;
    public static AESKey receiptKey = null;
    public static KeyAgreement ka = null;
    public static MessageDigest sha256 = null;
    public static RandomData rand = null;
    public static Cipher aesECB = null;
    public static Cipher aesCBCNoPad = null;
    public static byte[] b0 = null;
    public static byte[] b1 = null;
    public static byte[] b2 = null;
    public static byte[] hmacSHA256KeyBuff = null;
    public static byte[] secSessMacChain = null;
    public static byte[] secSessIV = null;
    public static byte[] debugBytes = null;
    public static byte[] debugBytes1 = null;
    public static short SW_INVALID_ALGO = (short) 0x6fb1;
    public static short SW_INVALID_KEY_SIZE = (short) 0x6fb2;
    public static byte ALGO_EC_P256R1 = (byte) 0x01;
    public static byte ALGO_EC_P256K1 = (byte) 0x02;

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        b0 = JCSystem.makeTransientByteArray((short) 267, JCSystem.CLEAR_ON_RESET);
        b1 = JCSystem.makeTransientByteArray((short) 267, JCSystem.CLEAR_ON_RESET);
        b2 = JCSystem.makeTransientByteArray((short) 258, JCSystem.CLEAR_ON_RESET);
        hmacSHA256KeyBuff = JCSystem.makeTransientByteArray((short) 32, JCSystem.CLEAR_ON_RESET);
        secSessMacChain = JCSystem.makeTransientByteArray((short) 16, JCSystem.CLEAR_ON_RESET);
        secSessIV = JCSystem.makeTransientByteArray((short) 16, JCSystem.CLEAR_ON_RESET);
        debugBytes = JCSystem.makeTransientByteArray((short) 160, JCSystem.CLEAR_ON_RESET);
        debugBytes1 = JCSystem.makeTransientByteArray((short) 71, JCSystem.CLEAR_ON_RESET);
        rand = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);
        ka = KeyAgreement.getInstance(KeyAgreement.ALG_EC_SVDP_DH_PLAIN, false);
        sha256 = MessageDigest.getInstance(MessageDigest.ALG_SHA_256, false);
        aesCBCNoPad = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_CBC_NOPAD, false);
        aesECB = Cipher.getInstance(Cipher.ALG_AES_BLOCK_128_ECB_NOPAD, false);
        hwProfile = new KeyProfile();
        hwProfile.genNewKeyPair((byte) 0x01);
        SCP11BApplet.ecTmpPriv = (ECPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PRIVATE_TRANSIENT_DESELECT,
                KeyBuilder.LENGTH_EC_FP_256, false);
        SCP11BApplet.ecTmpPub = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC,
                KeyBuilder.LENGTH_EC_FP_256, false);
        ECC.setCommonCurveParameters(SCP11BApplet.ecTmpPriv, (byte) 0x01);
        ECC.setCommonCurveParameters(SCP11BApplet.ecTmpPub, (byte) 0x01);
        SCP11BApplet.tmpKP = new KeyPair(SCP11BApplet.ecTmpPub, SCP11BApplet.ecTmpPriv);
        SMACKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256, false);
        SRMACKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256, false);
        SENCKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256, false);
        SDECKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256, false);
        receiptKey = (AESKey) KeyBuilder.buildKey(KeyBuilder.TYPE_AES_TRANSIENT_RESET, KeyBuilder.LENGTH_AES_256,
                false);
        new SCP11BApplet().register(bArray, (short) (bOffset + 1), bArray[bOffset]);
    }

    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }

        byte[] buf = apdu.getBuffer();
        JCSystem.requestObjectDeletion();

        short len = 0;
        byte p1 = (byte) 0x00;
        byte p2 = (byte) 0x00;

        switch (buf[ISO7816.OFFSET_INS]) {
            case (byte) 0xFF:
                // SCP Debug
                p1 = buf[ISO7816.OFFSET_P1];
                p2 = buf[ISO7816.OFFSET_P2];
                if (p1 == (byte) 0x00) {
                    // Get Card Long Term Public Key
                    len = hwProfile.getPublicKey().getW(buf, (short) 0);
                    apdu.setOutgoingAndSend((short) 0, len);
                } else if (p1 == (byte) 0x01) {
                    // Init Secure Channel
                    len = apdu.setIncomingAndReceive();
                    Util.arrayFillNonAtomic(b0, (short) 0, (short) b0.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(b1, (short) 0, (short) b1.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(b2, (short) 0, (short) b2.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(hmacSHA256KeyBuff, (short) 0, (short) hmacSHA256KeyBuff.length,
                            (byte) 0x00);
                    Util.arrayFillNonAtomic(secSessMacChain, (short) 0, (short) secSessMacChain.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(secSessIV, (short) 0, (short) secSessIV.length, (byte) 0x00);
                    if (SecureChannel.initSecureChannelOpen(buf, apdu.getOffsetCdata(), len, hwProfile.getPrivateKey(),
                            b0, (short) 0, b1, (short) 0, b2, (short) 0, receiptKey, SENCKey, SMACKey, SRMACKey,
                            SDECKey) != 160) {
                        ISOException.throwIt(Util.makeShort((byte) 0x6f, (byte) 0x21));
                    }

                    // // DEBUG: Caching of keystream
                    Util.arrayFillNonAtomic(debugBytes, (short) 0, (short) debugBytes.length, (byte) 0x00);
                    Util.arrayCopyNonAtomic(b0, (short) 0, debugBytes, (short) 0, (short) 160);

                    // SecureChannel.keystreamToKeys(b0, (short) 0, receiptKey, SENCKey, SMACKey,
                    // SRMACKey, SDECKey);
                    len = SecureChannel.generateSCP11BReceipt(receiptKey, buf, apdu.getOffsetCdata(), len, buf,
                            (short) 0, b0, (short) 0, b1, (short) 0);
                    apdu.setOutgoingAndSend((short) 0, len);
                } else if (p1 == (byte) 0x02) {
                    // Receive secure echo message and output unwrapped plaintext back into APDU
                    // buffer
                    len = apdu.setIncomingAndReceive();
                    Util.arrayFillNonAtomic(b0, (short) 0, (short) b0.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(b1, (short) 0, (short) b1.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(b2, (short) 0, (short) b2.length, (byte) 0x00);
                    Util.arrayFillNonAtomic(hmacSHA256KeyBuff, (short) 0, (short) hmacSHA256KeyBuff.length,
                            (byte) 0x00);

                    len = SecureChannel.handleMessage(false, SENCKey, SMACKey, buf, (short) 0, (short) (len + 5), b0,
                            (short) 0, b1, (short) 0, hmacSHA256KeyBuff, (short) 0, b2, (short) 0);

                    if (len != 0) {
                        Util.arrayCopyNonAtomic(b2, (short) 0, buf, (short) 0, len);

                        // Get message len from unwrapped APDU buffer command
                        len = (short) (buf[ISO7816.OFFSET_LC] & 0xFF);
                    }

                    if (p2 == 0x01) {
                        // Wrap with echo response from APDU buffer command data
                        len = SecureChannel.handleMessage(true, SENCKey, SRMACKey, buf, apdu.getOffsetCdata(), len, b0,
                                (short) 0, b1, (short) 0, hmacSHA256KeyBuff, (short) 0, b2, (short) 0);
                    } else {
                        // Wrap without echo with only a RMAC response
                        len = SecureChannel.handleMessage(true, SENCKey, SRMACKey, buf, (short) 0, (short) 0, b0,
                                (short) 0, b1, (short) 0, hmacSHA256KeyBuff, (short) 0, b2, (short) 0);
                    }

                    // Send back wrapped echo
                    Util.arrayCopyNonAtomic(b2, (short) 0, buf, (short) 0, len);
                    apdu.setOutgoingAndSend((short) 0, len);
                } else if (p1 == (byte) 0x03) {
                    // Test secure SW only
                    len = SecureChannel.handleMessage(true, SENCKey, SRMACKey, buf, apdu.getOffsetCdata(), len, b0,
                            (short) 0, b1, (short) 0, hmacSHA256KeyBuff, (short) 0, b2, (short) 0);
                    Util.arrayCopyNonAtomic(b2, (short) 0, buf, (short) 0, len);
                    apdu.setOutgoingAndSend((short) 0, len);
                } else if (p1 == (byte) 0x04) {
                    Util.arrayCopyNonAtomic(debugBytes, (short) 0, buf, (short) 0, (short) debugBytes.length);
                    apdu.setOutgoingAndSend((short) 0, (short) debugBytes.length);
                } else if (p1 == (byte) 0x05) {
                    Util.arrayCopyNonAtomic(debugBytes1, (short) 0, buf, (short) 0, (short) debugBytes1.length);
                    apdu.setOutgoingAndSend((short) 0, (short) debugBytes1.length);
                } else {
                    ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
                }
                break;
            default:
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }
}