package org.thothtrust.sc.scplib.sc;

import javax.smartcardio.ResponseAPDU;

public class DeviceHelper {

	public static byte[] getResponseData(ResponseAPDU apdu) {
		return apdu.getData();
	}

	public static boolean isSuccessfulResponse(ResponseAPDU apdu) {
		byte[] apduBytes = apdu.getBytes();
		if ((apduBytes[apduBytes.length - 2] == (byte) 0x90) && (apduBytes[(apduBytes.length - 1)] == (byte) 0x00)) {
			return true;
		}
		return false;
	}

	public static byte[] getSuccessfulResponseData(ResponseAPDU apdu) {
		byte[] apduBytes = apdu.getBytes();
		if ((apduBytes[apduBytes.length - 2] == (byte) 0x90) && (apduBytes[(apduBytes.length - 1)] == (byte) 0x00)) {
			return apdu.getData();
		} else {
			return null;
		}
	}

	public static byte[] getResponseSW(ResponseAPDU apdu) {
		byte[] apduBytes = apdu.getBytes();
		return new byte[] { apduBytes[apduBytes.length - 2], apduBytes[(apduBytes.length - 1)] };
	}

	public static byte[] getResponseSW(byte[] apdu) {
		return new byte[] { apdu[apdu.length - 2], apdu[(apdu.length - 1)] };
	}
	
}
