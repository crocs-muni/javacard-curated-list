package org.thothtrust.sc.scplib.sc;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import org.thothtrust.sc.scplib.Constants;

/**
 *
 * @author ThothTrust Pte Ltd.
 */
public class SEDevice {

	private Card card = null;
	private CardChannel channel = null;

	private String terminalName = null;

	public SEDevice(Card card, String terminalName) {
		if ((card != null) && (terminalName != null)) {
			setCard(card);
			setTerminalName(terminalName);
		}
	}

	public boolean connect(byte[] aid) throws CardException {
//		System.out.println("Connecting AID: " + BinUtils.toHexString(aid));
		if (card != null) {
			setChannel(getCard().getBasicChannel());
		}
		return checkCard(aid);
	}

	public void disconnect() throws CardException {
		if (card != null) {
//			System.out.println("Disconnecting terminal: " + terminalName);
			card.disconnect(true);
			card = null;
			channel = null;
		}
	}

	public boolean checkCard(byte[] aid) throws CardException {
//		System.out.println("Checking card ...");
		if (channel != null) {
//			System.out.println("Selecting aid ...");
			CommandAPDU cmd = new CommandAPDU(Constants.APDU_SELECT[0], Constants.APDU_SELECT[1],
					Constants.APDU_SELECT[2], Constants.APDU_SELECT[2], aid);
			ResponseAPDU selectResponse = send(cmd);
			return DeviceHelper.isSuccessfulResponse(selectResponse);
		}
//		System.out.println("Channel not found ...");
		return false;
	}

	public byte[] getATRBytes() {
		return card.getATR().getBytes();
	}

	public ResponseAPDU send(CommandAPDU message) throws CardException {
//		System.out.println("Sending APDU ...");
		if (channel != null) {
			return channel.transmit(message);
		} else {
//			System.out.println("[ERR] Invalid Channel !!!");
			if (getCard() == null) {
//				System.out.println("Card is null for terminal: " + terminalName);
			} else {
				if (getCard().getBasicChannel() == null) {
//					System.out.println("Card has problems on basic channel ...");
				}
			}
		}

		return null;
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	private void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the channel
	 */
	public CardChannel getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	private void setChannel(CardChannel channel) {
		this.channel = channel;
	}

	/**
	 * @return the terminalName
	 */
	public String getTerminalName() {
		return terminalName;
	}

	/**
	 * @param terminalName the terminalName to set
	 */
	private void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

}
