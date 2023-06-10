package dgafiulov.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dgafiulov.control_center.ControlCenter;
import dgafiulov.ui.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ControlCenter controlCenter;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(binding.getRoot());
        controlCenterInit();
    }

    private void controlCenterInit() {
        controlCenter = new ControlCenter(binding);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        controlCenter.getFile(requestCode, resultCode, data, this);
    }

    public void openFileChooser(View view) {
        startActivityForResult(controlCenter.getIntentForFileChooser(view), controlCenter.getChooseFileCode());
    }
}