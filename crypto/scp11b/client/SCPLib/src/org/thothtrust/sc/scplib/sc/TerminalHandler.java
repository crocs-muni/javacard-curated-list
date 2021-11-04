/*
 * Handles hardware terminals.
 */
package org.thothtrust.sc.scplib.sc;

import java.util.List;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;

/**
 *
 * @author ThothTrust Pte Ltd.
 */
public class TerminalHandler {

	// Card Protocol Flags
	public static String CARD_PROTO_T_0 = "T=0";
	public static String CARD_PROTO_T_1 = "T=1";
	public static String CARD_PROTO_T_CL = "T=CL";
	public static String CARD_PROTO_ANY = "*";

	private TerminalFactory factory = null;
	private List<CardTerminal> terminals = null;

	public TerminalHandler() {

	}

	public void loadDefaultTerminal() {
		try {
			setFactory(TerminalFactory.getDefault());
			setTerminals(getFactory().terminals().list());
		} catch (CardException ex) {
		}
	}

	public void printTerminalInfo(List<CardTerminal> terminals) {
		System.out.println("Available Terminals: \r\n");

		for (int i = 0; i < terminals.size(); i++) {
			try {
				System.out.println("Terminal ID: " + i);
				System.out.println("    Terminal Name: " + terminals.get(i).getName());
				System.out.println("    Has Presence : " + terminals.get(i).isCardPresent());
			} catch (CardException ex) {
			}
		}
	}

	public TerminalFactory getFactory() {
		return factory;
	}

	public void setFactory(TerminalFactory factory) {
		this.factory = factory;
	}

	public List<CardTerminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<CardTerminal> terminals) {
		this.terminals = terminals;
	}

	public Card getCard(String cardProtocol, int terminalId) {
		try {
			return terminals.get(terminalId).connect(cardProtocol);
		} catch (CardException ex) {
		}
		return null;
	}	
}
