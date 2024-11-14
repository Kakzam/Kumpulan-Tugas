package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivitySatuMainBinding;

public class SatuMainActivity extends BaseActivity {

    ActivitySatuMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySatuMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.usernameTextView.setText(getNama());

        binding.tombol1.setOnClickListener(view -> setIntent(RumahActivity.class));

        binding.tombol2.setOnClickListener(view -> setIntent(TigaMainActivity.class));

        binding.tombol3.setOnClickListener(view -> setIntent(EmpatActivity.class));
    }
}