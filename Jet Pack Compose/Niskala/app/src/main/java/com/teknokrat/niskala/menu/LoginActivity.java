package com.teknokrat.niskala.menu;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentSnapshot;
import com.teknokrat.niskala.databinding.ActivityLoginBinding;
import com.teknokrat.niskala.dll.Base;

public class LoginActivity extends Base {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");
        int position = 0;
        binding.textForget.setOnClickListener(view -> {
            if (position > 10){
                setIntent(InputActivity.class);
            }
            setToast("Masih dalam proses development");
        });

        binding.textRegister.setOnClickListener(view -> setIntent(RegisterActivity.class));
        binding.buttonLogin.setOnClickListener(view -> {
            if (binding.inputUsername.getText().toString().isEmpty()){
                setToast("Username tidak boleh kosong");
            } else if (binding.inputPassword.getText().toString().isEmpty()){
                setToast("Password tidak boleh kosong");
            } else {
                setToast("Sedang melakukan validasi, harap menunggu");
                db.collection("niskala").document("user").collection("-").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            boolean check = false;
                            String n = "", i = "";
                            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                                if (documentSnapshot.get("username").toString().equals(binding.inputUsername.getText().toString()) && documentSnapshot.get("password").toString().equals(binding.inputPassword.getText().toString())){
                                    check = true;
                                    n = documentSnapshot.get("nama_lengkap").toString();
                                    i = documentSnapshot.getId();
                                }
                            }

                            if (check){
                                setL(true);
                                setN(n);
                                setId(i);
                                setIntent(DashboardActivity.class);
                            } else setToast("Silahkan periksa kembali username dan password anda");
                        })
                        .addOnFailureListener(e -> setToast("Login Gagal, Silahkan Periksa Kembali Koneksi Internet Anda"));
            }
        });
    }
}