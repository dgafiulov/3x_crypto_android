package dgafiulov.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
        bindingInit();
        controlCenterInit();
    }

    private void bindingInit() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    private void controlCenterInit() {
        controlCenter = new ControlCenter(binding, MainActivity.this, getApplicationContext());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == controlCenter.getChooseFileCode()) {
            controlCenter.getFile(requestCode, resultCode, data, this);
        } else if (requestCode == controlCenter.getSaveFileCode()) {
            Log.i(String.valueOf(Log.INFO), "on activity result");
            controlCenter.changeAndSaveFile(resultCode, data);
        }
    }

}