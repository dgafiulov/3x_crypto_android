package dgafiulov.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import dgafiulov.worker_files.FileWorker;

public class WorkWithDialogs {

    public AlertDialog getErrorDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(R.string.error);
        alertDialog.setMessage(String.valueOf(R.string.error_text));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, String.valueOf(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss());
        return alertDialog;
    }

    public AlertDialog getAskForNewNameDialog(Context context, FileWorker fileSaver, boolean switchResult, Uri fileUri, String password) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View promptView = layoutInflater.inflate(R.layout.prompt, null);
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setView(promptView);
        final EditText userInput = (EditText) promptView.findViewById(R.id.newNameEt);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            String newName = String.valueOf(userInput.getText());
            fileSaver.initSaver((switchResult ? 2 : 3), fileUri, password, context, newName);
            Thread saveThread = new Thread(fileSaver);
            saveThread.start();
        });
        return alertDialog;
    }

    public AlertDialog getErrorSendDialog(Context context) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(R.string.error);
        String error = context.getString(R.string.error_send);
        alertDialog.setMessage(error);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, String.valueOf("OK"), (dialogInterface, i) -> dialogInterface.dismiss());
        return alertDialog;
    }
}