package com.sekud.id.feature;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.base.Preference;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration;

        if (new Preference(this).getLogin()) appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.navigation_item, R.id.navigation_about, R.id.navigation_location).build();
        else
            appBarConfiguration = new AppBarConfiguration
                    .Builder(R.id.navigation_item_admin, R.id.navigation_about_admin, R.id.navigation_location_admin, R.id.navigation_user_admin).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onBackPressed() {
        setAlerttDialogExit();
    }
}