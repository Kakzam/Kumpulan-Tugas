package com.example.tugasakhir;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityTigaMainBinding;

public class TigaMainActivity extends BaseActivity {

    ActivityTigaMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTigaMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tombol1.setOnClickListener(view -> {
            if (binding.check.isChecked()) {
                setIntent(MultimediaActivity.class);
            } else {
                setToast("Anda belum setuju dengan ketentuan yang berlaku");
            }
        });
        binding.tombol2.setOnClickListener(view -> {
            if (binding.check.isChecked()) {
                setIntent(AcaraActivity.class);
            } else {
                setToast("Anda belum setuju dengan ketentuan yang berlaku");
            }
        });
    }
}