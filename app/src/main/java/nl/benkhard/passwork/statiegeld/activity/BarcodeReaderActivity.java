package nl.benkhard.passwork.statiegeld.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import nl.benkhard.passwork.statiegeld.bottle.BarcodeProcessor;
import nl.benkhard.passwork.statiegeld.DialogListener;
import nl.benkhard.passwork.statiegeld.bottle.BottleRepository;
import nl.benkhard.passwork.statiegeld.R;
import nl.benkhard.passwork.statiegeld.StatiegeldDialog;
import nl.benkhard.passwork.statiegeld.UnknownBarcodeDialog;
import nl.benkhard.passwork.statiegeld.bottle.FileBottleRepository;

public class BarcodeReaderActivity extends AppCompatActivity implements SurfaceHolder.Callback, BarcodeProcessor.BarcodeDetectionListener, DialogListener {

    private SurfaceView cameraPreviewSurface;
    private CameraSource cameraSource;
    private BarcodeProcessor barcodeProcessor;
    private BottleRepository repository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        cameraPreviewSurface = findViewById(R.id.camera_preview);

        repository = FileBottleRepository.getInstance();
        createCameraResource();
        cameraPreviewSurface.getHolder().addCallback(this);
    }

    private void createCameraResource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        barcodeProcessor = new BarcodeProcessor(this);
        barcodeDetector.setProcessor(barcodeProcessor);

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();
    }

    @SuppressLint("MissingPermission")
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            cameraSource.start(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("abc", "onResume!!!!");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        cameraSource.stop();
    }

    public void onBarcodeDetected(final Barcode barcode) {
        Log.i(this.getClass().toString(), String.format("Barcode read: " + barcode.displayValue));
        if(repository.exists(barcode.displayValue)) showStatiegeldDialog(barcode);
        else showUnknownBarcodeDialog(barcode);
    }

    private void showStatiegeldDialog(Barcode barcode) {
        StatiegeldDialog dialog = new StatiegeldDialog(repository.hasStatiegeld(barcode.displayValue));
        dialog.setListener(this);
        dialog.show(getSupportFragmentManager(), "Barcoderesult");
    }

    private void showUnknownBarcodeDialog(final Barcode barcode) {
        UnknownBarcodeDialog dialog = new UnknownBarcodeDialog(barcode.displayValue);
        dialog.setListener(this);
        dialog.show(getSupportFragmentManager(), "Unknownbarcode");
    }

    @Override
    public void onDialogClose() {
        barcodeProcessor.resumeDetection();
    }
}
