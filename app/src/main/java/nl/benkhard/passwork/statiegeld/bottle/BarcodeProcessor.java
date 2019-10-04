package nl.benkhard.passwork.statiegeld.bottle;

import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeProcessor implements Detector.Processor<Barcode> {
    private final int BARCODE_VALIDATION_COUNT = 5;

    private boolean paused = false;
    private Barcode[] history;
    private int index = 0;
    private BarcodeDetectionListener listener;

    public BarcodeProcessor(BarcodeDetectionListener listener) {
        history = new Barcode[BARCODE_VALIDATION_COUNT];
        this.listener = listener;
    }

    @Override
    public void release() {

    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        if(paused) return;

        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
        if(barcodes.size() > 0) {
            Barcode scannedBarcode = barcodes.valueAt(0);
            history[index++ % BARCODE_VALIDATION_COUNT] = scannedBarcode;

            if (isValidBarcode()) {
                paused = true;
                listener.onBarcodeDetected(barcodes.valueAt(0));
            }
        }
    }

    public boolean isValidBarcode() {
        Barcode previous = null;
        for(Barcode barcode : history) {
            if(previous == null) {
                previous = barcode;
                continue;
            }

            if(barcode == null) return false;
            if(!barcode.displayValue.equals(previous.displayValue)) return false;

            previous = barcode;
        }
        return true;
    }

    public void resumeDetection() {
        paused = false;
    }

    public interface BarcodeDetectionListener {
        public void onBarcodeDetected(Barcode barcode);
    }
}
