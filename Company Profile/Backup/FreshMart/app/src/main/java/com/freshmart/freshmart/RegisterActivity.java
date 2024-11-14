package com.freshmart.freshmart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextView login, daftar;
    TextInputEditText nama, noHandphone, username, password;
    ProgressBar progressBar;
    int posisi = 1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.login);
        daftar = findViewById(R.id.daftar);
        nama = findViewById(R.id.nama_lengkap);
        noHandphone = findViewById(R.id.no_handphone);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressbar);

        login.setOnClickListener(view -> {
            nama.setText("");
            noHandphone.setText("");
            username.setText("");
            password.setText("");

            if (posisi > 0) {
                daftar.setText("LOGIN");
                login.setText("DAFTAR");

                nama.setVisibility(View.GONE);
                noHandphone.setVisibility(View.GONE);
                posisi = 0;
            } else {
                daftar.setText("LOGIN");
                login.setText("DAFTAR");

                posisi = 1;
                nama.setVisibility(View.VISIBLE);
                noHandphone.setVisibility(View.VISIBLE);
            }
        });

        daftar.setOnClickListener(view -> {
            if (posisi > 0) {
                Map<String, String> map = new HashMap<>();
                map.put("nama", nama.getText().toString());
                map.put("handphone", noHandphone.getText().toString());
                map.put("username", username.getText().toString());
                map.put("password", password.getText().toString());
                map.put("type", "-");

                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").add(map).addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        daftar.setText("LOGIN");
                        login.setText("DAFTAR");
                        nama.setText("");
                        noHandphone.setText("");
                        username.setText("");
                        password.setText("");
                        nama.setVisibility(View.GONE);
                        noHandphone.setVisibility(View.GONE);
                        posisi = 0;
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
            } else {
                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").get().addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.get("username").toString().equals(username.getText().toString()) && document.get("password").toString().equals(password.getText().toString())) {
                                if (document.get("type").toString().equals("1"))
                                    new TestPenyimpanan(this).setLogin(true, document.getId());
                                else new TestPenyimpanan(this).setLogin(false, document.getId());
                                Intent i = new Intent(this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
            }
        });
    }
}