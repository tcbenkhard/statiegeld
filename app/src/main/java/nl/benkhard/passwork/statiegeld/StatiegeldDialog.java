package nl.benkhard.passwork.statiegeld;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

@SuppressLint("ValidFragment")
public class StatiegeldDialog extends DialogFragment {
    private DialogListener listener;
    private boolean hasStatiegeld;

    public StatiegeldDialog(boolean hasStatiegeld) {
        this.hasStatiegeld = hasStatiegeld;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        if(hasStatiegeld) {
            dialogBuilder.setTitle("Statiegeld!");
            dialogBuilder.setMessage("Op deze fles staat statiegeld!");
        } else {
            dialogBuilder.setTitle("Geen statiegeld!");
            dialogBuilder.setMessage("Op deze fles staat geen statiegeld!");
        }

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                listener.onDialogClose();
            }
        });

        return dialogBuilder.create();
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }
}
