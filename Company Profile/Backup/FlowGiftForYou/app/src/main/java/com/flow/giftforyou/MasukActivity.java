package com.flow.giftforyou;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flow.giftforyou.ui.UtilString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.QuerySnapshot;

public class MasukActivity extends AppCompatActivity {

    TextInputEditText textId, textSandi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        textId = findViewById(R.id.activity_masuk_id);
        textSandi = findViewById(R.id.activity_masuk_sandi);

        findViewById(R.id.activity_masuk_repassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MasukActivity.this, "Sedang tahap development", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.activity_masuk_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DaftarActivity.class));
                finish();
            }
        });

        findViewById(R.id.activity_masuk_tombol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = textId.getText().toString();
                String sandi = textSandi.getText().toString();

                Toast.makeText(MasukActivity.this, "Sedang diperiksa", Toast.LENGTH_SHORT).show();
                if (!id.isEmpty() && !sandi.isEmpty()) {
                    Toast.makeText(MasukActivity.this, "Sedang melakukan pemeriksaan", Toast.LENGTH_LONG).show();
                    new UtilString().e().collection(UtilString.PENDAFTARAN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                Intent intent;
                                switch (new UtilString().check(task.getResult().getDocuments(), id, sandi)){
                                    case "GAGAL":
                                        Toast.makeText(MasukActivity.this, "Silahkan periksa kembali ID dan Password anda.", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "UBAH":
                                        intent = new Intent(getApplicationContext(), MainActivity2.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                    case "LOGIN":
                                        intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;
                                }
                            } else
                                Toast.makeText(MasukActivity.this, "Masalah jaringan terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(MasukActivity.this, "Pastikan semua field sudah terisi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}