package aji.bayu.adnan.cataloguemovies;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Jihad044 on 05/09/2018.
 */

public class SplashScreenActivity extends AppCompatActivity {
    private static final int splashTime = 3000;
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    handler = new Handler();

                    runnable = new Runnable() {
                        @Override
                        public void run() {
//
                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                            startActivity(intent);
                            finish();
                        }
                    };

                    handler.postDelayed(runnable, splashTime);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SplashScreenActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.INTERNET},
                1);
    }
}
