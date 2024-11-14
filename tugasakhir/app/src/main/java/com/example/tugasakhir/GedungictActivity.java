package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityGedungictBinding;

public class GedungictActivity extends BaseActivity {

    ActivityGedungictBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGedungictBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}