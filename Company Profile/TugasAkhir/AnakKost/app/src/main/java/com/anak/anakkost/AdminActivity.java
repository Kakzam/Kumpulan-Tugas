package com.anak.anakkost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.anak.anakkost.ui.DBConfig;

public class AdminActivity extends AppCompatActivity {

    DBConfig config;
    Cursor cr;
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        config = new DBConfig(this, DBConfig.ANAK_KOST, null, DBConfig.DB_VERSION);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_password", null);
        cr.moveToFirst();

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            password = cr.getString(1);
        }

        EditText editPassword = findViewById(R.id.password_toggle);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
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