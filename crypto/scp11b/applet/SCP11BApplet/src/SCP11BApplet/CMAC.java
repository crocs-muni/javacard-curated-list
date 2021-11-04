package SCP11BApplet ;

import javacardx.crypto.Cipher;
import javacard.security.AESKey;
import javacard.framework.Util;

public class CMAC {

    public static final short blockSize = 16;
    public static final short subKeys = 2; // Subkeys K1, K2
    public static final short subKeyLen = blockSize;
    public static final byte[] Rb = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x87 };

    public static short process(Cipher aesCipher, AESKey aesKey, byte[] msg, short mOff, short mLen, byte[] buff,
            short bOff, byte[] output, short oOff, short oLen) {
        cmacGenerateSubKey(aesCipher, aesKey, // Key and Cipher
                buff, (short) (bOff + (blockSize * 2)), // L buffer
                buff, bOff, // K1 buffer
                buff, (short) (bOff + blockSize), // K2 buffer
                buff, (short) (bOff + (blockSize * 3)) // Compute buffer
        );
        if (cmacGenerateMAC(aesCipher, aesKey, // Key and Cipher
                msg, mOff, mLen, // Input message buffer
                output, oOff, oLen, // Output tag buffer
                buff, bOff, // K1 buffer
                buff, (short) (bOff + blockSize), // K2 buffer
                buff, (short) (bOff + (blockSize * 2)), // Message computation buffer
                buff, (short) (bOff + (blockSize * 3)) // Ciphertext buffer
        )) {
            return oLen;
        }
        return 0;
    }

    private static void cmacGenerateSubKey(Cipher aesCipher, AESKey aesKey, byte[] lBuff, short lOff, byte[] k1Buff,
            short k1Off, byte[] k2Buff, short k2Off, byte[] buff, short buffOff) {
        // 1.) Use an AES key to encrypt blockSize of 0 bits resulting in L
        Util.arrayFillNonAtomic(lBuff, lOff, blockSize, (byte) 0x00);
        aesCipher.init(aesKey, Cipher.MODE_ENCRYPT);
        aesCipher.doFinal(lBuff, lOff, blockSize, lBuff, lOff);

        // 2.) If MSB of L == 0, K1 = L << 1. Else K1 = (L << 1) ^ Rb.
        Util.arrayCopyNonAtomic(lBuff, lOff, k1Buff, k1Off, blockSize);
        MathUtil.shiftLeft(k1Buff, k1Off, blockSize, (short) 1);
        if ((lBuff[lOff] & (byte) 0x80) != (byte) 0x00) {
            MathUtil.xorN(k1Buff, k1Off, Rb, (short) 0, blockSize, k1Buff, k1Off);
        }

        // 3.) If MSB of K1 == 0, K2 = K1 << 1. Else K2 = (K1 << 1) ^ Rb.
        Util.arrayCopyNonAtomic(k1Buff, k1Off, k2Buff, k2Off, blockSize);
        MathUtil.shiftLeft(k2Buff, k2Off, blockSize, (short) 1);
        if ((k1Buff[k1Off] & (byte) 0x80) != (byte) 0x00) {
            MathUtil.xorN(k2Buff, k2Off, Rb, (short) 0, blockSize, k2Buff, k2Off);
        }
    }

    private static boolean cmacGenerateMAC(Cipher aesCipher, AESKey aesKey, byte[] message, short mOff, short mLen,
            byte[] output, short oOff, short oLen, byte[] k1Buff, short k1Off, byte[] k2Buff, short k2Off, byte[] buff,
            short bOff, byte[] buff1, short bOff1) {
        short n = MathUtil.ceil(mLen, blockSize);
        boolean lastBlockComplete = false;

        if (oLen > blockSize) {
            // Requested tag length is incorrect
            return false;
        }

        // Clear buffers
        Util.arrayFillNonAtomic(buff, bOff, blockSize, (byte) 0x00); // Message computation buffer
        Util.arrayFillNonAtomic(buff1, bOff1, blockSize, (byte) 0x00); // Ciphertext buffer

        if (n == 0) {
            n = 1;
        } else {
            if ((mLen % blockSize) == 0) {
                lastBlockComplete = true;
            }
        }

        for (short i = 0; i < n; i++) {
            if (i == (short) (n - 1)) {
                // Last block
                // If is complete block, no padding needed
                if (!lastBlockComplete) {
                    // Copy remaining message data and pad with 10...0 pad
                    short off = 0;
                    short len = 0;
                    if (mLen > 0) {
                        if (i == 0) {
                            len = (short) (blockSize - mLen);
                        } else {
                            off = (short) (i * blockSize);
                            len = (short) (mLen - off);
                        }
                    }
                    Util.arrayFillNonAtomic(buff, bOff, blockSize, (byte) 0x00); // Pre-emptive zero byte filling
                    Util.arrayCopyNonAtomic(message, (short) (off + mOff), buff, bOff, len);
                    buff[(short) (bOff + len)] = (byte) 0x80;

                    // PreMfinal = Remaining padded message XOR with K2
                    MathUtil.xorN(buff, bOff, k2Buff, k2Off, blockSize, buff, bOff);
                } else {
                    // Copy 1 blockSize worth of message data and process
                    Util.arrayCopyNonAtomic(message, (short) (mOff + (i * blockSize)), buff, bOff, blockSize);

                    // PreMfinal = Remaining message XOR with K1
                    MathUtil.xorN(buff, bOff, k1Buff, k1Off, blockSize, buff, bOff);
                }

                // Mfinal = Previous existing ciphertext XOR with PreMfinal
                MathUtil.xorN(buff1, bOff1, buff, bOff, blockSize, buff, bOff);

                // Encrypt Mfinal with K
                aesCipher.init(aesKey, Cipher.MODE_ENCRYPT);
                aesCipher.doFinal(buff, bOff, blockSize, buff, bOff);

                // Extract Tag
                Util.arrayCopyNonAtomic(buff, bOff, output, oOff, oLen);
            } else {
                // Not last block
                // Copy 1 blockSize worth of message data and process
                Util.arrayCopyNonAtomic(message, (short) (mOff + (i * blockSize)), buff, bOff, blockSize);

                // Mfinal = Previous existing ciphertext XOR with PreMfinal
                MathUtil.xorN(buff1, bOff1, buff, bOff, blockSize, buff, bOff);

                // Encrypt Mfinal with K
                aesCipher.init(aesKey, Cipher.MODE_ENCRYPT);
                aesCipher.doFinal(buff, bOff, blockSize, buff, bOff);

                // Extract new ciphertext
                Util.arrayCopyNonAtomic(buff, bOff, buff1, bOff1, blockSize);
            }
        }
        return true;
    }
}