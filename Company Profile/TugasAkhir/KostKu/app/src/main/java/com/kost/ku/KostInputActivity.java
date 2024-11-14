package com.kost.ku;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kost.ku.test.Input;

import java.util.ArrayList;
import java.util.List;

public class KostInputActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DBConfig config;
    Cursor cr;

    List<String> user = new ArrayList<>();
    List<String> gambar = new ArrayList<>();
    List<String> kost = new ArrayList<>();
    List<String> alamat = new ArrayList<>();
    List<String> jenis = new ArrayList<>();
    List<String> fasilitas = new ArrayList<>();
    List<String> phone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_input);
        recyclerView = findViewById(R.id.list);
        config = new DBConfig(this, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);

        findViewById(R.id.tambah_kost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                startActivity(intent);
            }
        });

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_kost", null);
        cr.moveToFirst();
        user.clear();
        kost.clear();
        alamat.clear();
        jenis.clear();
        fasilitas.clear();
        phone.clear();
        gambar.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            user.add(cr.getString(0));
            kost.add(cr.getString(1));
            alamat.add(cr.getString(2));
            jenis.add(cr.getString(3));
            fasilitas.add(cr.getString(4));
            phone.add(cr.getString(5));
            gambar.add(cr.getString(6));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Input(getApplicationContext(), user, gambar, kost, alamat, jenis, fasilitas, phone));
    }
}