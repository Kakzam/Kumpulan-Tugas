package com.companyprofile.willywarman.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.companyprofile.willywarman.R;

public class ContactActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView textName, textPhone, textSosmed1, textSosmed2, textSosmed3, textEmail, textAlamat;
        textName = findViewById(R.id.title);
        textPhone = findViewById(R.id.phone);
        textSosmed1 = findViewById(R.id.sosmed1);
        textSosmed2 = findViewById(R.id.sosmed2);
        textSosmed3 = findViewById(R.id.sosmed3);
        textEmail = findViewById(R.id.email);
        textAlamat = findViewById(R.id.alamat);

        textName.setText("PT Lion Super Indo");
        textPhone.setText("+628 - 8050 - 0057");
        textSosmed1.setText("superindo@twitter");
        textSosmed2.setText("superindo@facebook");
        textSosmed3.setText("superindo@instagram");
        textEmail.setText("informasi@superindo.com");
        textAlamat.setText("Mall Kartini, Jl. Kartini No. 49, Tanjung Karang, Palapa, Kec. Tj. Karang Pusat, Kota Bandar Lampung, Lampung 35118");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, DashboardActivity.class);
        startActivity(i);
        finish();
    }
}