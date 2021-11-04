package org.thothtrust.sc.scplib.sc;

import java.util.ArrayList;
import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;

/**
 *
 * @author ThothTrust Pte Ltd.
 */
public class DeviceManager {

	private static volatile DeviceManager instance = null;
	private static volatile TerminalHandler termMan = null;
	private static final String DEFAULT_CARD_PROTO = TerminalHandler.CARD_PROTO_ANY;
	private static volatile ArrayList<SEDevice> devices = new ArrayList<>();
	private static byte[] tagAid = null;

	protected DeviceManager() throws CardException {
		termMan = new TerminalHandler();
		refreshDevices();
	}

	protected DeviceManager(byte[] tagAid) throws CardException {
		this.tagAid = tagAid;
		termMan = new TerminalHandler();
		refreshDevices();
	}

	public static DeviceManager getInstance() throws CardException {
		if (instance == null) {
			instance = new DeviceManager();
		}

		return instance;
	}

	public static DeviceManager getInstance(byte[] tagAid) throws CardException {
		if (instance == null) {
			instance = new DeviceManager(tagAid);
		}

		return instance;
	}

	public void refreshDevices() throws CardException {
		if (tagAid == null) {
			return;
		}
//		System.out.println("Refreshing NFC and Token devices ...");
		termMan.loadDefaultTerminal();
		devices.clear();
		List<CardTerminal> terminals = termMan.getTerminals();
//		System.out.println("Found terminals: " + terminals.size());
		for (int i = 0; i < terminals.size(); i++) {
//			System.out.println("Querying terminal: " + terminals.get(i).getName());
			Card tempCard = termMan.getCard(DEFAULT_CARD_PROTO, i);
			SEDevice tempDevice = new SEDevice(tempCard, terminals.get(i).getName());
			if (tempDevice.connect(tagAid)) {
//				System.out.println("Adding to NFC Device ...");
				devices.add(tempDevice);
			}
		}
	}

	public void disconnectAllExistingDevices() throws CardException {
		if (devices.size() > 0) {
			for (SEDevice tempDevice : devices) {
				tempDevice.disconnect();
			}
		}
	}

	public int getDevicesCount() {
		return devices.size();
	}

	public ArrayList<SEDevice> getDevices() {
		return devices;
	}

	public SEDevice getDevice(int i) {
		return devices.get(i);
	}

	public byte[] getAid() {
		return tagAid;
	}

	public void setAid(byte[] aid) {
		this.tagAid = aid;
	}
}
