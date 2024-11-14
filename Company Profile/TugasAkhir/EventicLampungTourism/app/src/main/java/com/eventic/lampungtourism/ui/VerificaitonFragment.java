package com.eventic.lampungtourism.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eventic.lampungtourism.AdminActivity;
import com.eventic.lampungtourism.CustomerActivity;
import com.eventic.lampungtourism.DBConfig;
import com.eventic.lampungtourism.R;
import com.eventic.lampungtourism.databinding.FragmentVerificaitonBinding;

public class VerificaitonFragment extends Fragment {

    FragmentVerificaitonBinding binding;
    DBConfig config;
    Cursor cr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificaitonBinding.inflate(getLayoutInflater());
        binding.getFullName.setVisibility(View.GONE);
        binding.getFullName.setText("");
        binding.getId.setText("");
        binding.getPassword.setText("");
        binding.createAdmin.setImageDrawable(getActivity().getDrawable(R.drawable.login));
        config = new DBConfig(getActivity(), DBConfig.EVENTIC_LAMPUNG_TOURISM, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
        cr.moveToFirst();

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.getId.getText().toString().isEmpty() && !binding.getPassword.getText().toString().isEmpty()) {
                    for (int count = 0; count < cr.getCount(); count++) {
                        cr.moveToPosition(count);
                        if (cr.getString(1).equals(binding.getId.getText().toString())
                                && cr.getString(3).equals(binding.getPassword.getText().toString())) {
                            if (Integer.parseInt(cr.getString(4)) > 5) {
                                Intent intent = new Intent(getContext(), AdminActivity.class);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getContext(), CustomerActivity.class);
                                startActivity(intent);
                            }
                            getActivity().finish();
                        }
                    }
                } else
                    Toast.makeText(getContext(), "Silahkan isi semua dengan benar", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }
}