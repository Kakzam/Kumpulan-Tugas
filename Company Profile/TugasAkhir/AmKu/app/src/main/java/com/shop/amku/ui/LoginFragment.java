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
import com.shop.amku.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding login;
    Boolean isLogin = false;
    DBConfig config;
    Cursor cr;

    String id = "";
    String permission = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        login = FragmentLoginBinding.inflate(getLayoutInflater());
        config = new DBConfig(getActivity(), DBConfig.AMKU, null, DBConfig.DB_VERSION);
        login.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.bar.setVisibility(View.VISIBLE);
                if (!login.username.getText().toString().isEmpty()
                        && !login.password.getText().toString().isEmpty()) {

                    cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                    cr.moveToFirst();

                    for (int count = 0; count < cr.getCount(); count++) {
                        cr.moveToPosition(count);
                        if (cr.getString(2).equals(login.username.getText().toString())
                                && cr.getString(3).equals(login.password.getText().toString())
                        ) {
                            id = cr.getString(0);
                            permission = cr.getString(5);
                            isLogin = true;
                        }
                    }

                    if (isLogin) {
                        login.bar.setVisibility(View.GONE);
                        Intent intent = new Intent(getContext(), DashboardActivity.class);
                        intent.putExtra(DashboardActivity.ID, id);
                        intent.putExtra(DashboardActivity.PERMISSION, permission);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        login.bar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Silahkan periksa kembali username, dan password anda.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    login.bar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Silahkan periksa kembali username, dan password anda.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return login.getRoot();
    }
}