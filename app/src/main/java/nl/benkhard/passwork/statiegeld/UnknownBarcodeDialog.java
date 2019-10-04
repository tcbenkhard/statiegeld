package nl.benkhard.passwork.statiegeld;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import nl.benkhard.passwork.statiegeld.bottle.BottleRepository;

@SuppressLint("ValidFragment")
public class UnknownBarcodeDialog extends DialogFragment {
    private DialogListener listener;
    private BottleRepository repository;
    private String barcode;

    public UnknownBarcodeDialog(String barcode) {
        repository = MemoryBottleRepository.getInstance();
        this.barcode = barcode;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("Onbekende barcode ("+barcode+")")
                .setMessage("Deze barcode ken ik niet! Staat er statiegeld op deze fles?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.add(barcode, true);
                        listener.onDialogClose();
                    }
                })
                .setNegativeButton("Nee", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        repository.add(barcode, false);
                        listener.onDialogClose();
                    }
                })
                .create();
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }
}
