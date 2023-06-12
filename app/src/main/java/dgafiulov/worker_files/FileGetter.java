package dgafiulov.worker_files;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import dgafiulov.control_center.ControlCenter;
import dgafiulov.control_center.ResultsOfUIGetter;
import dgafiulov.ui.databinding.ActivityMainBinding;

public class FileGetter {

    private Uri fileUri;
    private ActivityMainBinding binding;
    private final FileWorker fileWorker = new FileWorker();
    private final ResultsOfUIGetter resultsOfUIGetter = new ResultsOfUIGetter(binding);
    private ControlCenter controlCenter;

    public FileGetter(ActivityMainBinding binding, ControlCenter controlCenter) {
        this.binding = binding;
        this.controlCenter = controlCenter;
    }

    public void getFile(int requestCode, int resultCode, Intent data, Context context) {
        if (requestCode == controlCenter.getChooseFileCode()) {
            fileUri = fileGet(resultCode, data, context);
        } else if (requestCode == controlCenter.getSaveFileCode()) {
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
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        return intent;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void intentForFileSave(Activity mainActivity) {
        Log.i(String.valueOf(Log.INFO), "intent for file save");
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        mainActivity.startActivityForResult(intent, 2);
    }
}
