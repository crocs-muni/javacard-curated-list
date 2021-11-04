package org.thothtrust.sc.scplib.test;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import org.thothtrust.sc.scplib.crypto.CryptoUtil;
import org.thothtrust.sc.scplib.crypto.SCPCryptoUtil;
import org.thothtrust.sc.scplib.crypto.SCPSession;
import org.thothtrust.sc.scplib.sc.APDUResult;
import org.thothtrust.sc.scplib.sc.DeviceManager;
import org.thothtrust.sc.scplib.sc.SEDevice;
import org.thothtrust.sc.scplib.util.BinUtils;

public class SCPTest {

	public static byte[] outputKeyStream = null;
	public static byte[] kdfData = null;

	public static void main(String[] args) {
		try {
			int rounds = 1; // 247;
			int success = 0;
//			int testByteLenLow = 0;
//			int testByteLenUpp = 246;
			int keystreamError = 0;
			SecureRandom srand = new SecureRandom();
			Scanner sc = new Scanner(System.in);
			byte[] tagAid = null;
			tagAid = BinUtils.hexStringToByteArray("5454544741555448");
//			while (true) {
//				System.out.println("Enter Card AID: ");
//				String aidStr = sc.nextLine();
//				if (!aidStr.isEmpty()) {
//					try {
//						tagAid = BinUtils.hexStringToByteArray(aidStr);
//						if (tagAid != null) {
//							break;
//						}
//					} catch (Exception e) {
//					}
//				}
//			}
			DeviceManager devMan = null;
			for (int i = 0; i < rounds; i++) {
				if (devMan == null) {
					devMan = DeviceManager.getInstance(tagAid);
				}
				devMan.refreshDevices();
				SEDevice tag = devMan.getDevice(0);
				if (tag == null) {
					System.out.println("[ERR] Device not found !!!");
					System.exit(1);
				}

				APDUResult res = null;
				// Retrieve card long term key
				res = new APDUResult(tag.send(new CommandAPDU((byte) 0x88, (byte) 0xFF, (byte) 0x00, (byte) 0x00)));
				if (!res.isSuccess()) {
					System.out.println("[ERR] Card long term public key not found !!!");
					System.out.println("SW: " + BinUtils.toHexString(res.getSw()));
					System.exit(1);
				}
				byte[] cardLongTermPublicKeyASN1 = res.getResult();
				byte[] cardLongTermPublicKeyData = new byte[cardLongTermPublicKeyASN1.length - 1];
				System.arraycopy(cardLongTermPublicKeyASN1, 1, cardLongTermPublicKeyData, 0,
						cardLongTermPublicKeyData.length);
				ECPublicKey cardLongTermPubKey = (ECPublicKey) CryptoUtil.getPublicKey(cardLongTermPublicKeyData,
						"secp256r1");
				System.out
						.println("[INF] Card Long Term Public Key: " + BinUtils.toHexString(cardLongTermPublicKeyASN1));

				// Init secure channel - Generate OCE ephemeral keypair and send OCE ephemeral
				// public key into card
				KeyPair oceKP = CryptoUtil.generateECKeyPair("secp256r1");
				byte[] ocePublicKeyData = CryptoUtil.getPublicKeyBytes(oceKP, 32, true);
				System.out.println("[INF] OCE Ephemeral Public Key: " + BinUtils.toHexString(ocePublicKeyData));
				res = new APDUResult(tag.send(
						new CommandAPDU((byte) 0x88, (byte) 0xFF, (byte) 0x01, (byte) 0x00, ocePublicKeyData, 255)));
				if (!res.isSuccess()) {
					System.out.println("[ERR] Receipt generation failed !!!");
					System.out.println("SW: " + BinUtils.toHexString(res.getSw()));
					continue;
				}
				byte[] receipt = res.getResult();
				System.out.println("Card receipt: " + BinUtils.toHexString(receipt));

				// Init secure channel - Use receipt to generate session keys and verify receipt
				SCPSession sessionData = SCPCryptoUtil.initSecureChannelOpen(oceKP, cardLongTermPubKey, receipt, 0,
						receipt.length);

				if (sessionData == null) {
					System.out.println("[ERR] Init secure channel failed !!!");
					continue;
				}

				// Read out debug information to check if keystream and kdf stream are correctly
				// generated
//				res = new APDUResult(tag.send(new CommandAPDU((byte) 0x88, (byte) 0xFF, (byte) 0x04, (byte) 0x00)));
//				byte[] retrievedKeyStream = res.getResult();
//				res = new APDUResult(tag.send(new CommandAPDU((byte) 0x88, (byte) 0xFF, (byte) 0x05, (byte) 0x00)));
//				byte[] retrievedKdfData = res.getResult();
//				System.out.println("[INF] Terminal generated keystream: " + BinUtils.toHexString(outputKeyStream));
//				System.out.println("[INF] Card generated keystream: " + BinUtils.toHexString(retrievedKeyStream));
//				System.out.println("[INF] Card KDF Data bytes: " + BinUtils.toHexString(retrievedKdfData));
//				System.out.println("[INF] Terminal KDF Data bytes: " + BinUtils.toHexString(kdfData));
//				if ((outputKeyStream.length == retrievedKeyStream.length)
//						&& (kdfData.length == retrievedKdfData.length)) {
//					if (BinUtils.binArrayElementsCompare(outputKeyStream, 0, retrievedKeyStream, 0,
//							outputKeyStream.length)
//							&& BinUtils.binArrayElementsCompare(kdfData, 0, retrievedKdfData, 0, kdfData.length)) {
//						System.out.println("[INF] No keystream and KDF data error.");
//					} else {
//						System.out.println("[ERR] Keystream/KDF data error.");
//						keystreamError++;
//						continue;
//					}
//				} else {
//					System.out.println("[ERR] Retrieved keystream or kdf data is incorrect length.");
//					keystreamError++;
//					continue;
//				}

				System.out.println("Beginning to send test data ...");
				
				// Send encrypted command apdu
				byte[] apduEchoPlainHeader = { (byte) 0x88, // CLA
						(byte) 0xFF, // INS
						(byte) 0x02, // P1
						(byte) 0x01 // P2
				};

//				int dataLen = srand.nextInt(testByteLenUpp + 1);
				int dataLen = 239; // i;
				System.out.println("DataLen: " + dataLen);
				byte[] data = new byte[dataLen];
				srand.nextBytes(data);
				byte[] apduEchoPlain = new byte[5 + data.length];
				System.arraycopy(apduEchoPlainHeader, 0, apduEchoPlain, 0, 4);
				apduEchoPlain[4] = (byte) (data.length & 0xFF);
				System.arraycopy(data, 0, apduEchoPlain, 5, data.length);
				System.out.println("Plain APDU before wrap: " + BinUtils.toHexString(apduEchoPlain));

				byte[] apduWrapped = SCPCryptoUtil.handleMessage(true, sessionData, apduEchoPlain, 0,
						apduEchoPlain.length);
				if (apduWrapped == null) {
					System.out.println("[ERR] Wrapping message failed !!!");
					continue;
				}
				System.out.println("Wrapped APDU: " + BinUtils.toHexString(apduWrapped));
				byte[] wrappedPayload = new byte[(int) (apduWrapped[4] & 0xFF)];
				System.arraycopy(apduWrapped, 5, wrappedPayload, 0, wrappedPayload.length);
				System.out.println("Wrapped Payload: " + BinUtils.toHexString(wrappedPayload));
				ResponseAPDU respApdu = tag.send(new CommandAPDU(apduWrapped[0], apduWrapped[1], apduWrapped[2],
						apduWrapped[3], wrappedPayload, 255));
				res = new APDUResult(respApdu);
				if (!res.isSuccess()) {
					System.out.println("[ERR] Sending wrapped message failed !!!");
					System.out.println("SW: " + BinUtils.toHexString(res.getSw()));
					continue;
				}

				// Receive and decrypt response apdu
				byte[] wrappedResponse = respApdu.getBytes();
				System.out.println("Wrapped Response: " + BinUtils.toHexString(wrappedResponse));
				byte[] apduUnwrapped = SCPCryptoUtil.handleMessage(false, sessionData, wrappedResponse, 0,
						wrappedResponse.length);
				System.out.println("Unwrapped Response: " + BinUtils.toHexString(apduUnwrapped));
				if (apduUnwrapped == null) {
					System.out.println("[ERR] Unwrapping message failed !!!");
					continue;
				}
				res = new APDUResult(new ResponseAPDU(apduUnwrapped));
				if (!res.isSuccess()) {
					System.out.println("[ERR] Unwrapped message has SW: " + BinUtils.toHexString(res.getSw()));
				}

				// Extract decrypted plaintext without RMAC
				byte[] decryptedPlaintext = null;
				if (res.getResult().length > 8) {
					decryptedPlaintext = new byte[res.getResult().length - 8];
					System.arraycopy(res.getResult(), 0, decryptedPlaintext, 0, decryptedPlaintext.length);
				} else {
					decryptedPlaintext = new byte[0];
				}
				System.out.println("Decrypted Plaintext: " + BinUtils.toHexString(decryptedPlaintext));
				if ((data.length == decryptedPlaintext.length)
						&& (BinUtils.binArrayElementsCompare(data, 0, decryptedPlaintext, 0, data.length))) {
					System.out.println("SUCCESS !!!");
					success++;
				} else {
					System.out.println("FAILED !!!");
				}
				devMan.disconnectAllExistingDevices();
			}
			System.out.println("Test Result: " + success + "/" + rounds);
			System.out.println("Keystream Error: " + keystreamError);
		} catch (CardException | NoSuchAlgorithmException | InvalidParameterSpecException
				| InvalidAlgorithmParameterException | InvalidKeySpecException | InvalidKeyException
				| NoSuchPaddingException | ShortBufferException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
}