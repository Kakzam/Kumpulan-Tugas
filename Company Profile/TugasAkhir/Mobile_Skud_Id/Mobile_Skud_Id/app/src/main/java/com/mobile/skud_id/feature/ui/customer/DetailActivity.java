package com.mobile.skud_id.feature.ui.customer;

import android.os.Bundle;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}