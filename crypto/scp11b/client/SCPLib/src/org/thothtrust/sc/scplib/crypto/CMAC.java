package org.thothtrust.sc.scplib.crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import org.thothtrust.sc.scplib.util.MathUtil;

public class CMAC {

	public static final short blockSize = 16;
	public static final short subKeys = 2; // Subkeys K1, K2
	public static final short subKeyLen = blockSize;
	public static final byte[] Rb = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x87 };
	public static final String algo = "AES/ECB/NoPadding";
	private static byte[] k1Buff;
	private static byte[] k2Buff;

	public static byte[] process(Key aesKey, byte[] msg, int mOff, int mLen, int oLen)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		Cipher aesCipher = Cipher.getInstance(algo);
		k1Buff = new byte[blockSize];
		k2Buff = new byte[blockSize];
		cmacGenerateSubKey(aesCipher, aesKey);
		byte[] output = cmacGenerateMAC(aesCipher, aesKey, msg, mOff, mLen, oLen);
		Arrays.fill(k1Buff, (byte) 0x00);
		Arrays.fill(k2Buff, (byte) 0x00);
		return output;
	}

	private static void cmacGenerateSubKey(Cipher aesCipher, Key aesKey)
			throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
		// 1.) Use an AES key to encrypt blockSize of 0 bits resulting in L
		byte[] lBuff = new byte[blockSize];
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
		aesCipher.doFinal(lBuff, 0, blockSize, lBuff, 0);

		// 2.) If MSB of L == 0, K1 = L << 1. Else K1 = (L << 1) ^ Rb.
		System.arraycopy(lBuff, 0, k1Buff, 0, blockSize);
		MathUtil.shiftLeft(k1Buff, (short) 0, blockSize, (short) 1);
		if ((lBuff[0] & (byte) 0x80) != (byte) 0x00) {
			MathUtil.xorN(k1Buff, (short) 0, Rb, (short) 0, blockSize, k1Buff, (short) 0);
		}

		// 3.) If MSB of K1 == 0, K2 = K1 << 1. Else K2 = (K1 << 1) ^ Rb.
		System.arraycopy(k1Buff, 0, k2Buff, 0, blockSize);
		MathUtil.shiftLeft(k2Buff, (short) 0, blockSize, (short) 1);
		if ((k1Buff[0] & (byte) 0x80) != (byte) 0x00) {
			MathUtil.xorN(k2Buff, (short) 0, Rb, (short) 0, blockSize, k2Buff, (short) 0);
		}
	}

	private static byte[] cmacGenerateMAC(Cipher aesCipher, Key aesKey, byte[] message, int mOff, int mLen, int oLen)
			throws InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
		short n = MathUtil.ceil((short) mLen, blockSize);
		boolean lastBlockComplete = false;
		byte[] buff = new byte[blockSize]; // Message computation buffer
		byte[] buff1 = new byte[blockSize]; // Ciphertext buffer
		byte[] output = new byte[oLen];

		if (oLen > blockSize) {
			// Requested tag length is incorrect
			return null;
		}

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
					Arrays.fill(buff, (byte) 0x00); // Pre-emptive zero byte filling
					System.arraycopy(message, (short) (off + mOff), buff, 0, len);
					buff[len] = (byte) 0x80;

					// PreMfinal = Remaining padded message XOR with K2
					MathUtil.xorN(buff, (short) 0, k2Buff, (short) 0, blockSize, buff, (short) 0);
				} else {
					// Copy 1 blockSize worth of message data and process
					System.arraycopy(message, (short) (mOff + (i * blockSize)), buff, 0, blockSize);

					// PreMfinal = Remaining message XOR with K1
					MathUtil.xorN(buff, (short) 0, k1Buff, (short) 0, blockSize, buff, (short) 0);
				}

				// Mfinal = Previous existing ciphertext XOR with PreMfinal
				MathUtil.xorN(buff1, (short) 0, buff, (short) 0, blockSize, buff, (short) 0);

				// Encrypt Mfinal with K
				aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
				aesCipher.doFinal(buff, 0, blockSize, buff, 0);

				// Extract Tag
				System.arraycopy(buff, 0, output, 0, oLen);
			} else {
				// Not last block
				// Copy 1 blockSize worth of message data and process
				System.arraycopy(message, (short) (mOff + (i * blockSize)), buff, (short) 0, blockSize);

				// Mfinal = Previous existing ciphertext XOR with PreMfinal
				MathUtil.xorN(buff1, (short) 0, buff, (short) 0, blockSize, buff, (short) 0);

				// Encrypt Mfinal with K
				aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
				aesCipher.doFinal(buff, 0, blockSize, buff, 0);

				// Extract new ciphertext
				System.arraycopy(buff, 0, buff1, 0, blockSize);
			}
		}
		return output;
	}

}