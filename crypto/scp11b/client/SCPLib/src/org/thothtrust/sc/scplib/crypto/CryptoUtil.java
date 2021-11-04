package org.thothtrust.sc.scplib.crypto;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.thothtrust.sc.scplib.util.BinUtils;

public class CryptoUtil {

	private static SecureRandom rand = new SecureRandom();

	public static byte[] getSecureRandomBytes(int len) {
		byte[] b = new byte[len];
		rand.nextBytes(b);
		return b;
	}

	public static KeyPair generateECKeyPair(String algo)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException {
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
		parameters.init(new ECGenParameterSpec(algo));
		java.security.spec.ECParameterSpec ecParameterSpec = parameters
				.getParameterSpec(java.security.spec.ECParameterSpec.class);
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
		keyGen.initialize(ecParameterSpec);
		KeyPair kp = keyGen.generateKeyPair();
		return kp;
	}

	public static byte[] getPrivateKeyBytes(KeyPair kp, int keyLength) {
		ECPrivateKey privKey = (ECPrivateKey) kp.getPrivate();
		byte[] s = privKey.getS().toByteArray();
		byte[] outFormattedPrivKey = new byte[keyLength];
		if (s.length > keyLength) {
			System.arraycopy(s, 1, outFormattedPrivKey, 0, keyLength);
		} else if (s.length < keyLength) {
			System.arraycopy(s, 0, outFormattedPrivKey, keyLength - s.length, s.length);
		} else {
			System.arraycopy(s, 0, outFormattedPrivKey, 0, keyLength);
		}
		return outFormattedPrivKey;
	}

	public static byte[] getPrivateKeyBytes(PrivateKey privKey, int keyLength) {
		byte[] s = ((ECPrivateKey) privKey).getS().toByteArray();
		byte[] outFormattedPrivKey = new byte[keyLength];
		if (s.length > keyLength) {
			System.arraycopy(s, 1, outFormattedPrivKey, 0, keyLength);
		} else if (s.length < keyLength) {
			System.arraycopy(s, 0, outFormattedPrivKey, keyLength - s.length, s.length);
		} else {
			System.arraycopy(s, 0, outFormattedPrivKey, 0, keyLength);
		}
		return outFormattedPrivKey;
	}

	public static byte[] getPublicKeyBytes(KeyPair kp, int keyLength, boolean hasHeader) {
		int bytesToCopy = 1 + (2 * keyLength);
		byte[] outFormattedPubKey = new byte[1 + (2 * keyLength)];
		int cursor = 0;
		int copy = 0;
		outFormattedPubKey[cursor] = (byte) 0x04;

		if (!hasHeader) {
			bytesToCopy = 2 * keyLength;
			outFormattedPubKey = new byte[bytesToCopy];
		} else {
			cursor++;
		}

		// Handle key material via key length checking
		byte[] X = ((ECPublicKey) kp.getPublic()).getW().getAffineX().toByteArray();
		if (X.length > keyLength) {
			copy = 1;
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		} else if (X.length < keyLength) {
			copy = keyLength - X.length;
			System.arraycopy(X, 0, outFormattedPubKey, cursor + copy, X.length);
		} else {
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		}
		System.out.println("[DBG] X: " + BinUtils.toHexString(X));
		cursor += keyLength;
		copy = 0;
		byte[] Y = ((ECPublicKey) kp.getPublic()).getW().getAffineY().toByteArray();
		if (Y.length > keyLength) {
			copy = 1;
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		} else if (Y.length < keyLength) {
			copy = keyLength - Y.length;
			System.arraycopy(Y, 0, outFormattedPubKey, cursor + copy, Y.length);
		} else {
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		}
		System.out.println("[DBG] Y: " + BinUtils.toHexString(Y));
		return outFormattedPubKey;
	}

