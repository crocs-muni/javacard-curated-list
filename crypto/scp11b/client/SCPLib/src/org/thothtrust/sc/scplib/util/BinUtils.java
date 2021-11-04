/*
 * Binary Utility Tools.
 */
package org.thothtrust.sc.scplib.util;

import java.io.UnsupportedEncodingException;

/**
 * Binary Utility Tools.
 *
 * @version 1.0 (Stable)
 * @author ThothTrust Pte Ltd.
 */
public class BinUtils {

	/**
	 * Converts binary string to hexadecimal represented string.
	 *
	 * @param bytes Byte array input
	 * @return Hexadecimal representation string
	 */
	public static String toHexString(byte[] bytes) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static String toFormattedHexString(byte[] bytes, int column, int whitespace) {
		StringBuffer strb = new StringBuffer();
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		int wordCtr = 0;
		int wsCtr = 0;
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			strb.append(hexArray[v >>> 4]);
			strb.append(hexArray[v & 0x0F]);
			wordCtr++;
			if (wordCtr == column) {
				strb.append("\r\n");
				if (whitespace > 0) {
					wsCtr = whitespace;
					while (wsCtr > 0) {
						strb.append(" ");
						wsCtr--;
					}
				}
				wordCtr = 0;
			}
		}
		return strb.toString();
	}

	/**
	 * Converts binary string to hexadecimal represented string.
	 * 
	 * @param bytes
	 * @param offset
	 * @param length
	 * @return
	 */
	public static String toHexString(byte[] bytes, int offset, int length) {
		char[] hexArray = "0123456789ABCDEF".toCharArray();
		char[] hexChars = new char[length * 2];
		for (int j = 0; j < length; j++) {
			int v = bytes[j + offset] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Converts binary string to ASCII string.
	 *
	 * @param bytes Byte array input
	 * @return ASCII representation string
	 */
	public static String toAsciiString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
		}
		return null;
	}

	/**
	 * Converts binary string to ASCII string.
	 *
	 * @param bytes  Byte array input
	 * @param offset Offset of byte array input for processing
	 * @param length Length of byte array input for processing
	 * @return ASCII representation string
	 */
	public static String toAsciiString(byte[] bytes, int offset, int length) {
		try {
			return new String(bytes, offset, length, "UTF-8");
		} catch (UnsupportedEncodingException ex) {
		}
		return null;
	}

	/**
	 * Binary array comparison.
	 *
	 * @param srcArray   Source byte array
	 * @param srcOffset  Offset within source byte array
	 * @param destArray  Destination byte array
	 * @param destOffset Offset within destination byte array
	 * @param length     Length of binary data to be compared in bytes
	 * @return Boolean result of comparison if both arrays contain same data within
	 *         the specified parameters
	 */
	public static boolean binArrayElementsCompare(byte[] srcArray, int srcOffset, byte[] destArray, int destOffset,
			int length) {
		boolean isMatch = true;
		if ((srcArray != null) && (destArray != null)) {
			for (int i = 0; i < length; i++) {
				if (srcArray[srcOffset + i] != destArray[destOffset + i]) {
					isMatch = false;
				}
			}
		} else {
			isMatch = false;
		}
		return isMatch;
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}
	
	public static byte[] hexStringToByteArray(String s, int off, int len) {
		byte[] data = new byte[len / 2];
		for (int i = off; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static byte[] flipByteArrayElements(byte[] bArray) {
		byte b;
		byte[] res = new byte[bArray.length];
		for (int i = 0; i < bArray.length; i++) {
			b = bArray[i];
			res[((bArray.length - 1) - i)] = b;
		}
		return res;
	}

	public static String[] preciseSplit(String input, byte delimiter) {
		StringBuffer kv1 = new StringBuffer();
		StringBuffer kv2 = new StringBuffer();
		// byte delimiter = (byte) 0x3D;
		boolean isKeyFound = false;
		for (byte b : input.getBytes()) {
			if (b == delimiter) {
				isKeyFound = true;
			} else {
				if (!isKeyFound) {
					kv1.append(new String(new byte[] { b }));
				} else {
					kv2.append(new String(new byte[] { b }));
				}
			}
		}
		return new String[] { kv1.toString(), kv2.toString() };
	}

	/**
	 * Resize byte array to expected length.
	 *
	 * @param bytes
	 * @param expectedSize
	 * @return
	 */
	public static byte[] resizeBytes(byte[] bytes, int expectedSize) {
		byte[] resArray = new byte[expectedSize];
		if (bytes.length > expectedSize) {
			System.arraycopy(bytes, (bytes.length - expectedSize), resArray, 0, expectedSize);
			return resArray;
		} else if (bytes.length < expectedSize) {
			System.arraycopy(bytes, 0, resArray, (expectedSize - bytes.length), expectedSize);
			return resArray;
		} else {
			return bytes;
		}
	}
	
	public static byte[] concatenate(byte[][] bArray) {
		int ttlSize = 0;
		int pos = 0;
		byte[] response = null;
		
		for (byte[] b : bArray) {
			try {
			ttlSize += b.length;
			} catch (Exception e) {
				System.out.println(pos);
				System.out.println(b);
				e.printStackTrace();
			}
		}
		
		response = new byte[ttlSize];
		
		for (byte[] b : bArray) {
			System.arraycopy(b, 0, response, pos, b.length);
			pos += b.length;
		}
		
		return response;
	}

	public static void shortToBytes(short s, byte[] b, short offset) {
		b[offset] = (byte) ((s >> 8) & 0xFF);
		b[offset + 1] = (byte) (s & 0xFF);
	}

	public static short bytesToShort(byte b1, byte b2) {
		return (short) (((b1 & 0xFF) << 8) | ((b2 & 0xFF) << 0));
	}
	
	public static int bytesToInt(byte[] b, int bOff) {
		return (int) (((b[bOff] & 0xFF) << 24) | ((b[bOff + 1] & 0xFF) << 16) | ((b[bOff + 2] & 0xFF) << 8) | ((b[bOff + 3] & 0xFF) << 0));
	}
	
	public static boolean isBytePrintableAscii(byte b) {
		int i = (int) (b & 0xFF);
		if (i >= 32 && i <= 126) {
			return true;
		}
		return false;
	}
	
	public static boolean isBytePrintableAscii(int b) {
		if ((b == 10) || (b == 13) || (b >= 32 && b <= 126)) {
			return true;
		}
		return false;
	}

	public static void intToBytes(int i, byte[] b, int offset) {
		b[offset] = (byte) ((i >> 24) & 0xFF);
		b[offset + 1] = (byte) ((i >> 16) & 0xFF);
		b[offset + 2] = (byte) ((i >> 8) & 0xFF);
		b[offset + 3] = (byte) (i & 0xFF);
	}
}
