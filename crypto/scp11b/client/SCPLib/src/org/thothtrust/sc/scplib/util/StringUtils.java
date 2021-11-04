package org.thothtrust.sc.scplib.util;

public class StringUtils {

	public static boolean checkName(byte[] b, int limit) {
		int lower = 32;
		int upper = 126;
		int i;
		
		// Check length
		if (b.length > limit) {
			return false;
		}
		
		// Check chars
		for (byte a : b) {
			i = (int) (a & 0xFF);
			if (i < lower || i > upper) {
				return false;
			}
		}
		return true;
	}
	
}
