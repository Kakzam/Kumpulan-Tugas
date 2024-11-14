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

public class HistoryFragment extends Fragment {

    View view;
    Context context;

    EditText editHistory;
    TextView textHistory;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        context = getContext();
        settingsInisialisasi();
        return view;
    }

    private void settingsInisialisasi() {
        if (new Preference(context).getLogin())
            view.findViewById(R.id.edit).setVisibility(View.VISIBLE);

        editHistory = view.findViewById(R.id.fh_history);
        textHistory = view.findViewById(R.id.text_history);

        view.findViewById(R.id.text_title).setOnClickListener(view -> {

        });

        view.findViewById(R.id.edit).setOnClickListener(view -> {
            view.findViewById(R.id.ll_edit).setVisibility(View.VISIBLE);
        });

        view.findViewById(R.id.edit_save).setOnClickListener(view1 -> {
            if (editHistory.getText().toString().isEmpty()){
                new AlertDialog.Builder(context)
                        .setMessage("Isi sejarah dengan benar")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            } else {
                view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, String> map = new HashMap<>();
                map.put("isi", editHistory.getText().toString());
                db.collection("alzi").document("sejarah").set(map).addOnCompleteListener(task -> {
                    view.findViewById(R.id.progress).setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        view.findViewById(R.id.edit).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.ll_edit).setVisibility(View.GONE);
                        settingsLoad();
                        new AlertDialog.Builder(context).setMessage("Sejarah berhasil diubah!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        }).create().show();
                    } else
                        new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                }).addOnFailureListener(e1 -> {
                    view.findViewById(R.id.progress).setVisibility(View.GONE);
                    new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                });
            }
        });

        settingsLoad();
    }

    private void settingsLoad() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("alzi").document("sejarah").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                try {
                    textHistory.setText(task.getResult().get("isi").toString());
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