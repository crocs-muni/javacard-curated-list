package jcKeccak;

import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.Util;

/**
 * Keccak-F1600 with 256 bits output.
 *
 * @author Thotheolh
 */
public class Keccak256 {

    public static final short R = (short) 1088; // Bits form of block length
    public static final short blockLen = R / (short) 8; // Byte form of block length
    public static final short rounds = (short) 24;
    public static final short[] rhoPositions = {
        1, 3, 6, 10, 15, 21, 28, 36, 45, 55, 2, 14,
        27, 41, 56, 8, 25, 43, 62, 18, 39, 61, 20, 44
    };
    public static final short[] piPositions = {
        10, 7, 11, 17, 18, 3, 5, 16, 8, 21, 24, 4,
        15, 23, 19, 13, 12, 2, 20, 14, 22, 9, 6, 1
    };
    public static final byte[] rc0 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01};
    public static final byte[] rc1 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x82};
    public static final byte[] rc2 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x8A};
    public static final byte[] rc3 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x00};
    public static final byte[] rc4 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x8B};
    public static final byte[] rc5 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
    public static final byte[] rc6 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x81};
    public static final byte[] rc7 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x09};
    public static final byte[] rc8 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8A};
    public static final byte[] rc9 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x88};
    public static final byte[] rc10 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x09};
    public static final byte[] rc11 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x0A};
    public static final byte[] rc12 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x8B};
    public static final byte[] rc13 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x8B};
    public static final byte[] rc14 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x89};
    public static final byte[] rc15 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x03};
    public static final byte[] rc16 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x02};
    public static final byte[] rc17 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80};
    public static final byte[] rc18 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x0A};
    public static final byte[] rc19 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x0A};
    public static final byte[] rc20 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x81};
    public static final byte[] rc21 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80, (byte) 0x80};
    public static final byte[] rc22 = {(byte) 0x00, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x01};
    public static final byte[] rc23 = {(byte) 0x80, (byte) 0x00, (byte) 0x00,
        (byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x80, (byte) 0x08};

    public byte[] state = JCSystem.makeTransientByteArray((short) 200, JCSystem.CLEAR_ON_DESELECT);
    public byte[] bkState = JCSystem.makeTransientByteArray((short) 200, JCSystem.CLEAR_ON_DESELECT);
    public byte[] msgBuff = JCSystem.makeTransientByteArray(blockLen, JCSystem.CLEAR_ON_DESELECT);
    public byte[] rdBuff = JCSystem.makeTransientByteArray(blockLen, JCSystem.CLEAR_ON_DESELECT);
    public byte[] B = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public byte[] C = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public byte[] D = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public byte[] bkC = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public byte[] bkD = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public byte[] buff = JCSystem.makeTransientByteArray((short) 10, JCSystem.CLEAR_ON_DESELECT);
    public byte[] buff1 = JCSystem.makeTransientByteArray((short) 8, JCSystem.CLEAR_ON_DESELECT);
    public byte[] buff2 = JCSystem.makeTransientByteArray((short) 8, JCSystem.CLEAR_ON_DESELECT);
    public byte[] buff3 = JCSystem.makeTransientByteArray((short) 40, JCSystem.CLEAR_ON_DESELECT);
    public short[] sb = JCSystem.makeTransientShortArray((short) 10, JCSystem.CLEAR_ON_DESELECT);
    public boolean[] bb = JCSystem.makeTransientBooleanArray((short) 1, JCSystem.CLEAR_ON_DESELECT);

    public Keccak256() {
    }

    public void update(byte[] input, short offset, short length) {
        if (input == null) {
            throw new NullPointerException();
        }
        hash(input, offset, length, false);
    }

    // Input 136 bytes of message data block and can be continously called until when a final hash is required, use digest() to get desired hash result.
    private void hash(byte[] input, short offset, short length, boolean finalize) {
        // Absorb phase
        // If is finalize() phase, complete hash everything else hash in 
        // blocks of 136 bytes and if input is less than 136 bytes, buffer off 
        // in msgBuffer and hold till next update() or finalize() call.
        if (input != null) {
            for (sb[7] = 0; sb[7] < length;) {
                // Reset round execution state
                bb[0] = true;

                // Check if msgBuff already have previously cached content not yet processed
                if (sb[9] > 0) {
                    // Derive read length
                    sb[8] = (short) (length - sb[7]);

                    // Check if read length is more than a block length
                    if (sb[8] >= blockLen) {
                        // Adjust read length to compensate for existing content length in msgBuff
                        sb[8] = (short) (blockLen - sb[9]);
                    } else {

                        // Check if available buffer length after compensating for existing content in msgBuff is less than read length
                        if (sb[8] > (short) (blockLen - sb[9])) {
                            // Read as much data to fit into current available space in msgBuff
                            sb[8] = (short) (blockLen - sb[9]);
                            // ISOException.throwIt(Util.makeShort((byte) 0x6f, (byte) (sb[8] & 0xff)));
                        } else {
                            // Handle scenario where input is smaller than block and NOT in finalize state
                            if ((sb[8] < (short) (blockLen - sb[9])) && (!finalize)) {
                                // Buffer off into msgBuff and wait for reading future inputs via disable executing round state
                                bb[0] = false;
                            }
                        }
                    }

                    // Copy to msgBuff before processing or simply for buffering until enough block length or finalize() call
                    Util.arrayCopyNonAtomic(input, (short) (offset + sb[7]), msgBuff, sb[9], sb[8]);

                    if (!bb[0]) {
                        // Increment msgBuff buffer length
                        sb[9] += sb[8];
                    }
                } else {
                    sb[8] = (short) (length - sb[7]);
                    if (sb[8] >= blockLen) {
                        sb[8] = blockLen;
                    } else {
                        // Handle scenario where input is smaller than block and NOT in finalize state
                        if (!finalize) {
                            // Buffer off into msgBuff and wait for reading future inputs via disable executing round state
                            bb[0] = false;

                            // Set msgBuff buffered content length for future processing
                            sb[9] = sb[8];
                        }
                    }
                    Util.arrayCopyNonAtomic(input, (short) (offset + sb[7]), msgBuff, (short) 0, sb[8]);
                }
                sb[7] += sb[8];
                if (bb[0]) {
                    // Derive total content buffered in msgBuff by adding msgBuff length and current read length before executing round
                    sb[8] += sb[9];

                    // Reset msgBuff length
                    sb[9] = (short) 0;

                    // Execute round
                    round(msgBuff, (short) 0, sb[8], false);

                    // Buffer off some debug info
                    Util.arrayCopyNonAtomic(state, (short) 0, bkState, (short) 0, (short) 200);
                    Util.arrayCopyNonAtomic(C, (short) 0, bkC, (short) 0, (short) 40);
                    Util.arrayCopyNonAtomic(D, (short) 0, bkD, (short) 0, (short) 40);
                }
            }
        } else {
//            ISOException.throwIt(Util.makeShort((byte) 0x6f, (byte) (sb[9] & 0xff)));
            if (finalize && (sb[9] > 0)) {
                // Read remaining data in msgBuff for a final round execution on the remaining data
                sb[8] = sb[9];

                // Reset msgbuff length
                sb[9] = (short) 0;

                // Execute round
                round(msgBuff, (short) 0, sb[8], false);

                // Buffer off some debug info
                Util.arrayCopyNonAtomic(state, (short) 0, bkState, (short) 0, (short) 200);
                Util.arrayCopyNonAtomic(C, (short) 0, bkC, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(D, (short) 0, bkD, (short) 0, (short) 40);
            }
        }

        if (finalize) {
            if ((short) (sb[8] % blockLen) == (short) 0) {
                state[(short) 0] ^= (byte) 0x01;
                state[(short) 135] ^= (byte) 0x80;
                round(msgBuff, (short) 0, sb[8], true);
            }
        }
    }

    private void padAndLoadInput(byte[] input, short offset, short length) {
        // XOR input into states
        for (sb[0] = 0; sb[0] < length; sb[0]++) {
            state[sb[0]] ^= input[(short) (offset + sb[0])];
        }

        // Handle padding
        if (length < blockLen) {
            if (length == (short) (blockLen - 1)) {
                // Pad with single 0x81 and XOR into state
                state[(short) (blockLen - 1)] ^= (byte) 0x81;
            } else {
                // Pad with 0x01 followed by filling all existing empty spaces with 
                // 0x00 except for the last byte being 0x80 and XOR into state.
                // Firstly, figure out amt of intermediate 0x00 bytes to handle 
                // excluding 0x80 and 0x01. Set the 0x80 and 0x01 bytes and
                // the rest are 0x00 bytes.
                sb[1] = (short) (blockLen - 1);
                state[length] ^= (byte) 0x01;
                state[sb[1]] ^= (byte) 0x80;
                for (sb[0] = (short) (length + 1); sb[0] < sb[1]; sb[0]++) {
                    state[sb[0]] ^= (byte) 0x00;
                }
            }
        }
    }

    private void round(byte[] input, short offset, short length, boolean bypass) {
        // Debug
        Util.arrayCopyNonAtomic(input, offset, rdBuff, (short) 0, length);
        if (length <= blockLen || bypass) {
            if (!bypass) {
                // Copy the message into the states with padding
                padAndLoadInput(input, offset, length);
            }

            // Some little endian decoding magicks here before processing round(s)...
            for (sb[0] = (short) 0; sb[0] < state.length; sb[0] += (short) 8) {
                MathUtil.littleEndian64(state, sb[0], state, sb[0], buff[0]);
            }

            // Iterate 24 times of all the following steps in order
            for (sb[0] = (short) 0; sb[0] < (short) 24; sb[0]++) {

                // Step 1: Theta
                // Iterations: CORRECTLY DONE !!!
                // C{byte 0 .. byte 7} = {X=0, Y=0} ^ {X=0, Y=1} ^ {X=0, Y=2} ^ {X=0, Y=3} ^ {X=0, Y=4}
                MathUtil.xor64(state, (short) 0, state, (short) 40, C, (short) 0);
                MathUtil.xor64(C, (short) 0, state, (short) 80, C, (short) 0);
                MathUtil.xor64(C, (short) 0, state, (short) 120, C, (short) 0);
                MathUtil.xor64(C, (short) 0, state, (short) 160, C, (short) 0);
                // C{byte 8 .. byte 15} = {X=1, Y=0} ^ {X=1, Y=1} ^ {X=1, Y=2} ^ {X=1, Y=3} ^ {X=1, Y=4}
                MathUtil.xor64(state, (short) 8, state, (short) 48, C, (short) 8);
                MathUtil.xor64(C, (short) 8, state, (short) 88, C, (short) 8);
                MathUtil.xor64(C, (short) 8, state, (short) 128, C, (short) 8);
                MathUtil.xor64(C, (short) 8, state, (short) 168, C, (short) 8);

                // C{byte 16 .. byte 23} = {X=2, Y=0} ^ {X=2, Y=1} ^ {X=2, Y=2} ^ {X=2, Y=3} ^ {X=2, Y=4}
                MathUtil.xor64(state, (short) 16, state, (short) 56, C, (short) 16);
                MathUtil.xor64(C, (short) 16, state, (short) 96, C, (short) 16);
                MathUtil.xor64(C, (short) 16, state, (short) 136, C, (short) 16);
                MathUtil.xor64(C, (short) 16, state, (short) 176, C, (short) 16);

                // C{byte 24 .. byte 31} = {X=3, Y=0} ^ {X=3, Y=1} ^ {X=3, Y=2} ^ {X=3, Y=3} ^ {X=3, Y=4}
                MathUtil.xor64(state, (short) 24, state, (short) 64, C, (short) 24);
                MathUtil.xor64(C, (short) 24, state, (short) 104, C, (short) 24);
                MathUtil.xor64(C, (short) 24, state, (short) 144, C, (short) 24);
                MathUtil.xor64(C, (short) 24, state, (short) 184, C, (short) 24);

                // C{byte 32 .. byte 39} = {X=4, Y=0} ^ {X=4, Y=1} ^ {X=4, Y=2} ^ {X=4, Y=3} ^ {X=4, Y=4}
                MathUtil.xor64(state, (short) 32, state, (short) 72, C, (short) 32);
                MathUtil.xor64(C, (short) 32, state, (short) 112, C, (short) 32);
                MathUtil.xor64(C, (short) 32, state, (short) 152, C, (short) 32);
                MathUtil.xor64(C, (short) 32, state, (short) 192, C, (short) 32);

                // CORRECTLY DONE !!!
                for (sb[1] = (short) 0; sb[1] < (short) 5; sb[1]++) {

                    // D[i] = C[(i + 4) % 5].xor(leftRotate64(C[(i + 1) % 5], 1));
                    // Portion 1: leftRotate64(C[(i + 1) % 5], 1)
                    Util.arrayCopyNonAtomic(C, (short) ((sb[1] + 1) % 5 * 8), buff1, (short) 0, (short) 8);
                    MathUtil.rotl64(buff1, (short) 0, (short) 1, buff, (short) 0, sb[3], sb[4], sb[5], sb[6]);

                    // Portion 2: D[i] = C[(i + 4) % 5] ^ Portion 1;
                    MathUtil.xor64(C, (short) ((sb[1] + 4) % 5 * 8), buff1, (short) 0, D, (short) (sb[1] * 8));

                }

                // state[i][j] = state[i][j].xor(D[i]);
                // X=0 ; Y=0,1,2,3,4;
                MathUtil.xor64(state, (short) 0, D, (short) 0, state, (short) 0);
                MathUtil.xor64(state, (short) 40, D, (short) 0, state, (short) 40);
                MathUtil.xor64(state, (short) 80, D, (short) 0, state, (short) 80);
                MathUtil.xor64(state, (short) 120, D, (short) 0, state, (short) 120);
                MathUtil.xor64(state, (short) 160, D, (short) 0, state, (short) 160);

                // X=1 ; Y=0,1,2,3,4;
                MathUtil.xor64(state, (short) 8, D, (short) 8, state, (short) 8);
                MathUtil.xor64(state, (short) 48, D, (short) 8, state, (short) 48);
                MathUtil.xor64(state, (short) 88, D, (short) 8, state, (short) 88);
                MathUtil.xor64(state, (short) 128, D, (short) 8, state, (short) 128);
                MathUtil.xor64(state, (short) 168, D, (short) 8, state, (short) 168);

                // X=2 ; Y=0,1,2,3,4;
                MathUtil.xor64(state, (short) 16, D, (short) 16, state, (short) 16);
                MathUtil.xor64(state, (short) 56, D, (short) 16, state, (short) 56);
                MathUtil.xor64(state, (short) 96, D, (short) 16, state, (short) 96);
                MathUtil.xor64(state, (short) 136, D, (short) 16, state, (short) 136);
                MathUtil.xor64(state, (short) 176, D, (short) 16, state, (short) 176);

                // X=3 ; Y=0,1,2,3,4;
                MathUtil.xor64(state, (short) 24, D, (short) 24, state, (short) 24);
                MathUtil.xor64(state, (short) 64, D, (short) 24, state, (short) 64);
                MathUtil.xor64(state, (short) 104, D, (short) 24, state, (short) 104);
                MathUtil.xor64(state, (short) 144, D, (short) 24, state, (short) 144);
                MathUtil.xor64(state, (short) 184, D, (short) 24, state, (short) 184);

                // X=4 ; Y=0,1,2,3,4;
                MathUtil.xor64(state, (short) 32, D, (short) 32, state, (short) 32);
                MathUtil.xor64(state, (short) 72, D, (short) 32, state, (short) 72);
                MathUtil.xor64(state, (short) 112, D, (short) 32, state, (short) 112);
                MathUtil.xor64(state, (short) 152, D, (short) 32, state, (short) 152);
                MathUtil.xor64(state, (short) 192, D, (short) 32, state, (short) 192);

                // Step 2: Rho and Pi
                Util.arrayCopyNonAtomic(state, (short) 8, buff2, (short) 0, (short) 8); // t = st[1];
                for (sb[1] = (short) 0; sb[1] < (short) 24; sb[1]++) { // for (i = 0; i < 24; i++) {
                    Util.arrayCopyNonAtomic(state, (short) (piPositions[sb[1]] * 8), buff1, (short) 0, (short) 8);// bc[0] = st[keccakf_piln[i]];
                    MathUtil.rotl64(buff2, (short) 0, rhoPositions[sb[1]], buff, (short) 0, sb[2], sb[3], sb[4], sb[5]); // rho <- ROTL64(t, keccakf_rotc[i]);
                    Util.arrayCopyNonAtomic(buff2, (short) 0, state, (short) (piPositions[sb[1]] * 8), (short) 8);// st[keccakf_piln[i]] = rho;
                    Util.arrayCopyNonAtomic(buff1, (short) 0, buff2, (short) 0, (short) 8);// t = bc[0];
                } // }

                // Step 3: Chi
                // #1
                Util.arrayCopyNonAtomic(state, (short) 0, B, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(state, (short) 0, buff3, (short) 0, (short) 40);

                // Unroll execute on Chi function B buffer
                MathUtil.flip64(B, (short) 8);
                MathUtil.and64(B, (short) 8, B, (short) 16, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 0, buff1, (short) 0, state, (short) 0);
                MathUtil.flip64(B, (short) 16);
                MathUtil.and64(B, (short) 16, B, (short) 24, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 8, buff1, (short) 0, state, (short) 8);
                MathUtil.flip64(B, (short) 24);
                MathUtil.and64(B, (short) 24, B, (short) 32, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 16, buff1, (short) 0, state, (short) 16);
                MathUtil.flip64(B, (short) 32);
                MathUtil.and64(B, (short) 32, B, (short) 0, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 24, buff1, (short) 0, state, (short) 24);
                MathUtil.flip64(B, (short) 0);
                MathUtil.and64(B, (short) 0, buff3, (short) 8, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 32, buff1, (short) 0, state, (short) 32);

                // #2
                Util.arrayCopyNonAtomic(state, (short) 40, B, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(state, (short) 40, buff3, (short) 0, (short) 40);
                MathUtil.flip64(B, (short) 8);
                MathUtil.and64(B, (short) 8, B, (short) 16, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 0, buff1, (short) 0, state, (short) 40);
                MathUtil.flip64(B, (short) 16);
                MathUtil.and64(B, (short) 16, B, (short) 24, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 8, buff1, (short) 0, state, (short) 48);
                MathUtil.flip64(B, (short) 24);
                MathUtil.and64(B, (short) 24, B, (short) 32, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 16, buff1, (short) 0, state, (short) 56);
                MathUtil.flip64(B, (short) 32);
                MathUtil.and64(B, (short) 32, B, (short) 0, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 24, buff1, (short) 0, state, (short) 64);
                MathUtil.flip64(B, (short) 0);
                MathUtil.and64(B, (short) 0, buff3, (short) 8, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 32, buff1, (short) 0, state, (short) 72);

                // #3
                Util.arrayCopyNonAtomic(state, (short) 80, B, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(state, (short) 80, buff3, (short) 0, (short) 40);
                MathUtil.flip64(B, (short) 8);
                MathUtil.and64(B, (short) 8, B, (short) 16, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 0, buff1, (short) 0, state, (short) 80);
                MathUtil.flip64(B, (short) 16);
                MathUtil.and64(B, (short) 16, B, (short) 24, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 8, buff1, (short) 0, state, (short) 88);
                MathUtil.flip64(B, (short) 24);
                MathUtil.and64(B, (short) 24, B, (short) 32, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 16, buff1, (short) 0, state, (short) 96);
                MathUtil.flip64(B, (short) 32);
                MathUtil.and64(B, (short) 32, B, (short) 0, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 24, buff1, (short) 0, state, (short) 104);
                MathUtil.flip64(B, (short) 0);
                MathUtil.and64(B, (short) 0, buff3, (short) 8, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 32, buff1, (short) 0, state, (short) 112);

                // #4
                Util.arrayCopyNonAtomic(state, (short) 120, B, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(state, (short) 120, buff3, (short) 0, (short) 40);
                MathUtil.flip64(B, (short) 8);
                MathUtil.and64(B, (short) 8, B, (short) 16, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 0, buff1, (short) 0, state, (short) 120);
                MathUtil.flip64(B, (short) 16);
                MathUtil.and64(B, (short) 16, B, (short) 24, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 8, buff1, (short) 0, state, (short) 128);
                MathUtil.flip64(B, (short) 24);
                MathUtil.and64(B, (short) 24, B, (short) 32, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 16, buff1, (short) 0, state, (short) 136);
                MathUtil.flip64(B, (short) 32);
                MathUtil.and64(B, (short) 32, B, (short) 0, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 24, buff1, (short) 0, state, (short) 144);
                MathUtil.flip64(B, (short) 0);
                MathUtil.and64(B, (short) 0, buff3, (short) 8, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 32, buff1, (short) 0, state, (short) 152);

                // #5
                Util.arrayCopyNonAtomic(state, (short) 160, B, (short) 0, (short) 40);
                Util.arrayCopyNonAtomic(state, (short) 160, buff3, (short) 0, (short) 40);
                MathUtil.flip64(B, (short) 8);
                MathUtil.and64(B, (short) 8, B, (short) 16, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 0, buff1, (short) 0, state, (short) 160);
                MathUtil.flip64(B, (short) 16);
                MathUtil.and64(B, (short) 16, B, (short) 24, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 8, buff1, (short) 0, state, (short) 168);
                MathUtil.flip64(B, (short) 24);
                MathUtil.and64(B, (short) 24, B, (short) 32, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 16, buff1, (short) 0, state, (short) 176);
                MathUtil.flip64(B, (short) 32);
                MathUtil.and64(B, (short) 32, B, (short) 0, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 24, buff1, (short) 0, state, (short) 184);
                MathUtil.flip64(B, (short) 0);
                MathUtil.and64(B, (short) 0, buff3, (short) 8, buff1, (short) 0);
                MathUtil.xor64(buff3, (short) 32, buff1, (short) 0, state, (short) 192);

                // Step 4: Iota
                MathUtil.xor64(state, (short) 0, getRoundConstant(sb[0]), (short) 0, state, (short) 0);
            }

            // Some little endian encoding magicks after round(s) done
            for (sb[0] = (short) 0; sb[0] < state.length; sb[0] += (short) 8) {
                MathUtil.littleEndian64(state, sb[0], state, sb[0], buff[0]);
            }
        }
    }

    public void reset() {
        Util.arrayFillNonAtomic(state, (short) 0, (short) state.length, (byte) 0x00);
        Util.arrayFillNonAtomic(bkState, (short) 0, (short) bkState.length, (byte) 0x00);
        Util.arrayFillNonAtomic(msgBuff, (short) 0, (short) msgBuff.length, (byte) 0x00);
        Util.arrayFillNonAtomic(rdBuff, (short) 0, (short) rdBuff.length, (byte) 0x00);
        Util.arrayFillNonAtomic(B, (short) 0, (short) B.length, (byte) 0x00);
        Util.arrayFillNonAtomic(C, (short) 0, (short) C.length, (byte) 0x00);
        Util.arrayFillNonAtomic(D, (short) 0, (short) D.length, (byte) 0x00);
        Util.arrayFillNonAtomic(bkC, (short) 0, (short) bkC.length, (byte) 0x00);
        Util.arrayFillNonAtomic(bkD, (short) 0, (short) bkD.length, (byte) 0x00);
        Util.arrayFillNonAtomic(buff, (short) 0, (short) buff.length, (byte) 0x00);
        Util.arrayFillNonAtomic(buff1, (short) 0, (short) buff1.length, (byte) 0x00);
        Util.arrayFillNonAtomic(buff2, (short) 0, (short) buff2.length, (byte) 0x00);
        Util.arrayFillNonAtomic(buff3, (short) 0, (short) buff3.length, (byte) 0x00);
        sb[0] = (short) 0;
        sb[1] = (short) 0;
        sb[2] = (short) 0;
        sb[3] = (short) 0;
        sb[4] = (short) 0;
        sb[5] = (short) 0;
        sb[6] = (short) 0;
        sb[7] = (short) 0;
        sb[8] = (short) 0;
        sb[9] = (short) 0;
        bb[0] = false;
    }

    // Use to inspect state of Keccak but should not be used to derive a proper hash result !!!
    public void getState(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(C, (short) 0, output, offset, (short) 40);
        Util.arrayCopyNonAtomic(D, (short) 0, output, (short) 40, (short) 40);
    }

    public void getState1(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(state, (short) 0, output, offset, length);
    }

    public void getState2(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(bkC, (short) 0, output, offset, (short) 40);
        Util.arrayCopyNonAtomic(bkD, (short) 0, output, (short) 40, (short) 40);
    }

    public void getState3(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(bkState, (short) 0, output, offset, length);
    }

    public void getState4(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(msgBuff, (short) 0, output, (short) 0, (short) msgBuff.length);
    }

    public void getState5(byte[] output, short offset, short length) {
        if (bb[0]) {
            output[offset] = (byte) 0xFF;
        } else {
            output[offset] = (byte) 0x00;
        }
    }

    public void getState6(byte[] output, short offset, short length) {
        output[offset] = (byte) (sb[8] & 0xff);
    }

    public void getState7(byte[] output, short offset, short length) {
        output[offset] = (byte) (sb[9] & 0xff);
    }

    public void getState8(byte[] output, short offset, short length) {
        Util.arrayCopyNonAtomic(rdBuff, (short) 0, output, (short) 0, (short) rdBuff.length);
    }

    public byte[] getRoundConstant(short r) {
        switch (r) {
            case 0:
                return rc0;
            case 1:
                return rc1;
            case 2:
                return rc2;
            case 3:
                return rc3;
            case 4:
                return rc4;
            case 5:
                return rc5;
            case 6:
                return rc6;
            case 7:
                return rc7;
            case 8:
                return rc8;
            case 9:
                return rc9;
            case 10:
                return rc10;
            case 11:
                return rc11;
            case 12:
                return rc12;
            case 13:
                return rc13;
            case 14:
                return rc14;
            case 15:
                return rc15;
            case 16:
                return rc16;
            case 17:
                return rc17;
            case 18:
                return rc18;
            case 19:
                return rc19;
            case 20:
                return rc20;
            case 21:
                return rc21;
            case 22:
                return rc22;
            case 23:
                return rc23;
            default:
                return null;
        }
    }

    public void digest(byte[] input, short inOffset, short length, byte[] output, short outOffset) {
        // do rounds on input as well as remaining buffer data
        if ((input == null) || (output == null)) {
            throw new NullPointerException();
        }
        if (length == 0) {
            digest(output, outOffset);
        } else {
            hash(input, inOffset, length, true);
            Util.arrayCopyNonAtomic(state, (short) 0, output, outOffset, (short) 32);
        }
    }

    // Always use finalize to extract resulting hash result !!!
    public void digest(byte[] output, short offset) {
        // do round on remaining msgBuffer data
        if (output == null) {
            throw new NullPointerException();
        }
        hash(null, (short) 0, (short) 0, true);
        Util.arrayCopyNonAtomic(state, (short) 0, output, offset, (short) 32);
    }
}
