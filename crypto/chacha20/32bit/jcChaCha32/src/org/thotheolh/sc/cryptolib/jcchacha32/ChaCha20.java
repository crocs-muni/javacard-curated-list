package org.thotheolh.sc.cryptolib.jcchacha32;

/*
 * 20 rounds of ChaCha20 using 32-bit Integer JCint according to RFC7539 
 * Standard setup of key, nonce and counter (32 bytes, 12 bytes, 4 bytes) 
 * accordingly.
 *
 */
import java.io.IOException;
import javacard.framework.APDUException;
import javacard.framework.CardException;
import javacard.framework.ISOException;
import javacard.framework.JCSystem;
import javacard.framework.SystemException;
import javacard.framework.TransactionException;
import javacard.framework.Util;
import javacard.security.CryptoException;

/**
 *
 * @author Thotheolh
 */
public class ChaCha20 {

    private byte[] matrix0 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix1 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix2 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix3 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix4 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix5 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix6 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix7 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix8 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix9 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix10 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix11 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix12 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix13 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix14 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] matrix15 = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] buffer = JCSystem.makeTransientByteArray((short) 4, JCSystem.CLEAR_ON_RESET);
    private byte[] inputInitState = JCSystem.makeTransientByteArray((short) 64, JCSystem.CLEAR_ON_RESET); // original keystream
    private short[] sBuff = JCSystem.makeTransientShortArray((short) 2, JCSystem.CLEAR_ON_RESET);
    private MathUtil math = new MathUtil();

    public ChaCha20() {
    }

    /**
     * ChaCha QuarterRound Round Function in sequence.
     * a += b; d ^= a; d <<<= 16;
     * c += d; b ^= c; b <<<= 12;
     * a += b; d ^= a; d <<<= 8;
     * c += d; b ^= c; b <<<= 7;
     *
     * @param a
     * @param b
     * @param c
     * @param d
     */
    public void quarterRound(byte[] a, byte[] b, byte[] c, byte[] d) {
        // a += b;
        math.mod32Add(a, (short) 0, b, (short) 0, a, (short) 0);

        // d ^= a;
        math.xor32(d, (short) 0, a, (short) 0, d, (short) 0);

        // d <<<= 16;
        math.rotl32(d, (short) 0, (short) 16, buffer, (short) 0);

        // c += d;
        math.mod32Add(c, (short) 0, d, (short) 0, c, (short) 0);

        // b ^= c;
        math.xor32(b, (short) 0, c, (short) 0, b, (short) 0);

        // b <<<= 12;
        math.rotl32(b, (short) 0, (short) 12, buffer, (short) 0);

        // a += b;
        math.mod32Add(a, (short) 0, b, (short) 0, a, (short) 0);

        // d ^= a;
        math.xor32(d, (short) 0, a, (short) 0, d, (short) 0);

        // d <<<= 8;
        math.rotl32(d, (short) 0, (short) 8, buffer, (short) 0);

        // c += d;
        math.mod32Add(c, (short) 0, d, (short) 0, c, (short) 0);

        // b ^= c;
        math.xor32(b, (short) 0, c, (short) 0, b, (short) 0);

        // b <<<= 7;
        math.rotl32(b, (short) 0, (short) 7, buffer, (short) 0);
    }

    /**
     * Converts 32-bit word into little endian format.
     *
     * @param data
     * @param dataOffset
     * @param buff
     * @param buffOffset
     */
    private void littleEndian(byte[] data, short dataOffset, byte[] buff, short buffOffset) {
        Util.arrayCopyNonAtomic(data, dataOffset, buff, buffOffset, (short) 4);
        data[dataOffset] = buff[(short) (buffOffset + 3)];
        data[(short) (dataOffset + 1)] = buff[(short) (buffOffset + 2)];
        data[(short) (dataOffset + 2)] = buff[(short) (buffOffset + 1)];
        data[(short) (dataOffset + 3)] = buff[buffOffset];
    }

    /**
     * Initializes cryptographic parameters.
     *
     * @param key
     * @param keyOffset
     * @param nonce
     * @param nonceOffset
     * @param counter
     * @param ctrOffset
     * @return
     */
    private boolean init(byte[] key, short keyOffset, byte[] nonce, short nonceOffset, byte[] counter, short ctrOffset) {
        if (((short) (key.length - keyOffset) >= (short) 32) && ((short) (nonce.length - nonceOffset) >= (short) 12) && ((short) (counter.length - ctrOffset) >= (short) 4)) {
            // Set constant into matrcies
            matrix0[0] = (byte) 0x61;
            matrix0[1] = (byte) 0x70;
            matrix0[2] = (byte) 0x78;
            matrix0[3] = (byte) 0x65;
            matrix1[0] = (byte) 0x33;
            matrix1[1] = (byte) 0x20;
            matrix1[2] = (byte) 0x64;
            matrix1[3] = (byte) 0x6E;
            matrix2[0] = (byte) 0x79;
            matrix2[1] = (byte) 0x62;
            matrix2[2] = (byte) 0x2D;
            matrix2[3] = (byte) 0x32;
            matrix3[0] = (byte) 0x6B;
            matrix3[1] = (byte) 0x20;
            matrix3[2] = (byte) 0x65;
            matrix3[3] = (byte) 0x74;

            // Set constant into inputInitState
            inputInitState[0] = (byte) 0x61;
            inputInitState[1] = (byte) 0x70;
            inputInitState[2] = (byte) 0x78;
            inputInitState[3] = (byte) 0x65;
            inputInitState[4] = (byte) 0x33;
            inputInitState[5] = (byte) 0x20;
            inputInitState[6] = (byte) 0x64;
            inputInitState[7] = (byte) 0x6E;
            inputInitState[8] = (byte) 0x79;
            inputInitState[9] = (byte) 0x62;
            inputInitState[10] = (byte) 0x2D;
            inputInitState[11] = (byte) 0x32;
            inputInitState[12] = (byte) 0x6B;
            inputInitState[13] = (byte) 0x20;
            inputInitState[14] = (byte) 0x65;
            inputInitState[15] = (byte) 0x74;

            // Set key into matrices then into little endian format
            Util.arrayCopyNonAtomic(key, keyOffset, matrix4, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 8), matrix5, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 12), matrix6, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 16), matrix7, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 18), matrix8, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 20), matrix9, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 24), matrix10, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(key, (short) (keyOffset + 28), matrix11, (short) 0, (short) 4);
            littleEndian(matrix4, (short) 0, buffer, (short) 0);
            littleEndian(matrix5, (short) 0, buffer, (short) 0);
            littleEndian(matrix6, (short) 0, buffer, (short) 0);
            littleEndian(matrix7, (short) 0, buffer, (short) 0);
            littleEndian(matrix8, (short) 0, buffer, (short) 0);
            littleEndian(matrix9, (short) 0, buffer, (short) 0);
            littleEndian(matrix10, (short) 0, buffer, (short) 0);
            littleEndian(matrix11, (short) 0, buffer, (short) 0);

            // Set key into inputInitState
            Util.arrayCopyNonAtomic(matrix4, (short) 0, inputInitState, (short) 16, (short) 4);
            Util.arrayCopyNonAtomic(matrix5, (short) 0, inputInitState, (short) 20, (short) 4);
            Util.arrayCopyNonAtomic(matrix6, (short) 0, inputInitState, (short) 24, (short) 4);
            Util.arrayCopyNonAtomic(matrix7, (short) 0, inputInitState, (short) 28, (short) 4);
            Util.arrayCopyNonAtomic(matrix8, (short) 0, inputInitState, (short) 32, (short) 4);
            Util.arrayCopyNonAtomic(matrix9, (short) 0, inputInitState, (short) 36, (short) 4);
            Util.arrayCopyNonAtomic(matrix10, (short) 0, inputInitState, (short) 40, (short) 4);
            Util.arrayCopyNonAtomic(matrix11, (short) 0, inputInitState, (short) 44, (short) 4);

            // Set counter into matrices
            Util.arrayCopyNonAtomic(counter, ctrOffset, matrix12, (short) 0, (short) 4);

            // Set counter into inputInitState
            Util.arrayCopyNonAtomic(counter, ctrOffset, inputInitState, (short) 48, (short) 4);

            // Set nonce into matrices then into little endian format
            Util.arrayCopyNonAtomic(nonce, nonceOffset, matrix13, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(nonce, (short) (nonceOffset + 4), matrix14, (short) 0, (short) 4);
            Util.arrayCopyNonAtomic(nonce, (short) (nonceOffset + 8), matrix15, (short) 0, (short) 4);
            littleEndian(matrix13, (short) 0, buffer, (short) 0);
            littleEndian(matrix14, (short) 0, buffer, (short) 0);
            littleEndian(matrix15, (short) 0, buffer, (short) 0);

            // Set nonce into inputInitState
            Util.arrayCopyNonAtomic(matrix13, (short) 0, inputInitState, (short) 52, (short) 4);
            Util.arrayCopyNonAtomic(matrix14, (short) 0, inputInitState, (short) 56, (short) 4);
            Util.arrayCopyNonAtomic(matrix15, (short) 0, inputInitState, (short) 60, (short) 4);
            return true;
        }
        return false;
    }

    /**
     * Encryption function which also serves as decryption function.
     *
     * @param key
     * @param keyOffset
     * @param nonce
     * @param nonceOffset
     * @param counter
     * @param ctrOffset
     * @param input
     * @param inOffset
     * @param length
     * @param output
     * @param outOffset
     * @return
     */
    public boolean encrypt(byte[] key, short keyOffset, byte[] nonce,
            short nonceOffset, byte[] counter, short ctrOffset, byte[] input,
            short inOffset, short length, byte[] output, short outOffset) {

        // Setup internal state
        if (init(key, keyOffset, nonce, nonceOffset, counter, ctrOffset)) {
            if (length <= 64) {
                
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 2
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 3
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 4
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 5
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 6
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 7
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 8
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 9
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//
//                // Quarter Round 10
//                quarterRound(matrix0, matrix4, matrix8, matrix12);
//                quarterRound(matrix1, matrix5, matrix9, matrix13);
//                quarterRound(matrix2, matrix6, matrix10, matrix14);
//                quarterRound(matrix3, matrix7, matrix11, matrix15);
//                quarterRound(matrix0, matrix5, matrix10, matrix15);
//                quarterRound(matrix1, matrix6, matrix11, matrix12);
//                quarterRound(matrix2, matrix7, matrix8, matrix13);
//                quarterRound(matrix3, matrix4, matrix9, matrix14);
//                
//                math.mod32Add(inputInitState, (short) 0, matrix0, (short) 0, inputInitState, (short) 0);
//                math.mod32Add(inputInitState, (short) 4, matrix1, (short) 0, inputInitState, (short) 4);
//                math.mod32Add(inputInitState, (short) 8, matrix2, (short) 0, inputInitState, (short) 8);
//                math.mod32Add(inputInitState, (short) 12, matrix3, (short) 0, inputInitState, (short) 12);
//                math.mod32Add(inputInitState, (short) 16, matrix4, (short) 0, inputInitState, (short) 16);
//                math.mod32Add(inputInitState, (short) 20, matrix5, (short) 0, inputInitState, (short) 20);
//                math.mod32Add(inputInitState, (short) 24, matrix6, (short) 0, inputInitState, (short) 24);
//                math.mod32Add(inputInitState, (short) 28, matrix7, (short) 0, inputInitState, (short) 28);
//                math.mod32Add(inputInitState, (short) 32, matrix8, (short) 0, inputInitState, (short) 32);
//                math.mod32Add(inputInitState, (short) 36, matrix9, (short) 0, inputInitState, (short) 36);
//                math.mod32Add(inputInitState, (short) 40, matrix10, (short) 0, inputInitState, (short) 40);
//                math.mod32Add(inputInitState, (short) 44, matrix11, (short) 0, inputInitState, (short) 44);
//                math.mod32Add(inputInitState, (short) 48, matrix12, (short) 0, inputInitState, (short) 48);
//                math.mod32Add(inputInitState, (short) 52, matrix13, (short) 0, inputInitState, (short) 52);
//                math.mod32Add(inputInitState, (short) 56, matrix14, (short) 0, inputInitState, (short) 56);
//                math.mod32Add(inputInitState, (short) 60, matrix15, (short) 0, inputInitState, (short) 60);
//                
//                littleEndian(inputInitState, (short) 0, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 4, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 8, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 12, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 16, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 20, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 24, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 28, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 32, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 36, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 40, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 44, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 48, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 52, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 56, buffer, (short) 0);
//                littleEndian(inputInitState, (short) 60, buffer, (short) 0);

                for (sBuff[1] = 0; sBuff[1] < 10; sBuff[1]++) {
                    quarterRound(matrix0, matrix4, matrix8, matrix12);
                    quarterRound(matrix1, matrix5, matrix9, matrix13);
                    quarterRound(matrix2, matrix6, matrix10, matrix14);
                    quarterRound(matrix3, matrix7, matrix11, matrix15);
                    quarterRound(matrix0, matrix5, matrix10, matrix15);
                    quarterRound(matrix1, matrix6, matrix11, matrix12);
                    quarterRound(matrix2, matrix7, matrix8, matrix13);
                    quarterRound(matrix3, matrix4, matrix9, matrix14);
                }

                // Update inputInitState (keystream) by mod32Add matrices
                for (sBuff[1] = 0; sBuff[1] < 64; sBuff[1] += 4) {
                    math.mod32Add(inputInitState, sBuff[1], matrix0, (short) 0, inputInitState, sBuff[1]);
                }

                // Convert inputInitState (keystream) into little endian format
                for (sBuff[1] = 0; sBuff[1] < 64; sBuff[1] += 4) {
                    littleEndian(inputInitState, sBuff[1], buffer, (short) 0);
                }

                // Loops through every byte of plaintext/ciphertext and then XOR it with finalized keystream
                for (sBuff[0] = 0; sBuff[0] < 64; sBuff[0]++) {
                    if (length > 0) {
                        output[(short) (outOffset + sBuff[0])] = (byte) (input[(short) (inOffset + sBuff[0])] ^ inputInitState[sBuff[0]]);
                        length--;
                    } else {
                        break;
                    }
                }

                if (length == 0) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * Decryption function.
     *
     * @param key
     * @param keyOffset
     * @param nonce
     * @param nonceOffset
     * @param counter
     * @param ctrOffset
     * @param input
     * @param inOffset
     * @param length
     * @param output
     * @param outOffset
     * @return
     */
    public boolean decrypt(byte[] key, short keyOffset, byte[] nonce,
            short nonceOffset, byte[] counter, short ctrOffset, byte[] input,
            short inOffset, short length, byte[] output, short outOffset) {
        return encrypt(key, keyOffset, nonce, nonceOffset, counter, ctrOffset, input, inOffset, length, output, outOffset);
    }

    /**
     * Reveals current keystream state. Disable this code in production
     * environment to prevent leaking of keystream state by accident.
     *
     * @return keystream in current state.
     */
    public byte[] getCurrentKeyStreamState() {
        return inputInitState;
    }
}
