package com.catering.dessert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {

            new Storage(this).setLogin(false);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }, 2000);

    }
}