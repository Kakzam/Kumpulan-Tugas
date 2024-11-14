package com.faz.catering;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.faz.catering.ui.DBConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TambahActivity extends AppCompatActivity {

    EditText cateringNama, cateringDeskripsi, cateringHarga, cateringGambar, cateringHandphone, cateringLokasi;
    String cateringMakanan = "";
    String data = "";
    DBConfig config;

    ActivityResultLauncher<Intent> gambarCateringmu = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            cateringMakanan = Base64.encodeToString(b, Base64.DEFAULT);
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

        try {
            data = getIntent().getStringExtra("NAME");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Inisalisasi Program */
        config = new DBConfig(this, DBConfig.FAZ_CATERING, null, DBConfig.DB_VERSION);
        cateringNama = findViewById(R.id.catering_nama);
        cateringDeskripsi = findViewById(R.id.catering_deskripsi);
        cateringHarga = findViewById(R.id.catering_harga);
        cateringGambar = findViewById(R.id.catering_gambar);
        cateringHandphone = findViewById(R.id.catering_handphone);
        cateringLokasi = findViewById(R.id.catering_lokasi);

        cateringGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                gambarCateringmu.launch(Intent.createChooser(intent, "Menu Catering"));
            }
        });

        findViewById(R.id.catering_simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        !cateringNama.getText().toString().isEmpty() &&
                                !cateringDeskripsi.getText().toString().isEmpty() &&
                                !cateringHarga.getText().toString().isEmpty() &&
                                !cateringMakanan.isEmpty() &&
                                !cateringHandphone.getText().toString().isEmpty() &&
                                !cateringLokasi.getText().toString().isEmpty()
                ) {

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_menu_catering (nama,deskripsi,harga,gambar,handphone,lokasi,menu) VALUES(" +
                                    "'" + cateringNama.getText().toString() + "'," +
                                    "'" + cateringDeskripsi.getText().toString() + "'," +
                                    "'" + cateringHarga.getText().toString() + "'," +
                                    "'" + cateringMakanan + "'," +
                                    "'" + cateringHandphone.getText().toString() + "'," +
                                    "'" + cateringLokasi.getText().toString() + "'," +
                                    "'" + data + "')");

                    Intent intent = new Intent(getApplicationContext(), MainActivityCatering.class);
                    startActivity(intent);
                    finish();
                } else Log.v("TambahActivity", "Data ada yang kosong");
            }
        });
    }
}