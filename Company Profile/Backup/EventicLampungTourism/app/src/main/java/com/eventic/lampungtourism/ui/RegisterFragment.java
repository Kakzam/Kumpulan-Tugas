package com.eventic.lampungtourism.ui;

import static java.sql.Types.NULL;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eventic.lampungtourism.AdminActivity;
import com.eventic.lampungtourism.CustomerActivity;
import com.eventic.lampungtourism.DBConfig;
import com.eventic.lampungtourism.databinding.FragmentVerificaitonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    FragmentVerificaitonBinding binding;
    int position = 0;
    FirebaseFirestore firestore;
    DBConfig config;
    Cursor cr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificaitonBinding.inflate(getLayoutInflater());
        firestore = FirebaseFirestore.getInstance();
        config = new DBConfig(getActivity(), DBConfig.EVENTIC_LAMPUNG_TOURISM, null, DBConfig.DB_VERSION);
        binding.getFullName.setText("");
        binding.getId.setText("");
        binding.getPassword.setText("");

        binding.createAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position += 1;
            }
        });

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.getFullName.getText().toString().isEmpty()
                        && !binding.getId.getText().toString().isEmpty()
                        && !binding.getPassword.getText().toString().isEmpty()) {

//                    db.execSQL("INSERT INTO tb_mahasiswa VALUES('"+edt_npm.getText().toString()+"'," +
//                            "'"+edt_nama.getText().toString()+"'," +
//                            "'"+spn_jurusan.getSelectedItem().toString()+"')");

                    Log.v("ZAM", "INPUT DATABASE ==== ==== ");
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_user (id_id,nama,password,position) VALUES(" +
//                                    "'null'," +
                                    "'" + binding.getId.getText().toString() + "'," +
                                    "'" + binding.getFullName.getText().toString() + "'," +
                                    "'" + binding.getPassword.getText().toString() + "'," +
                                    "'" + position + "')");

//                    if (position > 5) {
//                        Intent intent = new Intent(getContext(), AdminActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(getContext(), CustomerActivity.class);
//                        startActivity(intent);
//                    }
//                    getActivity().finish();
                    Log.v("ZAM", "NGAMBIL DATABASE ==== ==== ");
                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                    cr.moveToFirst();

                    for (int count = 0; count < cr.getCount(); count++) {
                        cr.moveToPosition(count);
                        Log.v("ZAM", cr.getInt(0) + "");
                        Log.v("ZAM", cr.getString(1));
                        Log.v("ZAM", cr.getString(2));
                        Log.v("ZAM", cr.getString(3));
                        Log.v("ZAM", cr.getString(4));
                    }

//                    Map<String, String> add = new HashMap<>();
//                    add.put("full_name", binding.getFullName.getText().toString());
//                    add.put("id", binding.getId.getText().toString());
//                    add.put("password", binding.getPassword.getText().toString());
//                    add.put("position", position + "");
//                    firestore.collection("user").add(add).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                        @Override
//                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            if (task.isSuccessful()) {
//                                if (position > 5) {
//                                    Intent intent = new Intent(getContext(), AdminActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    Intent intent = new Intent(getContext(), CustomerActivity.class);
//                                    startActivity(intent);
//                                }
//                                getActivity().finish();
//                            } else {
//                                Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                } else
                    Toast.makeText(getContext(), "Silahkan ini semua dengan benar", Toast.LENGTH_SHORT).show();

            }
        });

//        View view = inflater.inflate(R.layout.fragment_register, container, false);


        return binding.getRoot();
    }
}