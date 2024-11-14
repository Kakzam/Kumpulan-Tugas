package com.square.hijab;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;

import com.square.hijab.ui.DBConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    List<String> gamabarJilbab = new ArrayList<>();
    EditText tNama, tHarga;
    DBConfig config;
    Cursor cr;
    ActivityResultLauncher<Intent> getImageJilbab = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            gamabarJilbab.add(Base64.encodeToString(b, Base64.DEFAULT));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        config = new DBConfig(this, DBConfig.HIJAB_SQUARE, null, DBConfig.DB_VERSION);
        tNama = findViewById(R.id.a);
        tHarga = findViewById(R.id.b);

        findViewById(R.id.c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                getImageJilbab.launch(Intent.createChooser(intent, "Kumpulan Jilbab"));
            }
        });

        findViewById(R.id.d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_hijab (nama,harga) VALUES(" +
                                "'" + tNama.getText().toString() + "'," +
                                "'" + tHarga.getText().toString() + "')");

                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_hijab", null);
                cr.moveToFirst();
                cr.moveToPosition(cr.getCount() - 1);

                if (gamabarJilbab.size() > 0) for (String data : gamabarJilbab)
                        config.getWritableDatabase().execSQL(
                                "INSERT INTO tbl_gambar_hijab (id_hijab,gambar) VALUES(" +
                                        "'" + cr.getString(0) + "'," +
                                        "'" + data + "')");

                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

    }
}