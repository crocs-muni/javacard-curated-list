package org.thothtrust.sc.scplib.crypto;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;

public class SCPSession {

	private byte[] sessCtr = null;
	private byte[] sessMacChain = null;
	private byte[] ocePublicKeyData = null;
	private byte[] cardEphemeralPublicKeyData = null;
	private Key receiptKey = null;
	private Key SENCKey = null;
	private Key SMACKey = null;
	private Key SRMACKey = null;
	private Key SDEKKey = null;

	public SCPSession() {
	}

	public SCPSession(byte[] keystream, int keyStreamOff, byte[] ocePublicKeyData, byte[] cardEphemeralPublicKeyData) {
		sessCtr = new byte[16];
		sessMacChain = new byte[16];
		sessCtr[15] = (byte) 0x01;
		receiptKey = new SecretKeySpec(keystream, keyStreamOff, 32, "AES");
		SENCKey = new SecretKeySpec(keystream, keyStreamOff + 32, 32, "AES");
		SMACKey = new SecretKeySpec(keystream, keyStreamOff + 64, 32, "AES");
		SRMACKey = new SecretKeySpec(keystream, keyStreamOff + 96, 32, "AES");
		SDEKKey = new SecretKeySpec(keystream, keyStreamOff + 128, 32, "AES");
		setOcePublicKeyData(ocePublicKeyData);
		setCardEphemeralPublicKeyData(cardEphemeralPublicKeyData);
	}

	public SCPSession(byte[] sessCtr, byte[] sessMacChain, byte[] ocePublicKeyData, byte[] cardEphemeralPublicKeyData,
			Key receiptKey, Key SENCKey, Key SMACKey, Key SRMACKey, Key SDEKKey) {
		setSessCtr(sessCtr);
		setSessMacChain(sessMacChain);
		setOcePublicKeyData(ocePublicKeyData);
		setCardEphemeralPublicKeyData(cardEphemeralPublicKeyData);
		setReceiptKey(receiptKey);
		setSENCKey(SENCKey);
		setSMACKey(SMACKey);
		setSRMACKey(SRMACKey);
		setSDEKKey(SDEKKey);
	}

	public byte[] getSessCtr() {
		return sessCtr;
	}

	public void setSessCtr(byte[] sessCtr) {
		this.sessCtr = sessCtr;
	}

	public byte[] getSessMacChain() {
		return sessMacChain;
	}

	public void setSessMacChain(byte[] sessMacChain) {
		this.sessMacChain = sessMacChain;
	}

	public Key getReceiptKey() {
		return receiptKey;
	}

	public void setReceiptKey(Key receiptKey) {
		this.receiptKey = receiptKey;
	}

	public Key getSENCKey() {
		return SENCKey;
	}

	public void setSENCKey(Key sENCKey) {
		SENCKey = sENCKey;
	}

	public Key getSMACKey() {
		return SMACKey;
	}

	public void setSMACKey(Key sMACKey) {
		SMACKey = sMACKey;
	}

	public Key getSRMACKey() {
		return SRMACKey;
	}

	public void setSRMACKey(Key sRMACKey) {
		SRMACKey = sRMACKey;
	}

	public Key getSDEKKey() {
		return SDEKKey;
	}

	public void setSDEKKey(Key sDEKKey) {
		SDEKKey = sDEKKey;
	}

	public byte[] getOcePublicKeyData() {
		return ocePublicKeyData;
	}

	public void setOcePublicKeyData(byte[] ocePublicKeyData) {
		this.ocePublicKeyData = ocePublicKeyData;
	}

	public byte[] getCardEphemeralPublicKeyData() {
		return cardEphemeralPublicKeyData;
	}

	public void setCardEphemeralPublicKeyData(byte[] cardEphemeralPublicKeyData) {
		this.cardEphemeralPublicKeyData = cardEphemeralPublicKeyData;
	}

}