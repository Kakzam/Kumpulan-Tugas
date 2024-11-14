package com.ayamgeprek.agh;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahGeprekActivity extends AppCompatActivity {

    TextInputEditText tietEats, tietPrice;
    String base = "";

    public static String EASTS = "EASTS";
    public static String PRICE = "PRICE";
    public static String IMAGE = "IMAGE";

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

        FirebaseFirestore data = FirebaseFirestore.getInstance();
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
                if (
                        !tietEats.getText().toString().isEmpty()
                                && !tietPrice.getText().toString().isEmpty()
                                && !base.isEmpty()) {
                    Map<String, String> geprek = new HashMap<>();
                    geprek.put(EASTS, tietEats.getText().toString());
                    geprek.put(PRICE, tietPrice.getText().toString());
                    geprek.put(IMAGE, base);

                    data.collection(EASTS).add(geprek).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), MainActivityPemilik.class);
                                startActivity(intent);
                                finish();
                            } else
                                Toast.makeText(TambahGeprekActivity.this, "Koneksimu jelek, coba ulang", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else
                    Toast.makeText(TambahGeprekActivity.this, "Isi semua dulu dong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}