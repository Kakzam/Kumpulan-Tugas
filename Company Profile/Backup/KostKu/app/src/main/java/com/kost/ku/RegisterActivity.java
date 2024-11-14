package com.kost.ku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText iUsername, iPassword, iNama;
    TextView tombol1, tombol2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        iUsername = findViewById(R.id.username);
        iPassword = findViewById(R.id.password);
        iNama = findViewById(R.id.nama);
        tombol1 = findViewById(R.id.tombol_1);
        tombol2 = findViewById(R.id.tombol_2);

        tombol1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!iUsername.getText().toString().isEmpty()
                        || !iPassword.getText().toString().isEmpty()
                        || !iNama.getText().toString().isEmpty()
                ) {
                    Map<String, String> map = new HashMap<>();
                    map.put("username", iUsername.getText().toString());
                    map.put("password", iPassword.getText().toString());
                    map.put("nama", iNama.getText().toString());
                    map.put("select", "pengguna");
                    FirebaseFirestore.getInstance().collection("local")
                            .document("user")
                            .collection("space")
                            .add(map)
                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()) {
                                        new Tambah().ToastLong("Anda berhasil Registrasi", RegisterActivity.this);
                                        Intent intent = new Intent(getApplicationContext(), KostActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        new Tambah().ToastLong("Silahkan periksa kembali jaringan anda", RegisterActivity.this);
                                    }
                                }
                            });
                } else {
                    new Tambah().ToastLong("Silahkan isi semua dengan benar", RegisterActivity.this);
                }
            }
        });

        tombol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}