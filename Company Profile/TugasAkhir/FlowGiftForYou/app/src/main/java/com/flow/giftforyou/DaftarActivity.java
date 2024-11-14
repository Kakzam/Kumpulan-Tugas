package com.flow.giftforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flow.giftforyou.ui.DBConfig;
import com.flow.giftforyou.ui.UtilString;
import com.google.android.material.textfield.TextInputEditText;

public class DaftarActivity extends AppCompatActivity {

    String login = UtilString.LOGIN;
    TextInputEditText ieUser, ieSandi, ieName;
    DBConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        config = new DBConfig(this, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
        ieName = findViewById(R.id.name);
        ieSandi = findViewById(R.id.sandi);
        ieUser = findViewById(R.id.id);

        findViewById(R.id.tombol_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasukActivity.class));
                finish();
            }
        });

        findViewById(R.id.tombol_1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                login = UtilString.UBAH;
                return false;
            }
        });

        findViewById(R.id.tombol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ieName.getText().toString().isEmpty()
                        && !ieSandi.getText().toString().isEmpty()
                        && !ieUser.getText().toString().isEmpty()) {
                    config.getWritableDatabase().execSQL("INSERT INTO tbl_pelanggan (username, password, nama, login) VALUES(" +
                            "'" + ieUser.getText().toString() + "'," +
                            "'" + ieSandi.getText().toString() + "'," +
                            "'" + ieName.getText().toString() + "'," +
                            "'" + login + "')");
                    Intent intent;
                    if (login.equals(UtilString.UBAH)) {
                        intent = new Intent(getApplicationContext(), MainActivity2.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    finish();
                    Toast.makeText(DaftarActivity.this, "Melakukan Proses", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(DaftarActivity.this, "Silahkan periksa semua data sudah diisi dengan benar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}