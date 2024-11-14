package com.eventic.lampungtourism.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eventic.lampungtourism.AdminActivity;
import com.eventic.lampungtourism.CustomerActivity;
import com.eventic.lampungtourism.DBConfig;
import com.eventic.lampungtourism.databinding.FragmentVerificaitonBinding;

public class RegisterFragment extends Fragment {

    FragmentVerificaitonBinding binding;
    int position = 0;
    DBConfig config;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificaitonBinding.inflate(getLayoutInflater());
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

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_user (id_id,nama,password,position) VALUES(" +
                                    "'" + binding.getId.getText().toString() + "'," +
                                    "'" + binding.getFullName.getText().toString() + "'," +
                                    "'" + binding.getPassword.getText().toString() + "'," +
                                    "'" + position + "')");

                    if (position > 5) {
                        Intent intent = new Intent(getContext(), AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), CustomerActivity.class);
                        startActivity(intent);
                    }
                    getActivity().finish();

                } else
                    Toast.makeText(getContext(), "Silahkan ini semua dengan benar", Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }
}