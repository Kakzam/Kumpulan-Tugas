package com.kost.ku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText iUsername, iPassword;
    TextView text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text1 = findViewById(R.id.tombol_1);
        text2 = findViewById(R.id.tombol_2);
        iUsername = findViewById(R.id.username);
        iPassword = findViewById(R.id.password);

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().collection("local")
                        .document("user")
                        .collection("space")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot d : task.getResult()) {
                                        if (iUsername.getText().toString().equals(d.get("username").toString()) &&
                                                iPassword.getText().toString().equals(d.get("password").toString())
                                        ) {
                                            if (d.get("select").toString().equals("pengguna")) {
                                                Intent intent = new Intent(getApplicationContext(), KostActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Intent intent = new Intent(getApplicationContext(), KostInputActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                } else {
                                    new Tambah().ToastLong("Silahkan periksa koneksi anda.", getApplicationContext());
                                }
                            }
                        });
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}