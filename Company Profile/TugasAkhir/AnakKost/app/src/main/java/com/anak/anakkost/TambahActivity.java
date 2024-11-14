package com.anak.anakkost;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.anak.anakkost.ui.DBConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TambahActivity extends AppCompatActivity {

    EditText judul, harga, deskripsi, lokasi;
    String kost = "";
    DBConfig config;
    ActivityResultLauncher<Intent> gambarKost = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            kost = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        config = new DBConfig(this, DBConfig.ANAK_KOST, null, DBConfig.DB_VERSION);
        judul = findViewById(R.id.nama);
        harga = findViewById(R.id.harga);
        deskripsi = findViewById(R.id.deskripsi);
        lokasi = findViewById(R.id.lokasi);

        findViewById(R.id.gambar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                gambarKost.launch(Intent.createChooser(intent, "Foto Lokasi"));
            }
        });

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_kost (judul,harga,deskripsi,lokasi,image, menu) VALUES(" +
                                "'" + judul.getText().toString() + "'," +
                                "'" + harga.getText().toString() + "'," +
                                "'" + deskripsi.getText().toString() + "'," +
                                "'" + lokasi.getText().toString() + "'," +
                                "'" + kost + "'," +
                                "'" + getIntent().getStringExtra("COLLECTION") + "')");

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });
    }
}