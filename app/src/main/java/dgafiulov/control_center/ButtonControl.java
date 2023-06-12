package dgafiulov.control_center;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileGetter;

public class ButtonControl {

    private ActivityMainBinding binding;
    private FileGetter fileGetter;
    private Activity mainActivity;
    private ControlCenter controlCenter;
    private ResultsOfUIGetter resultsOfUIGetter;
    private int currentSDKVersion;

    public ButtonControl(ActivityMainBinding binding, Activity mainActivity, ControlCenter controlCenter, ResultsOfUIGetter resultsOfUIGetter, int sdk) {
        this.binding = binding;
        this.mainActivity = mainActivity;
        this.controlCenter = controlCenter;
        this.resultsOfUIGetter = resultsOfUIGetter;
        this.currentSDKVersion = sdk;
        fileGetter = controlCenter.getFileGetter();
    }

    public void setOnClickListenersForAllButtons() {
        btChooseFileSetOnClickListener();
        btStartSetOnClickListener();
    }

    private void btChooseFileSetOnClickListener() {
        binding.btChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fileGetter.openFileChooser(view);
                mainActivity.startActivityForResult(fileGetter.getIntentForFileChooser(view), controlCenter.getChooseFileCode());
            }
        });
    }

    private void btStartSetOnClickListener() {
        binding.btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(String.valueOf(Log.INFO), "click");
                Log.i(String.valueOf(Log.INFO), String.valueOf(fileGetter.getFileUri()));
                Log.i(String.valueOf(Log.INFO), String.valueOf(resultsOfUIGetter.getPasswordFromEditText().equals("")));
                if (fileGetter.getFileUri() != null & !resultsOfUIGetter.getPasswordFromEditText().equals("")) {
                    if (currentSDKVersion >= 29) {
                        try {
                            fileGetter.intentForFileSave(mainActivity);
                        } catch (Exception e) {
                            //workWithDialogs.getErrorDialog(mainActivity).show();
                        }
                    } else {
                        //workWithDialogs.getAskForNewNameDialog(MainActivity.this, fileSaver, getSwitchResult(), fileUri, getPasswordFromEditText()).show();
                    }
                }
            }
        });
    }
}
