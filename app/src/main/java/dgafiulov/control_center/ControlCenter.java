package dgafiulov.control_center;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import dgafiulov.app_start.AppStart;
import dgafiulov.file_getter.FileGetter;
import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileWorker;

public class ControlCenter {

    private Uri fileUri;
    private ActivityMainBinding binding;
    private final int chooseFileCode = 1;
    private final int saveFileCode = 2;
    private int sdkVersion = new AppStart().getSdkVersion();
    private final FileGetter fileGetter = new FileGetter();
    private final FileWorker fileWorker = new FileWorker();
    private final ResultsOfUIGetter resultsOfUIGetter = new ResultsOfUIGetter(binding);

    public ControlCenter(ActivityMainBinding binding) {
        this.binding = binding;
    }
    public void getFile(int requestCode, int resultCode, Intent data, Context context) {
        if (requestCode == chooseFileCode) {
            fileUri = fileGetter.fileGet(resultCode, data, context);
        } else if (requestCode == saveFileCode) {
            try {
                fileWorker.initSaver((resultsOfUIGetter.getSwitchResult() ? 0 : 1), fileUri, resultsOfUIGetter.getPasswordFromEditText(), context, fileGetter.fileGet(resultCode, data, context));
                Thread saveThread = new Thread(fileWorker);
                saveThread.start();
            } catch (Exception e) {
                //workWithDialogs.getErrorDialog(context).show();
            }
        }
    }

    public Intent getIntentForFileChooser(View view) {
        Intent intent = fileGetter.openFileChooserIntent(view);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        return intent;
    }

    public int getChooseFileCode() {
        return chooseFileCode;
    }
}
