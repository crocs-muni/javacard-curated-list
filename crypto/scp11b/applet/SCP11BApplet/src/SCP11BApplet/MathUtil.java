package SCP11BApplet ;

import javacard.framework.Util;
import javacard.framework.ISOException;

public class MathUtil {

    public static final byte CMP_EQUALS_TO = (byte) 0x00;
    public static final byte CMP_BIGGER_THAN = (byte) 0x01;
    public static final byte CMP_SMALLER_THAN = (byte) 0x02;
    public static final byte CMP_FAIL = (byte) 0x0F;
    public static final byte[] ASCIIHEX = new byte[] { (byte) 0x30, (byte) 0x31, (byte) 0x32, (byte) 0x33, (byte) 0x34,
            (byte) 0x35, (byte) 0x36, (byte) 0x37, (byte) 0x38, (byte) 0x39, (byte) 0x41, (byte) 0x42, (byte) 0x43,
            (byte) 0x44, (byte) 0x45, (byte) 0x46 };
    public static final byte[] BCD_SIZE_PER_BYTES = { 0, 3, 5, 8, 10, 13, 15, 17, 20, 22, 25, 27, 29, 32, 34, 37, 39,
            41, 44, 46, 49, 51, 53, 56, 58, 61, 63, 66, 68, 70, 73, 75, 78 };

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

    private static void add64(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        byte c = 0;
        c = add(a[(short) (aOffset + 7)], b[(short) (bOffset + 7)], c, result, (short) (offset + 7));
        c = add(a[(short) (aOffset + 6)], b[(short) (bOffset + 6)], c, result, (short) (offset + 6));
        c = add(a[(short) (aOffset + 5)], b[(short) (bOffset + 5)], c, result, (short) (offset + 5));
        c = add(a[(short) (aOffset + 4)], b[(short) (bOffset + 4)], c, result, (short) (offset + 4));
        c = add(a[(short) (aOffset + 3)], b[(short) (bOffset + 3)], c, result, (short) (offset + 3));
        c = add(a[(short) (aOffset + 2)], b[(short) (bOffset + 2)], c, result, (short) (offset + 2));
        c = add(a[(short) (aOffset + 1)], b[(short) (bOffset + 1)], c, result, (short) (offset + 1));
        c = add(a[aOffset], b[bOffset], c, result, offset);
    }

    private static void add128(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
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

    public static void mod32Add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        add(a, aOffset, b, bOffset, result, offset);
        result[offset] &= 0xFF;
        result[(short) (offset + 1)] &= 0xFF;
        result[(short) (offset + 2)] &= 0xFF;
        result[(short) (offset + 3)] &= 0xFF;
    }

