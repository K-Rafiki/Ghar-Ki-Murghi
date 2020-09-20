package com.eyris.desimurghi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        Intent intent;
//        intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
//        finish();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the MainActivity. */
                Intent mainIntent = new Intent(SplashScreen.this, LocationActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}
