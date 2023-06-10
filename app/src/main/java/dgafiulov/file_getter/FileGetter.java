package dgafiulov.file_getter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

public class FileGetter {

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
}
