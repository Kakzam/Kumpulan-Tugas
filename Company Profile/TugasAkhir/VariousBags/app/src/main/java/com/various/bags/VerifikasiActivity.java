package com.various.bags;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.various.bags.databinding.ActivityVerifikasiBinding;

public class VerifikasiActivity extends AppCompatActivity {

    ActivityVerifikasiBinding binding;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifikasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogin.setEnabled(false);
        config = new DBConfig(this, DBConfig.VARIOUS_BAGS, null, DBConfig.DB_VERSION);

        /* Enable for login button */
        binding.checkAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.checkAllow.isSelected()) binding.buttonLogin.setEnabled(false);
                else binding.buttonLogin.setEnabled(true);
            }
        });

        /* To Registration Activity */
        binding.buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistrasiActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /* On Development */
        binding.buttonForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VerifikasiActivity.this, "Sedang dalam tahap development", Toast.LENGTH_SHORT).show();
            }
        });

        /* Confirmation Login */
        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check = false;
                Intent intent;
                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                cr.moveToFirst();
                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (cr.getString(1).equals(binding.inputUsername.getText().toString()) && cr.getString(2).equals(binding.inputPassword.getText().toString())) {
                        check = true;
                        if (binding.inputUsername.getText().toString().equals("admin") && binding.inputPassword.getText().toString().equals("admin")) {
                            intent = new Intent(VerifikasiActivity.this, AdministratorActivity.class);
                            startActivity(intent);
                        } else {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(RegistrasiActivity.USER, cr.getString(0)).apply();
                            intent = new Intent(VerifikasiActivity.this, CustomerActivity.class);
                            startActivity(intent);
                        }
                        finish();
                    }
                }

                if (!check)
                    Toast.makeText(VerifikasiActivity.this, "Silahkan periksa username atau password anda", Toast.LENGTH_SHORT).show();
            }
        });

    }
}