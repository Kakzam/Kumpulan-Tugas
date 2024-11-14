package com.companyprofile.danyekasaputra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

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

        textName.setText("PT SUGITY CREATIVES");
        textPhone.setText("+628 - 1238 - 0987");
        textSosmed1.setText("sugitycreatives@facebook");
        textSosmed2.setText("sugitycreatives@twitter");
        textSosmed3.setText("sugitycreatives@instagram");
        textEmail.setText("informasi@sugitycreatives.com");
        textAlamat.setText("Kawasan Industri, Jl. Bali I Jl. Mm 2100 No.17-20, Gandamekar, Cikarang Barat, Bekasi Regency, West Java 17530");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}