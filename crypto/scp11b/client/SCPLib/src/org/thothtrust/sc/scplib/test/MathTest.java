package org.thothtrust.sc.scplib.test;

import org.thothtrust.sc.scplib.util.BinUtils;
import org.thothtrust.sc.scplib.util.MathUtil;

public class MathTest {

	public static void main(String[] args) {
		byte[] iv = new byte[16];
		int inc = 128;
		System.out.println("Starting IV Ctr: " + BinUtils.toHexString(iv));
		for (int i = 0; i < inc; i++) {
			MathUtil.increment128(iv, 0, iv, 0);
			System.out.println("Starting IV Ctr: " + BinUtils.toHexString(iv));
		}
	}
}