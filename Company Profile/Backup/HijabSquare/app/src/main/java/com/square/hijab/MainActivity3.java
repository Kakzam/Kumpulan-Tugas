package com.square.hijab;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.square.hijab.ui.Adapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity3 extends AppCompatActivity {

    List<String> gamabarJilbab = new ArrayList<>();
    EditText tNama, tHarga;
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
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

                Map<String, String> jilbab = new HashMap<>();
                jilbab.put(Adapter.NAMA_JILBAB, tNama.getText().toString());
                jilbab.put(Adapter.HARGA, tHarga.getText().toString());

                fs.collection(Adapter.JILBAB).add(jilbab).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            if (gamabarJilbab.size() > 0) {
                                for (String data : gamabarJilbab) {
                                    Map<String, String> gambarJilbab = new HashMap<>();
                                    gambarJilbab.put(Adapter.GAMBAR, data);
                                    fs.collection(Adapter.JILBAB).document(task.getResult().getId()).collection(Adapter.GAMBAR).add(gambarJilbab);
                                }

                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });

            }
        });

    }
}