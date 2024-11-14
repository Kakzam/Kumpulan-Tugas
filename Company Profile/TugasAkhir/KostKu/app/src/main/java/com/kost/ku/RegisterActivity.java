package com.kost.ku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText iUsername, iPassword, iNama;
    TextView tombol1, tombol2;
    DBConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        config = new DBConfig(this, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);
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
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_pengguna (username,password,nama,pilih) VALUES(" +
                                    "'" + iUsername.getText().toString() + "'," +
                                    "'" + iPassword.getText().toString() + "'," +
                                    "'" + iNama.getText().toString() + "'," +
                                    "'" + "pengguna" + "')");
                    new Tambah().ToastLong("Anda berhasil Registrasi", RegisterActivity.this);
                    Intent intent = new Intent(getApplicationContext(), KostActivity.class);
                    startActivity(intent);
                    finish();
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