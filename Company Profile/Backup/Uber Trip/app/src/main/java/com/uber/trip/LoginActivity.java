package com.uber.trip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText input_username, input_password;
    TextView textMasuk, textDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Other other = new Other();
        textMasuk = findViewById(R.id.masuk);
        textDaftar = findViewById(R.id.daftar);
        input_password = findViewById(R.id.sandi);
        input_username = findViewById(R.id.hp);

        textDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DaftarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                other.setLoading(LoginActivity.this);
                FirebaseFirestore.getInstance()
                        .collection("collection")
                        .document("user")
                        .collection("collection")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                other.dissmissLoading();
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        if (document.get("password").toString().equals(input_password.getText().toString())
                                                && document.get("username").toString().equals(input_username.getText().toString())
                                        ) {
                                            if (document.get("pilih").equals("administrator")) {
                                                Log.v("ZAM", document.get("username").toString());
                                                Log.v("ZAM", document.get("password").toString());
                                                Log.v("ZAM", document.get("pilih").toString());
                                                Intent intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                                                startActivity(intent);
                                                finish();
                                            }

                                            if (document.get("pilih").equals("bukan")){
                                                Log.v("ZAM", "user");
                                                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                } else {
                                    other.setToast("Silahkan periksa koneksi anda", LoginActivity.this);
                                }
                            }
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}