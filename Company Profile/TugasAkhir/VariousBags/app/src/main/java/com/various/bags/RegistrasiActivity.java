package com.various.bags;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.various.bags.databinding.ActivityRegistrasiBinding;

public class RegistrasiActivity extends AppCompatActivity {

    ActivityRegistrasiBinding binding;
    Boolean check1 = false, check2 = false;
    DBConfig config;
    Cursor cr;

    public static String USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonRegistration.setEnabled(false);
        config = new DBConfig(this, DBConfig.VARIOUS_BAGS, null, DBConfig.DB_VERSION);

        /* Enable Registration */
        binding.checkAllow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check1) check1 = false;
                else check1 = true;

                if (check1 && check2) binding.buttonRegistration.setEnabled(true);
            }
        });

        /* Enable Registration */
        binding.checkAllow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check2) check2 = false;
                else check2 = true;

                if (check1 && check2) binding.buttonRegistration.setEnabled(true);
            }
        });

        /* Confirm Registration */
        binding.buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.inputUsername.getText().toString().isEmpty()
                        && !binding.inputPassword.getText().toString().isEmpty()
                        && !binding.inputName.getText().toString().isEmpty()
                        && !binding.inputPhone.getText().toString().isEmpty()) {

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_user (username,password,name,phone) VALUES(" +
                                    "'" + binding.inputUsername.getText().toString() + "'," +
                                    "'" + binding.inputPassword.getText().toString() + "'," +
                                    "'" + binding.inputName.getText().toString() + "'," +
                                    "'" + binding.inputPhone.getText().toString() + "')");

                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                    cr.moveToFirst();
                    cr.moveToPosition(cr.getCount()-1);

                    if (binding.inputUsername.getText().toString().equals("admin") && binding.inputPassword.getText().toString().equals("admin")) {
                        startActivity(new Intent(RegistrasiActivity.this, AdministratorActivity.class));
                        finish();
                    } else {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(RegistrasiActivity.USER, cr.getString(0)).apply();
                        startActivity(new Intent(RegistrasiActivity.this, CustomerActivity.class));
                        finish();
                    }

                } else if (binding.inputUsername.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrasiActivity.this, "Silahkan ini Username anda", Toast.LENGTH_SHORT).show();
                } else if (binding.inputPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrasiActivity.this, "Silahkan ini Password anda", Toast.LENGTH_SHORT).show();
                } else if (binding.inputName.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrasiActivity.this, "Silahkan ini Your Name anda", Toast.LENGTH_SHORT).show();
                } else if (binding.inputPhone.getText().toString().isEmpty()) {
                    Toast.makeText(RegistrasiActivity.this, "Silahkan ini Phone Number anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}