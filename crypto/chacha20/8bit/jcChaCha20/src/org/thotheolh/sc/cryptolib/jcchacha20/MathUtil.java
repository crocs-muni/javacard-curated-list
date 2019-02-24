/*
 * 32 bit Math in 8 bit format.
 */
package org.thotheolh.sc.cryptolib.jcchacha20;

/**
 *
 * @author Thotheolh
 */
public class MathUtil {

    private static byte add(byte x, byte y, byte[] result, short index) {
        byte c = 0;
        while (y != 0) {
            byte t = (byte) (x & y);
            c |= t;
            x = (byte) (x ^ y);
            y = (byte) (t << 1);
        }
        result[index] = x;
        return (byte) ((c & 0x80) != 0 ? 1 : 0);
    }

    private static byte add(byte a, byte b, byte c, byte[] result, short index) {
        byte carry = add(a, b, result, index);
        if (c > 0) {
            carry |= add(result[index], c, result, index);
        }
        return carry;
    }

    private static void add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        byte c = 0;
        c = add(a[(short) (aOffset + 3)], b[(short) (bOffset + 3)], c, result, (short) (offset + 3));
        c = add(a[(short) (aOffset + 2)], b[(short) (bOffset + 2)], c, result, (short) (offset + 2));
        c = add(a[(short) (aOffset + 1)], b[(short) (bOffset + 1)], c, result, (short) (offset + 1));
        c = add(a[aOffset], b[bOffset], c, result, offset);
    }

    public static void mod32Add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        add(a, aOffset, b, bOffset, result, offset);
        result[offset] &= 0xFF;
        result[(short) (offset + 1)] &= 0xFF;
        result[(short) (offset + 2)] &= 0xFF;
        result[(short) (offset + 3)] &= 0xFF;
    }

    public static void xor32(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        result[offset] = (byte) (a[aOffset] ^ b[bOffset]);
        result[(short) (offset + 1)] = (byte) (a[(short) (aOffset + 1)] ^ b[(short) (bOffset + 1)]);
        result[(short) (offset + 2)] = (byte) (a[(short) (aOffset + 2)] ^ b[(short) (bOffset + 2)]);
        result[(short) (offset + 3)] = (byte) (a[(short) (aOffset + 3)] ^ b[(short) (bOffset + 3)]);
    }

    public static void rotl32(byte[] a, short offset, short amt, byte[] buff, short buffOffset) {
        byte normalizer;

        if (amt == 7) {
            normalizer = (byte) 0x80;

            if (((byte) a[offset] | (byte) 0x7F) != (byte) 0x7F) {
                buff[buffOffset] = (byte) (a[offset] >>> 1 ^ normalizer);
            } else {
                buff[buffOffset] = (byte) (a[offset] >>> 1);
            }

            if (((byte) a[(short) (offset + 1)] | (byte) 0x7F) != (byte) 0x7F) {
                a[offset] = (byte) ((a[offset] << 7) | (a[(short) (offset + 1)] >>> 1) ^ normalizer);
            } else {
                a[offset] = (byte) ((a[offset] << 7) | (a[(short) (offset + 1)] >>> 1));
            }

            if (((byte) a[(short) (offset + 2)] | (byte) 0x7F) != (byte) 0x7F) {
                a[(short) (offset + 1)] = (byte) ((a[(short) (offset + 1)] << 7) | ((a[(short) (offset + 2)] >>> 1) ^ normalizer));
            } else {
                a[(short) (offset + 1)] = (byte) ((a[(short) (offset + 1)] << 7) | ((a[(short) (offset + 2)] >>> 1)));
            }

            if (((byte) a[(short) (offset + 3)] | (byte) 0x7F) != (byte) 0x7F) {
                a[(short) (offset + 2)] = (byte) ((a[(short) (offset + 2)] << 7) | ((a[(short) (offset + 3)] >>> 1) ^ normalizer));
            } else {
                a[(short) (offset + 2)] = (byte) ((a[(short) (offset + 2)] << 7) | ((a[(short) (offset + 3)] >>> 1)));
            }

            a[(short) (offset + 3)] = (byte) ((a[(short) (offset + 3)] << 7) | buff[buffOffset]);

        } else if (amt == 8) {
            buff[buffOffset] = a[offset];
            a[offset] = a[(short) (offset + 1)];
            a[(short) (offset + 1)] = a[(short) (offset + 2)];
            a[(short) (offset + 2)] = a[(short) (offset + 3)];
            a[(short) (offset + 3)] = buff[offset];
        } else if (amt == 12) {
            buff[buffOffset] = a[offset];
            buff[(short) (buffOffset + 1)] = a[(short) (offset + 1)];
            normalizer = (byte) 0xF0;

            if (((byte) a[(short) (offset + 2)] | (byte) 0x7F) != (byte) 0x7F) {
                a[offset] = (byte) ((a[(short) (offset + 1)] << 4) ^ ((a[(short) (offset + 2)] >>> 4) ^ normalizer));
            } else {
                a[offset] = (byte) ((a[(short) (offset + 1)] << 4) ^ (a[(short) (offset + 2)] >>> 4));
            }

            if (((byte) a[(short) (offset + 3)] | (byte) 0x7F) != (byte) 0x7F) {
                a[(short) (offset + 1)] = (byte) ((a[(short) (offset + 2)] << 4) ^ ((a[(short) (offset + 3)] >>> 4) ^ normalizer));
            } else {
                a[(short) (offset + 1)] = (byte) ((a[(short) (offset + 2)] << 4) ^ (a[(short) (offset + 3)] >>> 4));
            }

            if (((byte) buff[offset] | (byte) 0x7F) != (byte) 0x7F) {
                a[(short) (offset + 2)] = (byte) ((a[(short) (offset + 3)] << 4) ^ ((buff[offset] >>> 4) ^ normalizer));
            } else {
                a[(short) (offset + 2)] = (byte) ((a[(short) (offset + 3)] << 4) ^ (buff[offset] >>> 4));
            }

            if (((byte) buff[(short) (offset + 1)] | (byte) 0x7F) != (byte) 0x7F) {
                a[(short) (offset + 3)] = (byte) ((buff[offset] << 4) ^ ((buff[(short) (offset + 1)] >>> 4) ^ normalizer));
            } else {
                a[(short) (offset + 3)] = (byte) ((buff[offset] << 4) ^ (buff[(short) (offset + 1)] >>> 4));
            }

        } else if (amt == 16) {
            buff[buffOffset] = a[offset];
            buff[(short) (buffOffset + 1)] = a[(short) (offset + 1)];
            a[offset] = a[(short) (offset + 2)];
            a[(short) (offset + 1)] = a[(short) (offset + 3)];
            a[(short) (offset + 2)] = buff[offset];
            a[(short) (offset + 3)] = buff[(short) (offset + 1)];
        }
    }
}