package com.kost.ku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText iUsername, iPassword;
    TextView text1, text2;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        config = new DBConfig(this, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);
        text1 = findViewById(R.id.tombol_1);
        text2 = findViewById(R.id.tombol_2);
        iUsername = findViewById(R.id.username);
        iPassword = findViewById(R.id.password);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pengguna", null);
                cr.moveToFirst();

                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (iUsername.getText().toString().equals(cr.getString(1)) &&
                            iPassword.getText().toString().equals(cr.getString(2))
                    ) {
                        if (cr.getString(4).equals("pengguna")) {
                            Intent intent = new Intent(getApplicationContext(), KostActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}