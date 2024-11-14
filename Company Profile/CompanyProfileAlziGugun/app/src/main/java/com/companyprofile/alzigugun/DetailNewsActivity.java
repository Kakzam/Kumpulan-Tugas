package com.companyprofile.alzigugun;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.companyprofile.alzigugun.another.Preference;

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
            if (new Preference(this).getLogin()){
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (new Preference(this).getLogin()){
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}