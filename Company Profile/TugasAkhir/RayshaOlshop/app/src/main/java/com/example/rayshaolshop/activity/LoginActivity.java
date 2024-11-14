package com.example.rayshaolshop.activity;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rayshaolshop.DBConfig;
import com.example.rayshaolshop.R;

public class LoginActivity extends AppCompatActivity {

    //deklarasi variabel
    EditText email, password;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void signin(View view) {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_customer", null);
        cr.moveToFirst();
        boolean cek = false;

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            if (cr.getString(2).equals(userEmail) && cr.getString(3).equals(userPassword)) {
                cek = true;
                Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        }

        if (!cek)
            Toast.makeText(this, "Login gagal, silahkan cek kembali username, dan password anda", Toast.LENGTH_SHORT).show();
    }

    public void signup(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}