package com.ayamgeprek.agh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SelectActivity extends AppCompatActivity {

    String very = "pelanggan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        findViewById(R.id.activity_select_2).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                very = "pemilik";
                return false;
            }
        });

        findViewById(R.id.activity_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogSelect().newInstance(very, "1").show(getSupportFragmentManager(), "");
            }
        });

        findViewById(R.id.activity_select_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogSelect().newInstance(very, "").show(getSupportFragmentManager(), "");
            }
        });

        findViewById(R.id.activity_select_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

    }
}