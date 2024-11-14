package com.uber.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText input_username, input_password;
    TextView textMasuk, textDaftar;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Other other = new Other();
        config = new DBConfig(this, DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);
        textMasuk = findViewById(R.id.masuk);
        textDaftar = findViewById(R.id.daftar);
        input_password = findViewById(R.id.sandi);
        input_username = findViewById(R.id.hp);

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DaftarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                other.setLoading(LoginActivity.this);
                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pengguna", null);
                cr.moveToFirst();

                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (cr.getString(3).equals(input_password.getText().toString())
                            && cr.getString(2).equals(input_username.getText().toString())
                    ) {
                        if (cr.getString(1).equals("administrator")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                            startActivity(intent);
                            finish();
                        }

                        if (cr.getString(1).equals("bukan")) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                    other.dissmissLoading();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}