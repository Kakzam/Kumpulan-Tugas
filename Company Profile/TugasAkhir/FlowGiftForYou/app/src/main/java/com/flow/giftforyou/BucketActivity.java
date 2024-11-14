package com.flow.giftforyou;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flow.giftforyou.ui.DBConfig;
import com.flow.giftforyou.ui.ModelBuket;
import com.flow.giftforyou.ui.UtilString;
import com.flow.giftforyou.ui.ubah.AddBucketActivity;

import java.util.ArrayList;
import java.util.List;

public class BucketActivity extends AppCompatActivity {

    List<ModelBuket> list = new ArrayList<>();
    RecyclerView view;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        view = findViewById(R.id.activity_masuk_view);
        config = new DBConfig(this, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_buket WHERE buket = '" + getIntent().getStringExtra(UtilString.BUCKET) + "'", null);
        cr.moveToFirst();

        list.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            list.add(new ModelBuket(cr.getString(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4)));
        }

        if (list.size() == 0)
            Toast.makeText(getApplicationContext(), "Data masih kosong", Toast.LENGTH_LONG).show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);
        view.setAdapter(new GambarActivity(list, getIntent().getStringExtra(UtilString.LOGIN), getIntent().getStringExtra(UtilString.BUCKET), BucketActivity.this, BucketActivity.this));

        if (!getIntent().getStringExtra(UtilString.LOGIN).equals(UtilString.UBAH))
            findViewById(R.id.activity_masuk_daftar).setVisibility(View.GONE);
        findViewById(R.id.activity_masuk_daftar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddBucketActivity.class);
                intent.putExtra(UtilString.BUCKET, getIntent().getStringExtra(UtilString.BUCKET));
                intent.putExtra(UtilString.LOGIN, getIntent().getStringExtra(UtilString.LOGIN));
                startActivity(intent);
                finish();
            }
        });
    }
}