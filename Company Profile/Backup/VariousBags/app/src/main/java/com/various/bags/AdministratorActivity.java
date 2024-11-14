package com.various.bags;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.various.bags.databinding.ActivityAdministratorBinding;

public class AdministratorActivity extends AppCompatActivity {

    private ActivityAdministratorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdministratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_administrator);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}