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
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class DaftarActivity extends AppCompatActivity {

    String login = UtilString.LOGIN;
    TextInputEditText ieUser, ieSandi, ieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        ieName = findViewById(R.id.name);
        ieSandi = findViewById(R.id.sandi);
        ieUser = findViewById(R.id.id);

        findViewById(R.id.tombol_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MasukActivity.class));
                finish();
            }
        });

        findViewById(R.id.tombol_1).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                login = UtilString.UBAH;
                return false;
            }
        });

        findViewById(R.id.tombol).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ieName.getText().toString().isEmpty()
                        && !ieSandi.getText().toString().isEmpty()
                        && !ieUser.getText().toString().isEmpty()) {
                    Map<String, String> value = new HashMap<>();
                    value.put(UtilString.USERNAME, ieUser.getText().toString());
                    value.put(UtilString.PASSWORD, ieSandi.getText().toString());
                    value.put(UtilString.NAMA, ieName.getText().toString());
                    value.put(UtilString.LOGIN, login);
                    Toast.makeText(DaftarActivity.this, "Melakukan Proses", Toast.LENGTH_SHORT).show();
                    new UtilString().e().collection(UtilString.PENDAFTARAN).add(value).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Intent intent;
                                if (login.equals(UtilString.UBAH)) {
                                    intent = new Intent(getApplicationContext(), MainActivity2.class);
                                    startActivity(intent);
                                } else {
                                    intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }
                                finish();
                            } else
                                Toast.makeText(DaftarActivity.this, "Masalah jaringan terdeteksi", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(DaftarActivity.this, "Silahkan periksa semua data sudah diisi dengan benar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}