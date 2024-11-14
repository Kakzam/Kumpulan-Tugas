package com.various.bags;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.various.bags.databinding.ActivityVerifikasiBinding;

import io.github.fentonmartin.aappz.util.PrefZ;

public class VerifikasiActivity extends AppCompatActivity {

    ActivityVerifikasiBinding binding;
    FirebaseFirestore ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerifikasiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogin.setEnabled(false);
        ff = FirebaseFirestore.getInstance();

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
                ff.collection(RegistrasiActivity.USER).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Boolean check = false;
                            Intent intent;
                            for (DocumentSnapshot ds : task.getResult()) {
                                if (ds.get(RegistrasiActivity.USERNAME).equals(binding.inputUsername.getText().toString()) && ds.get(RegistrasiActivity.PASSWORD).equals(binding.inputPassword.getText().toString())) {
                                    check = true;
                                    if (binding.inputUsername.getText().toString().equals("admin") && binding.inputPassword.getText().toString().equals("admin")) {
                                        intent = new Intent(VerifikasiActivity.this, AdministratorActivity.class);
                                        startActivity(intent);
                                    } else {
                                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(RegistrasiActivity.USER, ds.getId()).apply();
                                        intent = new Intent(VerifikasiActivity.this, CustomerActivity.class);
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            }

                            if (!check)
                                Toast.makeText(VerifikasiActivity.this, "Silahkan periksa username atau password anda", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(VerifikasiActivity.this, "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}