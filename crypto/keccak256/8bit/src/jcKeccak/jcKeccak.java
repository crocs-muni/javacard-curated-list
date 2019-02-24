/*
 * JavaCard implementation of Keccak-256.
 */
package jcKeccak;

import javacard.framework.*;

/**
 *
 * @author Thotheolh
 */
public class jcKeccak extends Applet {

    public Keccak256 keccak;
    public byte[] buf1 = JCSystem.makeTransientByteArray((short) 256, JCSystem.CLEAR_ON_DESELECT);
    public byte[] buf2 = JCSystem.makeTransientByteArray((short) 200, JCSystem.CLEAR_ON_DESELECT);
    public short[] sb1 = JCSystem.makeTransientShortArray((short) 20, JCSystem.CLEAR_ON_DESELECT);

    /**
     * Installs this applet.
     *
     * @param bArray
     * the array containing installation parameters
     * @param bOffset
     * the starting offset in bArray
     * @param bLength
     * the length in bytes of the parameter data in bArray
     */
    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new jcKeccak();
    }

    /**
     * Only this class's install method should create the applet object.
     */
    protected jcKeccak() {
        keccak = new Keccak256();
        register();
    }

    /**
     * Processes an incoming APDU.
     *
     * @see APDU
     * @param apdu
     * the incoming APDU
     */
    public void process(APDU apdu) {
        if (selectingApplet()) {
            return;
        }

        byte[] buffer = apdu.getBuffer();
        if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x18)) {
            // Reset hash
            keccak.reset();

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x19)) {
            // Update hash
            sb1[0] = apdu.setIncomingAndReceive();
            keccak.update(buffer, ISO7816.OFFSET_CDATA, sb1[0]);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x20)) {
            // Digest hash
            sb1[0] = apdu.setIncomingAndReceive();
            keccak.digest(buffer, ISO7816.OFFSET_CDATA, sb1[0], buf2, (short) 0);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 32);
            apdu.sendBytesLong(buf2, (short) 0, (short) 32);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x21)) {
            // Get State CD
            keccak.getState(buf1, (short) 0, (short) 80);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 80);
            apdu.sendBytesLong(buf1, (short) 0, (short) 80);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x22)) {
            // Get State
            keccak.getState1(buf1, (short) 0, (short) 200);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 200);
            apdu.sendBytesLong(buf1, (short) 0, (short) 200);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x23)) {
            // Get Old CD State
            keccak.getState2(buf1, (short) 0, (short) 80);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 80);
            apdu.sendBytesLong(buf1, (short) 0, (short) 80);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x24)) {
            // Get Old State
            keccak.getState3(buf1, (short) 0, (short) 200);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 200);
            apdu.sendBytesLong(buf1, (short) 0, (short) 200);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x25)) {
            // Get msgBuff State
            keccak.getState4(buf1, (short) 0, (short) 136);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 136);
            apdu.sendBytesLong(buf1, (short) 0, (short) 136);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x26)) {
            // Get BB State
            keccak.getState5(buf1, (short) 0, (short) 1);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 1);
            apdu.sendBytesLong(buf1, (short) 0, (short) 1);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x27)) {
            // Get read / process buffer length
            keccak.getState6(buf1, (short) 0, (short) 1);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 1);
            apdu.sendBytesLong(buf1, (short) 0, (short) 1);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x28)) {
            // Get msgBuffer length
            keccak.getState7(buf1, (short) 0, (short) 1);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 1);
            apdu.sendBytesLong(buf1, (short) 0, (short) 1);

        } else if ((buffer[ISO7816.OFFSET_CLA] == (byte) 0xB0) && (buffer[ISO7816.OFFSET_INS] == (byte) 0x29)) {
            // Get rdBuffer length
            keccak.getState8(buf1, (short) 0, (short) 136);
            apdu.setOutgoing();
            apdu.setOutgoingLength((short) 136);
            apdu.sendBytesLong(buf1, (short) 0, (short) 136);

        } else {
            ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
        }
    }
}
