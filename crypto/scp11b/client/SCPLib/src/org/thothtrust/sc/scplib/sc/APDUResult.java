package org.thothtrust.sc.scplib.sc;

import javax.smartcardio.ResponseAPDU;

import org.thothtrust.sc.scplib.util.BinUtils;

public class APDUResult {

	private byte[] result;
	private byte[] sw;
	private boolean isSuccess;

	public APDUResult(ResponseAPDU apdu) {
//		System.out.println("APDU <<< " + BinUtils.toHexString(apdu.getBytes()));
		setResult(DeviceHelper.getSuccessfulResponseData(apdu));
		setSw(DeviceHelper.getResponseSW(apdu));
		setSuccess(DeviceHelper.isSuccessfulResponse(apdu));
	}

	public APDUResult(byte[] result, byte[] sw, boolean isSuccess) {
		setResult(result);
		setSw(sw);
		setSuccess(isSuccess);
	}

	public byte[] getResult() {
		return result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}

	public byte[] getSw() {
		return sw;
	}

	public void setSw(byte[] sw) {
		this.sw = sw;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

}