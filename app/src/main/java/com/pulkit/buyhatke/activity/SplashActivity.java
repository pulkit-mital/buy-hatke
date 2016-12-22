package com.pulkit.buyhatke.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.pulkit.buyhatke.R;


/**
 * @author pulkitmital
 * @date 22-12-2016
 */
public class SplashActivity extends AppCompatActivity {

    final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        askRuntimePermission();
    }

    private void askRuntimePermission() {

        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_SMS") ==
                PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.READ_CONTACTS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Sorry BuyHatke can't work unless permissions are granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
