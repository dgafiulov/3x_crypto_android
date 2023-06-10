package dgafiulov.worker_files;

import android.content.Context;
import android.net.Uri;
import java.io.IOException;
import java.util.Arrays;

import dgafiulov.cipher.Cipher;

public class FileWorker implements Runnable{

    /*
    actionType:
    0 - decode in new versions
    1 - encode in new versions
    2 - decode in old versions
    3 - encode in old versions
     */
    private int actionType;
    private Uri orig;
    private Uri newFile;
    private String key;
    private Context context;
    String newFileName;
    private boolean isInitialized = false;
    Cipher cipher = new Cipher();
    boolean wasEven;
    FileReader fileReader = new FileReader();
    FileWriter fileWriter = new FileWriter();

    @Override
    public void run() {
        if (isInitialized) {
            globalInitialisation();
            switch (actionType) {
                case 0:
                    case0();
                    break;
                case 1:
                    case1();
                    break;
                case 2:
                    case2();
                    break;
                case 3:
                    case3();
                    break;
            }
            globalDestruction();
        }
    }

    private void globalInitialisation() {
        try {
            fileReader.readerInit(orig, context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void globalDestruction() {
        try {
            fileReader.readerDestruct();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void case0() {
        try {
            decodeAndWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void case1() {
        try {
            encodeAndWrite();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void case2() {
        try {
            decodeAndWriteInOldVersions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void case3() {
        try {
            encodeAndWriteInOldVersions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initSaver(int actionType, Uri orig, String key, Context context, Uri newFile) {
        this.actionType = actionType;
        this.orig = orig;
        this.key = key;
        this.context = context;
        this.newFile = newFile;
        isInitialized = true;
    }

    public void initSaver(int actionType, Uri orig, String key, Context context, String newFileName) {
        this.actionType = actionType;
        this.orig = orig;
        this.key = key;
        this.context = context;
        this.newFileName = newFileName;
        isInitialized = true;
    }

    private void encodeAndWrite() throws IOException {
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        boolean isLastChunk;

        fileWriter.writerInit(newFile, context);

        fileWriter.writeInFile(new byte[]{(byte) (wasEven ? 0 : 1)});

        while ((bytesRead = fileReader.getBufferedInputStream().read(buffer)) != -1) {
            isLastChunk = bytesRead < bufferSize;
            if (isLastChunk) {
                buffer = cipher.encodeChunk(fileReader.readLastChunk(buffer, bytesRead), key, context);
            } else {
                buffer = cipher.encodeChunk(buffer, key, context);
            }
            fileWriter.writeInFile(buffer);
        }
        fileWriter.writerDestruct();
    }

    private void encodeAndWriteInOldVersions() throws IOException {
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        boolean isLastChunk;

        fileWriter.writerInOldVersionsInit(newFileName);

        fileWriter.writeInFileInOldVersions(new byte[]{(byte) (wasEven ? 0 : 1)});

        while ((bytesRead = fileReader.getBufferedInputStream().read(buffer)) != -1) {
            isLastChunk = bytesRead < bufferSize;
            if (isLastChunk) {
                buffer = cipher.encodeChunk(fileReader.readLastChunk(buffer, bytesRead), key, context);
            } else {
                buffer = cipher.encodeChunk(buffer, key, context);
            }
            fileWriter.writeInFileInOldVersions(buffer);
        }
        fileWriter.writerInOldVersionsDestruct();
    }

    private void decodeAndWrite() throws IOException{
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        long totalBytesRead = 0;
        boolean isLastChunk;

        fileWriter.writerInit(newFile, context);

        byte[] firstSymbol = new byte[1];
        fileReader.getBufferedInputStream().read(firstSymbol);
        boolean wasEven = firstSymbol[0] == 0;

        long fileSize = fileReader.getBufferedInputStream().available();

        while ((bytesRead = fileReader.getBufferedInputStream().read(buffer)) != -1) {
            isLastChunk = fileSize - totalBytesRead <= bufferSize;
            if (isLastChunk) {
                buffer = cipher.decodeChunk(fileReader.readLastChunkForDecoding(buffer, bytesRead), key);
                if (!wasEven) {
                    buffer = Arrays.copyOfRange(buffer, 0, buffer.length - 1);
                }
            } else {
                buffer = cipher.decodeChunk(buffer, key);
            }
            fileWriter.writeInFile(buffer);
            totalBytesRead += bytesRead;
        }

        fileWriter.writerDestruct();
    }

    private void decodeAndWriteInOldVersions() throws IOException{
        int bufferSize = 4096;
        byte[] buffer = new byte[bufferSize];
        int bytesRead;
        long totalBytesRead = 0;
        boolean isLastChunk;

        fileWriter.writerInOldVersionsInit(newFileName);

        byte[] firstSymbol = new byte[1];
        fileReader.getBufferedInputStream().read(firstSymbol);
        boolean wasEven = firstSymbol[0] == 0;

        while ((bytesRead = fileReader.getBufferedInputStream().read(buffer)) != -1) {
            isLastChunk = fileReader.getBufferedInputStream().available() - totalBytesRead <= bufferSize;
            if (isLastChunk) {
                buffer = cipher.decodeChunk(fileReader.readLastChunkForDecoding(buffer, bytesRead), key);
                if (!wasEven) {
                    buffer = Arrays.copyOfRange(buffer, 0, buffer.length - 1);
                }
            } else {
                buffer = cipher.decodeChunk(buffer, key);
            }
            fileWriter.writeInFileInOldVersions(buffer);
            totalBytesRead += bytesRead;
        }

        fileWriter.writerInOldVersionsDestruct();
    }
}