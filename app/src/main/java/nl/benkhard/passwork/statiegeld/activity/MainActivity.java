package nl.benkhard.passwork.statiegeld.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        validatePermissions();
        scanBarcode();
    }

    private void validatePermissions() {
        int permissionResult = PermissionChecker.checkSelfPermission(this, Manifest.permission.CAMERA);
        switch (permissionResult) {
            case PermissionChecker.PERMISSION_DENIED:
                Log.d(this.getClass().toString(), "Permissions not granted");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
                break;
            case PermissionChecker.PERMISSION_GRANTED:
                Log.d(this.getClass().toString(), "Permissions granted");
                break;
        }
    }

    private void scanBarcode() {
        Intent intent = new Intent(this, BarcodeReaderActivity.class);
        startActivity(intent);
    }
}
