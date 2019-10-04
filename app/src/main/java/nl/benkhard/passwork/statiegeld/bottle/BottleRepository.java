package nl.benkhard.passwork.statiegeld.bottle;

import com.google.android.gms.vision.barcode.Barcode;

public interface BottleRepository {
    public Bottle getBy(Barcode barcode);
    public void save(Bottle bottle);
}
