package com.flow.giftforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.flow.giftforyou.databinding.ActivitySwitchBinding;

public class SwitchActivity extends AppCompatActivity {

    ActivitySwitchBinding activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ActivitySwitchBinding.inflate(getLayoutInflater());
        setContentView(activity.getRoot());
        activity.activityMainMasuk.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), MasukActivity.class)));
        activity.activityMainDaftar.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DaftarActivity.class)));
        activity.activityMainGoogle.setOnClickListener(view -> Toast.makeText(this, "Masih dalam tahap development", Toast.LENGTH_LONG).show());
        activity.activityMainFacebook.setOnClickListener(view -> Toast.makeText(this, "Masih dalam tahap development", Toast.LENGTH_LONG).show());
    }
}