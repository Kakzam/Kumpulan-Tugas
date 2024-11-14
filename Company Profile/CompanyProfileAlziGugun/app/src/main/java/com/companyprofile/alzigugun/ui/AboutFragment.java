package com.companyprofile.alzigugun.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.companyprofile.alzigugun.R;
import com.companyprofile.alzigugun.another.Preference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
public class AboutFragment extends Fragment {

    View view;
    Context context;
    TextView textTitle, textPhone, textEmail, textSosmed1, textSosmed2, textSosmed3, textAddess;
    EditText editTitle, editPhone, editEmail, editSosmed1, editSosmed2, editSosmed3, editAddess;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        context = getContext();
        setInisialisasi(view);
        return view;
    }

    private void setInisialisasi(View view) {
        if (new Preference(context).getLogin()) {
            view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
            editTitle = view.findViewById(R.id.fa_title);
            editPhone = view.findViewById(R.id.fa_telephone);
            editEmail = view.findViewById(R.id.fa_email);
            editSosmed1 = view.findViewById(R.id.fa_sosmed_1);
            editSosmed2 = view.findViewById(R.id.fa_sosmed_2);
            editSosmed3 = view.findViewById(R.id.fa_sosmed3);
            editAddess = view.findViewById(R.id.fa_alamat);
        }

        view.findViewById(R.id.fa_icon).setOnClickListener(view1 -> {
            new Preference(context).setLogin(true);
            view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
        });

        view.findViewById(R.id.edit).setOnClickListener(view12 -> {
            view.findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
            editTitle.setText(textTitle.getText().toString());
            editPhone.setText(textPhone.getText().toString());
            editEmail.setText(textEmail.getText().toString());
            editSosmed1.setText(textSosmed1.getText().toString());
            editSosmed2.setText(textSosmed2.getText().toString());
            editSosmed3.setText(textSosmed3.getText().toString());
            editAddess.setText(textAddess.getText().toString());
        });

        textTitle = view.findViewById(R.id.title);
        textPhone = view.findViewById(R.id.phone);
        textEmail = view.findViewById(R.id.email);
        textSosmed1 = view.findViewById(R.id.sosmed1);
        textSosmed2 = view.findViewById(R.id.sosmed2);
        textSosmed3 = view.findViewById(R.id.sosmed3);
        textAddess = view.findViewById(R.id.alamat);

        view.findViewById(R.id.edit_save).setOnClickListener(view1 -> {
            view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put("tit", editTitle.getText().toString());
            map.put("pho", editPhone.getText().toString());
            map.put("ema", editEmail.getText().toString());
            map.put("so1", editSosmed1.getText().toString());
            map.put("so2", editSosmed2.getText().toString());
            map.put("so3", editSosmed3.getText().toString());
            map.put("add", editAddess.getText().toString());
            db.collection("alzi").document("tentang").set(map).addOnCompleteListener(task -> {
                view.findViewById(R.id.progress).setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.ll_edit).setVisibility(View.GONE);
                    setLoadData();
                    new AlertDialog.Builder(context).setMessage("Tentang berhasil diubah!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }).create().show();
                } else
                    new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }).addOnFailureListener(e1 -> {
                view.findViewById(R.id.progress).setVisibility(View.GONE);
                new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            });
        });

        setLoadData();
    }

    private void setLoadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("alzi").document("tentang").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    textTitle.setText(task.getResult().get("tit").toString());
                    textPhone.setText(task.getResult().get("pho").toString());
                    textAddess.setText(task.getResult().get("add").toString());
                    textSosmed1.setText(task.getResult().get("so1").toString());
                    textEmail.setText(task.getResult().get("ema").toString());

                    if (task.getResult().get("so2").toString().isEmpty()){
                        textSosmed2.setVisibility(View.GONE);
                        textSosmed3.setVisibility(View.GONE);
                    } else if (task.getResult().get("so3").toString().isEmpty()){
                        textSosmed2.setText(task.getResult().get("so2").toString());
                        textSosmed3.setVisibility(View.GONE);
                    } else {
                        textSosmed2.setText(task.getResult().get("so2").toString());
                        textSosmed3.setText(task.getResult().get("so3").toString());
                    }
                } catch (NullPointerException e) {
                    view.findViewById(R.id.edit).setVisibility(View.GONE);
                    view.findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
                }
            } else new AlertDialog.Builder(context)
                    .setMessage("Periksa koneksi anda.")
                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();
        });
    }
}