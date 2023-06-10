package dgafiulov.app_start;

import android.app.Application;

public class AppStart extends Application {

    private final int sdkVersion = android.os.Build.VERSION.SDK_INT;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public int getSdkVersion() {
        return sdkVersion;
    }

}