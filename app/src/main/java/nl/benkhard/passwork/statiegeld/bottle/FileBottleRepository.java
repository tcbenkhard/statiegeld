package nl.benkhard.passwork.statiegeld.bottle;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.File;

public class FileBottleRepository implements BottleRepository {
    private static FileBottleRepository instance;

    public static FileBottleRepository getInstance() {
        if(instance == null) {
            instance = new FileBottleRepository();
        }

        return instance;
    }

    public void load(File file) {

    }

    public void save() {

    }

    @Override
    public Bottle getBy(Barcode barcode) {
        return null;
    }

    @Override
    public void save(Bottle bottle) {

    }
}
