package dgafiulov.control_center;

import android.app.Activity;
import android.view.View;

import dgafiulov.ui.databinding.ActivityMainBinding;
import dgafiulov.worker_files.FileGetter;

public class ButtonControl {

    private ActivityMainBinding binding;
    private FileGetter fileGetter;
    private Activity mainActivity;

    public ButtonControl(ActivityMainBinding binding, Activity mainActivity) {
        this.binding = binding;
        fileGetter = new FileGetter(binding);
        this.mainActivity = mainActivity;
    }

    public void setOnClickListenersForAllButtons() {
        btChooseFileSetOnClickListener();
    }

    private void btChooseFileSetOnClickListener() {
        binding.btChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fileGetter.openFileChooser(view);
                mainActivity.startActivityForResult(fileGetter.getIntentForFileChooser(view), fileGetter.getChooseFileCode());
            }
        });
    }
}
