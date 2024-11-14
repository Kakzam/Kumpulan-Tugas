package com.faz.catering;

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
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity {

    EditText cateringNama, cateringDeskripsi, cateringHarga, cateringGambar, cateringHandphone, cateringLokasi;
    String cateringMakanan = "";
    String data = "";

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

                    Map<String, String> catering = new HashMap<>();
                    catering.put("nama", cateringNama.getText().toString());
                    catering.put("deskripsi", cateringDeskripsi.getText().toString());
                    catering.put("harga", cateringHarga.getText().toString());
                    catering.put("gambar", cateringMakanan);
                    catering.put("handphone", cateringHandphone.getText().toString());
                    catering.put("lokasi", cateringLokasi.getText().toString());

                    FirebaseFirestore.getInstance().collection(data).add(catering).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Intent intent = new Intent(getApplicationContext(), MainActivityCatering.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                } else Log.v("TambahActivity", "Data ada yang kosong");
            }
        });
    }
}