package com.companyprofile.alzigugun.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.companyprofile.alzigugun.MainActivity;
import com.companyprofile.alzigugun.R;
import com.companyprofile.alzigugun.another.Preference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkFragment extends Fragment {

    View view;
    int secret = 0;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_work, container, false);

        view.findViewById(R.id.text_notifications).setOnClickListener(view -> {
            secret++;
            if (secret > 10){
                if (new Preference(getContext()).getLogin()){
                    secret = 0;
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.dialog_repassword);
                    EditText edit1 = dialog.findViewById(R.id.edit_1);
                    EditText edit2 = dialog.findViewById(R.id.edit_2);
                    Button button = dialog.findViewById(R.id.edit);
                    button.setText("SIMPAN");
                    button.setOnClickListener(view1 -> {
                        if (edit1.getText().toString().isEmpty() || edit2.getText().toString().isEmpty()){
                            new AlertDialog.Builder(getContext())
                                    .setMessage("Input semua pin, tidak boleh kosong")
                                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                    .create()
                                    .show();
                        } else if (!edit1.getText().toString().isEmpty() || !edit2.getText().toString().isEmpty()) {
                            Map<String, String> map = new HashMap<>();
                            map.put("pp", edit1.getText().toString());
                            db.collection("alzi").document("Verifikasi").set(map).addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    dialog.dismiss();
                                    new AlertDialog.Builder(getContext())
                                            .setMessage("Pin Berhasil diubah")
                                            .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                            .create()
                                            .show();
                                } else new AlertDialog.Builder(getContext())
                                        .setMessage("Periksa koneksi anda.")
                                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                        .create()
                                        .show();
                            });
                        }
                    });

                    dialog.show();
                } else {
                    secret = 0;
                    //input pin
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("alzi").document("Verifikasi").get().addOnCompleteListener(task -> {
                        Dialog dialog = new Dialog(getContext());
                        if (task.isSuccessful()){
                            try {
                                if (task.getResult().get("pp").toString().isEmpty()){
                                    new AlertDialog.Builder(getContext())
                                            .setMessage("Periksa koneksi anda.")
                                            .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                            .create()
                                            .show();
                                } else {
                                    dialog.setContentView(R.layout.dialog_repassword);
                                    dialog.findViewById(R.id.title).setVisibility(View.GONE);
                                    dialog.findViewById(R.id.edit_1).setVisibility(View.GONE);
                                    EditText edit2 = dialog.findViewById(R.id.edit_2);
                                    edit2.setHint("Pin");
                                    Button button = dialog.findViewById(R.id.edit);
                                    button.setText("Check");
                                    button.setOnClickListener(view12 -> {
                                        if (edit2.getText().toString().equals(task.getResult().get("pp").toString())){
                                            new Preference(getContext()).setLogin(true);
                                            dialog.dismiss();
                                            startActivity(new Intent(getContext(), MainActivity.class));
                                            getActivity().finish();
                                        }
                                    });
                                    dialog.show();
                                }
                            } catch (NullPointerException e){
                                dialog.dismiss();
                                Dialog dialogRe = new Dialog(getContext());
                                dialogRe.setContentView(R.layout.dialog_repassword);
                                EditText edit1 = dialogRe.findViewById(R.id.edit_1);
                                EditText edit2 = dialogRe.findViewById(R.id.edit_2);
                                Button button = dialogRe.findViewById(R.id.edit);
                                button.setText("SIMPAN");
                                button.setOnClickListener(view1 -> {
                                    if (edit1.getText().toString().isEmpty() || edit2.getText().toString().isEmpty()){
                                        new AlertDialog.Builder(getContext())
                                                .setMessage("Input semua pin, tidak boleh kosong")
                                                .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                                .create()
                                                .show();
                                    } else if (!edit1.getText().toString().isEmpty() || !edit2.getText().toString().isEmpty()) {
                                        Map<String, String> map = new HashMap<>();
                                        map.put("pp", edit1.getText().toString());
                                        db.collection("alzi").document("Verifikasi").set(map).addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()){
                                                dialogRe.dismiss();
                                                new AlertDialog.Builder(getContext())
                                                        .setMessage("Pin Berhasil diubah")
                                                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                                        .create()
                                                        .show();
                                            } else new AlertDialog.Builder(getContext())
                                                    .setMessage("Periksa koneksi anda.")
                                                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                                    .create()
                                                    .show();
                                        });
                                    }
                                });

                                dialogRe.show();
                            }
                        } else new AlertDialog.Builder(getContext())
                                .setMessage("Periksa koneksi anda.")
                                .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                                .create()
                                .show();
                    });
                }
            }
        });
        return view;
    }
}