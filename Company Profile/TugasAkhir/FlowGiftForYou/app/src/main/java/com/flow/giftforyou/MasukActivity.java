package com.flow.giftforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flow.giftforyou.ui.DBConfig;
import com.google.android.material.textfield.TextInputEditText;

public class MasukActivity extends AppCompatActivity {

    TextInputEditText textId, textSandi;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);
        config = new DBConfig(this, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
        textId = findViewById(R.id.activity_masuk_id);
        textSandi = findViewById(R.id.activity_masuk_sandi);

        findViewById(R.id.activity_masuk_repassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MasukActivity.this, "Sedang tahap development", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.activity_masuk_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
                finish();
            }
        });

        findViewById(R.id.activity_masuk_tombol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = textId.getText().toString();
                String sandi = textSandi.getText().toString();

                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pelanggan", null);
                cr.moveToFirst();
                boolean periksa = false;

                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (cr.getString(2).equals(id) && cr.getString(3).equals(sandi)) {
                        periksa = true;
                        Intent intent;
                        if (cr.getString(4).equals("UBAH")) {
                            intent = new Intent(getApplicationContext(), MainActivity2.class);
                            startActivity(intent);
                            finish();
                        } else {
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                if (!periksa)
                    Toast.makeText(MasukActivity.this, "Silahkan periksa kembali login anda", Toast.LENGTH_SHORT).show();
            }
        });
    }
}