package cipher;

public class VigenereCipher {

    public byte[] encode(byte[] orig, String key) {
        byte[] encoded = new byte[orig.length];
        int currentPosOfKey = 0;

        for (int i = 0; i < orig.length; i++) {
            int afterSum = (short) (orig[i]) + (short) (key.charAt(currentPosOfKey));
            if (afterSum > 127) {
                encoded[i] = (byte) (afterSum - 256);
            } else {
                encoded[i] = (byte) (afterSum);
            }
            currentPosOfKey = (currentPosOfKey + 1) % key.length();
        }
        return encoded;
    }

    public byte[] decode(byte[] encoded, String key) {
        byte[] decoded = new byte[encoded.length];
        int currentPosOfKey = 0;

        for (int i = 0; i < encoded.length; i++) {
            int afterSubtraction = (short) (encoded[i]) - (short) (key.charAt(currentPosOfKey));
            if (afterSubtraction < -128) {
                decoded[i] = (byte) (afterSubtraction + 256);
            } else {
                decoded[i] = (byte) (afterSubtraction);
            }
            currentPosOfKey = (currentPosOfKey + 1) % key.length();
        }
        return decoded;
    }
}