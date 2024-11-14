package com.mobile.skud_id.feature;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.mobile.skud_id.R;
import com.mobile.skud_id.databinding.ActivityCustomerBinding;

public class CustomerActivity extends AppCompatActivity {

    private ActivityCustomerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_verification);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}