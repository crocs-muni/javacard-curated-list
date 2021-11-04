/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thothtrust.sc.scplib.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author Thotheolh
 */
public class MathUtil {

    public static byte[] littleEndian(int a) {
//        return new byte[]{
//            (byte) a,
//            (byte) (a >>> 8),
//            (byte) (a >>> 16),
//            (byte) (a >>> 24)
//        };
        byte[] res = new byte[4];
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(a);
        bb.flip();
        bb.get(res);
        return res;
    }

    public static void littleEndian(int a, byte[] destination, int offset) {
//        destination[offset] = (byte) a;
//        destination[offset + 1] = (byte) (a >>> 8);
//        destination[offset + 2] = (byte) (a >>> 16);
//        destination[offset + 3] = (byte) (a >>> 24);
        byte[] res = new byte[4];
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(a);
        bb.flip();
        bb.get(res);
        System.arraycopy(res, 0, destination, offset, 4);
    }

    public static byte[] littleEndian64(int a) {
        return new byte[]{
            (byte) a, (byte) (a >>> 8), (byte) (a >>> 16), (byte) (a >>> 24),
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
        };
    }

    public static void littleEndian64(int a, byte[] destination, int offset) {
        destination[offset + 7] = (byte) 0x00;
        destination[offset + 6] = (byte) 0x00;
        destination[offset + 5] = (byte) 0x00;
        destination[offset + 4] = (byte) 0x00;
        destination[offset + 3] = (byte) (a >>> 24);
        destination[offset + 2] = (byte) (a >>> 16);
        destination[offset + 1] = (byte) (a >>> 8);
        destination[offset] = (byte) a;

    }

    public static short getShort(byte[] a, int offset) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(a[offset]);
        bb.put(a[offset + 1]);
        return bb.getShort(0);
    }

    /**
     * Translates little endian encoded 32 bit bit string to integer.
     *
     * @param a
     * @param offset
     * @return
     */
    public static int getInt(byte[] a, int offset) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(a, offset, 4);
        bb.flip();
        return bb.getInt();
    }

    /**
     * Translates little endian encoded 64 bit bit string to long.
     *
     * @param a
     * @param offset
     * @return
     */
    public static long getLong(byte[] a, int offset) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.put(a, offset, 8);
        bb.flip();
        return bb.getLong();
    }

    public static int toUnsignedInt(byte x) {
        return ((int) x) & 0xff;
    }

    public static boolean isEven(BigInteger number) {
        return number.getLowestSetBit() != 0;
    }
    
    public static void xorN(byte[] a, short aOffset, byte[] b, short bOffset, short xorLen, byte[] result,
            short offset) {
        for (short i = 0; i < xorLen; i++) {
            result[(short) (offset + i)] = (byte) (a[(short) (aOffset + i)] ^ b[(short) (bOffset + i)]);
        }
    }
    
    public static void shiftLeft(byte[] data, short off, short len, short amt) {
        short word_size = (short) ((amt / 8) + 1);
        short shift = (short) (amt % 8);
        byte carry_mask = (byte) ((1 << shift) - 1);
        short offset = (short) (word_size - 1);
        for (short i = 0; i < len; i++) {
            short src_index = (short) (i + offset);
            if (src_index >= len) {
                data[(short) (off + i)] = (short) 0;
            } else {
                byte src = data[(short) (off + src_index)];
                byte dst = (byte) (src << shift);
                if ((short) (src_index + 1) < len) {
                    dst |= data[(short) (off + src_index + 1)] >>> (8 - shift) & carry_mask;
                }
                data[(short) (off + i)] = dst;
            }
        }
    }
    
    public static short ceil(short a, short b) {
        return (short) ((a + b - 1) / b);
    }
    
    private static byte add(byte x, byte y, byte[] result, int index) {
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
    
    private static byte add(byte a, byte b, byte c, byte[] result, int index) {
        byte carry = add(a, b, result, index);
        if (c > 0) {
            carry |= add(result[index], c, result, index);
        }
        return carry;
    }
    
    private static void add128(byte[] a, int aOffset, byte[] b, int bOffset, byte[] result, int offset) {
        byte c = 0;
        c = add(a[(short) (aOffset + 15)], b[(short) (bOffset + 15)], c, result, (short) (offset + 15));
        c = add(a[(short) (aOffset + 14)], b[(short) (bOffset + 14)], c, result, (short) (offset + 14));
        c = add(a[(short) (aOffset + 13)], b[(short) (bOffset + 13)], c, result, (short) (offset + 13));
        c = add(a[(short) (aOffset + 12)], b[(short) (bOffset + 12)], c, result, (short) (offset + 12));
        c = add(a[(short) (aOffset + 11)], b[(short) (bOffset + 11)], c, result, (short) (offset + 11));
        c = add(a[(short) (aOffset + 10)], b[(short) (bOffset + 10)], c, result, (short) (offset + 10));
        c = add(a[(short) (aOffset + 9)], b[(short) (bOffset + 9)], c, result, (short) (offset + 9));
        c = add(a[(short) (aOffset + 8)], b[(short) (bOffset + 8)], c, result, (short) (offset + 8));
        c = add(a[(short) (aOffset + 7)], b[(short) (bOffset + 7)], c, result, (short) (offset + 7));
        c = add(a[(short) (aOffset + 6)], b[(short) (bOffset + 6)], c, result, (short) (offset + 6));
        c = add(a[(short) (aOffset + 5)], b[(short) (bOffset + 5)], c, result, (short) (offset + 5));
        c = add(a[(short) (aOffset + 4)], b[(short) (bOffset + 4)], c, result, (short) (offset + 4));
        c = add(a[(short) (aOffset + 3)], b[(short) (bOffset + 3)], c, result, (short) (offset + 3));
        c = add(a[(short) (aOffset + 2)], b[(short) (bOffset + 2)], c, result, (short) (offset + 2));
        c = add(a[(short) (aOffset + 1)], b[(short) (bOffset + 1)], c, result, (short) (offset + 1));
        c = add(a[aOffset], b[bOffset], c, result, offset);
    }
    
    public static void mod128Add(byte[] a, int aOffset, byte[] b, int bOffset, byte[] result, int offset) {
        add128(a, aOffset, b, bOffset, result, offset);
        result[offset] &= 0xFF;
        result[(short) (offset + 1)] &= 0xFF;
        result[(short) (offset + 2)] &= 0xFF;
        result[(short) (offset + 3)] &= 0xFF;
        result[(short) (offset + 4)] &= 0xFF;
        result[(short) (offset + 5)] &= 0xFF;
        result[(short) (offset + 6)] &= 0xFF;
        result[(short) (offset + 7)] &= 0xFF;
        result[(short) (offset + 8)] &= 0xFF;
        result[(short) (offset + 9)] &= 0xFF;
        result[(short) (offset + 10)] &= 0xFF;
        result[(short) (offset + 11)] &= 0xFF;
        result[(short) (offset + 12)] &= 0xFF;
        result[(short) (offset + 13)] &= 0xFF;
        result[(short) (offset + 14)] &= 0xFF;
        result[(short) (offset + 15)] &= 0xFF;
    }
    
    public static void increment128(byte[] input, int offset, byte[] result, int resOffset) {
        byte[] buff = new byte[16];
        buff[15] = (byte) 0x01;
        mod128Add(input, offset, buff, (short) 0, result, resOffset);
    }
    
}
