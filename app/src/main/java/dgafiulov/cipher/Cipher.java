package dgafiulov.cipher;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import dgafiulov.ui.R;

public class Cipher {

    cipher.VigenereCipher vigenereCipher = new cipher.VigenereCipher();
    cipher.PlayfairCipher playfairCipher = new cipher.PlayfairCipher();
    cipher.HillCipher hillCipher = new cipher.HillCipher();

    public byte[] encodeChunk(byte[] bytes, String key, Context context) {
        try {
            bytes = vigenereCipher.encode(bytes, key);
            bytes = hillCipher.encode(bytes, key);
            bytes = playfairCipher.encode(toObjByte(bytes), key, context);
        } catch (Exception e) {
            Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show();
        }
        return bytes;
    }

    public byte[] decodeChunk(byte[] bytes, String key) {
        try {
            bytes = playfairCipher.decode(bytes, key);
            bytes = hillCipher.decode(bytes, key);
            bytes = vigenereCipher.decode(bytes, key);
        } catch (Exception e) {
            Log.i(String.valueOf(Log.INFO), String.valueOf(e));
        }
        return bytes;
    }

    private Byte[] toObjByte(byte[] bytes) {
        Byte[] bytesObj = new Byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            bytesObj[i] = bytes[i];
        }

        return bytesObj;
    }
}