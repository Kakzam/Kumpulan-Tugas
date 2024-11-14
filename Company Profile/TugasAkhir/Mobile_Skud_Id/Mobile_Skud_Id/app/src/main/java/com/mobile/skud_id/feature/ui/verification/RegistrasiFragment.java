package com.mobile.skud_id.feature.ui.verification;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.base.BaseFragment;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.FragmentRegistrasiBinding;
import com.mobile.skud_id.feature.AdministratorActivity;
import com.mobile.skud_id.feature.CustomerActivity;

import java.io.IOException;

public class RegistrasiFragment extends BaseFragment {

    private FragmentRegistrasiBinding binding;
    DBConfig config;
    String imageProfile = "";
    String permission = "2";
    int checkPermission = 0;

    ActivityResultLauncher<Intent> getImageProfile = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            imageProfile = BaseActivity.encodeTobase64(bitmap);
                            binding.addImage.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistrasiBinding.inflate(getLayoutInflater());
        config = new DBConfig(getContext(), DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);

        binding.save.setOnLongClickListener(view -> {
            checkPermission += 1;
            if (checkPermission > 4) {
                permission = "1";
                setToast("Pengguna akan menjadi admin");
            }
            return false;
        });

        binding.addImage.setOnClickListener(view -> getImageProfile.launch(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Pilih Profile")));

        binding.save.setOnClickListener(view -> {
            if (!imageProfile.isEmpty()
                    && !binding.username.getText().toString().isEmpty()
                    && !binding.password.getText().toString().isEmpty()
                    && !binding.phone.getText().toString().isEmpty()) {
                setLoading("Sedang melakukan proses");
                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_user (username,password,phone_number,image,permission) VALUES(" +
                                "'" + binding.username.getText().toString() + "'," +
                                "'" + binding.password.getText().toString() + "'," +
                                "'" + binding.phone.getText().toString() + "'," +
                                "'" + imageProfile + "'," +
                                "'" + permission + "')");
                dismissLoading();
                if (permission.equals("1")) setActivity(AdministratorActivity.class);
                else setActivity(CustomerActivity.class);
            } else if (imageProfile.isEmpty()) setToastLong("Photo Profile anda masih kosong");
            else if (binding.username.getText().toString().isEmpty())
                setToastLong("Username anda masih kosong");
            else if (binding.password.getText().toString().isEmpty())
                setToastLong("Password anda masih kosong");
            else if (binding.phone.getText().toString().isEmpty())
                setToastLong("Nomor Handphone anda masih kosong");
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}