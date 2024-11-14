package com.ayamgeprek.agh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.ayamgeprek.agh.databinding.DialogSelectBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DialogSelect extends BottomSheetDialogFragment {

    public static String VERY = "VERY";
    public static String DIALOG = "DIALOG";

    public static String PENDAFTARAN = "PENDAFTARAN";
    public static String ALL_NAME = "ALL_NAME";
    public static String USER = "USER";
    public static String SANDI = "SANDI";
    public static String NUMBER = "NUMBER";

    public static DialogSelect newInstance(String very, String dialog) {
        final DialogSelect fragment = new DialogSelect();
        final Bundle args = new Bundle();
        args.putString(VERY, very);
        args.putString(DIALOG, dialog);
        fragment.setArguments(args);
        return fragment;
    }

    DialogSelectBinding dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dialog = DialogSelectBinding.inflate(inflater, container, false);
        return dialog.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FirebaseFirestore data = FirebaseFirestore.getInstance();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (getArguments().getString(DIALOG).equals("")) {
            view.findViewById(R.id.dialog_select_2).setVisibility(View.GONE);
            view.findViewById(R.id.dialog_select_all_name_hide).setVisibility(View.GONE);
            view.findViewById(R.id.dialog_select_all_phone_hide).setVisibility(View.GONE);
        } else view.findViewById(R.id.dialog_select_1).setVisibility(View.GONE);

        view.findViewById(R.id.dialog_select_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog.dialogSelectUsername.getText().toString().isEmpty()
                        && !dialog.dialogSelectPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Proses pengecekan", Toast.LENGTH_SHORT).show();
                    data.collection(PENDAFTARAN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot data : task.getResult()) {
                                    if (dialog.dialogSelectUsername.getText().toString().equals(data.getString(USER))
                                            && dialog.dialogSelectPassword.getText().toString().equals(data.getString(SANDI))) {
                                        Intent intent;
                                        editor.putString("ID", data.getId()).apply();
                                        if (data.get(VERY).toString().equals("pelanggan")) {
                                            intent = new Intent(getContext(), MainActivityPengguna.class);
                                            startActivity(intent);
                                        }
                                        if (data.get(VERY).toString().equals("pemilik")) {
                                            intent = new Intent(getContext(), MainActivityPemilik.class);
                                            startActivity(intent);
                                        }
                                        getActivity().finish();
                                    }
                                }
                            } else
                                Toast.makeText(getContext(), "Koneksimu jelek banget", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(getContext(), "Isi semua dong", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.dialog_select_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dialog.dialogSelectAllName.getText().toString().isEmpty()
                        && !dialog.dialogSelectPhone.getText().toString().isEmpty()
                        && !dialog.dialogSelectUsername.getText().toString().isEmpty()
                        && !dialog.dialogSelectPassword.getText().toString().isEmpty()) {

                    Map<String, String> daftar = new HashMap<>();
                    daftar.put(ALL_NAME, dialog.dialogSelectAllName.getText().toString());
                    daftar.put(NUMBER, dialog.dialogSelectPhone.getText().toString());
                    daftar.put(USER, dialog.dialogSelectUsername.getText().toString());
                    daftar.put(SANDI, dialog.dialogSelectPassword.getText().toString());
                    daftar.put(VERY, getArguments().getString(VERY));

                    Toast.makeText(getContext(), "Menambahkan Pengguna", Toast.LENGTH_SHORT).show();
                    data.collection(PENDAFTARAN).add(daftar).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Intent intent;

                                editor.putString("ID", task.getResult().getId()).apply();
                                if (getArguments().getString(VERY).equals("pemilik")) {
                                    intent = new Intent(getContext(), MainActivityPemilik.class);
                                    startActivity(intent);
                                } else {
                                    intent = new Intent(getContext(), MainActivityPengguna.class);
                                    startActivity(intent);
                                }

                                getActivity().finish();
                            } else
                                Toast.makeText(getContext(), "Koneksimu jelek banget", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else
                    Toast.makeText(getContext(), "Isi semua dong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dialog = null;
    }
}