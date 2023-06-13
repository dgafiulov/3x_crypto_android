package dgafiulov.control_center;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import dgafiulov.ui.WorkWithDialogs;
import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileGetter;

public class ButtonControl {

    private ActivityMainBinding binding;
    private FileGetter fileGetter;
    private Activity mainActivity;
    private ControlCenter controlCenter;
    private ResultsOfUIGetter resultsOfUIGetter;
    private int currentSDKVersion;
    WorkWithDialogs workWithDialogs = new WorkWithDialogs();

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
        btSendSetOnClickListener();
    }

    private void btChooseFileSetOnClickListener() {
        binding.btChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startActivityForResult(fileGetter.getIntentForFileChooser(view), controlCenter.getChooseFileCode());
            }
        });
    }

    private void btStartSetOnClickListener() {
        binding.btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileGetter.getFileUri() != null & !resultsOfUIGetter.getPasswordFromEditText().equals("")) {
                    if (currentSDKVersion >= 29) {
                        try {
                            fileGetter.intentForFileSave(mainActivity);
                        } catch (Exception e) {
                            workWithDialogs.getErrorDialog(mainActivity).show();
                        }
                    } else {
                        workWithDialogs.getAskForNewNameDialog(mainActivity, controlCenter.getFileWorker(), resultsOfUIGetter.getSwitchResult(), fileGetter.getFileUri(), resultsOfUIGetter.getPasswordFromEditText()).show();
                    }
                }
            }
        });
    }

    private void btSendSetOnClickListener() {
        binding.btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controlCenter.getSdkVersion() < 30) {
                    workWithDialogs.getErrorSendDialog(mainActivity).show();
                } else {
                    if (controlCenter.getFileWorker().isAvaibleToSend()) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, controlCenter.getFileWorker().getUri());
                        intent.setType("file/*");
                        mainActivity.startActivity(intent);
                    }
                }
            }
        });
    }
}
