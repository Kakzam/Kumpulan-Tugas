package com.companyprofile.ristianingsih.tampilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.companyprofile.ristianingsih.R;
import com.companyprofile.ristianingsih.lain.Preference;

public class UtamaActivity extends AppCompatActivity {

    int key = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);

        findViewById(R.id.image_key).setOnClickListener(view -> {
            key++;
            if (key > 10){
                new Preference(this).setLogin(true);
//                verifikasi
            }
        });

        findViewById(R.id.ll_filosofi).setOnClickListener(view -> {
            Intent intent = new Intent(this, FilosofiActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.ll_history).setOnClickListener(view -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.ll_awards).setOnClickListener(view -> {
            Intent intent = new Intent(this, AwardsActivity.class);
            startActivity(intent);
            finish();
        });

    }
}