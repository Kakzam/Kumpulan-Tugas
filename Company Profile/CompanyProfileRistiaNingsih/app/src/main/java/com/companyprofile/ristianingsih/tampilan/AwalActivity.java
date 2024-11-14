package com.companyprofile.ristianingsih.tampilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.companyprofile.ristianingsih.R;

public class AwalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(getApplicationContext(), UtamaActivity.class);
            startActivity(intent);
            finish();
        }, 2000);
    }
}