package dgafiulov.control_center;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import dgafiulov.app_start.AppStart;
import dgafiulov.ui.R;
import dgafiulov.ui.WorkWithDialogs;
import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileGetter;
import dgafiulov.worker_files.FileWorker;

public class ControlCenter {

    private final ActivityMainBinding binding;
    private FileGetter fileGetter;
    private ButtonControl buttonControl;
    private final Activity mainActivity;
    private ResultsOfUIGetter resultsOfUIGetter;
    private FileWorker fileWorker;
    private Context context;
    private int sdkVersion = new AppStart().getSdkVersion();
    private final int chooseFileCode = 1;
    private final int saveFileCode = 2;
    WorkWithDialogs workWithDialogs = new WorkWithDialogs();

    public ControlCenter(ActivityMainBinding binding, Activity mainActivity, Context context) {
        this.binding = binding;
        this.mainActivity = mainActivity;
        this.context = context;
        mainInit();
    }

    public void getFile(int requestCode, int resultCode, Intent data, Context context) {
        fileGetter.getFile(requestCode, resultCode, data, context);
    }

    public int getChooseFileCode() {
        return chooseFileCode;
    }

    public int getSaveFileCode() {
        return saveFileCode;
    }

    private void getUserPermissions() {
        ActivityCompat.requestPermissions(mainActivity,
                new String[]{READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    private void mainInit() {
        fileWorker = new FileWorker();
        fileGetter = new FileGetter(binding, this);
        resultsOfUIGetter = new ResultsOfUIGetter(binding);
        buttonControl = new ButtonControl(binding, mainActivity, this, resultsOfUIGetter, sdkVersion);
        getUserPermissions();
        buttonControl.setOnClickListenersForAllButtons();
    }

    public FileGetter getFileGetter() {
        return fileGetter;
    }

    public FileWorker getFileWorker() {
        return fileWorker;
    }

    public void changeAndSaveFile(int resultCode, Intent data) {
        try {
            fileWorker.initSaver((resultsOfUIGetter.getSwitchResult() ? 0 : 1), fileGetter.getFileUri(), resultsOfUIGetter.getPasswordFromEditText(), context, fileGetter.fileGet(resultCode, data, context));
            Thread saveThread = new Thread(fileWorker);
            Log.i(String.valueOf(Log.INFO), "saveThread");
            saveThread.start();
        } catch (Exception e) {
            workWithDialogs.getErrorDialog(mainActivity).show();
        }
    }

    public int getSdkVersion() {
        return sdkVersion;
    }

    public void setCorrectText() {
        binding.tvChosenNotChosen.setText(context.getString(R.string.file_is_chosen));
    }
}
