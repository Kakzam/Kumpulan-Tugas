package com.anak.anakkost;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity {

    EditText judul, harga, deskripsi, lokasi, gambar, upload;
    String kost = "";
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

        FirebaseFirestore cloud = FirebaseFirestore.getInstance();
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
                if (!judul.getText().toString().isEmpty() && !harga.getText().toString().isEmpty() && !deskripsi.getText().toString().isEmpty() && !lokasi.getText().toString().isEmpty() && !kost.isEmpty()) {
                    Map<String, String> has = new HashMap<>();
                    has.put("k1", judul.getText().toString());
                    has.put("k2", harga.getText().toString());
                    has.put("k3", deskripsi.getText().toString());
                    has.put("k4", lokasi.getText().toString());
                    has.put("k5", kost);
                    cloud.collection(getIntent().getStringExtra("COLLECTION")).add(has).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(intent);
                                finish();
                            } else
                                Toast.makeText(TambahActivity.this, "Koneksimu jelek banget", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(TambahActivity.this, "Data gagal di upload, karena koneksimu jelek", Toast.LENGTH_SHORT).show();
            }
        });
    }
}