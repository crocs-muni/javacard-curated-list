/*
 * 32 bit Math in 32 bit format.
 */
package org.thotheolh.sc.cryptolib.jcchacha32;

import javacard.framework.JCSystem;
import javacardx.framework.util.intx.JCint;

/**
 *
 * @author Thotheolh
 */
public class MathUtil {
    
    private int[] varops = JCint.makeTransientIntArray((short) 2, JCSystem.CLEAR_ON_DESELECT);
    
    public MathUtil() {
    }

    public void mod32Add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        varops[0] = JCint.getInt(a, aOffset);
        varops[1] = JCint.getInt(b, bOffset);
        varops[0] += varops[1];        
        result[(short) (offset + 3)] = (byte) varops[0];
        result[(short) (offset + 2)] = (byte) (varops[0] >>> 8);
        result[(short) (offset + 1)] = (byte) (varops[0] >>> 16);
        result[offset] = (byte) (varops[0] >>> 24);
    }

    public void xor32(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        varops[0] = JCint.getInt(a, aOffset);
        varops[1] = JCint.getInt(b, bOffset);
        varops[0] += varops[1];
        result[(short) (offset + 3)] = (byte) varops[0];
        result[(short) (offset + 2)] = (byte) (varops[0] >>> 8);
        result[(short) (offset + 1)] = (byte) (varops[0] >>> 16);
        result[offset] = (byte) (varops[0] >>> 24);
    }

    /**
     * Emulating J2SE code:
     *
     * static int ROTATE(int v, int c) { return (v << c) | (v >>> (32 - c)); }
     *
     *
     * @param a
     * @param aOffset
     * @param amt
     * @param result
     * @param offset
     */
    public void rotl32(byte[] a, short aOffset, short amt, byte[] result, short offset) {
        varops[0] = JCint.getInt(a, aOffset);
        // varops[1] = amt;
        // varops[0] = (varops[0] << varops[1]) | (varops[0] >>> (32 - varops[1]));        
        result[(short) (offset + 3)] = (byte) varops[0];
        result[(short) (offset + 2)] = (byte) (varops[0] >>> 8);
        result[(short) (offset + 1)] = (byte) (varops[0] >>> 16);
        result[offset] = (byte) (varops[0] >>> 24);
    }
}
