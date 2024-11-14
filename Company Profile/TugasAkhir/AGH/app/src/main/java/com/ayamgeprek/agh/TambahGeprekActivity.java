package com.ayamgeprek.agh;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TambahGeprekActivity extends AppCompatActivity {

    TextInputEditText tietEats, tietPrice;
    String base = "";
    DBConfig config;

    ActivityResultLauncher<Intent> geprek = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            base = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_geprek);

        config = new DBConfig(this, DBConfig.AGH, null, DBConfig.DB_VERSION);
        tietEats = findViewById(R.id.dialog_select_name_eats);
        tietPrice = findViewById(R.id.dialog_select_phone);

        findViewById(R.id.activity_tambah_geprek_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                geprek.launch(Intent.createChooser(intent, "Pilih Menu"));
            }
        });

        findViewById(R.id.activity_tambah_geprek_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tietEats.getText().toString().isEmpty()
                        && !tietPrice.getText().toString().isEmpty()
                        && !base.isEmpty()) {

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_menu_geprek (east,price,image) VALUES(" +
                                    "'" + tietEats.getText().toString() + "'," +
                                    "'" + tietPrice.getText().toString() + "'," +
                                    "'" + base + "')");

                    Intent intent = new Intent(getApplicationContext(), MainActivityPemilik.class);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(TambahGeprekActivity.this, "Isi semua dulu dong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}