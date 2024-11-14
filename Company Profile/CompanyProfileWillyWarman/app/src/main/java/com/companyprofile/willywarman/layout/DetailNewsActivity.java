package com.companyprofile.willywarman.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.companyprofile.willywarman.R;

public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        TextView title = findViewById(R.id.title), date = findViewById(R.id.date), deskripsi = findViewById(R.id.deskripsi);

        try {
            title.setText(getIntent().getStringExtra("title"));
            date.setText(getIntent().getStringExtra("date"));
            deskripsi.setText(getIntent().getStringExtra("deskripsi"));
        } catch (Exception e){
            e.printStackTrace();
            startActivity(new Intent(this, NewsActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}