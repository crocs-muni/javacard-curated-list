package org.thothtrust.sc.scplib.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	public static String getTimestamp() {
		Date currDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d h:m:s a z");
		return dateFormatter.format(currDate);
	}

	public static Date convertStringTimestamp(String dateString) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d h:m:s a z");
		Date date = null;
		try {
			date = dateFormatter.parse(dateString);
		} catch (ParseException ex) {
			// Logger.getLogger(DateTimeUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return date;
	}

	public static String convertDateTimestamp(Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("E, y-M-d h:m:s a z");
		return dateFormatter.format(date);
	}

	public static String unixHexStrFromTimestsamp(Timestamp ts) {
		int timestamp = (int) (ts.getTime() / 1000);
		return BinUtils.toHexString(new byte[] { (byte) (timestamp >> 24), (byte) (timestamp >> 16),
				(byte) (timestamp >> 8), (byte) timestamp });
	}

	public static byte[] unixHexBytesFromTimestsamp(Timestamp ts) {
		int timestamp = (int) (ts.getTime() / 1000);
		return new byte[] { (byte) (timestamp >> 24), (byte) (timestamp >> 16), (byte) (timestamp >> 8),
				(byte) timestamp };
	}

	public static int integerTimestampFromUnixHexStr(String ts) {
		byte[] bytes = BinUtils.hexStringToByteArray(ts);
		return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8)
				| ((bytes[3] & 0xFF) << 0);
	}

	public static int integerTimestampFromUnixBin(byte[] ts) {
		return ((ts[0] & 0xFF) << 24) | ((ts[1] & 0xFF) << 16) | ((ts[2] & 0xFF) << 8) | ((ts[3] & 0xFF) << 0);
	}

	public Timestamp timestampFromUnixHexStr(String ts) {
		return new Timestamp(integerTimestampFromUnixHexStr(ts));
	}

	public static String notBeforeNotAfterHexStr(int years) {
		int currTimestampSeconds = (int) (System.currentTimeMillis() / 1000);
		int expTimestampSeconds = currTimestampSeconds + 157680000;
		return BinUtils.toHexString(new byte[] { (byte) (currTimestampSeconds >> 24),
				(byte) (currTimestampSeconds >> 16), (byte) (currTimestampSeconds >> 8), (byte) currTimestampSeconds,
				(byte) (expTimestampSeconds >> 24), (byte) (expTimestampSeconds >> 16),
				(byte) (expTimestampSeconds >> 8), (byte) expTimestampSeconds });
	}

	public static byte[] notBeforeNotAfterBytes(int years) {
		int currTimestampSeconds = (int) (System.currentTimeMillis() / 1000);
		int expTimestampSeconds = currTimestampSeconds + 157680000;
		return new byte[] { (byte) (currTimestampSeconds >> 24), (byte) (currTimestampSeconds >> 16),
				(byte) (currTimestampSeconds >> 8), (byte) currTimestampSeconds, (byte) (expTimestampSeconds >> 24),
				(byte) (expTimestampSeconds >> 16), (byte) (expTimestampSeconds >> 8), (byte) expTimestampSeconds };
	}
}
