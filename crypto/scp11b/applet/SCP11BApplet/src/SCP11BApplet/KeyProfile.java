package SCP11BApplet;

import javacard.framework.ISOException;
import javacard.framework.Util;
import javacard.security.ECPrivateKey;
import javacard.security.ECPublicKey;
import javacard.security.KeyBuilder;
import javacard.security.KeyPair;

public class KeyProfile {

    private ECPublicKey pubKey = null;
    private ECPrivateKey privKey = null;
    private KeyPair kp = null;
    private byte ecKeyAlgo = (byte) 0x00;

    public KeyProfile() {
    }

    public ECPublicKey getPublicKey() {
        return pubKey;
    }

    public ECPrivateKey getPrivateKey() {
        return privKey;
    }

    public void genNewKeyPair(byte keyAlgo) {
        // Set new key algo
        if (isValidKeyAlgo(keyAlgo)) {
            ecKeyAlgo = keyAlgo;
        } else {
            ISOException.throwIt(SCP11BApplet.SW_INVALID_ALGO);
        }

        // Destroy all keys
        if (privKey != null) {
            privKey.clearKey();
            privKey = null;
        }

        if (pubKey != null) {
            pubKey.clearKey();
            pubKey = null;
        }

        // Create new keys
        privKey = (ECPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PRIVATE, KeyBuilder.LENGTH_EC_FP_256, false);
        ECC.setCommonCurveParameters(privKey, ecKeyAlgo);
        pubKey = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC, KeyBuilder.LENGTH_EC_FP_256, false);
        ECC.setCommonCurveParameters(pubKey, ecKeyAlgo);
        kp = new KeyPair(pubKey, privKey);
        kp.genKeyPair();
    }

    public void clearKeys() {
        ecKeyAlgo = (byte) 0x00;

        // Destroy all keys
        if (privKey != null) {
            privKey.clearKey();
            privKey = null;
        }

        if (pubKey != null) {
            pubKey.clearKey();
            pubKey = null;
        }
    }

    public byte getKeyType() {
        return ecKeyAlgo;
    }

    public short computeECDHSharedSecret(byte[] ecPubKey, short ecPubKeyOff, short ecPubKeyLen, byte[] buff, short bOff,
            byte[] output, short outOff) {
        // Check ecPubKey length
        if (ecPubKeyLen != (short) 64) {
            ISOException.throwIt(SCP11BApplet.SW_INVALID_KEY_SIZE);
        }
        buff[bOff] = (byte) 0x04;
        Util.arrayCopyNonAtomic(ecPubKey, ecPubKeyOff, buff, (short) (bOff + 1), (short) 64);
        SCP11BApplet.ka.init(privKey);
        return SCP11BApplet.ka.generateSecret(buff, bOff, (short) 65, output, outOff);
    }

    public short computeECDHSharedSecret(ECPrivateKey otherPrivKey, byte[] ecPubKey, short ecPubKeyOff,
            short ecPubKeyLen, byte[] buff, short bOff, byte[] output, short outOff) {
        // Check ecPubKey length
        if (ecPubKeyLen != (short) 64) {
            ISOException.throwIt(SCP11BApplet.SW_INVALID_KEY_SIZE);
        }
        buff[bOff] = (byte) 0x04;
        Util.arrayCopyNonAtomic(ecPubKey, ecPubKeyOff, buff, (short) (bOff + 1), (short) 64);
        SCP11BApplet.ka.init(otherPrivKey);
        return SCP11BApplet.ka.generateSecret(buff, bOff, (short) 65, output, outOff);
    }

    public short computeECDHSharedSecretASN1(byte[] ecPubKey, short ecPubKeyOff, short ecPubKeyLen, byte[] output,
            short outOff) {
        // Check ecPubKey length
        if (ecPubKeyLen != (short) 65 && (ecPubKey[ecPubKeyOff] != (byte) 0x04)) {
            ISOException.throwIt(SCP11BApplet.SW_INVALID_KEY_SIZE);
        }
        SCP11BApplet.ka.init(privKey);
        return SCP11BApplet.ka.generateSecret(ecPubKey, ecPubKeyOff, ecPubKeyLen, output, outOff);
    }

    public short computeECDHSharedSecretASN1(ECPrivateKey otherPrivKey, byte[] ecPubKey, short ecPubKeyOff,
            short ecPubKeyLen, byte[] output, short outOff) {
        // Check ecPubKey length
        if (ecPubKeyLen != (short) 65 && (ecPubKey[ecPubKeyOff] != (byte) 0x04)) {
            ISOException.throwIt(SCP11BApplet.SW_INVALID_KEY_SIZE);
        }
        SCP11BApplet.ka.init(otherPrivKey);
        return SCP11BApplet.ka.generateSecret(ecPubKey, ecPubKeyOff, ecPubKeyLen, output, outOff);
    }

    public boolean isValidKeyAlgo(byte b) {
        if (b == SCP11BApplet.ALGO_EC_P256R1 || b == SCP11BApplet.ALGO_EC_P256K1) {
            return true;
        }
        return false;
    }
}