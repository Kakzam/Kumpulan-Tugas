package com.flow.giftforyou.ui.ubah;

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

import com.flow.giftforyou.BucketActivity;
import com.flow.giftforyou.R;
import com.flow.giftforyou.ui.DBConfig;
import com.flow.giftforyou.ui.UtilString;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddBucketActivity extends AppCompatActivity {

    TextInputEditText ietJudul, ietDeskripsi, ietWa;
    String gambar = "";
    DBConfig config;
    ActivityResultLauncher<Intent> bucket = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
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
        setContentView(R.layout.activity_add_bucket);
        config = new DBConfig(this, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
        ietJudul = findViewById(R.id.activity_add_nama);
        ietDeskripsi = findViewById(R.id.activity_add_deskripsi);
        ietWa = findViewById(R.id.activity_add_wa);

        findViewById(R.id.activity_add_bucket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                bucket.launch(Intent.createChooser(intent, "Pilih Gambar"));
            }
        });

        findViewById(R.id.activity_add_tombol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ietJudul.getText().toString().isEmpty()
                        && !ietDeskripsi.getText().toString().isEmpty()
                        && !gambar.isEmpty()
                        && !ietWa.getText().toString().isEmpty()) {
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_buket (judul, deskripsi, wa, jpeg, buket) VALUES(" +
                                    "'" + ietJudul.getText().toString() + "'," +
                                    "'" + ietDeskripsi.getText().toString() + "'," +
                                    "'" + "+628" + ietWa.getText().toString() + "'," +
                                    "'" + gambar + "'," +
                                    "'" + getIntent().getStringExtra(UtilString.BUCKET) + "')");
                    startActivity(new Intent(getApplicationContext(), BucketActivity.class).putExtra(UtilString.BUCKET, getIntent().getStringExtra(UtilString.BUCKET)).putExtra(UtilString.LOGIN, getIntent().getStringExtra(UtilString.LOGIN)));
                    finish();
                } else
                    Toast.makeText(AddBucketActivity.this, "Isi semua tong, jangan gak di isi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}