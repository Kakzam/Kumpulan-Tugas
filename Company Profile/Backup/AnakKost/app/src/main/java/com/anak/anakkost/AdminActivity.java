package com.anak.anakkost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    String password = "EMPTY";
    String collection = "EMPTY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        FirebaseFirestore cloud = FirebaseFirestore.getInstance();

        try {
            cloud.collection("PASSWORD").document("PASSWORD").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        password = task.getResult().get("password").toString();
                    } else Toast.makeText(AdminActivity.this, "Koneksimu mati cuy", Toast.LENGTH_SHORT).show();
                }
            });
            if (!getIntent().getStringExtra(collection).isEmpty()) collection = "PASSWORD";
        } catch (Exception e) {
            password = "EMPTY";
        }


        EditText editPassword = findViewById(R.id.password_toggle);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (collection.equals("PASSWORD")) {
                    Map<String, String> password = new HashMap<>();
                    password.put("password", editPassword.getText().toString());

                    if (cloud.collection(collection).document(collection).set(password).isSuccessful()) {
                        Toast.makeText(AdminActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(AdminActivity.this, "Berhasil mengubah password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                editPassword.removeTextChangedListener(this);
                if (editPassword.getText().toString().equals(password)) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                    finish();
                }
                editPassword.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}