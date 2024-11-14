package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityGedungbBinding;

public class GedungbActivity extends BaseActivity {

    ActivityGedungbBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGedungbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}