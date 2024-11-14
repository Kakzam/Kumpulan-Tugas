package com.mobile.skud_id.feature.ui.create;

import android.os.Bundle;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.ActivityCreateLocationBinding;
import com.mobile.skud_id.feature.AdministratorActivity;

public class CreateLocationActivity extends BaseActivity {

    ActivityCreateLocationBinding binding;
    DBConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        config = new DBConfig(this, DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);

        binding.back.setOnClickListener(view -> setActivity(AdministratorActivity.class));
        binding.save.setOnClickListener(view -> {
            if (!binding.title.getText().toString().isEmpty() && !binding.maps.getText().toString().isEmpty()) {
                setLoading("Sedang melakukan proses");
                config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_location (nama,link) VALUES(" +
                                "'" + binding.title.getText().toString() + "'," +
                                "'" + binding.maps.getText().toString() + "')");
                dismissLoading();
                setActivity(AdministratorActivity.class);
            } else if (binding.title.getText().toString().isEmpty()) setToastLong("Silahkan isi nama lokasi");
            else if (binding.maps.getText().toString().isEmpty()) setToastLong("Silahkan isi link google maps");
        });
    }

    @Override
    public void onBackPressed() {
        setActivity(AdministratorActivity.class);
    }
}