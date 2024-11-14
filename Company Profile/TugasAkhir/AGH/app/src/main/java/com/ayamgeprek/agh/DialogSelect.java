package com.ayamgeprek.agh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.ayamgeprek.agh.databinding.DialogSelectBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DialogSelect extends BottomSheetDialogFragment {

    public static String VERY = "VERY";
    public static String DIALOG = "DIALOG";

    public static String PENDAFTARAN = "PENDAFTARAN";

    DBConfig config;
    Cursor cr;

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
        config = new DBConfig(getContext(), DBConfig.AGH, null, DBConfig.DB_VERSION);
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
                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pengguna_geprek", null);
                    cr.moveToFirst();
                    for (int count = 0; count < cr.getCount(); count++) {
                        cr.moveToPosition(count);
                        if (dialog.dialogSelectUsername.getText().toString().equals(cr.getString(3))
                                && dialog.dialogSelectPassword.getText().toString().equals(cr.getString(4))) {
                            Intent intent;
                            editor.putString("ID", cr.getString(0)).apply();
                            if (cr.getString(5).equals("pelanggan")) {
                                intent = new Intent(getContext(), MainActivityPengguna.class);
                                startActivity(intent);
                            }
                            if (cr.getString(5).equals("pemilik")) {
                                intent = new Intent(getContext(), MainActivityPemilik.class);
                                startActivity(intent);
                            }
                            getActivity().finish();
                        }
                    }
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

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_pengguna_geprek (all_name,number,user,sandi,very) VALUES(" +
                                    "'" + dialog.dialogSelectAllName.getText().toString() + "'," +
                                    "'" + dialog.dialogSelectPhone.getText().toString() + "'," +
                                    "'" + dialog.dialogSelectUsername.getText().toString() + "'," +
                                    "'" + dialog.dialogSelectPassword.getText().toString() + "'," +
                                    "'" + getArguments().getString(VERY) + "')");

                    Toast.makeText(getContext(), "Menambahkan Pengguna", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_pengguna_geprek", null);
                    cr.moveToFirst();
                    cr.moveToPosition(cr.getCount()-1);
                    editor.putString("ID", cr.getString(0)).apply();
                    if (getArguments().getString(VERY).equals("pemilik")) {
                        intent = new Intent(getContext(), MainActivityPemilik.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getContext(), MainActivityPengguna.class);
                        startActivity(intent);
                    }

                    getActivity().finish();

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