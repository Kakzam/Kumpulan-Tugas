package com.shop.amku;

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
import com.shop.amku.other.Loading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TambahActivity extends AppCompatActivity {

    TextInputEditText EDITnama, EDITstatus, EDITharga, EDITdeskripsi, EDITdilihat;

    String photo = "";
    String id = "";
    String menu = "";
    String permission = "";

    public static String CAR = "CAR";
    public static String ACCESSORIES = "ACCESSORIES";

    public static String NAMA = "NAMA";
    public static String STATUS = "STATUS";
    public static String HARGA = "HARGA";
    public static String DESKRIPSI = "DESKRIPSI";
    public static String DILIHAT = "DILIHAT";
    public static String PHOTO = "PHOTO";

    Loading loading = new Loading();
    ActivityResultLauncher<Intent> kamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            photo = Base64.encodeToString(b, Base64.DEFAULT);
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
            id = getIntent().getStringExtra(DashboardActivity.ID);
            menu = getIntent().getStringExtra(DashboardActivity.MENU);
            permission = getIntent().getStringExtra(DashboardActivity.PERMISSION);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        EDITnama = findViewById(R.id.nama);
        EDITstatus = findViewById(R.id.status);
        EDITharga = findViewById(R.id.harga);
        EDITdeskripsi = findViewById(R.id.deskripsi);
        EDITdilihat = findViewById(R.id.dilihat);

        findViewById(R.id.button_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                kamera.launch(Intent.createChooser(intent, "Pahawang"));
            }
        });

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EDITnama.getText().toString().isEmpty()
                        && !EDITstatus.getText().toString().isEmpty()
                        && !EDITharga.getText().toString().isEmpty()
                        && !EDITdeskripsi.getText().toString().isEmpty()
                        && !EDITdilihat.getText().toString().isEmpty()
                        && !photo.isEmpty()) {
                    loading.setLoading(TambahActivity.this);
                    Map<String, String> tambah = new HashMap<>();
                    tambah.put(NAMA, EDITnama.getText().toString());
                    tambah.put(STATUS, EDITstatus.getText().toString());
                    tambah.put(HARGA, EDITharga.getText().toString());
                    tambah.put(DESKRIPSI, EDITdeskripsi.getText().toString());
                    tambah.put(DILIHAT, EDITdilihat.getText().toString());
                    tambah.put(PHOTO, photo);
                    firestore.collection(menu).add(tambah).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            loading.dissmissLoading();
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                                intent.putExtra(DashboardActivity.MENU, menu);
                                intent.putExtra(DashboardActivity.ID, id);
                                intent.putExtra(DashboardActivity.PERMISSION, permission);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(TambahActivity.this, "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else
                    Toast.makeText(TambahActivity.this, "Silahkan isi semua dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
        intent.putExtra(DashboardActivity.MENU, menu);
        intent.putExtra(DashboardActivity.ID, id);
        intent.putExtra(DashboardActivity.PERMISSION, permission);
        startActivity(intent);
        finish();
    }
}