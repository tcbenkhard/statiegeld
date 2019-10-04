package nl.benkhard.passwork.statiegeld.bottle;

import com.google.android.gms.vision.barcode.Barcode;

public class Bottle {
    private final Barcode barcode;
    private final boolean statiegeld;

    public Bottle(Barcode barcode) {
        this(barcode, false);
    }

    public Bottle(Barcode barcode, boolean statiegeld) {
        this.barcode = barcode;
        this.statiegeld = statiegeld;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public boolean hasStatiegeld() {
        return statiegeld;
    }
}
