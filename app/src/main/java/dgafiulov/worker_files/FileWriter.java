package dgafiulov.worker_files;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import dgafiulov.ui.R;

public class FileWriter {

    ParcelFileDescriptor pfd;
    FileOutputStream fileOutputStream;
    BufferedOutputStream bufferedOutputStream;
    Context context;

    public void writerInit(Uri uri, Context context) throws FileNotFoundException {
        this.context = context;
        pfd = context.getContentResolver().
                openFileDescriptor(uri, "w");
        fileOutputStream =
                new FileOutputStream(pfd.getFileDescriptor());
    }

    public void writerDestruct() throws IOException {
        fileOutputStream.close();
        pfd.close();
    }

    public void writeInFile(byte[] bytes) {
        try {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            Toast.makeText(context, R.string.error_text, Toast.LENGTH_SHORT).show();
        }
    }

    public void writerInOldVersionsInit(String fileName) throws FileNotFoundException {
        File downloadDir = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)));
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        File file;
        if (fileName != null) {
            file = new File(downloadDir, fileName);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                file = new File(downloadDir, LocalDate.now() + ".txt");
            } else {
                file = new File((Math.random() * 1000) + ".txt");
            }
        }
        fileOutputStream = new FileOutputStream(file);
        bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
    }

    public void writerInOldVersionsDestruct() throws IOException {
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    public void writeInFileInOldVersions(byte[] orig) throws IOException {
        for (byte i:orig) {
            bufferedOutputStream.write(i);
        }
    }


}
