package com.anak.anakkost;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anak.anakkost.databinding.ActivityRegistrasiVerifikasiBinding;
import com.anak.anakkost.ui.DBConfig;

public class RegistrasiVerifikasiActivity extends AppCompatActivity {

    ActivityRegistrasiVerifikasiBinding activity;
    boolean regis = false;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = ActivityRegistrasiVerifikasiBinding.inflate(getLayoutInflater());
        setContentView(activity.getRoot());
        config = new DBConfig(this, DBConfig.ANAK_KOST, null, DBConfig.DB_VERSION);
        activity.belakang.setVisibility(View.GONE);
        activity.depan.setVisibility(View.GONE);
        activity.hp.setHint("Username");

        activity.regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (regis) {
                    if (!activity.depan.getText().toString().isEmpty()
                            && !activity.belakang.getText().toString().isEmpty()
                            && !activity.hp.getText().toString().isEmpty()
                            && !activity.password.getText().toString().isEmpty()) {

                        config.getWritableDatabase().execSQL(
                                "INSERT INTO tbl_pengguna (nama_depan,nama_belakang,no_hp,password) VALUES(" +
                                        "'" + activity.depan.getText().toString() + "'," +
                                        "'" + activity.depan.getText().toString() + "'," +
                                        "'" + activity.hp.getText().toString() + "'," +
                                        "'" + activity.password.getText().toString() + "')");

                        Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                        startActivity(intent);
                        finish();

                    } else if (activity.depan.getText().toString().isEmpty())
                        Toast.makeText(RegistrasiVerifikasiActivity.this, "Nama depan kosong", Toast.LENGTH_SHORT).show();
                    else if (activity.belakang.getText().toString().isEmpty())
                        Toast.makeText(RegistrasiVerifikasiActivity.this, "Nama belakang kosong", Toast.LENGTH_SHORT).show();
                    else if (activity.hp.getText().toString().isEmpty())
                        Toast.makeText(RegistrasiVerifikasiActivity.this, "No. Handphone kosong", Toast.LENGTH_SHORT).show();
                    else if (activity.password.getText().toString().isEmpty())
                        Toast.makeText(RegistrasiVerifikasiActivity.this, "Password kosong", Toast.LENGTH_SHORT).show();
                } else {
                    activity.belakang.setVisibility(View.VISIBLE);
                    activity.depan.setVisibility(View.VISIBLE);
                    activity.hp.setHint("No. Handphone");
                    activity.login.setVisibility(View.GONE);
                    regis = true;
                }
            }
        });

        activity.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activity.hp.getText().toString().isEmpty()
                        && !activity.password.getText().toString().isEmpty()) {

                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pengguna", null);
                    cr.moveToFirst();
                    boolean check = false;
                    for (int count = 0; count < cr.getCount(); count++) {
                        cr.moveToPosition(count);
                        if (activity.hp.getText().toString().equals(cr.getString(3)) && activity.password.getText().toString().equals(cr.getString(4))) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                            startActivity(intent);
                            finish();
                            check = true;
                        }
                    }
                    if (!check)
                        Toast.makeText(RegistrasiVerifikasiActivity.this, "Username atau password anda salah", Toast.LENGTH_SHORT).show();
                } else if (activity.hp.getText().toString().isEmpty())
                    Toast.makeText(RegistrasiVerifikasiActivity.this, "Username kosong", Toast.LENGTH_SHORT).show();
                else if (activity.password.getText().toString().isEmpty())
                    Toast.makeText(RegistrasiVerifikasiActivity.this, "Password kosong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}