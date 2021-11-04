package org.thothtrust.sc.scplib.test;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

import org.thothtrust.sc.scplib.crypto.CMAC;
import org.thothtrust.sc.scplib.util.BinUtils;

public class CMACTest {

	public static void main(String[] args) {

		String[][] nistCMACKAT = {
				new String[] { "CMAC-AES128-Example-1", "2B7E151628AED2A6ABF7158809CF4F3C", "",
						"BB1D6929E95937287FA37D129B756746" },
				new String[] { "CMAC-AES128-Example-2", "2B7E151628AED2A6ABF7158809CF4F3C",
						"6BC1BEE22E409F96E93D7E117393172A", "070A16B46B4D4144F79BDD9DD04A287C" },
				new String[] { "CMAC-AES128-Example-3", "2B7E151628AED2A6ABF7158809CF4F3C",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A57", "7D85449EA6EA19C823A7BF78837DFADE" },
				new String[] { "CMAC-AES128-Example-4", "2B7E151628AED2A6ABF7158809CF4F3C",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710",
						"51F0BEBF7E3B9D92FC49741779363CFE" },
				new String[] { "CMAC-AES192-Example-1", "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B", "",
						"D17DDF46ADAACDE531CAC483DE7A9367" },
				new String[] { "CMAC-AES192-Example-2", "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B",
						"6BC1BEE22E409F96E93D7E117393172A", "9E99A7BF31E710900662F65E617C5184" },
				new String[] { "CMAC-AES192-Example-3", "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A57", "3D75C194ED96070444A9FA7EC740ECF8" },
				new String[] { "CMAC-AES192-Example-4", "8E73B0F7DA0E6452C810F32B809079E562F8EAD2522C6B7B",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710",
						"A1D5DF0EED790F794D77589659F39A11" },
				new String[] { "CMAC-AES256-Example-1",
						"603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4", "",
						"028962F61B7BF89EFC6B551F4667D983" },
				new String[] { "CMAC-AES256-Example-2",
						"603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4",
						"6BC1BEE22E409F96E93D7E117393172A", "28A7023F452E8F82BD4BF28D8C37C35C" },
				new String[] { "CMAC-AES256-Example-3",
						"603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A57", "156727DC0878944A023C1FE03BAD6D93" },
				new String[] { "CMAC-AES256-Example-4",
						"603DEB1015CA71BE2B73AEF0857D77811F352C073B6108D72D9810A30914DFF4",
						"6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710",
						"E1992190549F6ED5696A2C056C315410" } };

		int pass = 0;
		try {
			for (int i = 0; i < nistCMACKAT.length; i++) {
				String[] katParams = nistCMACKAT[i];
				String sampleTestName = katParams[0];
				String sampleTestKey = katParams[1];
				String sampleTestMsg = katParams[2];
				String sampleTestRes = katParams[3];
				String resData = null;
				System.out.println("============================================================");
				System.out.println("Executing Test Case [" + sampleTestName + "] ...");
				System.out.println("Sample Key: " + sampleTestKey);
				System.out.println("Sample Msg: " + sampleTestMsg);
				System.out.println("Expected Out: " + sampleTestRes);
				long startTime = System.currentTimeMillis();
				byte[] msg = BinUtils.hexStringToByteArray(sampleTestMsg);
				Key skeySpec = new SecretKeySpec(BinUtils.hexStringToByteArray(sampleTestKey), "AES");
				byte[] output;
				output = CMAC.process(skeySpec, msg, 0, msg.length, 16);
				long endTime = System.currentTimeMillis();
				long diffTime = endTime - startTime;
				if (output != null) {
					System.out.println("Time taken: " + diffTime + " ms ...");
					resData = BinUtils.toHexString(output);
					System.out.println("Received Out: " + resData);
					if (sampleTestRes.equals(resData)) {
						System.out.println("--- KAT PASS !");
						pass++;
					} else {
						System.out.println("--- KAT FAIL !");
					}
				} else {
					System.out.println("CMAC failed to generate data !!!");
				}
			}
			System.out.println("============================================================");
			System.out.println("SCORE [Pass/Total]: " + pass + "/" + nistCMACKAT.length);
			System.out.println("============================================================");

		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | ShortBufferException
				| IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
}