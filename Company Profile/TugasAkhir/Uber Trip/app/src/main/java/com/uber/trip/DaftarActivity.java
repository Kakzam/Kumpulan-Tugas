package com.uber.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class DaftarActivity extends AppCompatActivity {

    TextInputEditText input_username, input_password, input_nama_lengkap;
    TextView textMasuk, textDaftar;
    String pilih = "bukan";
    int terpilih = 0;
    DBConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        Other other = new Other();
        config = new DBConfig(this, DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);
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
                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_pengguna (pilih,username,password,nama) VALUES(" +
                                "'" + pilih + "'," +
                                "'" + input_username.getText().toString() + "'," +
                                "'" + input_password.getText().toString() + "'," +
                                "'" + input_nama_lengkap.getText().toString() + "')");

                other.dissmissLoading();
                if (pilih.equals("administrator")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}