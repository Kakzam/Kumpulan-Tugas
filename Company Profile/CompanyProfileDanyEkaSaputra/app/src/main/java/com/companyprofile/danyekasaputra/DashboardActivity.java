package com.companyprofile.danyekasaputra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements Adapter.onListener {

    List<ModelBerita> model = new ArrayList<>();
    int check = 0;
    String pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        findViewById(R.id.wo).setOnClickListener(view -> {
            Intent intent = new Intent(this, VacancyActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.ab).setOnClickListener(view -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.pass).setOnClickListener(view -> pass = "2");

        findViewById(R.id.his).setOnClickListener(view -> {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.ne).setOnClickListener(view -> {
            startActivity(new Intent(this, NewsActivity.class).putExtra("berita", pass));
            finish();
        });

        findViewById(R.id.cm).setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.im).setOnClickListener(view -> {
            check++;
            if (check >= 5){
                check = 0;
                findViewById(R.id.pass).setVisibility(View.VISIBLE);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.rb);
        db.collection("dany").document("berita").collection("-").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    model.add(new ModelBerita(document.getData().get("da") + "", document.getData().get("title") + "",document.getData().get("news") + ""));
                }
                setRec(recyclerView);
            }
            else new AlertDialog.Builder(this)
                    .setMessage("Periksa koneksi anda.")
                    .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                    .create()
                    .show();
        });
    }

    private void setRec(RecyclerView recyclerView) {
        Adapter adapter;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(model, false, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(String select, int position) {
        startActivity(new Intent(DashboardActivity.this, DetailNewsActivity.class).putExtra("title", model.get(position).getJudul()).putExtra("date", model.get(position).getId()).putExtra("deskripsi", model.get(position).getBerita()));
    }
}