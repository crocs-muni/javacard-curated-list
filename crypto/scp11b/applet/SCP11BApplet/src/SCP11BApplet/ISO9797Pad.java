package SCP11BApplet ;

import javacard.framework.Util;

public class ISO9797Pad {

	// GP 2.3.1 Section B.2.3
    public static short process(boolean isEncode, byte[] input, short iOff, short iLen, byte[] output, short oOff) {
        if (input != null && iLen != 0) {
            if (isEncode) {
                short outLen = iLen;
                if (output != null) {
                    Util.arrayCopyNonAtomic(input, iOff, output, oOff, iLen);
                    output[(short) (oOff + outLen)] = (byte) 0x80; // append '80' directly to back of input
                }                
                outLen++;
                while ((short) (outLen % 16) != 0) {
                    if (output != null) {
                        output[(short) (oOff + outLen)] = (byte) 0x00;
                    }
                    outLen++;
                }
                return outLen; // returns final total length of input + padding bytes
            } else {
                byte b;
                for (short i = (short) (iLen - 1); i >= 0; i--) {
                    b = input[(short) (iOff + i)];
                    if (b == (byte) 0x80) {
                        if (output != null) {
                            Util.arrayCopyNonAtomic(input, iOff, output, oOff, i);
                        }
                        return i; // returns length of unpadded data length
                    } else if (b == (byte) 0x00) {
                        // Do nothing and continue parsing
                    } else {
                        return -1;
                    }
                }
            }
        }
        return 0;
    }
}