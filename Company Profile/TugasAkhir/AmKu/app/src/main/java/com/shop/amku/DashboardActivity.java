package com.shop.amku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    public static String ID = "ID";
    public static String PERMISSION = "PERMISSION";
    public static String MENU = "MENU";

    boolean verfy = false;
    String id = "";
    String permission = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        try {
            permission = getIntent().getStringExtra(PERMISSION);
            id = getIntent().getStringExtra(ID);
            if (!id.isEmpty()) {
                verfy = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!verfy) {
            new AlertDialog.Builder(this)
                    .setTitle("Pembaritahuan")
                    .setMessage("Apakah anda mau login atau registrasi?")
                    .setNeutralButton("Ya", (dialog, id) -> {
                        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                        intent.putExtra(ID, id);
                        intent.putExtra(PERMISSION, permission);
                        intent.putExtra(MENU, "5");
                        startActivity(intent);
                        finish();
                    })
                    .setPositiveButton("Tidak", (dialog, i) -> {
                        dialog.dismiss();
                    })
                    .create()
                    .show();
        }

        findViewById(R.id.activity_dashboard_mobil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                intent.putExtra(ID, id);
                intent.putExtra(PERMISSION, permission);
                intent.putExtra(MENU, "1");
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.activity_dashboard_accessories).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                intent.putExtra(ID, id);
                intent.putExtra(PERMISSION, permission);
                intent.putExtra(MENU, "2");
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.activity_dashboard_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DashboardActivity.this, "Menu ini masih tahap development", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), ServiceActivity.class);
//                intent.putExtra(ID, id);
//                intent.putExtra(PERMISSION, permission);
//                intent.putExtra(MENU, "3");
//                startActivity(intent);
//                finish();
            }
        });

        findViewById(R.id.activity_dashboard_transaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id.equals("")) {
                    new AlertDialog.Builder(DashboardActivity.this)
                            .setTitle("Pembaritahuan")
                            .setMessage("Apakah anda mau login atau registrasi?")
                            .setNeutralButton("Ya", (dialog, id) -> {
                                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                                intent.putExtra(ID, id);
                                intent.putExtra(PERMISSION, permission);
                                intent.putExtra(MENU, "5");
                                startActivity(intent);
                                finish();
                            })
                            .setPositiveButton("Tidak", (dialog, i) -> {
                                dialog.dismiss();
                            })
                            .create()
                            .show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
                    intent.putExtra(ID, id);
                    intent.putExtra(PERMISSION, permission);
                    intent.putExtra(MENU, "4");
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}