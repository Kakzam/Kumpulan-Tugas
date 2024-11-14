package com.freshmart.freshmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextView login, daftar;
    TextInputEditText nama, noHandphone, username, password;
    ProgressBar progressBar;
    int posisi = 1;
    DBConfig config;
    Cursor cr;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        config = new DBConfig(this, DBConfig.FRESH_MART, null, DBConfig.DB_VERSION);
        login = findViewById(R.id.login);
        daftar = findViewById(R.id.daftar);
        nama = findViewById(R.id.nama_lengkap);
        noHandphone = findViewById(R.id.no_handphone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);

        login.setOnClickListener(view -> {
            nama.setText("");
            noHandphone.setText("");
            username.setText("");
            password.setText("");

            if (posisi > 0) {
                daftar.setText("LOGIN");
                login.setText("DAFTAR");

                nama.setVisibility(View.GONE);
                noHandphone.setVisibility(View.GONE);
                posisi = 0;
            } else {
                daftar.setText("LOGIN");
                login.setText("DAFTAR");

                posisi = 1;
                nama.setVisibility(View.VISIBLE);
                noHandphone.setVisibility(View.VISIBLE);
            }
        });

        daftar.setOnClickListener(view -> {
            if (posisi > 0) {
                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_pemakai (nama,handphone,username,password,type) VALUES(" +
                                "'" + nama.getText().toString() + "'," +
                                "'" + noHandphone.getText().toString() + "'," +
                                "'" + username.getText().toString() + "'," +
                                "'" + password.getText().toString() + "'," +
                                "'" + "-" + "')");

                daftar.setText("LOGIN");
                login.setText("DAFTAR");
                nama.setText("");
                noHandphone.setText("");
                username.setText("");
                password.setText("");
                nama.setVisibility(View.GONE);
                noHandphone.setVisibility(View.GONE);
                posisi = 0;
            } else {
                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pemakai", null);
                cr.moveToFirst();

                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (cr.getString(3).equals(username.getText().toString()) && cr.getString(3).equals(password.getText().toString())) {
                        if (cr.getString(5).equals("1"))
                            new TestPenyimpanan(this).setLogin(true, cr.getString(0));
                        else new TestPenyimpanan(this).setLogin(false, cr.getString(0));
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }
}