	public static byte[] getPublicKeyBytes(ECPublicKey publicKey, int keyLength, boolean hasHeader) {
		int bytesToCopy = 1 + (2 * keyLength);
		byte[] outFormattedPubKey = new byte[1 + (2 * keyLength)];
		int cursor = 0;
		int copy = 0;
		outFormattedPubKey[cursor] = (byte) 0x04;

		if (!hasHeader) {
			bytesToCopy = 2 * keyLength;
			outFormattedPubKey = new byte[bytesToCopy];
		} else {
			cursor++;
		}

		// Handle key material via key length checking
		byte[] X = publicKey.getW().getAffineX().toByteArray();
		if (X.length > keyLength) {
			copy = 1;
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		} else if (X.length < keyLength) {
			copy = keyLength - X.length;
			System.arraycopy(X, 0, outFormattedPubKey, cursor + copy, X.length);
		} else {
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		}
		System.out.println("[DBG] X: " + BinUtils.toHexString(X));
		cursor += keyLength;
		copy = 0;
		byte[] Y = publicKey.getW().getAffineY().toByteArray();
		if (Y.length > keyLength) {
			copy = 1;
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		} else if (Y.length < keyLength) {
			copy = keyLength - Y.length;
			System.arraycopy(Y, 0, outFormattedPubKey, cursor + copy, Y.length);
		} else {
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		}
		System.out.println("[DBG] X: " + BinUtils.toHexString(Y));
		return outFormattedPubKey;
	}

