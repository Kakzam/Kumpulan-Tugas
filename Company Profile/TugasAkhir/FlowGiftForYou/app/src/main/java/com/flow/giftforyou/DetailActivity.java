package com.flow.giftforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flow.giftforyou.ui.DBConfig;

public class DetailActivity extends AppCompatActivity {

    TextView tJudul, tDescription, tWa;
    ImageView view;
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        config = new DBConfig(this, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
        String select = getIntent().getStringExtra("SELECT");
        view = findViewById(R.id.activity_detail_gambar);
        tJudul = findViewById(R.id.activity_detail_judul);
        tDescription = findViewById(R.id.activity_detail_description);
        tWa = findViewById(R.id.activity_detail_wa);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_buket WHERE id = " + select, null);
        cr.moveToFirst();
        cr.moveToPosition(cr.getCount()-1);

        byte[] d = Base64.decode(cr.getString(4), Base64.DEFAULT);
        view.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
        tJudul.setText(cr.getString(1));
        tDescription.setText(cr.getString(2));
        tWa.setText("wa.me//+62896-8412-2524");
        tWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=+6289684122524";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}