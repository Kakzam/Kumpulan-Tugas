package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityEmpatBinding;

public class EmpatActivity extends BaseActivity {

    ActivityEmpatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmpatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tombol1.setOnClickListener(view -> setIntent(GedunggsgActivity.class));
        binding.tombol2.setOnClickListener(view -> setIntent(GedungaActivity.class));
        binding.tombol3.setOnClickListener(view -> setIntent(GedungbActivity.class));
        binding.tombol4.setOnClickListener(view -> setIntent(GedungictActivity.class));
    }
}