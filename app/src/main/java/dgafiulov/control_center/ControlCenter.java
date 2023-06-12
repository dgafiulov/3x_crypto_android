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
import android.view.View;

import androidx.core.app.ActivityCompat;

import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileGetter;

public class ControlCenter {

    ActivityMainBinding binding;
    FileGetter fileGetter;
    private final ButtonControl buttonControl;
    private Activity mainActivity;

    public ControlCenter(ActivityMainBinding binding, Activity mainActivity) {
        this.binding = binding;
        this.mainActivity = mainActivity;
        this.fileGetter = new FileGetter(binding);
        buttonControl = new ButtonControl(binding, mainActivity);
        mainInit();
    }

    public void getFile(int requestCode, int resultCode, Intent data, Context context) {
        fileGetter.getFile(requestCode, resultCode, data, context);
    }

    public Intent getIntentForFileChooser(View view) {
        return fileGetter.getIntentForFileChooser(view);
    }

    public int getChooseFileCode() {
        return fileGetter.getChooseFileCode();
    }

    private void getUserPermissions() {
        ActivityCompat.requestPermissions(mainActivity,
                new String[]{READ_MEDIA_IMAGES, READ_MEDIA_VIDEO, READ_MEDIA_AUDIO, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
    }

    private void mainInit() {
        getUserPermissions();
        buttonControl.setOnClickListenersForAllButtons();
    }
}
