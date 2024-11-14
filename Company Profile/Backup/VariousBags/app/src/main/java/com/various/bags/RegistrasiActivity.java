package com.various.bags;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.various.bags.databinding.ActivityRegistrasiBinding;

import java.util.HashMap;
import java.util.Map;

import io.github.fentonmartin.aappz.util.PrefZ;

public class RegistrasiActivity extends AppCompatActivity {

    ActivityRegistrasiBinding binding;
    Boolean check1 = false, check2 = false;
    FirebaseFirestore ff;

    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String NAME = "NAME";
    public static String PHONE = "PHONE";

    public static String USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonRegistration.setEnabled(false);
        ff = FirebaseFirestore.getInstance();

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
                    Map<String, String> data = new HashMap<>();
                    data.put(USERNAME, binding.inputUsername.getText().toString());
                    data.put(PASSWORD, binding.inputPassword.getText().toString());
                    data.put(NAME, binding.inputName.getText().toString());
                    data.put(PHONE, binding.inputPhone.getText().toString());
                    ff.collection(USER).add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                if (binding.inputUsername.getText().toString().equals("admin") && binding.inputPassword.getText().toString().equals("admin")) {
                                    startActivity(new Intent(RegistrasiActivity.this, AdministratorActivity.class));
                                    finish();
                                } else {
                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(RegistrasiActivity.USER, task.getResult().getId()).apply();
                                    startActivity(new Intent(RegistrasiActivity.this, CustomerActivity.class));
                                    finish();
                                }
                            } else
                                Toast.makeText(RegistrasiActivity.this, "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                        }
                    });
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