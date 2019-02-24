/*
 * 20 rounds of ChaCha20 using only 8 bit maths for embedded purposes. Uses 
 * RFC7539 Standard setup of key, nonce and counter (32 bytes, 12 bytes, 
 * 4 bytes) accordingly.
 *
 */
package org.thotheolh.sc.cryptolib.jcchacha20;

import javacard.framework.JCSystem;
import javacardx.framework.util.ArrayLogic;

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
    private short[] sBuff = JCSystem.makeTransientShortArray((short) 1, JCSystem.CLEAR_ON_RESET);

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
        MathUtil.mod32Add(a, (short) 0, b, (short) 0, a, (short) 0);

        // d ^= a;
        MathUtil.xor32(d, (short) 0, a, (short) 0, d, (short) 0);

        // d <<<= 16;
        MathUtil.rotl32(d, (short) 0, (short) 16, buffer, (short) 0);

        // c += d;
        MathUtil.mod32Add(c, (short) 0, d, (short) 0, c, (short) 0);

        // b ^= c;
        MathUtil.xor32(b, (short) 0, c, (short) 0, b, (short) 0);

        // b <<<= 12;
        MathUtil.rotl32(b, (short) 0, (short) 12, buffer, (short) 0);

        // a += b;
        MathUtil.mod32Add(a, (short) 0, b, (short) 0, a, (short) 0);

        // d ^= a;
        MathUtil.xor32(d, (short) 0, a, (short) 0, d, (short) 0);

        // d <<<= 8;
        MathUtil.rotl32(d, (short) 0, (short) 8, buffer, (short) 0);

        // c += d;
        MathUtil.mod32Add(c, (short) 0, d, (short) 0, c, (short) 0);

        // b ^= c;
        MathUtil.xor32(b, (short) 0, c, (short) 0, b, (short) 0);

        // b <<<= 7;
        MathUtil.rotl32(b, (short) 0, (short) 7, buffer, (short) 0);
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
        ArrayLogic.arrayCopyRepackNonAtomic(data, dataOffset, (short) 4, buff, buffOffset);
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
            ArrayLogic.arrayCopyRepackNonAtomic(key, keyOffset, (short) 4, matrix4, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 4), (short) 4, matrix5, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 8), (short) 4, matrix6, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 12), (short) 4, matrix7, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 16), (short) 4, matrix8, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 20), (short) 4, matrix9, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 24), (short) 4, matrix10, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(key, (short) (keyOffset + 28), (short) 4, matrix11, (short) 0);
            littleEndian(matrix4, (short) 0, buffer, (short) 0);
            littleEndian(matrix5, (short) 0, buffer, (short) 0);
            littleEndian(matrix6, (short) 0, buffer, (short) 0);
            littleEndian(matrix7, (short) 0, buffer, (short) 0);
            littleEndian(matrix8, (short) 0, buffer, (short) 0);
            littleEndian(matrix9, (short) 0, buffer, (short) 0);
            littleEndian(matrix10, (short) 0, buffer, (short) 0);
            littleEndian(matrix11, (short) 0, buffer, (short) 0);

            // Set key into inputInitState
            ArrayLogic.arrayCopyRepackNonAtomic(matrix4, (short) 0, (short) 4, inputInitState, (short) 16);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix5, (short) 0, (short) 4, inputInitState, (short) 20);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix6, (short) 0, (short) 4, inputInitState, (short) 24);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix7, (short) 0, (short) 4, inputInitState, (short) 28);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix8, (short) 0, (short) 4, inputInitState, (short) 32);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix9, (short) 0, (short) 4, inputInitState, (short) 36);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix10, (short) 0, (short) 4, inputInitState, (short) 40);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix11, (short) 0, (short) 4, inputInitState, (short) 44);

            // Set counter into matrices
            ArrayLogic.arrayCopyRepackNonAtomic(counter, ctrOffset, (short) 4, matrix12, (short) 0);

            // Set counter into inputInitState
            ArrayLogic.arrayCopyRepackNonAtomic(counter, ctrOffset, (short) 4, inputInitState, (short) 48);

            // Set nonce into matrices then into little endian format
            ArrayLogic.arrayCopyRepackNonAtomic(nonce, nonceOffset, (short) 4, matrix13, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(nonce, (short) (nonceOffset + 4), (short) 4, matrix14, (short) 0);
            ArrayLogic.arrayCopyRepackNonAtomic(nonce, (short) (nonceOffset + 8), (short) 4, matrix15, (short) 0);
            littleEndian(matrix13, (short) 0, buffer, (short) 0);
            littleEndian(matrix14, (short) 0, buffer, (short) 0);
            littleEndian(matrix15, (short) 0, buffer, (short) 0);

            // Set nonce into inputInitState
            ArrayLogic.arrayCopyRepackNonAtomic(matrix13, (short) 0, (short) 4, inputInitState, (short) 52);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix14, (short) 0, (short) 4, inputInitState, (short) 56);
            ArrayLogic.arrayCopyRepackNonAtomic(matrix15, (short) 0, (short) 4, inputInitState, (short) 60);
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
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 2
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 3
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 4
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 5
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 6
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 7
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 8
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 9
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Quarter Round 10
                quarterRound(matrix0, matrix4, matrix8, matrix12);
                quarterRound(matrix1, matrix5, matrix9, matrix13);
                quarterRound(matrix2, matrix6, matrix10, matrix14);
                quarterRound(matrix3, matrix7, matrix11, matrix15);
                quarterRound(matrix0, matrix5, matrix10, matrix15);
                quarterRound(matrix1, matrix6, matrix11, matrix12);
                quarterRound(matrix2, matrix7, matrix8, matrix13);
                quarterRound(matrix3, matrix4, matrix9, matrix14);

                // Update inputInitState (keystream) by mod32Add matrices
                MathUtil.mod32Add(inputInitState, (short) 0, matrix0, (short) 0, inputInitState, (short) 0);
                MathUtil.mod32Add(inputInitState, (short) 4, matrix1, (short) 0, inputInitState, (short) 4);
                MathUtil.mod32Add(inputInitState, (short) 8, matrix2, (short) 0, inputInitState, (short) 8);
                MathUtil.mod32Add(inputInitState, (short) 12, matrix3, (short) 0, inputInitState, (short) 12);
                MathUtil.mod32Add(inputInitState, (short) 16, matrix4, (short) 0, inputInitState, (short) 16);
                MathUtil.mod32Add(inputInitState, (short) 20, matrix5, (short) 0, inputInitState, (short) 20);
                MathUtil.mod32Add(inputInitState, (short) 24, matrix6, (short) 0, inputInitState, (short) 24);
                MathUtil.mod32Add(inputInitState, (short) 28, matrix7, (short) 0, inputInitState, (short) 28);
                MathUtil.mod32Add(inputInitState, (short) 32, matrix8, (short) 0, inputInitState, (short) 32);
                MathUtil.mod32Add(inputInitState, (short) 36, matrix9, (short) 0, inputInitState, (short) 36);
                MathUtil.mod32Add(inputInitState, (short) 40, matrix10, (short) 0, inputInitState, (short) 40);
                MathUtil.mod32Add(inputInitState, (short) 44, matrix11, (short) 0, inputInitState, (short) 44);
                MathUtil.mod32Add(inputInitState, (short) 48, matrix12, (short) 0, inputInitState, (short) 48);
                MathUtil.mod32Add(inputInitState, (short) 52, matrix13, (short) 0, inputInitState, (short) 52);
                MathUtil.mod32Add(inputInitState, (short) 56, matrix14, (short) 0, inputInitState, (short) 56);
                MathUtil.mod32Add(inputInitState, (short) 60, matrix15, (short) 0, inputInitState, (short) 60);

                // Convert inputInitState (keystream) into little endian format
                littleEndian(inputInitState, (short) 0, buffer, (short) 0);
                littleEndian(inputInitState, (short) 4, buffer, (short) 0);
                littleEndian(inputInitState, (short) 8, buffer, (short) 0);
                littleEndian(inputInitState, (short) 12, buffer, (short) 0);
                littleEndian(inputInitState, (short) 16, buffer, (short) 0);
                littleEndian(inputInitState, (short) 20, buffer, (short) 0);
                littleEndian(inputInitState, (short) 24, buffer, (short) 0);
                littleEndian(inputInitState, (short) 28, buffer, (short) 0);
                littleEndian(inputInitState, (short) 32, buffer, (short) 0);
                littleEndian(inputInitState, (short) 36, buffer, (short) 0);
                littleEndian(inputInitState, (short) 40, buffer, (short) 0);
                littleEndian(inputInitState, (short) 44, buffer, (short) 0);
                littleEndian(inputInitState, (short) 48, buffer, (short) 0);
                littleEndian(inputInitState, (short) 52, buffer, (short) 0);
                littleEndian(inputInitState, (short) 56, buffer, (short) 0);
                littleEndian(inputInitState, (short) 60, buffer, (short) 0);

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
     * Reveals current keystream state. Disable this code in production environment to prevent leaking of keystream state by accident.
     * 
     * @return keystream in current state.
     */
    public byte[] getCurrentKeyStreamState() {
        return inputInitState;
    }
}
