package com.kost.ku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InputActivity extends AppCompatActivity {

    TextInputEditText iNamaKost, iAlamat, iJenis, iFasilitas, iPhone;
    String user = "EMPTY";
    String gambar = "";
    DBConfig config;

    ActivityResultLauncher<Intent> coba = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        TextView textView = findViewById(R.id.gambar);
                        try {
                            textView.setText("Terpilih");
                            textView.setBackground(getDrawable(R.drawable.background_b));
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            gambar = Base64.encodeToString(b, Base64.DEFAULT);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        config = new DBConfig(this, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);
        iNamaKost = findViewById(R.id.nama_kost);
        iAlamat = findViewById(R.id.alamat);
        iJenis = findViewById(R.id.jenis);
        iFasilitas = findViewById(R.id.fasilitas);
        iPhone = findViewById(R.id.phone);

        try {
            if (!getIntent().getStringExtra("nama").isEmpty()) {
                iNamaKost.setText(getIntent().getStringExtra("nama"));
                iAlamat.setText(getIntent().getStringExtra("alamat"));
                iJenis.setText(getIntent().getStringExtra("jenis"));
                iFasilitas.setText(getIntent().getStringExtra("fasilitas"));
                iPhone.setText(getIntent().getStringExtra("phone"));
                gambar = getIntent().getStringExtra("gambar");
                user = getIntent().getStringExtra("user");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        findViewById(R.id.gambar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                coba.launch(Intent.createChooser(intent, "Coba"));
            }
        });

        findViewById(R.id.simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!iNamaKost.getText().toString().isEmpty()
                        || !iAlamat.getText().toString().isEmpty()
                        || !iJenis.getText().toString().isEmpty()
                        || !iFasilitas.getText().toString().isEmpty()
                        || !iPhone.getText().toString().isEmpty()
                        || !gambar.isEmpty()) {
                    if (user.equals("EMPTY")) {
                        config.getWritableDatabase().execSQL(
                                "INSERT INTO tbl_kost (kost, alamat, jenis, fasilitas, phone, gambar) VALUES(" +
                                        "'" + iNamaKost.getText().toString() + "'," +
                                        "'" + iAlamat.getText().toString() + "'," +
                                        "'" + iJenis.getText().toString() + "'," +
                                        "'" + iFasilitas.getText().toString() + "'," +
                                        "'" + iPhone.getText().toString() + "'," +
                                        "'" + gambar + "')");
                        Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        config.getWritableDatabase().execSQL(
                                "UPDATE tbl_kost SET " +
                                        "kost = '" + iNamaKost.getText().toString() + "'," +
                                        "alamat = '" + iAlamat.getText().toString() + "'," +
                                        "jenis = '" + iJenis.getText().toString() + "'," +
                                        "fasilitas = '" + iFasilitas.getText().toString() + "'," +
                                        "phone = '" + iPhone.getText().toString() + "', " +
                                        "gambar = '" + gambar + "' " +
                                        "WHERE id = " + user);

                        Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else
                    new Tambah().ToastShort("Silahkan isi semua dengan benar", getApplicationContext());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
        startActivity(intent);
        finish();
    }
}