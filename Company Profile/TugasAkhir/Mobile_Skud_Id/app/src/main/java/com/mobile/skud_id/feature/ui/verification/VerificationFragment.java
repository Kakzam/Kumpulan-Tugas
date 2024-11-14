package com.mobile.skud_id.feature.ui.verification;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.mobile.skud_id.base.BaseFragment;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.FragmentVerificationBinding;
import com.mobile.skud_id.feature.AdministratorActivity;
import com.mobile.skud_id.feature.CustomerActivity;

public class VerificationFragment extends BaseFragment {

    private FragmentVerificationBinding binding;
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVerificationBinding.inflate(getLayoutInflater());
        config = new DBConfig(getContext(), DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);

        binding.save.setOnClickListener(view -> {
            if (!binding.username.getText().toString().isEmpty()
                    && !binding.password.getText().toString().isEmpty()) {
                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
                cr.moveToFirst();

                for (int count = 0; count < cr.getCount(); count++) {
                    cr.moveToPosition(count);
                    if (cr.getString(1).equals(binding.username.getText().toString())
                            && cr.getString(2).equals(binding.password.getText().toString())) {
                        if (cr.getString(5).equals("1")) setActivity(AdministratorActivity.class);
                        else setActivity(CustomerActivity.class);
                    }
                }
            } else if (binding.username.getText().toString().isEmpty())
                setToastLong("Username anda masih kosong");
            else if (binding.password.getText().toString().isEmpty())
                setToastLong("Password anda masih kosong");
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}