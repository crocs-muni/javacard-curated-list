package org.thothtrust.sc.scplib.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

import org.thothtrust.sc.scplib.test.SCPTest;
import org.thothtrust.sc.scplib.util.BinUtils;
import org.thothtrust.sc.scplib.util.MathUtil;

public class SCPCryptoUtil {

	public static final short SCP11BIDParams = (short) 0x1100;
	public static final byte[] CRTTLCA6 = { (byte) 0xA6, (byte) 0x0D, (byte) 0x90, (byte) 0x02, (byte) 0x11,
			(byte) 0x00, (byte) 0x95, (byte) 0x01, (byte) 0x3C, (byte) 0x80, (byte) 0x01, (byte) 0x88, (byte) 0x81,
			(byte) 0x01, (byte) 0xA0 };
	public static final byte[] ePKOCEECKATAG = { (byte) 0x5F, (byte) 0x49 };

	// Assumes ephemeral off-card keypair has already been generated and card has
	// responded with receipt
	public static SCPSession initSecureChannelOpen(KeyPair oceKP, ECPublicKey cardLongTermPublicKey, byte[] receipt,
			int receiptOff, int receiptLen) throws NoSuchAlgorithmException, InvalidKeyException,
			InvalidParameterSpecException, InvalidKeySpecException, NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] buff = new byte[258];
		byte[] outputKeyStream = new byte[160];
		byte[] receiptCMAC = null;
		byte[] cardEphemeralPublicKeyData = null;
		int cardEphemeralPublicKeyLen;
		ECPublicKey cardEphemeralPublicKey;
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

		// 1.) Extract card ephemeral public key and CMAC MAC from receipt data
		int rrOff = 0;
		if ((receiptLen >= 86) && (receipt[receiptOff] == (byte) 0x5F) && (receipt[receiptOff + 1] == (byte) 0x49)) {
			if (receipt[receiptOff + 2] == (byte) 0x81) {
				cardEphemeralPublicKeyLen = (int) (receipt[receiptOff + 3] & 0xFF);
				cardEphemeralPublicKeyData = new byte[cardEphemeralPublicKeyLen];
				System.arraycopy(receipt, receiptOff + 4, cardEphemeralPublicKeyData, 0, cardEphemeralPublicKeyLen);
				rrOff += 4 + cardEphemeralPublicKeyLen;
			} else {
				cardEphemeralPublicKeyLen = (int) (receipt[receiptOff + 2] & 0xFF);
				cardEphemeralPublicKeyData = new byte[cardEphemeralPublicKeyLen];
				System.arraycopy(receipt, receiptOff + 3, cardEphemeralPublicKeyData, 0, cardEphemeralPublicKeyLen);
				rrOff += 3 + cardEphemeralPublicKeyLen;
			}
			System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Card Ephemeral Public Key: "
					+ BinUtils.toHexString(cardEphemeralPublicKeyData));
			byte[] xy = new byte[cardEphemeralPublicKeyLen - 1];
			System.arraycopy(cardEphemeralPublicKeyData, 1, xy, 0, cardEphemeralPublicKeyLen - 1);
			cardEphemeralPublicKey = (ECPublicKey) CryptoUtil.getPublicKey(xy, "secp256r1");
			if (cardEphemeralPublicKey == null) {
				System.out.println(
						"SCPCryptoUtil :: initSecureChannelOpen() :: Failed to convert card long term pub key to key");
				return null;
			}

			// Parsing SCP11 output receipt for the CMAC within the receipt
			if ((receipt[receiptOff + rrOff] == (byte) 0x86) && (receipt[receiptOff + rrOff + 1] == (byte) 0x10)) {
				rrOff += 2;
				
				// Creating buffer and copy in the receipt CMAC given by the card
				receiptCMAC = new byte[16];
				System.arraycopy(receipt, receiptOff + rrOff, receiptCMAC, 0, 16);
			} else {
				System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Receipt MAC length incorrect");
				return null;
			}
		} else {
			System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Receipt MAC header incorrect");
			return null;
		}

		// 2.) Generate ShSes via ECDH card long term private key with OCE ephemeral
		// public key
		int kdfOff = 0;
		int kdfLen = 0;
		int kdfCtrOff = 0; // Short cut caching of counter
		byte[] sharedSecret = null;
		try {
			sharedSecret = CryptoUtil.deriveECSharedSecret(cardLongTermPublicKey, oceKP);
		} catch (Exception e) {
			System.out.println(
					"SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSes #1 derivation failed with exception");
			e.printStackTrace();
		}
		if (sharedSecret == null) {
			System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSes derivation failed");
			return null;
		}
		System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSes #1: " + BinUtils.toHexString(sharedSecret));
		int kLen = sharedSecret.length;
		System.arraycopy(sharedSecret, 0, buff, kdfOff, kLen);
		kdfOff += kLen;
		kdfLen += kLen;

		// 3.) Generate ShSee via ECDH card ephemeral private key with OCE ephemeral
		// public key
		try {
			sharedSecret = CryptoUtil.deriveECSharedSecret(cardEphemeralPublicKey, oceKP);
		} catch (Exception e) {
			System.out.println(
					"SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSee #2 derivation failed with exception");
			e.printStackTrace();
		}
		if (sharedSecret == null) {
			System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSee derivation failed");
			return null;
		}
		System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Shared Secret for ShSee #2: " + BinUtils.toHexString(sharedSecret));
		kLen = sharedSecret.length;
		System.arraycopy(sharedSecret, 0, buff, kdfOff, kLen);
		kdfOff += kLen;
		kdfLen += kLen;

		// 4.) Set aside 32-bit space for KDF counter and set counter to 0x00000001
		kdfOff += 4;
		kdfLen += 4;
		kdfCtrOff = kdfOff - 1;
		buff[kdfCtrOff] = (byte) 0x01;

		// 5.) Derive AES session keys from X9.63-KDF(concat(ShSee || ShSes), counter,
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
		
		SCPTest.kdfData = new byte[kdfLen]; // DEBUG ONLY
		System.arraycopy(buff, 0, SCPTest.kdfData, 0, kdfLen);		

		// Gen key 1 with counter 1
		sha256.update(buff, 0, kdfLen);
		sharedSecret = sha256.digest();
		System.arraycopy(sharedSecret, 0, outputKeyStream, 0, 32);

		// Increment counter
		buff[kdfCtrOff] = (byte) 0x02;

		// Gen key 2 with counter 2
		sha256.reset();
		sha256.update(buff, 0, kdfLen);
		sharedSecret = sha256.digest();
		System.arraycopy(sharedSecret, 0, outputKeyStream, 32, 32);

		// Increment counter
		buff[kdfCtrOff] = (byte) 0x03;

		// Gen key 3 with counter 3
		sha256.reset();
		sha256.update(buff, 0, kdfLen);
		sharedSecret = sha256.digest();
		System.arraycopy(sharedSecret, 0, outputKeyStream, 64, 32);

		// Increment counter
		buff[kdfCtrOff] = (byte) 0x04;

		// Gen key 4 with counter 4
		sha256.reset();
		sha256.update(buff, 0, kdfLen);
		sharedSecret = sha256.digest();
		System.arraycopy(sharedSecret, 0, outputKeyStream, 96, 32);

		// Increment counter
		buff[kdfCtrOff] = (byte) 0x05;

		// Gen key 5 with counter 5
		sha256.reset();
		sha256.update(buff, 0, kdfLen);
		sharedSecret = sha256.digest();
		System.arraycopy(sharedSecret, 0, outputKeyStream, 128, 32);

		System.out.println("Keystream Data: " + BinUtils.toHexString(outputKeyStream));
		SCPTest.outputKeyStream = new byte[outputKeyStream.length]; // DEBUG ONLY
		System.arraycopy(outputKeyStream, 0, SCPTest.outputKeyStream, 0, outputKeyStream.length);	

		SCPSession sess = new SCPSession(outputKeyStream, 0, CryptoUtil.getPublicKeyBytes(oceKP, 32, true),
				cardEphemeralPublicKeyData);

		// 6.) Verifies receipt from card
		boolean isValidReceipt = verifySCP11BReceipt(sess, receiptCMAC);

		// 7.) Clear session data
		Arrays.fill(buff, (byte) 0x00);
		Arrays.fill(outputKeyStream, (byte) 0x00);
		buff = null;
		outputKeyStream = null;
		if (isValidReceipt) {
			return sess;
		} else {
			System.out.println("SCPCryptoUtil :: initSecureChannelOpen() :: Invalid receipt");
		}

		return null;
	}

	public static byte[] handleMessage(boolean isWrap, SCPSession sessionData, byte[] apduBuf, int apduBufOff,
			int apduLen) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		byte[] ret = null;
		if (isWrap) {
			// Encrypt then MAC APDU Command
			// Encrypt plaintext data
			byte[] encData = calcAPDUCrypto(true, sessionData, apduBuf, apduBufOff, apduLen);
			if (encData == null) {
				return null; // Return error if there is error
			}
			System.out.println("Encrypted APDU: " + BinUtils.toHexString(encData));

			// Calculate CMAC on top of ciphertext data and return combined ciphertext and
			// CMAC data as APDU Command
			byte[] mac = calcAPDUMAC(true, sessionData, encData, 0, encData.length);
			if (mac == null) {
				return null;
			}
			System.out.println("Calculated MAC: " + BinUtils.toHexString(mac));

			// Return wrapped command <Header :: CLA, INS, P1, P2, Lcc><Enciphered
			// data><CMAC>
			ret = new byte[encData.length + mac.length];
			System.arraycopy(encData, 0, ret, 0, encData.length);
			System.arraycopy(mac, 0, ret, encData.length, mac.length);

			// Adjust final Lcc to reflect inclusion of MAC length
			ret[4] = (byte) ((encData.length - 5 + mac.length) & 0xFF);
		} else {
			// MAC then Decrypt APDU Response
			// Calculate CMAC on APDU Response data buffer minus 8 bytes CMAC and 2 bytes SW
			byte[] mac = calcAPDUMAC(false, sessionData, apduBuf, apduBufOff, (short) (apduLen - 10));
			if (mac == null) {
				return null;
			}
			System.out.println("Calculated MAC: " + BinUtils.toHexString(mac));
			byte[] receivedMac = new byte[8];
			System.arraycopy(apduBuf, apduBufOff + apduLen - 10, receivedMac, 0, 8);
			System.out.println("Received MAC: " + BinUtils.toHexString(receivedMac));
			// Check MAC
			if (BinUtils.binArrayElementsCompare(receivedMac, 0, mac, 0, 8)) {
				// Decrypt ciphertext data if MAC check passes and return decrypted plaintext
				if (apduLen - 10 == 0) {
					// Possibly no content data and only SW returned, therefore no need to decrypt
					ret = new byte[2];
					System.arraycopy(apduBuf, apduBufOff + apduLen - 2, ret, 0, 2);
				} else {
					ret = calcAPDUCrypto(false, sessionData, apduBuf, apduBufOff, apduLen);
				}
			} else {
				System.out.println("SCPCryptoUtil :: handleMessage() :: MAC incorrect");
				return null; // Incorrect MAC
			}
		}
		return ret;
	}

	private static boolean verifySCP11BReceipt(SCPSession sessionData, byte[] receiptCMAC)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] buff = new byte[258];
		byte[] oceEcPublicKey = sessionData.getOcePublicKeyData();
		byte[] cardPublicKey = sessionData.getCardEphemeralPublicKeyData();
		int oceECPubKLen = oceEcPublicKey.length;
		int ecPubKLen = cardPublicKey.length;
		int buffOff = 0;

		// 1.) Generate CRT-TLV internally a.k.a 'CRT-TLV-A6'
		// CRT-TLV-SCPIDParam - T=0x90, L=0x02, V=0x1100
		// CRT-TLV-KeyUsage - T=0x95, L=0x01, V=0x3C
		// CRT-TLV-KeyType - T=0x80, L=0x01, V=0x88
		// CRT-TLV-KeyLength - T=81, L=0x01, V=A0
		// CRT-TLV-A6 encapsulate all sub-TLVs - T=0xA6, L=0x0D, V=<All the above
		// sub-TLVs
		System.arraycopy(CRTTLCA6, 0, buff, buffOff, CRTTLCA6.length);

		// 2.) Concat ePK.OCE.ECKA-TLV - T=0x5F49, L=TLVLen(oceEcPublicKey),
		// V=<oceEcPublicKey> behind CRT-TLV-A6
		System.arraycopy(ePKOCEECKATAG, 0, buff, buffOff + CRTTLCA6.length, ePKOCEECKATAG.length);
		int processLen = 0;
		if (oceECPubKLen >= 128) {
			System.arraycopy(oceEcPublicKey, 0, buff, buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 2,
					oceECPubKLen);
			buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length)] = (byte) 0x81;
			buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 1)] = (byte) (oceECPubKLen & 0xFF);
			processLen = (short) (CRTTLCA6.length + ePKOCEECKATAG.length + oceECPubKLen + 2);
		} else {
			System.arraycopy(oceEcPublicKey, 0, buff, buffOff + CRTTLCA6.length + ePKOCEECKATAG.length + 1,
					oceECPubKLen);
			buff[(short) (buffOff + CRTTLCA6.length + ePKOCEECKATAG.length)] = (byte) (oceECPubKLen & 0xFF);
			processLen = (short) (CRTTLCA6.length + ePKOCEECKATAG.length + oceECPubKLen + 1);
		}

		// 3.) Write return receipt with ePK.SD.ECKA TLV tag first where T=0x5F49,
		// L=Len(ePK.SD.ECKA), V=ePK.SD.ECKA)
		int rOff = 0;
		int encodedPKLen = 0;
		if (ecPubKLen >= 128) {
			encodedPKLen = 2;
		} else {
			encodedPKLen = 1;
		}
		byte[] receipt = new byte[ePKOCEECKATAG.length + encodedPKLen + ecPubKLen + 18];
		System.arraycopy(ePKOCEECKATAG, (short) 0, receipt, rOff, (short) ePKOCEECKATAG.length);
		rOff += ePKOCEECKATAG.length;
		if (ecPubKLen >= 128) {
			System.arraycopy(cardPublicKey, 0, receipt, rOff + 2, ecPubKLen);
			receipt[rOff] = (byte) 0x81;
			receipt[(short) (rOff + 1)] = (byte) (ecPubKLen & 0xFF);
			rOff += (short) (ecPubKLen + 2);
		} else {
			System.arraycopy(cardPublicKey, 0, receipt, rOff + 1, ecPubKLen);
			receipt[rOff] = (byte) (ecPubKLen & 0xFF);
			rOff += (short) (ecPubKLen + 1);
		}

		// Set CMAC T and L values for the CMAC tag.
		receipt[rOff] = (byte) 0x86;
		receipt[(short) (rOff + 1)] = (byte) 0x10;
		rOff += 2;

		// 4.) Create CMACReceipt = AES-CMAC(Key=ReceiptKey, Data= (CRT-TLV-A6 ||
		// ePK.OCE.ECKA-TLV)
		byte[] computedReceiptMAC = CMAC.process(sessionData.getReceiptKey(), buff, 0, processLen, 16);
		if (computedReceiptMAC == null) {
			return false;
		}

		// 4.) Return receipt = (T=0x5F49, L=Len(ePK.SD.ECKA), V=ePK.SD.ECKA) ||
		// (T=0x86, L=0x16, V=CMACReceipt) if CMAC is successful
		System.out.println("Computed Receipt: " + BinUtils.toHexString(computedReceiptMAC));
		System.out.println("Found Receipt: " + BinUtils.toHexString(receiptCMAC));
		System.out.println("Receipt Key: " + BinUtils.toHexString(sessionData.getReceiptKey().getEncoded()));
		if (computedReceiptMAC.length == 16) {
			return BinUtils.binArrayElementsCompare(computedReceiptMAC, 0, receiptCMAC, 0, 16);
		}

		return false;
	}

	private static byte[] calcAPDUMAC(boolean isCommand, SCPSession sessionData, byte[] input, int iOff, int iLen)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException {
		byte[] buff = new byte[16 + iLen];

		// 1.) Concat (MAC Chaining Value || CLA, INS, P1, P2, LC, Data) as target data
		// or encrypted response - RMAC - SW
		System.arraycopy(sessionData.getSessMacChain(), 0, buff, 0, sessionData.getSessMacChain().length);
		System.arraycopy(input, iOff, buff, sessionData.getSessMacChain().length, iLen);

		// Adjust LC length to add 8 for the CMAC according to SCP03 requirements for
		// CMAC
		if (isCommand) {
			short lc = (short) (buff[20] & 0xFF);
			lc += 8;
			buff[20] = (byte) (lc & 0xFF);			
		}
		
		byte[] b = new byte[iLen];
		System.arraycopy(input, iOff, b, 0, iLen);
		System.out.println("Input-To-Be-MAC-ed: " + BinUtils.toHexString(b));		
		System.out.println("To-Be-MAC-ed: " + BinUtils.toHexString(buff));		

		// 2.) CMAC (SMAC Key, target data) for command MAC and CMAC (SRMAC Key, target
		// data) for response MAC
		byte[] mac = null;
		if (isCommand) {
			System.out.println("SMACKey: " + BinUtils.toHexString(sessionData.getSMACKey().getEncoded()));
			mac = CMAC.process(sessionData.getSMACKey(), buff, 0, buff.length, 16);
		} else {
			System.out.println("SRMACKey: " + BinUtils.toHexString(sessionData.getSRMACKey().getEncoded()));
			mac = CMAC.process(sessionData.getSRMACKey(), buff, 0, buff.length, 16);
		}
		System.out.println("Raw MAC: " + BinUtils.toHexString(mac));	
		if (mac != null) {
			if (isCommand) {
				// 3.) CMAC result (entire 16 bytes) is updated as new MAC Chaining Value
				sessionData.setSessMacChain(mac);
			}
			// The leftmost 8 bytes are CMAC/RMAC value
			byte[] output = new byte[8];
			System.arraycopy(mac, 0, output, (short) 0, (short) 8);
			return output;
		}
		return null;
	}

	private static byte[] calcAPDUCrypto(boolean isWrap, SCPSession sessionData, byte[] apduBuf, int apduBufOff,
			int apduLen) throws InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] ret = null;
		Cipher aesCbcCipher = Cipher.getInstance("AES/CBC/NoPadding");
		if (isWrap) {
			// ----- Command APDU Encryption flow -----
			// Assumed input apduBuf should be: <CLA :: 1 byte><INS :: 1 byte><P1 :: 1 byte>
			// <P2 :: 1 byte><Lc :: 1 byte><data :: Lc bytes>
			if (apduLen < 5) {
				// APDU command including header should never be less than 5 bytes
				return null;
			}

			int dataLen = (int) (apduBuf[apduBufOff + 4] & 0xFF);
			if (dataLen == 0) {
				// Secure Channel Protocol '03' – Public Release v1.1.2, Section 6.2.6. No
				// encryption shall be applied to a command where there is no command data
				// field. In this case, the encryption counter shall still be incremented as
				// described above, and the message shall be protected as defined in section
				// 6.2.4. Otherwise the off-card entity performs the process detailed hereafter.
				ret = new byte[5];
				System.arraycopy(apduBuf, apduBufOff, ret, 0, ret.length);
			} else {
				// 1.) Pad with ISO9797
				// 5 APDU headers + additional 16 byte block to allow padding for buffering
				byte[] buff = new byte[dataLen + 21];

				// Pad
				int len = ISO9797Pad.process(true, apduBuf, apduBufOff + 5, dataLen, buff, 5);
				System.out.println("Finished ISO9797 Pad with padded len: " + len);
				
				// Copy 4 APDU headers to buff too and leave the LC byte setting after
				// encryption
				System.arraycopy(apduBuf, apduBufOff, buff, 0, 4);

				// 2.) Encrypt using AES-CBC-ISO9797 and return ciphertext using S-ENC key.
				aesCbcCipher.init(Cipher.ENCRYPT_MODE, sessionData.getSENCKey(),
						new IvParameterSpec(sessionData.getSessCtr()));
				aesCbcCipher.doFinal(buff, 5, len, buff, 5);

				// Overwrite the LC byte in the APDU buffer
				buff[4] = (byte) (len & 0xFF);

				// Return encrypted ciphertext
				ret = new byte[5 + len];
				System.arraycopy(buff, 0, ret, 0, ret.length);
			}
			MathUtil.increment128(sessionData.getSessCtr(), (short) 0, sessionData.getSessCtr(), (short) 0);
		} else {
			// ----- Response APDU Decryption flow -----
			// Assumed input apduBuf should be: <encrypted apdu response :: apduLen - 8 -
			// 2><cmac :: 8 bytes><sw :: 2 bytes>
			if (apduLen < 10) {
				// APDU response is never less than 10 bytes
				return null;
			}

			// 1.) Adjust IV by setting most significant byte of IV to be set to 0x80
			byte[] iv = new byte[16];
			System.arraycopy(sessionData.getSessCtr(), 0, iv, 0, sessionData.getSessCtr().length);
			iv[0] = (byte) 0x80;

			// 2.) Encrypt newly permutated IV with S-ENC key and AES-ECB to produce the
			// Response IV
			Cipher aesEcbCipher = Cipher.getInstance("AES/ECB/NoPadding");
			aesEcbCipher.init(Cipher.ENCRYPT_MODE, sessionData.getSENCKey());
			if (aesEcbCipher.doFinal(iv, 0, iv.length, iv, 0) != 16) {
				return null;
			}

			// 3.) Prepare compute buffer data is assumed and copy only the encrypted
			// response
			byte[] buff = new byte[apduLen - 10];
			System.arraycopy(apduBuf, apduBufOff, buff, 0, buff.length);
			System.out.println("To-Be-Decrypted Data: " + BinUtils.toHexString(buff));

			// 4.) Use the new Response IV to decrypt data with AES-CBC and S-ENC
			// key and return plaintext
			aesCbcCipher.init(Cipher.DECRYPT_MODE, sessionData.getSENCKey(), new IvParameterSpec(iv));
			int len = aesCbcCipher.doFinal(buff, 0, buff.length, buff, 0);
			System.out.println("After Decrypted Data: " + BinUtils.toHexString(buff));

			// 5.) ISO9797 unpad for final plaintext using null output to returning only
			// length of plaintext
			len = ISO9797Pad.process(false, buff, 0, len, null, 0);
			if (len == -1) {
				// Error unpadding
				return null;
			}

			// Copy plaintext data and the supposed CMAC and SW to return
			ret = new byte[len + 10];
			System.arraycopy(buff, 0, ret, 0, len);
			System.arraycopy(apduBuf, apduBufOff + buff.length, ret, len, 10);
		}
		return ret;
	}
}