package com.flow.giftforyou.ui.ubah;

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
import android.widget.Toast;

import com.flow.giftforyou.BucketActivity;
import com.flow.giftforyou.R;
import com.flow.giftforyou.ui.UtilString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddBucketActivity extends AppCompatActivity {

    TextInputEditText ietJudul, ietDeskripsi, ietWa;
    String collection, gambar = "";
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

        collection = getIntent().getStringExtra(UtilString.BUCKET);
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
                    Map<String, String> bucket = new HashMap<>();
                    bucket.put(UtilString.JUDUL, ietJudul.getText().toString());
                    bucket.put(UtilString.DESKRIPSI, ietDeskripsi.getText().toString());
                    bucket.put(UtilString.WA, "+628"+ ietWa.getText().toString());
                    bucket.put(UtilString.JPEG, gambar);
                    new UtilString().e().collection(collection).add(bucket).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getApplicationContext(), BucketActivity.class).putExtra(UtilString.BUCKET, collection).putExtra(UtilString.LOGIN, getIntent().getStringExtra(UtilString.LOGIN)));
                            } else {
                                Toast.makeText(AddBucketActivity.this, "Koneksi internetmu jelek banget", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else
                    Toast.makeText(AddBucketActivity.this, "Isi semua tong, jangan gak di isi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}