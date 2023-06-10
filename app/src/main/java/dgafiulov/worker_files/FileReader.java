package dgafiulov.worker_files;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private Context context;
    private boolean wasEven;

    public void readerInit(Uri uri, Context context) throws IOException {
        this.context = context;
        inputStream = context.getContentResolver().openInputStream(uri);
        bufferedInputStream = new BufferedInputStream(inputStream);
        wasEven = bufferedInputStream.available() % 2 == 0;
    }

    public void readerDestruct() throws IOException {
        inputStream.close();
        bufferedInputStream.close();
    }

    public byte[] readLastChunk(byte[] lastChunk, int bytesRead) {
        byte[] temp;

        if (wasEven) {
            temp = new byte[bytesRead];
            System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
        } else {
            temp = new byte[bytesRead + 1];
            System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
            temp[bytesRead] = 0;
        }
        return temp;
    }

    public byte[] readLastChunkForDecoding(byte[] lastChunk, int bytesRead) {
        byte[] temp = new byte[bytesRead];
        System.arraycopy(lastChunk, 0, temp, 0, bytesRead);
        return temp;
    }

    public BufferedInputStream getBufferedInputStream() {
        return bufferedInputStream;
    }
}
