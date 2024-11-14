package com.eventic.lampungtourism;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eventic.lampungtourism.databinding.ActivityAddEventBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {

    private ActivityAddEventBinding binding;
    FirebaseFirestore firestore;
    List<String> gambarTourism = new ArrayList<>();
    Boolean gambarButton = false;
    DBConfig config;

    ActivityResultLauncher<Intent> tourism = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            gambarTourism.add(Base64.encodeToString(b, Base64.DEFAULT));
                            if (gambarButton) {
                                binding.buttonGambarTourism.setText("Gambar Terpilih");
                                binding.buttonGambarTourism.setBackgroundResource(R.drawable.ic_dashboard_black_24dp);
                            } else {
                                binding.buttonGambarTourism2.setText("Gambar Terpilih");
                                binding.buttonGambarTourism2.setBackgroundResource(R.drawable.ic_dashboard_black_24dp);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEventBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        config = new DBConfig(this, DBConfig.EVENTIC_LAMPUNG_TOURISM, null, DBConfig.DB_VERSION);
        firestore = FirebaseFirestore.getInstance();

        binding.buttonGambarTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gambarButton = false;
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                tourism.launch(Intent.createChooser(intent, "Gambar"));
            }
        });

        binding.buttonGambarTourism2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gambarButton = true;
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                tourism.launch(Intent.createChooser(intent, "Gambar"));
            }
        });

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.getDescription.getText().toString().isEmpty()
                        && !binding.getJudul.getText().toString().isEmpty()
                        && !binding.getPrice.getText().toString().isEmpty()
                        && !binding.getType.getText().toString().isEmpty()
                        && gambarTourism.size() > 1) {

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_user (judul,description,price,type,image,image2) VALUES(" +
                                    "'" + binding.getJudul.getText().toString() + "'," +
                                    "'" + binding.getDescription.getText().toString() + "'," +
                                    "'" + binding.getPrice.getText().toString() + "'," +
                                    "'" + binding.getType.getText().toString() + "'," +
                                    "'" + gambarTourism.get(0) + "'," +
                                    "'" + gambarTourism.get(0) + "'," +
                                    "')");

                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                    finish();

//                    Map<String, String> addEvent = new HashMap<>();
//                    addEvent.put("judul", binding.getJudul.getText().toString());
//                    addEvent.put("description", binding.getDescription.getText().toString());
//                    addEvent.put("price", binding.getPrice.getText().toString());
//                    addEvent.put("type", binding.getType.getText().toString());
//                    addEvent.put("image", gambarTourism.get(0));
//                    addEvent.put("image2", gambarTourism.get(1));
//                    firestore.collection("tourism").add(addEvent).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            if (task.isSuccessful()) {
//                                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
//                                startActivity(intent);
//                                finish();
//                            } else
//                                Toast.makeText(AddEventActivity.this, "Silahkan periksa koneksi anda", Toast.LENGTH_SHORT).show();
//                        }
//                    });
                } else
                    Toast.makeText(AddEventActivity.this, "Silahkan isi semua dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivity(intent);
        finish();
    }
}