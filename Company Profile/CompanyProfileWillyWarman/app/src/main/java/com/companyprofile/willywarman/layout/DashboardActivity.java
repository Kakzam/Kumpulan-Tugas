package com.companyprofile.willywarman.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.companyprofile.willywarman.R;
import com.companyprofile.willywarman.other.Adapter;
import com.companyprofile.willywarman.other.ModelBerita;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements Adapter.onListener {

    List<ModelBerita> model = new ArrayList<>();
    int check = 0;
    String p = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = findViewById(R.id.rb);
        db.collection("willy-warman").document("list-berita").collection("-").get().addOnCompleteListener(task -> {
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

        findViewById(R.id.p).setOnClickListener(view -> {
            p = "1";
        });

        findViewById(R.id.mk).setOnClickListener(view -> {
            Intent intent = new Intent(this, ComingSoonActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.hk).setOnClickListener(view -> {
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.l).setOnClickListener(view -> {
            Intent intent = new Intent(this, ComingSoonActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.s).setOnClickListener(view -> {
            Intent intent = new Intent(this, ComingSoonActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.b).setOnClickListener(view -> {
            startActivity(new Intent(this, NewsActivity.class).putExtra("berita", p));
            finish();
        });

        findViewById(R.id.u).setOnClickListener(view -> {
            check++;
            if (check >= 5){
                check = 0;
                findViewById(R.id.p).setVisibility(View.VISIBLE);
            }
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
        finish();
    }
}