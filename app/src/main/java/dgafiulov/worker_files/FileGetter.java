package dgafiulov.worker_files;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import dgafiulov.app_start.AppStart;
import dgafiulov.control_center.ResultsOfUIGetter;
import dgafiulov.ui.databinding.ActivityMainBinding;

public class FileGetter {

    private Uri fileUri;
    private ActivityMainBinding binding;
    private final int chooseFileCode = 1;
    private final int saveFileCode = 2;
    private int sdkVersion = new AppStart().getSdkVersion();
    private final FileWorker fileWorker = new FileWorker();
    private final ResultsOfUIGetter resultsOfUIGetter = new ResultsOfUIGetter(binding);

    public FileGetter(ActivityMainBinding binding) {
        this.binding = binding;
    }

    public void getFile(int requestCode, int resultCode, Intent data, Context context) {
        if (requestCode == chooseFileCode) {
            fileUri = fileGet(resultCode, data, context);
        } else if (requestCode == saveFileCode) {
            try {
                fileWorker.initSaver((resultsOfUIGetter.getSwitchResult() ? 0 : 1), fileUri, resultsOfUIGetter.getPasswordFromEditText(), context, fileGet(resultCode, data, context));
                Thread saveThread = new Thread(fileWorker);
                saveThread.start();
            } catch (Exception e) {
                //workWithDialogs.getErrorDialog(context).show();
            }
        }
    }

    public Intent getIntentForFileChooser(View view) {
        Intent intent = openFileChooserIntent(view);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }

    public int getChooseFileCode() {
        return chooseFileCode;
    }

    public Uri fileGet(int resultCode, Intent data, Context context) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri fileUri = data.getData();
                return fileUri;
            }
        }
        return null;
    }

    public Intent openFileChooserIntent(View view) {
        Log.i(String.valueOf(Log.INFO), "click1");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        Log.i(String.valueOf(Log.INFO), "click2");
        return intent;
    }
}
