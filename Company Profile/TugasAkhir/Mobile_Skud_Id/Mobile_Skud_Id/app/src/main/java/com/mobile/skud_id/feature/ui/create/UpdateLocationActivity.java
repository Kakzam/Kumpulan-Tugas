package com.mobile.skud_id.feature.ui.create;

import android.database.Cursor;
import android.os.Bundle;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.ActivityCreateLocationBinding;
import com.mobile.skud_id.feature.AdministratorActivity;

public class UpdateLocationActivity extends BaseActivity {

    ActivityCreateLocationBinding binding;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        config = new DBConfig(this, DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_location WHERE id " + getIntent().getStringExtra(INTENT_1), null);
        cr.moveToFirst();
        cr.moveToPosition(cr.getCount() - 1);
        binding.title.setText(cr.getString(1));
        binding.maps.setText(cr.getString(2));

        binding.back.setOnClickListener(view -> setActivity(AdministratorActivity.class));
        binding.save.setOnClickListener(view -> {
            if (!binding.title.getText().toString().isEmpty() && !binding.maps.getText().toString().isEmpty()) {
                setLoading("Sedang melakukan proses");
                config.getWritableDatabase().execSQL(
                        "UPDATE tbl_location SET " +
                                "nama = '" + binding.title.getText().toString() + "'," +
                                "link = '" + binding.maps.getText().toString() + "'," +
                                "WHERE id = " + getIntent().getStringExtra(INTENT_1));
                dismissLoading();
                setActivity(AdministratorActivity.class);
            } else if (binding.title.getText().toString().isEmpty())
                setToastLong("Silahkan isi nama lokasi");
            else if (binding.maps.getText().toString().isEmpty())
                setToastLong("Silahkan isi link google maps");
        });
    }

    @Override
    public void onBackPressed() {
        setActivity(AdministratorActivity.class);
    }
}