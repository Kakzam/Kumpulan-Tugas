package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityGedungaBinding;

public class GedungaActivity extends BaseActivity {

    ActivityGedungaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGedungaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}