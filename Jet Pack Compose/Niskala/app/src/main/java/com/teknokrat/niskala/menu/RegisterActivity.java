package com.teknokrat.niskala.menu;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.teknokrat.niskala.databinding.ActivityRegisterBinding;
import com.teknokrat.niskala.dll.Base;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Base {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");
        binding.textForget.setOnClickListener(view -> setToast("Masih dalam proses development"));
        binding.textRegister.setOnClickListener(view -> {
            setIntent(LoginActivity.class);
        });
        binding.buttonLogin.setOnClickListener(view -> {
            if (binding.inputNamaLengkap.getText().toString().isEmpty()) {
                setToast("Nama Lengkap tidak boleh kosong");
            } else if (binding.inputUsername.getText().toString().isEmpty()) {
                setToast("Username tidak boleh kosong");
            } else if (binding.inputPassword.getText().toString().isEmpty()) {
                setToast("Password tidak boleh kosong");
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("nama_lengkap", binding.inputNamaLengkap.getText().toString());
                data.put("username", binding.inputUsername.getText().toString());
                data.put("password", binding.inputPassword.getText().toString());

                db.collection("niskala").document("user").collection("-").add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                setToast("Registrasi Berhasil");
                                setL(true);
                                setN(binding.inputNamaLengkap.getText().toString());
                                setId(documentReference.getId());
                                setIntent(DashboardActivity.class);
                            }
                        })
                        .addOnFailureListener(e -> setToast("Registrasi Gagal, Silahkan Periksa Kembali Koneksi Internet Anda"));
            }
        });
    }
}