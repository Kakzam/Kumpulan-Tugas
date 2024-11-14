package com.shop.amku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import io.github.fentonmartin.aappz.util.DelayZ;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DelayZ.post(2000, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.putExtra(DashboardActivity.ID, "");
                intent.putExtra(DashboardActivity.PERMISSION, "customer");
                startActivity(intent);
                finish();
            }
        });
    }
}