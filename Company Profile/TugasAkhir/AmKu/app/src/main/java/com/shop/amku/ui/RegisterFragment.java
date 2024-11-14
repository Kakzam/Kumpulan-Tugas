package com.shop.amku.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.shop.amku.DBConfig;
import com.shop.amku.DashboardActivity;
import com.shop.amku.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding register;
    DBConfig config;
    Cursor cr;

    String permission = "customer";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        register = FragmentRegisterBinding.inflate(getLayoutInflater());
        config = new DBConfig(getActivity(), DBConfig.AMKU, null, DBConfig.DB_VERSION);

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

                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_user (username,password,all_name,phone_number, permission) VALUES(" +
                                "'" + register.username.getText().toString() + "'," +
                                "'" + register.password.getText().toString() + "'," +
                                "'" + register.nama.getText().toString() + "'," +
                                "'" + register.hp.getText().toString() + "'," +
                                "'" + permission + "')");

                register.bar.setVisibility(View.GONE);

                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                cr.moveToFirst();
                cr.moveToPosition(cr.getCount()-1);

                Intent intent = new Intent(getContext(), DashboardActivity.class);
                intent.putExtra(DashboardActivity.ID, cr.getString(0));
                intent.putExtra(DashboardActivity.PERMISSION, permission);
                startActivity(intent);
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Silahkan isi semua dengan benar.", Toast.LENGTH_SHORT).show();
            }
        });

        return register.getRoot();
    }
}