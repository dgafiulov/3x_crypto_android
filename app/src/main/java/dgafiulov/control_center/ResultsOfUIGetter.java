package dgafiulov.control_center;

import dgafiulov.ui.databinding.ActivityMainBinding;

public class ResultsOfUIGetter {

    ActivityMainBinding binding;

    public ResultsOfUIGetter(ActivityMainBinding binding) {
        this.binding = binding;
    }

    public boolean getSwitchResult() {
        return binding.swChoose.isChecked();
    }

    public String getPasswordFromEditText() {
        return binding.etPasswordInput.getText().toString();
    }
}