    public static void mod64Add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        add64(a, aOffset, b, bOffset, result, offset);
        result[offset] &= 0xFF;
        result[(short) (offset + 1)] &= 0xFF;
        result[(short) (offset + 2)] &= 0xFF;
        result[(short) (offset + 3)] &= 0xFF;
        result[(short) (offset + 4)] &= 0xFF;
        result[(short) (offset + 5)] &= 0xFF;
        result[(short) (offset + 6)] &= 0xFF;
        result[(short) (offset + 7)] &= 0xFF;
    }
    
    public static void mod128Add(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
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

    public static void increment64(byte[] input, short offset, byte[] buff, short buffOffset, byte[] result,
            short resOffset) {
        Util.arrayFillNonAtomic(buff, buffOffset, (short) 8, (byte) 0x00);
        buff[(short) (buffOffset + 7)] = (byte) 0x01;
        mod64Add(input, offset, buff, buffOffset, result, resOffset);
    }
    
    public static void increment128(byte[] input, short offset, byte[] buff, short buffOffset, byte[] result,
            short resOffset) {
        Util.arrayFillNonAtomic(buff, buffOffset, (short) 16, (byte) 0x00);
        buff[(short) (buffOffset + 15)] = (byte) 0x01;
        mod128Add(input, offset, buff, buffOffset, result, resOffset);
    }

    public static void and64(byte[] a, short aOffset, byte[] b, short bOffset, byte[] result, short offset) {
        result[offset] = (byte) (a[aOffset] & b[bOffset]);
        result[(short) (offset + 1)] = (byte) (a[(short) (aOffset + 1)] & b[(short) (bOffset + 1)]);
        result[(short) (offset + 2)] = (byte) (a[(short) (aOffset + 2)] & b[(short) (bOffset + 2)]);
        result[(short) (offset + 3)] = (byte) (a[(short) (aOffset + 3)] & b[(short) (bOffset + 3)]);
        result[(short) (offset + 4)] = (byte) (a[(short) (aOffset + 4)] & b[(short) (bOffset + 4)]);
        result[(short) (offset + 5)] = (byte) (a[(short) (aOffset + 5)] & b[(short) (bOffset + 5)]);
        result[(short) (offset + 6)] = (byte) (a[(short) (aOffset + 6)] & b[(short) (bOffset + 6)]);
        result[(short) (offset + 7)] = (byte) (a[(short) (aOffset + 7)] & b[(short) (bOffset + 7)]);
    }

    public static void xorN(byte[] a, short aOffset, byte[] b, short bOffset, short xorLen, byte[] result,
            short offset) {
        for (short i = 0; i < xorLen; i++) {
            result[(short) (offset + i)] = (byte) (a[(short) (aOffset + i)] ^ b[(short) (bOffset + i)]);
        }
    }

    public static void rotl(byte[] a, short offset, short amt, byte[] buff, short buffOffset, short bitLenOps) {
        short sb1 = (short) (amt % 8);
        short sb2 = (short) (amt / 8);
        short sb3 = 0;
        short sb4 = 0;
        short sb5 = (short) (bitLenOps / 8);
        buff[buffOffset] = a[offset];
        for (sb3 = (short) (sb5 - 1); sb3 >= (short) 0; sb3--) {
            buff[(short) (buffOffset + 1)] = a[(short) (offset + sb3)];
            sb4 = (short) ((sb3 - sb2 + 8) % sb5);
            buff[(short) (buffOffset + 2 + sb4)] = (byte) ((byte) (buff[(short) (buffOffset + 1)] << sb1)
                    | (((byte) buff[buffOffset] & 0xff) >>> ((short) 8 - sb1)));
            buff[buffOffset] = buff[(short) (buffOffset + 1)];
        }

        Util.arrayCopyNonAtomic(buff, (short) (buffOffset + 2), a, offset, sb5);
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

    public static void flip64(byte[] a, short offset) {
        a[offset] = (byte) ~a[offset];
        a[(short) (offset + 1)] = (byte) ~a[(short) (offset + 1)];
        a[(short) (offset + 2)] = (byte) ~a[(short) (offset + 2)];
        a[(short) (offset + 3)] = (byte) ~a[(short) (offset + 3)];
        a[(short) (offset + 4)] = (byte) ~a[(short) (offset + 4)];
        a[(short) (offset + 5)] = (byte) ~a[(short) (offset + 5)];
        a[(short) (offset + 6)] = (byte) ~a[(short) (offset + 6)];
        a[(short) (offset + 7)] = (byte) ~a[(short) (offset + 7)];
    }

    public static void littleEndian64(byte[] input, short inOffset, byte[] output, short outOffset, byte buff) {
        // Swap pos 0 and 7
        try {
            buff = input[(short) (inOffset + 7)];
            output[(short) (outOffset + 7)] = input[inOffset];
            output[outOffset] = buff;
        } catch (ArrayIndexOutOfBoundsException e) {
            ISOException.throwIt(Util.makeShort((byte) 0x6a, (byte) 0xf1));
        }

        // Swap pos 1 and 6
        try {
            buff = input[(short) (inOffset + 6)];
            output[(short) (outOffset + 6)] = input[(short) (inOffset + 1)];
            output[(short) (outOffset + 1)] = buff;
        } catch (ArrayIndexOutOfBoundsException e) {
            ISOException.throwIt(Util.makeShort((byte) 0x6a, (byte) 0xf2));
        }

        // Swap pos 2 and 5
        try {
            buff = input[(short) (inOffset + 5)];
            output[(short) (outOffset + 5)] = input[(short) (inOffset + 2)];
            output[(short) (outOffset + 2)] = buff;
        } catch (ArrayIndexOutOfBoundsException e) {
            ISOException.throwIt(Util.makeShort((byte) 0x6a, (byte) 0xf3));
        }

        // Swap pos 3 and 4
        try {
            buff = input[(short) (inOffset + 4)];
            output[(short) (outOffset + 4)] = input[(short) (inOffset + 3)];
            output[(short) (outOffset + 3)] = buff;
        } catch (ArrayIndexOutOfBoundsException e) {
            ISOException.throwIt(Util.makeShort((byte) 0x6a, (byte) 0xf4));
        }
    }

    public static byte unixTimeCompare(byte[] compareFrom, short frmOffset, byte[] compareTo, short toOffset,
            short sbuff) {
        for (sbuff = (short) 0; sbuff < (short) 4; sbuff++) {
            if (compareFrom[(short) (frmOffset + sbuff)] != compareTo[(short) (toOffset + sbuff)]) {
                if ((short) (compareFrom[(short) (frmOffset + sbuff)]
                        & 0xFF) > (short) (compareTo[(short) (toOffset + sbuff)] & 0xFF)) {
                    return CMP_BIGGER_THAN;
                } else {
                    return CMP_SMALLER_THAN;
                }
            }
        }

        // Equals scenario where none of the looped comparison had conditions matched
        return CMP_EQUALS_TO;
    }

    public static short binToAsciiHex(byte[] input, short offset, short len, byte[] output, short outOff, short sbuff) {
        for (sbuff = (short) 0; sbuff < (short) (len * 2); sbuff += (short) 2) {
            output[(short) (outOff + sbuff)] = ASCIIHEX[(short) ((input[(short) (offset + (sbuff / 2))] >> (short) 4)
                    & (byte) 0x0F)];
            output[(short) (outOff + sbuff + 1)] = ASCIIHEX[(short) (input[(short) (offset + (sbuff / 2))]
                    & (byte) 0x0F)];
        }
        return (short) (len * 2);
    }

    public static byte asciiNibbleToBin(byte b) {
        if (((short) (b & 0xFF) > (short) 47) && ((short) (b & 0xFF) < (short) 58)) {
            return (byte) (b & 0x0F);
        } else {
            if ((b == (byte) 0x41) || (b == (byte) 0x61)) {
                return (byte) 0x0a;
            } else if ((b == (byte) 0x42) || (b == (byte) 0x62)) {
                return (byte) 0x0b;
            } else if ((b == (byte) 0x43) || (b == (byte) 0x63)) {
                return (byte) 0x0c;
            } else if ((b == (byte) 0x44) || (b == (byte) 0x64)) {
                return (byte) 0x0d;
            } else if ((b == (byte) 0x45) || (b == (byte) 0x65)) {
                return (byte) 0x0e;
            } else if ((b == (byte) 0x46) || (b == (byte) 0x66)) {
                return (byte) 0x0f;
            }
        }
        return (byte) 0xFF;
    }

    public static short hexStrToBin(byte[] input, short offset, short len, byte[] output, short outOff, short sbuff,
            byte[] buff, short buffOffset) {
        if ((short) (len % 2) == (short) 0) {
            for (sbuff = (short) 0; sbuff < len; sbuff += (short) 2) {
                buff[buffOffset] = asciiNibbleToBin(input[(short) (offset + sbuff)]);
                buff[(short) (buffOffset + 1)] = asciiNibbleToBin(input[(short) (offset + sbuff + 1)]);
                if ((buff[buffOffset] == (byte) 0xFF) || (buff[(short) (buffOffset + 1)] == (byte) 0xFF)) {
                    return (short) -1;
                }
                output[(short) (outOff + (sbuff / 2))] = (byte) ((byte) (buff[buffOffset] << 4)
                        | buff[(short) (buffOffset + 1)]);
            }
            return (short) (len / 2);
        }

        return (short) -1;
    }

    // NOTE: Only to be used on ASCIIfied positive integers of no more than 2 digits
    // length.
    public static void asciiToInt(byte[] input, short offset, short len, byte[] output, short outOffset) {
        output[outOffset] = (byte) (input[offset] & 0x0F);
        if (len == (short) 2) {
            output[outOffset] = (byte) ((byte) (output[outOffset] << 4) | (byte) (input[(short) (offset + 1)] & 0x0F));
        }
    }

    public static short toDecimalASCII(byte[] uBigBuf, short uBigOff, short uBigLen, byte[] decBuf, short decOff) {
        short bcdDigits = (short) BCD_SIZE_PER_BYTES[uBigLen];
        short byteValue, dividend, remainder;

        for (short bcdIndex = 0; bcdIndex < bcdDigits; bcdIndex++) {
            remainder = 0;
            for (short uBigIndex = 0; uBigIndex < uBigLen; uBigIndex++) {
                byteValue = (short) (uBigBuf[(short) (uBigOff + uBigIndex)] & 0xFF);
                dividend = (short) (remainder * 256 + byteValue);
                remainder = (short) (dividend % 10);
                uBigBuf[(short) (uBigOff + uBigIndex)] = (byte) (dividend / 10);
            }
            decBuf[(short) (decOff + bcdDigits - bcdIndex - 1)] = (byte) (remainder + '0');
        }

        return bcdDigits;
    }

    public static byte byteBool(boolean b) {
        if (b) {
            return (byte) 0xFF;
        } else {
            return (byte) 0x00;
        }
    }

    public static boolean boolByte(byte b) {
        if (b == (byte) 0xFF) {
            return true;
        }

        return false;
    }

    public static short shortBool(boolean b) {
        if (b) {
            return (short) 1;
        } else {
            return (short) 0;
        }
    }

    public static boolean boolShort(short b) {
        if (b == (short) 1) {
            return true;
        } else {
            return false;
        }
    }

    public static short ceil(short a, short b) {
        return (short) ((a + b - 1) / b);
    }
}