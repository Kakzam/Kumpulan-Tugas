package com.teknokrat.niskala.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.teknokrat.niskala.databinding.ActivityInputBinding;
import com.teknokrat.niskala.dll.Base;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputActivity extends Base {

    private ActivityInputBinding binding;
    private static final int PICK_IMAGE_REQUEST = 1;
    String base64Image;
    private ActivityResultLauncher<String> launcher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                if (result != null) {
                    try {
                        // Mengambil gambar dari URI
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result);
//
//                        // Menampilkan gambar di ImageView
//                        imageView.setImageBitmap(bitmap);

                        // Mengubah gambar menjadi base64
                        base64Image = convertBitmapToBase64(bitmap);

                        // Melakukan operasi lain dengan gambar
                        // ...
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInputBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonGambar.setOnClickListener(view -> launcher.launch("image/*"));
        binding.buttonSimpan.setOnClickListener(view -> {
            if (binding.inputTitle.getText().toString().isEmpty()) {
                setToast("Title tidak boleh kosong");
            }
//            else if (binding.inputDeskripsi.getText().toString().isEmpty()) {
//                setToast("Deskripsi tidak boleh kosong");
//            }
            else if (base64Image.isEmpty()) {
                setToast("Silahkan pilih gambar terlebih dahulu");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("gambar", base64Image);
                data.put("title", binding.inputTitle.getText().toString());
                data.put("deskripsi", binding.inputTitle.getText().toString() + " adalah ruangan yang sangat besar, dapat menampung 100 pengunjung, dan memiliki koneksi internet yang sangat cepat, kecepatan koneksi internetnya adalah 1Gb/ Detik");
//                data.put("deskripsi", binding.inputDeskripsi.getText().toString());

                db.collection("niskala").document("ruangan").collection("-").add(data)
                        .addOnSuccessListener(documentReference -> {
                            setToast("Ruangan Berhasil Disimpan");
                        })
                        .addOnFailureListener(e -> setToast("Registrasi Gagal, Silahkan Periksa Kembali Koneksi Internet Anda"));
            }
        });
    }

    public String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}