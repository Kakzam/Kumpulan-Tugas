package com.example.tugasakhir;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.tugasakhir.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(view -> {
            if (binding.usernameEditText.getText().toString().isEmpty()) {
                setToast("Username Tidak Boleh Kosong");
            } else if (binding.passwordEditText.getText().toString().isEmpty()) {
                setToast("Password Tidak Boleh Kosong");
            } else {
                setToast("Proses Sedang Berjalan");
                FirebaseFirestore.getInstance().collection("data").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                if (documentSnapshot.getString("username").equals(binding.usernameEditText.getText().toString()) && documentSnapshot.getString("password").equals(binding.passwordEditText.getText().toString())){
                                    setNama(documentSnapshot.getString("nama"));
                                    setId(documentSnapshot.getId());
                                    setIntent(SatuMainActivity.class);
                                    break;
                                }
                            }
                        }).addOnFailureListener(e -> setToast(e.getMessage()));
            }
        });

    }
}