package com.catering.dessert.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.catering.dessert.MainActivity;
import com.catering.dessert.R;
import com.catering.dessert.Storage;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    View view;
    ProgressBar progressBar;
    EditText editPassword;
    TextView textSave, textMessage;
    String password;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        editPassword = view.findViewById(R.id.fragment_register_edit);
        progressBar = view.findViewById(R.id.fragment_register_progressbar);
        textMessage = view.findViewById(R.id.fragment_register_message);

        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            try {
                password = task.getResult().get("password").toString();
                editPassword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        editPassword.removeTextChangedListener(this);
                        if (editPassword.getText().toString().equals(password)) {
                            new Storage(getContext()).setLogin(true);
                        }
                        editPassword.addTextChangedListener(this);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            } catch (NullPointerException e) {
                textMessage.setText("Reset Password");
                textSave = view.findViewById(R.id.fragment_register_button);
                textSave.setVisibility(View.VISIBLE);

                textSave.setOnClickListener(view -> {
                    if (!editPassword.getText().toString().isEmpty()) {
                        progressBar.setVisibility(View.VISIBLE);
                        textMessage.setVisibility(View.VISIBLE);

                        Map<String, String> map = new HashMap<>();
                        map.put("password", editPassword.getText().toString());

                        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").set(map).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                textMessage.setText("Anda berhasil riset password, silahkan beraktifitas");
                                new Storage(getContext()).setLogin(true);
                                textSave.setVisibility(View.GONE);
                            } else textMessage.setText("Silahkan tekan tombol simpan kembali");
                        });
                    } else textMessage.setText("Password anda kosong, silahkan isi dulu");
                });
            }
        });

        return view;
    }

}