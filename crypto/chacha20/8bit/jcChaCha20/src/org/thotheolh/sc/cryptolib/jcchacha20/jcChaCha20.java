/*
 * ChaCha20 implemented according to RFC-7539.
 */
package org.thotheolh.sc.cryptolib.jcchacha20;

import javacard.framework.*;
import javacardx.framework.util.ArrayLogic;

/**
 *
 * @author Thotheolh
 */
public class jcChaCha20 extends Applet {

    private short[] sBuff = JCSystem.makeTransientShortArray((short) 2, JCSystem.CLEAR_ON_RESET);
    private byte[] nonce = JCSystem.makeTransientByteArray((short) 12, JCSystem.CLEAR_ON_RESET);
    private byte[] counter = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] key = JCSystem.makeTransientByteArray((short) 32, JCSystem.CLEAR_ON_RESET);
    private byte[] b1 = JCSystem.makeTransientByteArray((short) 256, JCSystem.CLEAR_ON_RESET);
    private byte[] b2 = JCSystem.makeTransientByteArray((short) 256, JCSystem.CLEAR_ON_RESET);
    private static ChaCha20 cipher;

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
        new jcChaCha20();
        cipher = new ChaCha20();
    }

    /**
     * Only this class's install method should create the applet object.
     */
    protected jcChaCha20() {
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
        //Insert your code here

        if (selectingApplet()) {
            return;
        }

        byte[] buffer = apdu.getBuffer();
        apdu.setIncomingAndReceive();

        // CLA = 00, INS = DA (PUT DATA)
        if (buffer[ISO7816.OFFSET_CLA] == (byte) 0x00) {
            if (buffer[ISO7816.OFFSET_INS] == (byte) 0xDA) {

                /**
                 * Input APDU Data Structure
                 * -------------------------
                 *
                 * +------------+-------------+----------+---------------+
                 * | Nonce (12) | Counter (4) | Key (32) | Message (208) |
                 * +------------+-------------+----------+---------------+
                 *
                 */
                if (buffer[ISO7816.OFFSET_P1] == (byte) 0x01) {
                    if ((buffer[ISO7816.OFFSET_LC] & 0xFF) > 48) {

                        // Copy nonce
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 5, (short) 12, nonce, (short) 0);

                        // Copy counter
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 17, (short) 4, counter, (short) 0);

                        // Copy key
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 21, (short) 32, key, (short) 0);

                        // Calculate message length
                        sBuff[0] = (short) ((buffer[ISO7816.OFFSET_LC] & 0xFF) - 48);

                        // Copy incoming message
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 53, (short) sBuff[0], b1, (short) 0);

                        /**
                         * Encrypt in loops of 64 bytes with sBuff[0] storing a
                         * decrementing counter to keep track of number of
                         * remaining bytes needing processing while sBuff[1]
                         * stores an upwards incrementing counter to keep track
                         * of the number of bytes already processed.
                         */
                        while (sBuff[0] > 0) {

                            /**
                             * Need to only process less than or equals to 64
                             * bytes and also finalizes the processing by
                             * breaking the while loop via setting the sBuff[0]
                             * to 0.
                             */
                            if (sBuff[0] <= 64) {
                                cipher.encrypt(key, (short) 0, nonce, (short) 0, counter, (short) 0, b1, sBuff[1], (short) ((buffer[ISO7816.OFFSET_LC] & 0xFF) - 48 - sBuff[1]), b2, sBuff[1]);

                                // Increment length of processed bytes
                                sBuff[1] += sBuff[0];

                                // Reset loop counter
                                sBuff[0] = 0;
                            } else {
                                cipher.encrypt(key, (short) 0, nonce, (short) 0, counter, (short) 0, b1, (short) 0, (short) 64, b2, sBuff[1]);

                                // Decrement remainder bytes counter
                                sBuff[0] -= 64;

                                // Increment already processed bytes counter
                                sBuff[1] += 64;

                                // Increment ChaCha20's counter while using the b2 array that stores cryptographic result as the carry counter
                                increment(counter, (short) 0, b2[255]);
                            }
                        }

                        // Send out response of encrypted ciphertext
                        apdu.setOutgoing();
                        apdu.setOutgoingLength(sBuff[1]);
                        apdu.sendBytesLong(b2, (short) 0, sBuff[1]);

                        // Reset output amount counter
                        sBuff[1] = 0;
                    }
                } else if (buffer[ISO7816.OFFSET_P1] == (byte) 0x02) {

                    // Decrypt
                    if ((buffer[ISO7816.OFFSET_LC] & 0xFF) > 48) {

                        // Copy nonce
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 5, (short) 12, nonce, (short) 0);

                        // Copy counter
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 17, (short) 4, counter, (short) 0);

                        // Copy key
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 21, (short) 32, key, (short) 0);

                        // Calculate message length
                        sBuff[0] = (short) ((buffer[ISO7816.OFFSET_LC] & 0xFF) - 48);

                        // Copy incoming message
                        ArrayLogic.arrayCopyRepackNonAtomic(buffer, (short) 53, (short) sBuff[0], b1, (short) 0);

                        /**
                         * Decrypt in loops of 64 bytes with sBuff[0] storing a
                         * decrementing counter to keep track of number of
                         * remaining bytes needing processing while sBuff[1]
                         * stores an upwards incrementing counter to keep track
                         * of the number of bytes already processed.
                         */
                        while (sBuff[0] > 0) {

                            /**
                             * Need to only process less than or equals to 64
                             * bytes and also finalizes the processing by
                             * breaking the while loop via setting the sBuff[0]
                             * to 0.
                             */
                            if (sBuff[0] <= 64) {
                                cipher.decrypt(key, (short) 0, nonce, (short) 0, counter, (short) 0, b1, sBuff[1], (short) ((buffer[ISO7816.OFFSET_LC] & 0xFF) - 48 - sBuff[1]), b2, sBuff[1]);

                                // Increment length of processed bytes
                                sBuff[1] += sBuff[0];

                                // Reset loop counter
                                sBuff[0] = 0;
                            } else {
                                cipher.decrypt(key, (short) 0, nonce, (short) 0, counter, (short) 0, b1, (short) 0, (short) 64, b2, sBuff[1]);

                                // Decrement remainder bytes counter
                                sBuff[0] -= 64;

                                // Increment already processed bytes counter
                                sBuff[1] += 64;

                                // Increment ChaCha20's counter while using the b2 array that stores cryptographic result as the carry counter
                                increment(counter, (short) 0, b2[255]);
                            }
                        }

                        // Send out response of encrypted ciphertext
                        apdu.setOutgoing();
                        apdu.setOutgoingLength(sBuff[1]);
                        apdu.sendBytesLong(b2, (short) 0, sBuff[1]);

                        // Reset output amount counter
                        sBuff[1] = 0;
                    }
                } else {
                    ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
                }
            } else {
                ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
            }
        } else {
            ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
        }
    }

    public void increment(byte[] bCtrArray, short offset, byte carry) {
        carry = 0x00;
        if ((bCtrArray[(short) (offset + 3)] & 0xFF) < 255) {
            bCtrArray[(short) (offset + 3)] += 1;
        } else {
            bCtrArray[(short) (offset + 3)] = 0x00;
            carry = 1;
        }
        if (carry > 0) {
            if ((bCtrArray[(short) (offset + 2)] & 0xFF) < 255) {
                bCtrArray[(short) (offset + 2)] += carry;
                carry = 0;
            } else {
                bCtrArray[(short) (offset + 2)] = 0x00;
                carry = 1;
            }
        }
        if (carry > 0) {
            if ((bCtrArray[(short) (offset + 1)] & 0xFF) < 255) {
                bCtrArray[(short) (offset + 1)] += carry;
                carry = 0;
            } else {
                bCtrArray[(short) (offset + 1)] = 0x00;
                carry = 1;
            }
        }
        if (carry > 0) {
            if ((bCtrArray[offset] & 0xFF) < 255) {
                bCtrArray[offset] += carry;
            }
        }
    }
}
