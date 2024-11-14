package com.eventic.lampungtourism;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.eventic.lampungtourism.databinding.ActivityVerificaitonBinding;

public class VerificaitonActivity extends AppCompatActivity {

    private ActivityVerificaitonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerificaitonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_verificaiton);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}