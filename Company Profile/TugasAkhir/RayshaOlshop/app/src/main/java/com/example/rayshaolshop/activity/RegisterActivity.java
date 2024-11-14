package com.example.rayshaolshop.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rayshaolshop.DBConfig;
import com.example.rayshaolshop.R;

public class RegisterActivity extends AppCompatActivity {

    //deklarasi variabel
    EditText nama, email, password;
    DBConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        nama = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void signup(View view) {

        String userNama = nama.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        //membuat alert di edit_text nama
        if (TextUtils.isEmpty(userNama)) {
            Toast.makeText(this, "Masukan Nama", Toast.LENGTH_SHORT).show();
            return;
        }

        //membuat alert di edit_text email
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Masukan Email Anda!", Toast.LENGTH_SHORT).show();
            return;
        }

        //membuat alert di edit_text password
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Masukan Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password terlalu pendek, masukan minimum 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        config = new DBConfig(this, DBConfig.RAYSHA_OLSHOP, null, DBConfig.DB_VERSION);
        config.getWritableDatabase().execSQL(
                "INSERT INTO tbl_customer (nama,email,password) VALUES(" +
                        "'" + userNama + "'," +
                        "'" + userEmail + "'," +
                        "'" + userPassword + "')");
        Toast.makeText(RegisterActivity.this, "Daftar Berhasil", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

    public void signin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}