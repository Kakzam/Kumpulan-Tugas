package com.uber.trip;

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

public class DaftarActivity extends AppCompatActivity {

    TextInputEditText input_username, input_password, input_nama_lengkap;
    TextView textMasuk, textDaftar;
    String pilih = "bukan";
    int terpilih = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        Other other = new Other();
        input_nama_lengkap = findViewById(R.id.nama);
        input_username = findViewById(R.id.handphone);
        input_password = findViewById(R.id.katasandi);
        textMasuk = findViewById(R.id.daftar);
        textDaftar = findViewById(R.id.masuk);

        findViewById(R.id.pilih).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terpilih += 1;
                if (terpilih > 5) {
                    terpilih = 0;
                    pilih = "administrator";
                }
            }
        });

        textMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                other.setLoading(DaftarActivity.this);
                Map<String, String> daftar = new HashMap<>();
                daftar.put("pilih", pilih);
                daftar.put("username", input_username.getText().toString());
                daftar.put("password", input_password.getText().toString());
                daftar.put("nama", input_nama_lengkap.getText().toString());
                FirebaseFirestore.getInstance()
                        .collection("collection")
                        .document("user")
                        .collection("collection")
                        .add(daftar)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                other.dissmissLoading();
                                if (task.isSuccessful()) {
                                    if (pilih.equals("administrator")) {
                                        Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } else {
                                    other.setToast("Silahkan periksa koneksi anda", DaftarActivity.this);
                                }
                            }
                        });
            }
        });
    }
}