package org.thothtrust.sc.scplib.util;

public class ExportKeyObjectManifest {

	private short version = 0;
	private byte publicKeyType = (byte) 0x00;
	private byte privateKeyProtectionMech = (byte) 0x00;
	private byte[] publicKeyData = null;
	private byte[] privateKeyData = null;

	public ExportKeyObjectManifest() {
	}

	public ExportKeyObjectManifest(short version, byte publicKeyType, byte[] publicKeyData, byte[] privateKeyData,
			byte privateKeyProtectionMech) {
		setVersion(version);
		setPublicKeyType(publicKeyType);
		setPublicKeyData(publicKeyData);
		setPrivateKeyData(privateKeyData);
		setPrivateKeyProtectionMech(privateKeyProtectionMech);
	}

	public short getVersion() {
		return version;
	}

	public void setVersion(short version) {
		this.version = version;
	}

	public byte getPublicKeyType() {
		return publicKeyType;
	}

	public void setPublicKeyType(byte publicKeyType) {
		this.publicKeyType = publicKeyType;
	}

	public byte[] getPublicKeyData() {
		return publicKeyData;
	}

	public void setPublicKeyData(byte[] publicKeyData) {
		this.publicKeyData = publicKeyData;
	}

	public byte[] getPrivateKeyData() {
		return privateKeyData;
	}

	public void setPrivateKeyData(byte[] privateKeyData) {
		this.privateKeyData = privateKeyData;
	}

	public byte getPrivateKeyProtectionMech() {
		return privateKeyProtectionMech;
	}

	public void setPrivateKeyProtectionMech(byte privateKeyProtectionMech) {
		this.privateKeyProtectionMech = privateKeyProtectionMech;
	}

}
