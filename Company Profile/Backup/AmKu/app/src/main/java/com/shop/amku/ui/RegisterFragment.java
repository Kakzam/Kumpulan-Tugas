package com.shop.amku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.amku.DashboardActivity;
import com.shop.amku.databinding.FragmentRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding register;

    public static String REGISTER = "REGISTER";

    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String ALL_NAME = "ALL_NAME";
    public static String PHONE_NUMBER = "PHONE_NUMBER";
    public static String PERMISSION = "PERMISSION";

    String permission = "customer";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        register = FragmentRegisterBinding.inflate(getLayoutInflater());
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        register.permission.setOnLongClickListener(view -> {
            permission = "admin";
            return false;
        });

        register.button.setOnClickListener(view -> {
            if (
                    !register.username.getText().toString().isEmpty()
                            && !register.password.getText().toString().isEmpty()
                            && !register.nama.getText().toString().isEmpty()
                            && !register.hp.getText().toString().isEmpty()
            ) {
                register.bar.setVisibility(View.VISIBLE);
                Map<String, String> daftar = new HashMap<>();
                daftar.put(USERNAME, register.username.getText().toString());
                daftar.put(PASSWORD, register.password.getText().toString());
                daftar.put(ALL_NAME, register.nama.getText().toString());
                daftar.put(PHONE_NUMBER, register.hp.getText().toString());
                daftar.put(PERMISSION, permission);
                firestore.collection(REGISTER).add(daftar).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        register.bar.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Intent intent = new Intent(getContext(), DashboardActivity.class);
                            intent.putExtra(DashboardActivity.ID, task.getResult().getId());
                            intent.putExtra(DashboardActivity.PERMISSION, permission);
                            startActivity(intent);
                            getActivity().finish();
                        } else Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Silahkan isi semua dengan benar.", Toast.LENGTH_SHORT).show();
            }
        });

        return register.getRoot();
    }
}