	public static PublicKey getPublicKey(byte[] xBytes, byte[] yBytes)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException {

		BigInteger x = new BigInteger(BinUtils.toHexString(xBytes), 16);
		BigInteger y = new BigInteger(BinUtils.toHexString(yBytes), 16);
		ECPoint w = new ECPoint(x, y);
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
		parameters.init(new ECGenParameterSpec("secp256r1"));
		java.security.spec.ECParameterSpec ecParameterSpec = parameters
				.getParameterSpec(java.security.spec.ECParameterSpec.class);
		ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(w, ecParameterSpec);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPublicKey) keyFactory.generatePublic(ecPublicKeySpec);
	}

	public static PublicKey getPublicKey(byte[] xyBytes, String ecAlgo)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException {
		int keyLength = xyBytes.length / 2;
		byte[] xBytes = new byte[keyLength];
		byte[] yBytes = new byte[keyLength];
		System.arraycopy(xyBytes, 0, xBytes, 0, keyLength);
		System.arraycopy(xyBytes, keyLength, yBytes, 0, keyLength);
		BigInteger x = new BigInteger(BinUtils.toHexString(xBytes), 16);
		BigInteger y = new BigInteger(BinUtils.toHexString(yBytes), 16);
		ECPoint w = new ECPoint(x, y);
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
		parameters.init(new ECGenParameterSpec(ecAlgo));
		java.security.spec.ECParameterSpec ecParameterSpec = parameters
				.getParameterSpec(java.security.spec.ECParameterSpec.class);
		ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(w, ecParameterSpec);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPublicKey) keyFactory.generatePublic(ecPublicKeySpec);
	}

	public static PrivateKey getPrivateKey(byte[] sBytes, String ecAlgo)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException {
		BigInteger s = new BigInteger(BinUtils.toHexString(sBytes), 16);
		AlgorithmParameters parameters = AlgorithmParameters.getInstance("EC");
		parameters.init(new ECGenParameterSpec(ecAlgo));
		java.security.spec.ECParameterSpec ecParameterSpec = parameters
				.getParameterSpec(java.security.spec.ECParameterSpec.class);
		ECPrivateKeySpec ecPrivateKeySpec = new ECPrivateKeySpec(s, ecParameterSpec);
		KeyFactory keyFactory = KeyFactory.getInstance("EC");
		return (ECPrivateKey) keyFactory.generatePrivate(ecPrivateKeySpec);
	}

	public static KeyPair getKeyPair(byte[] publicXYBytes, byte[] privateBytes, String ecAlgo)
			throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeySpecException {
		return new KeyPair(getPublicKey(publicXYBytes, ecAlgo), getPrivateKey(privateBytes, ecAlgo));
	}

	public static byte[] getRSSignatureBytes(BigInteger r, BigInteger s, int keyLength) {
		int bytesToCopy = 2 * keyLength;
		byte[] outFormattedPubKey = new byte[bytesToCopy];
		int cursor = 0;
		int copy = 0;

		// Handle key material via key length checking
		byte[] X = r.toByteArray();
		if (X.length > keyLength) {
			copy = 1;
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		} else if (X.length < keyLength) {
			copy = keyLength - X.length;
			System.arraycopy(X, 0, outFormattedPubKey, cursor + copy, X.length);
		} else {
			System.arraycopy(X, copy, outFormattedPubKey, cursor, keyLength);
		}
		cursor += keyLength;
		copy = 0;

		byte[] Y = s.toByteArray();
		if (Y.length > keyLength) {
			copy = 1;
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		} else if (Y.length < keyLength) {
			copy = keyLength - Y.length;
			System.arraycopy(Y, 0, outFormattedPubKey, cursor + copy, Y.length);
		} else {
			System.arraycopy(Y, copy, outFormattedPubKey, cursor, keyLength);
		}

		return outFormattedPubKey;
	}

	public static boolean verifyECCSignatureASN1(String curveAlgo, String signAlgo, byte[] xyPubKey, byte[] rawMessage,
			byte[] asn1Signature) throws NoSuchAlgorithmException, InvalidParameterSpecException,
			InvalidKeySpecException, InvalidKeyException, SignatureException {
		Signature ecdsa = Signature.getInstance(signAlgo); // SHA256withECDSA
		PublicKey publicKey = getPublicKey(xyPubKey, curveAlgo); // secp256r1
		ecdsa.initVerify(publicKey);
		ecdsa.update(rawMessage);
		return ecdsa.verify(asn1Signature);
	}

	public static byte[] deriveECSharedSecret(byte[] pubKey, KeyPair kp) throws NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, InvalidKeyException {
		byte[] xBytes = new byte[32];
		byte[] yBytes = new byte[32];
		System.arraycopy(pubKey, 0, xBytes, 0, 32);
		System.arraycopy(pubKey, 32, yBytes, 0, 32);
		System.out.println("X: " + BinUtils.toHexString(xBytes));
		System.out.println("Y: " + BinUtils.toHexString(yBytes));
		ECPublicKey targetPubKey = (ECPublicKey) getPublicKey(xBytes, yBytes);
		KeyAgreement ka = KeyAgreement.getInstance("ECDH");
		ka.init(kp.getPrivate());
		ka.doPhase(targetPubKey, true);
		return ka.generateSecret();
	}

	public static byte[] deriveECSharedSecret(ECPublicKey targetPubKey, KeyPair kp) throws NoSuchAlgorithmException,
			InvalidParameterSpecException, InvalidKeySpecException, InvalidKeyException {
		KeyAgreement ka = KeyAgreement.getInstance("ECDH");
		ka.init(kp.getPrivate());
		ka.doPhase(targetPubKey, true);
		return ka.generateSecret();
	}

	public static byte[] aesWrapKey(byte[] kek, byte[] iv, byte[] data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec kekKey = new SecretKeySpec(kek, "AES");
		cipher.init(Cipher.ENCRYPT_MODE, kekKey, new IvParameterSpec(iv));
		return cipher.doFinal(data);
	}

	public static byte[] aesUnwrapKey(byte[] kek, byte[] iv, byte[] data)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec kekKey = new SecretKeySpec(kek, "AES");
		cipher.init(Cipher.DECRYPT_MODE, kekKey, new IvParameterSpec(iv));
		return cipher.doFinal(data);
	}

	public static byte[] macData(byte[] macKey, byte[] data) throws NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec macKeySpec = new SecretKeySpec(macKey, "HmacSHA256");
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		hmacSHA256.init(macKeySpec);
		return hmacSHA256.doFinal(data);
	}

	public static int macData(byte[] macKey, byte[] data, int dataOff, int dataLen, byte[] output, int outOff)
			throws NoSuchAlgorithmException, InvalidKeyException, ShortBufferException, IllegalStateException {
		SecretKeySpec macKeySpec = new SecretKeySpec(macKey, "HmacSHA256");
		Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
		hmacSHA256.init(macKeySpec);
		hmacSHA256.update(data, dataOff, dataLen);
		hmacSHA256.doFinal(output, outOff);
		return hmacSHA256.getMacLength();
	}

	public static byte[] wrapKeyBlob(KeyPair wrappingKeyPair, byte[] targetPublicKey, byte privateKeyToBeWrappedKeyType,
			byte[] privateKeyBytesToBeWrapped)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidParameterSpecException,
			InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, ShortBufferException, IllegalStateException {
		byte[] sharedSecret = CryptoUtil.deriveECSharedSecret(targetPublicKey, wrappingKeyPair);
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] cryptKeyBytes = sha256.digest(sharedSecret);
		sha256.reset();
		byte[] macKeyBytes = sha256.digest(cryptKeyBytes);
		SecureRandom rand = new SecureRandom();
		byte[] iv = new byte[16];
		rand.nextBytes(iv);
		byte[] wrappedPrivateBytes = CryptoUtil.aesWrapKey(cryptKeyBytes, iv, privateKeyBytesToBeWrapped);

		byte[] wrappedData = new byte[119 + wrappedPrivateBytes.length];

		// Arrange bytes
		// key type
		wrappedData[0] = privateKeyToBeWrappedKeyType;

		// wrapping public key length - short
		BinUtils.shortToBytes((short) 64, wrappedData, (short) 1);

		// wrapping public key data
		byte[] wrappingPublicKey = CryptoUtil.getPublicKeyBytes(wrappingKeyPair, 32, false);
		System.arraycopy(wrappingPublicKey, 0, wrappedData, 3, wrappingPublicKey.length);

		// iv length - short
		BinUtils.shortToBytes((short) 16, wrappedData, (short) 67);

		// iv data
		System.arraycopy(iv, 0, wrappedData, 69, iv.length);

		// wrapped data length - short
		BinUtils.shortToBytes((short) wrappedPrivateBytes.length, wrappedData, (short) 85);

		// wrapped data
		System.arraycopy(wrappedPrivateBytes, 0, wrappedData, 87, wrappedPrivateBytes.length);

		// HMAC protect wrapped content
		CryptoUtil.macData(macKeyBytes, wrappedData, 0, 87 + wrappedPrivateBytes.length, wrappedData,
				87 + wrappedPrivateBytes.length);

		return wrappedData;
	}

	public static byte[] unwrapKeyBlob(KeyPair unwrappingKeyPair, byte[] wrappedData)
			throws InvalidKeyException, NoSuchAlgorithmException, InvalidParameterSpecException,
			InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException, ShortBufferException, IllegalStateException {
		// Read bytes
		if (wrappedData.length < 119) {
			return null;
		}

		// Get wrapped private key type
		byte privateKeyToBeWrappedKeyType = wrappedData[0];

		// Get wrapping public key length
		short wrappingPubLen = BinUtils.bytesToShort(wrappedData[1], wrappedData[2]);

		if (wrappingPubLen != 64) {
			System.out.println("[ERR] Wrapping public key length is incorrect !!!");
			return null;
		}

		byte[] wrappingPubKey = new byte[64];
		System.arraycopy(wrappedData, 3, wrappingPubKey, 0, wrappingPubLen);

		// Get IV
		short ivLen = BinUtils.bytesToShort(wrappedData[67], wrappedData[68]);

		if (ivLen != 16) {
			System.out.println("[ERR] IV length is incorrect !!!");
			return null;
		}

		byte[] iv = new byte[ivLen];
		System.arraycopy(wrappedData, 69, iv, 0, ivLen);

		// Get wrapped key length
		short wrappedKeyLen = BinUtils.bytesToShort(wrappedData[85], wrappedData[86]);
		byte[] wrappedKeyData = new byte[wrappedKeyLen];
		System.arraycopy(wrappedData, 87, wrappedKeyData, 0, wrappedKeyLen);

		// Get MAC
		byte[] macData = new byte[32];
		System.arraycopy(wrappedData, 87 + wrappedKeyLen, macData, 0, 32);
		System.out.println("Found MAC: " + BinUtils.toHexString(macData));

		// Derive shared secret
		byte[] sharedSecret = CryptoUtil.deriveECSharedSecret(wrappingPubKey, unwrappingKeyPair);
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte[] cryptKeyBytes = sha256.digest(sharedSecret);
		sha256.reset();
		byte[] macKeyBytes = sha256.digest(cryptKeyBytes);

		// Calculate MAC and compare
		byte[] calcMac = new byte[32];
		CryptoUtil.macData(macKeyBytes, wrappedData, 0, 87 + wrappedKeyLen, calcMac, 0);
		System.out.println("Calculated MAC: " + BinUtils.toHexString(calcMac));
		if (!BinUtils.binArrayElementsCompare(macData, 0, calcMac, 0, 32)) {
			System.out.println("[ERR] MAC result does not match !!!");
			return null;
		}

		// Decrypt secret data payload
		byte[] unwrappedPrivateBytes = CryptoUtil.aesUnwrapKey(cryptKeyBytes, iv, wrappedKeyData);
		return unwrappedPrivateBytes;
	}

}
