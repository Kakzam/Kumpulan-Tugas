package com.various.bags;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.various.bags.databinding.ActivityMainBinding;

import io.github.fentonmartin.aappz.util.DelayZ;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DelayZ.post(2000, new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), VerifikasiActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}