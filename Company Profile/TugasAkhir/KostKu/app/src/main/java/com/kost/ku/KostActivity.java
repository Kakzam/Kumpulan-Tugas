package com.kost.ku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import com.kost.ku.test.Kost;

import java.util.ArrayList;
import java.util.List;

public class KostActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DBConfig config;
    Cursor cr;

    List<String> gambar = new ArrayList<>();
    List<String> kost = new ArrayList<>();
    List<String> alamat = new ArrayList<>();
    List<String> jenis = new ArrayList<>();
    List<String> fasilitas = new ArrayList<>();
    List<String> phone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost);
        recyclerView = findViewById(R.id.activity_kost);
        config = new DBConfig(this, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_kost", null);
        cr.moveToFirst();
        kost.clear();
        alamat.clear();
        jenis.clear();
        fasilitas.clear();
        phone.clear();
        gambar.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            kost.add(cr.getString(1));
            alamat.add(cr.getString(2));
            jenis.add(cr.getString(3));
            fasilitas.add(cr.getString(4));
            phone.add(cr.getString(5));
            gambar.add(cr.getString(6));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new Kost(gambar, kost, alamat, jenis, fasilitas, phone));
    }
}