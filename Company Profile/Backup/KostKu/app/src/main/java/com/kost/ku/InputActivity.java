package com.kost.ku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputActivity extends AppCompatActivity {

    TextInputEditText iNamaKost, iAlamat, iJenis, iFasilitas, iPhone;
    String user = "EMPTY";
    String gambar = "";

    ActivityResultLauncher<Intent> coba = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        TextView textView = findViewById(R.id.gambar);
                        try {
                            textView.setText("Terpilih");
                            textView.setBackground(getDrawable(R.drawable.background_b));
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
        setContentView(R.layout.activity_input);

        iNamaKost = findViewById(R.id.nama_kost);
        iAlamat = findViewById(R.id.alamat);
        iJenis = findViewById(R.id.jenis);
        iFasilitas = findViewById(R.id.fasilitas);
        iPhone = findViewById(R.id.phone);

        try {
            if (!getIntent().getStringExtra("nama").isEmpty()) {
                iNamaKost.setText(getIntent().getStringExtra("nama"));
                iAlamat.setText(getIntent().getStringExtra("alamat"));
                iJenis.setText(getIntent().getStringExtra("jenis"));
                iFasilitas.setText(getIntent().getStringExtra("fasilitas"));
                iPhone.setText(getIntent().getStringExtra("phone"));
                gambar = getIntent().getStringExtra("gambar");
                user = getIntent().getStringExtra("user");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        findViewById(R.id.gambar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                coba.launch(Intent.createChooser(intent, "Coba"));
            }
        });

        findViewById(R.id.simpan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        !iNamaKost.getText().toString().isEmpty()
                                || !iAlamat.getText().toString().isEmpty()
                                || !iJenis.getText().toString().isEmpty()
                                || !iFasilitas.getText().toString().isEmpty()
                                || !iPhone.getText().toString().isEmpty()
                                || !gambar.isEmpty()
                ) {
                    Map<String, String> map = new HashMap<>();
                    map.put("kost", iNamaKost.getText().toString());
                    map.put("alamat", iAlamat.getText().toString());
                    map.put("jenis", iJenis.getText().toString());
                    map.put("fasilitas", iFasilitas.getText().toString());
                    map.put("phone", iPhone.getText().toString());
                    map.put("gambar", gambar);

                    if (user.equals("EMPTY")) {
                        FirebaseFirestore.getInstance()
                                .collection("local")
                                .document("kost")
                                .collection("space")
                                .add(map)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else
                                            new Tambah().ToastShort("Silahkan periksa kembali jaringan anda", getApplicationContext());
                                    }
                                });
                    } else {
                        FirebaseFirestore.getInstance()
                                .collection("local")
                                .document("kost")
                                .collection("space")
                                .document(user)
                                .set(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else
                                            new Tambah().ToastShort("Silahkan periksa kembali jaringan anda", getApplicationContext());
                                    }
                                });
                    }

                } else
                    new Tambah().ToastShort("Silahkan isi semua dengan benar", getApplicationContext());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
        startActivity(intent);
        finish();
    }
}