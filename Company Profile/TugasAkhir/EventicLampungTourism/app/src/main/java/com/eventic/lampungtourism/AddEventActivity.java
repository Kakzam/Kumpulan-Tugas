package com.eventic.lampungtourism;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.eventic.lampungtourism.databinding.ActivityAddEventBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private ActivityAddEventBinding binding;
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
                            "INSERT INTO tbl_event (judul,description,price,type,image,image2) VALUES(" +
                                    "'" + binding.getJudul.getText().toString() + "'," +
                                    "'" + binding.getDescription.getText().toString() + "'," +
                                    "'" + binding.getPrice.getText().toString() + "'," +
                                    "'" + binding.getType.getText().toString() + "'," +
                                    "'" + gambarTourism.get(0) + "'," +
                                    "'" + gambarTourism.get(1) + "')");

                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                    finish();
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