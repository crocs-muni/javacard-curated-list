package SCP11BApplet ;

import javacard.security.ECKey;
import javacard.framework.ISOException;
import javacard.framework.Util;

public class ECC {
    // P256R1
    protected static byte[] EC_P256R1_FIELD_A = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFC };
    protected static byte[] EC_P256R1_FIELD_B = { (byte) 0x5A, (byte) 0xC6, (byte) 0x35, (byte) 0xD8, (byte) 0xAA,
            (byte) 0x3A, (byte) 0x93, (byte) 0xE7, (byte) 0xB3, (byte) 0xEB, (byte) 0xBD, (byte) 0x55, (byte) 0x76,
            (byte) 0x98, (byte) 0x86, (byte) 0xBC, (byte) 0x65, (byte) 0x1D, (byte) 0x06, (byte) 0xB0, (byte) 0xCC,
            (byte) 0x53, (byte) 0xB0, (byte) 0xF6, (byte) 0x3B, (byte) 0xCE, (byte) 0x3C, (byte) 0x3E, (byte) 0x27,
            (byte) 0xD2, (byte) 0x60, (byte) 0x4B };
    protected static byte[] EC_P256R1_FIELD_G = { (byte) 0x04, (byte) 0x6B, (byte) 0x17, (byte) 0xD1, (byte) 0xF2,
            (byte) 0xE1, (byte) 0x2C, (byte) 0x42, (byte) 0x47, (byte) 0xF8, (byte) 0xBC, (byte) 0xE6, (byte) 0xE5,
            (byte) 0x63, (byte) 0xA4, (byte) 0x40, (byte) 0xF2, (byte) 0x77, (byte) 0x03, (byte) 0x7D, (byte) 0x81,
            (byte) 0x2D, (byte) 0xEB, (byte) 0x33, (byte) 0xA0, (byte) 0xF4, (byte) 0xA1, (byte) 0x39, (byte) 0x45,
            (byte) 0xD8, (byte) 0x98, (byte) 0xC2, (byte) 0x96, (byte) 0x4F, (byte) 0xE3, (byte) 0x42, (byte) 0xE2,
            (byte) 0xFE, (byte) 0x1A, (byte) 0x7F, (byte) 0x9B, (byte) 0x8E, (byte) 0xE7, (byte) 0xEB, (byte) 0x4A,
            (byte) 0x7C, (byte) 0x0F, (byte) 0x9E, (byte) 0x16, (byte) 0x2B, (byte) 0xCE, (byte) 0x33, (byte) 0x57,
            (byte) 0x6B, (byte) 0x31, (byte) 0x5E, (byte) 0xCE, (byte) 0xCB, (byte) 0xB6, (byte) 0x40, (byte) 0x68,
            (byte) 0x37, (byte) 0xBF, (byte) 0x51, (byte) 0xF5 };
    protected static byte[] EC_P256R1_FIELD_R = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xBC, (byte) 0xE6, (byte) 0xFA, (byte) 0xAD, (byte) 0xA7,
            (byte) 0x17, (byte) 0x9E, (byte) 0x84, (byte) 0xF3, (byte) 0xB9, (byte) 0xCA, (byte) 0xC2, (byte) 0xFC,
            (byte) 0x63, (byte) 0x25, (byte) 0x51 };
    protected static byte[] EC_P256R1_FP = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

    // P256K1
    protected static byte[] EC_P256K1_FIELD_A = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };
    protected static byte[] EC_P256K1_FIELD_B = { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x07 };
    protected static byte[] EC_P256K1_FIELD_G = { (byte) 0x04, (byte) 0x79, (byte) 0xBE, (byte) 0x66, (byte) 0x7E,
            (byte) 0xF9, (byte) 0xDC, (byte) 0xBB, (byte) 0xAC, (byte) 0x55, (byte) 0xA0, (byte) 0x62, (byte) 0x95,
            (byte) 0xCE, (byte) 0x87, (byte) 0x0B, (byte) 0x07, (byte) 0x02, (byte) 0x9B, (byte) 0xFC, (byte) 0xDB,
            (byte) 0x2D, (byte) 0xCE, (byte) 0x28, (byte) 0xD9, (byte) 0x59, (byte) 0xF2, (byte) 0x81, (byte) 0x5B,
            (byte) 0x16, (byte) 0xF8, (byte) 0x17, (byte) 0x98, (byte) 0x48, (byte) 0x3A, (byte) 0xDA, (byte) 0x77,
            (byte) 0x26, (byte) 0xA3, (byte) 0xC4, (byte) 0x65, (byte) 0x5D, (byte) 0xA4, (byte) 0xFB, (byte) 0xFC,
            (byte) 0x0E, (byte) 0x11, (byte) 0x08, (byte) 0xA8, (byte) 0xFD, (byte) 0x17, (byte) 0xB4, (byte) 0x48,
            (byte) 0xA6, (byte) 0x85, (byte) 0x54, (byte) 0x19, (byte) 0x9C, (byte) 0x47, (byte) 0xD0, (byte) 0x8F,
            (byte) 0xFB, (byte) 0x10, (byte) 0xD4, (byte) 0xB8 };
    protected static byte[] EC_P256K1_FIELD_R = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFE, (byte) 0xBA, (byte) 0xAE, (byte) 0xDC, (byte) 0xE6, (byte) 0xAF,
            (byte) 0x48, (byte) 0xA0, (byte) 0x3B, (byte) 0xBF, (byte) 0xD2, (byte) 0x5E, (byte) 0x8C, (byte) 0xD0,
            (byte) 0x36, (byte) 0x41, (byte) 0x41 };
    protected static byte[] EC_P256K1_FP = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFE, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFC, (byte) 0x2F };

    // P384R1
    protected static byte[] EC_P384R1_FIELD_A = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFC };
    protected static byte[] EC_P384R1_FIELD_B = { (byte) 0xB3, (byte) 0x31, (byte) 0x2F, (byte) 0xA7, (byte) 0xE2,
            (byte) 0x3E, (byte) 0xE7, (byte) 0xE4, (byte) 0x98, (byte) 0x8E, (byte) 0x05, (byte) 0x6B, (byte) 0xE3,
            (byte) 0xF8, (byte) 0x2D, (byte) 0x19, (byte) 0x18, (byte) 0x1D, (byte) 0x9C, (byte) 0x6E, (byte) 0xFE,
            (byte) 0x81, (byte) 0x41, (byte) 0x12, (byte) 0x03, (byte) 0x14, (byte) 0x08, (byte) 0x8F, (byte) 0x50,
            (byte) 0x13, (byte) 0x87, (byte) 0x5A, (byte) 0xC6, (byte) 0x56, (byte) 0x39, (byte) 0x8D, (byte) 0x8A,
            (byte) 0x2E, (byte) 0xD1, (byte) 0x9D, (byte) 0x2A, (byte) 0x85, (byte) 0xC8, (byte) 0xED, (byte) 0xD3,
            (byte) 0xEC, (byte) 0x2A, (byte) 0xEF };
    protected static byte[] EC_P384R1_FIELD_G = { (byte) 0x04, (byte) 0xAA, (byte) 0x87, (byte) 0xCA, (byte) 0x22,
            (byte) 0xBE, (byte) 0x8B, (byte) 0x05, (byte) 0x37, (byte) 0x8E, (byte) 0xB1, (byte) 0xC7, (byte) 0x1E,
            (byte) 0xF3, (byte) 0x20, (byte) 0xAD, (byte) 0x74, (byte) 0x6E, (byte) 0x1D, (byte) 0x3B, (byte) 0x62,
            (byte) 0x8B, (byte) 0xA7, (byte) 0x9B, (byte) 0x98, (byte) 0x59, (byte) 0xF7, (byte) 0x41, (byte) 0xE0,
            (byte) 0x82, (byte) 0x54, (byte) 0x2A, (byte) 0x38, (byte) 0x55, (byte) 0x02, (byte) 0xF2, (byte) 0x5D,
            (byte) 0xBF, (byte) 0x55, (byte) 0x29, (byte) 0x6C, (byte) 0x3A, (byte) 0x54, (byte) 0x5E, (byte) 0x38,
            (byte) 0x72, (byte) 0x76, (byte) 0x0A, (byte) 0xB7, (byte) 0x36, (byte) 0x17, (byte) 0xDE, (byte) 0x4A,
            (byte) 0x96, (byte) 0x26, (byte) 0x2C, (byte) 0x6F, (byte) 0x5D, (byte) 0x9E, (byte) 0x98, (byte) 0xBF,
            (byte) 0x92, (byte) 0x92, (byte) 0xDC, (byte) 0x29, (byte) 0xF8, (byte) 0xF4, (byte) 0x1D, (byte) 0xBD,
            (byte) 0x28, (byte) 0x9A, (byte) 0x14, (byte) 0x7C, (byte) 0xE9, (byte) 0xDA, (byte) 0x31, (byte) 0x13,
            (byte) 0xB5, (byte) 0xF0, (byte) 0xB8, (byte) 0xC0, (byte) 0x0A, (byte) 0x60, (byte) 0xB1, (byte) 0xCE,
            (byte) 0x1D, (byte) 0x7E, (byte) 0x81, (byte) 0x9D, (byte) 0x7A, (byte) 0x43, (byte) 0x1D, (byte) 0x7C,
            (byte) 0x90, (byte) 0xEA, (byte) 0x0E, (byte) 0x5F };
    protected static byte[] EC_P384R1_FIELD_R = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xC7, (byte) 0x63, (byte) 0x4D, (byte) 0x81, (byte) 0xF4,
            (byte) 0x37, (byte) 0x2D, (byte) 0xDF, (byte) 0x58, (byte) 0x1A, (byte) 0x0D, (byte) 0xB2, (byte) 0x48,
            (byte) 0xB0, (byte) 0xA7, (byte) 0x7A, (byte) 0xEC, (byte) 0xEC, (byte) 0x19, (byte) 0x6A, (byte) 0xCC,
            (byte) 0xC5, (byte) 0x29, (byte) 0x73

    };
    protected static byte[] EC_P384R1_FP = { (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFE, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };

    // P521R1
    protected static byte[] EC_P521R1_FIELD_A = { (byte) 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFC };
    protected static byte[] EC_P521R1_FIELD_B = { (byte) 0x00, (byte) 0x51, (byte) 0x95, (byte) 0x3E, (byte) 0xB9,
            (byte) 0x61, (byte) 0x8E, (byte) 0x1C, (byte) 0x9A, (byte) 0x1F, (byte) 0x92, (byte) 0x9A, (byte) 0x21,
            (byte) 0xA0, (byte) 0xB6, (byte) 0x85, (byte) 0x40, (byte) 0xEE, (byte) 0xA2, (byte) 0xDA, (byte) 0x72,
            (byte) 0x5B, (byte) 0x99, (byte) 0xB3, (byte) 0x15, (byte) 0xF3, (byte) 0xB8, (byte) 0xB4, (byte) 0x89,
            (byte) 0x91, (byte) 0x8E, (byte) 0xF1, (byte) 0x09, (byte) 0xE1, (byte) 0x56, (byte) 0x19, (byte) 0x39,
            (byte) 0x51, (byte) 0xEC, (byte) 0x7E, (byte) 0x93, (byte) 0x7B, (byte) 0x16, (byte) 0x52, (byte) 0xC0,
            (byte) 0xBD, (byte) 0x3B, (byte) 0xB1, (byte) 0xBF, (byte) 0x07, (byte) 0x35, (byte) 0x73, (byte) 0xDF,
            (byte) 0x88, (byte) 0x3D, (byte) 0x2C, (byte) 0x34, (byte) 0xF1, (byte) 0xEF, (byte) 0x45, (byte) 0x1F,
            (byte) 0xD4, (byte) 0x6B, (byte) 0x50, (byte) 0x3F, (byte) 0x00 };
    protected static byte[] EC_P521R1_FIELD_G = { (byte) 0x04, (byte) 0x00, (byte) 0xC6, (byte) 0x85, (byte) 0x8E,
            (byte) 0x06, (byte) 0xB7, (byte) 0x04, (byte) 0x04, (byte) 0xE9, (byte) 0xCD, (byte) 0x9E, (byte) 0x3E,
            (byte) 0xCB, (byte) 0x66, (byte) 0x23, (byte) 0x95, (byte) 0xB4, (byte) 0x42, (byte) 0x9C, (byte) 0x64,
            (byte) 0x81, (byte) 0x39, (byte) 0x05, (byte) 0x3F, (byte) 0xB5, (byte) 0x21, (byte) 0xF8, (byte) 0x28,
            (byte) 0xAF, (byte) 0x60, (byte) 0x6B, (byte) 0x4D, (byte) 0x3D, (byte) 0xBA, (byte) 0xA1, (byte) 0x4B,
            (byte) 0x5E, (byte) 0x77, (byte) 0xEF, (byte) 0xE7, (byte) 0x59, (byte) 0x28, (byte) 0xFE, (byte) 0x1D,
            (byte) 0xC1, (byte) 0x27, (byte) 0xA2, (byte) 0xFF, (byte) 0xA8, (byte) 0xDE, (byte) 0x33, (byte) 0x48,
            (byte) 0xB3, (byte) 0xC1, (byte) 0x85, (byte) 0x6A, (byte) 0x42, (byte) 0x9B, (byte) 0xF9, (byte) 0x7E,
            (byte) 0x7E, (byte) 0x31, (byte) 0xC2, (byte) 0xE5, (byte) 0xBD, (byte) 0x66, (byte) 0x01, (byte) 0x18,
            (byte) 0x39, (byte) 0x29, (byte) 0x6A, (byte) 0x78, (byte) 0x9A, (byte) 0x3B, (byte) 0xC0, (byte) 0x04,
            (byte) 0x5C, (byte) 0x8A, (byte) 0x5F, (byte) 0xB4, (byte) 0x2C, (byte) 0x7D, (byte) 0x1B, (byte) 0xD9,
            (byte) 0x98, (byte) 0xF5, (byte) 0x44, (byte) 0x49, (byte) 0x57, (byte) 0x9B, (byte) 0x44, (byte) 0x68,
            (byte) 0x17, (byte) 0xAF, (byte) 0xBD, (byte) 0x17, (byte) 0x27, (byte) 0x3E, (byte) 0x66, (byte) 0x2C,
            (byte) 0x97, (byte) 0xEE, (byte) 0x72, (byte) 0x99, (byte) 0x5E, (byte) 0xF4, (byte) 0x26, (byte) 0x40,
            (byte) 0xC5, (byte) 0x50, (byte) 0xB9, (byte) 0x01, (byte) 0x3F, (byte) 0xAD, (byte) 0x07, (byte) 0x61,
            (byte) 0x35, (byte) 0x3C, (byte) 0x70, (byte) 0x86, (byte) 0xA2, (byte) 0x72, (byte) 0xC2, (byte) 0x40,
            (byte) 0x88, (byte) 0xBE, (byte) 0x94, (byte) 0x76, (byte) 0x9F, (byte) 0xD1, (byte) 0x66, (byte) 0x50 };
    protected static byte[] EC_P521R1_FIELD_R = { (byte) 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFA, (byte) 0x51, (byte) 0x86, (byte) 0x87,
            (byte) 0x83, (byte) 0xBF, (byte) 0x2F, (byte) 0x96, (byte) 0x6B, (byte) 0x7F, (byte) 0xCC, (byte) 0x01,
            (byte) 0x48, (byte) 0xF7, (byte) 0x09, (byte) 0xA5, (byte) 0xD0, (byte) 0x3B, (byte) 0xB5, (byte) 0xC9,
            (byte) 0xB8, (byte) 0x89, (byte) 0x9C, (byte) 0x47, (byte) 0xAE, (byte) 0xBB, (byte) 0x6F, (byte) 0xB7,
            (byte) 0x1E, (byte) 0x91, (byte) 0x38, (byte) 0x64, (byte) 0x09 };
    protected static byte[] EC_P521R1_FP = { (byte) 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
            (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF };
    protected static byte K = (byte) 0x01;

    protected static boolean setCommonCurveParameters(ECKey key, byte keyType) {
        try {
            switch (keyType) {
                case (byte) 0x01:
                    key.setA(EC_P256R1_FIELD_A, (short) 0, (short) EC_P256R1_FIELD_A.length);
                    key.setB(EC_P256R1_FIELD_B, (short) 0, (short) EC_P256R1_FIELD_B.length);
                    key.setG(EC_P256R1_FIELD_G, (short) 0, (short) EC_P256R1_FIELD_G.length);
                    key.setR(EC_P256R1_FIELD_R, (short) 0, (short) EC_P256R1_FIELD_R.length);
                    key.setFieldFP(EC_P256R1_FP, (short) 0, (short) EC_P256R1_FP.length);
                    key.setK(K);
                    return true;
                case (byte) 0x02:
                    key.setA(EC_P256K1_FIELD_A, (short) 0, (short) EC_P256K1_FIELD_A.length);
                    key.setB(EC_P256K1_FIELD_B, (short) 0, (short) EC_P256K1_FIELD_B.length);
                    key.setFieldFP(EC_P256K1_FP, (short) 0, (short) EC_P256K1_FP.length);
                    key.setG(EC_P256K1_FIELD_G, (short) 0, (short) EC_P256K1_FIELD_G.length);
                    key.setR(EC_P256K1_FIELD_R, (short) 0, (short) EC_P256K1_FIELD_R.length);
                    key.setK(K);
                    return true;
                case (byte) 0x03:
                    key.setA(EC_P384R1_FIELD_A, (short) 0, (short) EC_P384R1_FIELD_A.length);
                    key.setB(EC_P384R1_FIELD_B, (short) 0, (short) EC_P384R1_FIELD_B.length);
                    key.setG(EC_P384R1_FIELD_G, (short) 0, (short) EC_P384R1_FIELD_G.length);
                    key.setR(EC_P384R1_FIELD_R, (short) 0, (short) EC_P384R1_FIELD_R.length);
                    key.setFieldFP(EC_P384R1_FP, (short) 0, (short) EC_P384R1_FP.length);
                    key.setK(K);
                    return true;
                case (byte) 0x04:
                    key.setA(EC_P521R1_FIELD_A, (short) 0, (short) EC_P521R1_FIELD_A.length);
                    key.setB(EC_P521R1_FIELD_B, (short) 0, (short) EC_P521R1_FIELD_B.length);
                    key.setG(EC_P521R1_FIELD_G, (short) 0, (short) EC_P521R1_FIELD_G.length);
                    key.setR(EC_P521R1_FIELD_R, (short) 0, (short) EC_P521R1_FIELD_R.length);
                    key.setFieldFP(EC_P521R1_FP, (short) 0, (short) EC_P521R1_FP.length);
                    key.setK(K);
                    return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static short asn1SignatureToRS(byte[] input, short off, short len, byte[] output, short outOff) {
        short r = 0;
        short c = 0;

        // Cleanup output before setting values inside
        Util.arrayFillNonAtomic(output, outOff, (short) 64, (byte) 0x00);

        // Check 0x30 start byte
        if (input[off] != (byte) 0x30) {
            ISOException.throwIt((short) 0x6fa1);
            return (short) 0;
        }

        r++;

        // Read overall tag data length
        c = (short) (input[(short) (off + r)] & 0xFF);

        // Check if remaining tag data is correct length
        if ((short) (len - 2) != c) {
            ISOException.throwIt((short) 0x6fa2);
            return (short) 0;
        }

        r++;

        // Check if component start
        if (input[(short) (off + r)] != (byte) 0x02) {
            ISOException.throwIt((short) 0x6fa3);
            return (short) 0;
        }

        r++;

        // Read R component length
        c = (short) (input[(short) (off + r)] & 0xFF);

        if (!(c >= (short) 31 && c <= (short) 33)) {
            ISOException.throwIt((short) 0x6fa4);
            return (short) 0;
        }

        r++;

        // Copy R
        if (c == (short) 31) {
            // Read normal, copy out offset 1 with len 31
            Util.arrayCopyNonAtomic(input, (short) (off + r), output, (short) (outOff + 1), (short) 31);
            r += (short) 31;
        } else if (c == (short) 32) {
            // Read normal, copy out normal with len 32
            Util.arrayCopyNonAtomic(input, (short) (off + r), output, outOff, (short) 32);
            r += (short) 32;
        } else if (c == (short) 33) {
            // Read offset 1, copy out normal with len 32
            Util.arrayCopyNonAtomic(input, (short) (off + r + 1), output, outOff, (short) 32);
            r += (short) 33;
        }

        // Check if component start
        if (input[(short) (off + r)] != (byte) 0x02) {
            ISOException.throwIt((short) 0x6fa5);
            return (short) 0;
        }

        r++;

        // Read S component length
        c = (short) (input[(short) (off + r)] & 0xFF);

        if (!(c >= (short) 31 && c <= (short) 33)) {
            ISOException.throwIt((short) 0x6fa6);
            return (short) 0;
        }

        r++;

        // Copy S
        if (c == (short) 31) {
            // Read normal, copy out offset 1 with len 31
            Util.arrayCopyNonAtomic(input, (short) (off + r), output, (short) (outOff + 33), (short) 31);
            r += (short) 31;
        } else if (c == (short) 32) {
            // Read normal, copy out normal with len 32
            Util.arrayCopyNonAtomic(input, (short) (off + r), output, (short) (outOff + 32), (short) 32);
            r += 32;
        } else if (c == (short) 33) {
            // Read offset 1, copy out normal with len 32
            Util.arrayCopyNonAtomic(input, (short) (off + r + 1), output, (short) (outOff + 32), (short) 32);
            r += (short) 33;
        }

        return (short) 64;
    }
}