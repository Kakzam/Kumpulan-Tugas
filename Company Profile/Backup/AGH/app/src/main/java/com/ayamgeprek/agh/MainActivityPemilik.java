package com.ayamgeprek.agh;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ayamgeprek.agh.databinding.ActivityMainPemilikBinding;

public class MainActivityPemilik extends AppCompatActivity {

    private ActivityMainPemilikBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainPemilikBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_pemilik);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}