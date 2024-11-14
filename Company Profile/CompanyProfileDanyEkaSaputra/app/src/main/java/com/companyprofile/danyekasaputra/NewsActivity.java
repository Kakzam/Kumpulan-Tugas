package com.companyprofile.danyekasaputra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewsActivity extends AppCompatActivity implements Adapter.onListener {

    List<ModelBerita> model = new ArrayList<>();
    List<String> l = new ArrayList<>();
    Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.an_rc);
        findViewById(R.id.progress).setVisibility(View.VISIBLE);

        try {
            db.collection("dany").document("berita").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        model.add(new ModelBerita(document.getData().get("da") + "", document.getData().get("title") + "", document.getData().get("news") + ""));
                        l.add(document.getId());
                    }
                    setRec();
                } else new AlertDialog.Builder(this)
                        .setMessage("Periksa koneksi anda.")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            });
            setRec();
            if (getIntent().getStringExtra("berita").equals("2"))
                findViewById(R.id.an_tb).setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            db.collection("dany").document("berita").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        l.add(document.getId());
                        model.add(new ModelBerita(document.getData().get("da") + "", document.getData().get("title") + "", document.getData().get("news") + ""));
                    }
                    setRec();
                } else new AlertDialog.Builder(this)
                        .setMessage("Periksa koneksi anda.")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            });
        }

        findViewById(R.id.an_tb).setOnClickListener(view -> {
            Button button = findViewById(R.id.button_action);
            button.setText("Unggah");
            findViewById(R.id.ll).setVisibility(View.VISIBLE);
        });

        findViewById(R.id.button_action).setOnClickListener(view -> {
            Button button = findViewById(R.id.button_action);
            if (button.getText().toString().equals("Unggah")) {
                EditText editTitle = findViewById(R.id.title), editNews = findViewById(R.id.news);
                String getEditTitle = editTitle.getText().toString(), getEditNews = editNews.getText().toString();
                if (!getEditNews.isEmpty() || !getEditNews.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", getEditTitle);
                    map.put("news", getEditNews);
                    map.put("da", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

                    findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    db.collection("dany").document("berita").collection("-").add(map).addOnCompleteListener(task -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        findViewById(R.id.ll).setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            editTitle.setText("");
                            editNews.setText("");
                            new AlertDialog.Builder(NewsActivity.this).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        } else
                            new AlertDialog.Builder(NewsActivity.this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    }).addOnFailureListener(e -> {
                        findViewById(R.id.progress).setVisibility(View.GONE);
                        new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    });
                } else
                    new AlertDialog.Builder(this).setMessage("Isi semua terlebih dahulu..").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }
        });
    }

    private void setRec() {
        try {
            findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new Adapter(model, getIntent().getStringExtra("berita").equals("2"), this);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new Adapter(model, false, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(String select, int position) {
        if (select.equals("update")) {
            Button button = findViewById(R.id.button_action);
            button.setText("UPDATE");
            findViewById(R.id.ll).setVisibility(View.VISIBLE);
            EditText editTitle = findViewById(R.id.title), editNews = findViewById(R.id.news);
            editNews.setText(model.get(position).getBerita());
            editTitle.setText(model.get(position).getJudul());
            button.setOnClickListener(view -> {
                if (button.getText().toString().equals("UPDATE")) {
                    String getEditTitle = editTitle.getText().toString(), getEditNews = editNews.getText().toString();
                    if (!getEditNews.isEmpty() || !getEditNews.isEmpty()) {
                        String second = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        Map<String, String> map = new HashMap<>();
                        map.put("title", getEditTitle);
                        map.put("news", getEditNews);
                        map.put("da", second);

                        findViewById(R.id.progress).setVisibility(View.VISIBLE);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("dany").document("berita").collection("-").document(l.get(position)).set(map).addOnCompleteListener(task -> {
                            findViewById(R.id.progress).setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                editTitle.setText("");
                                editNews.setText("");
                                findViewById(R.id.ll).setVisibility(View.GONE);
                                model.set(position, new ModelBerita(second, getEditTitle, getEditNews));
                                setRec();
                                new AlertDialog.Builder(this).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                    startActivity(new Intent(this, NewsActivity.class).putExtra("berita", "2"));
                                    finish();
                                }).create().show();
                            } else
                                new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        }).addOnFailureListener(e -> {
                            findViewById(R.id.progress).setVisibility(View.GONE);
                            new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        });
                    } else
                        new AlertDialog.Builder(this).setMessage("Isi semua terlebih dahulu..").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                }
            });
        } else if (select.equals("delete")) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("dany").document("berita").collection("-").document(l.get(position)).delete().addOnCompleteListener(task -> {
                findViewById(R.id.progress).setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    model.remove(position);
                    setRec();
                    new AlertDialog.Builder(this).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                } else
                    new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }).addOnFailureListener(e -> {
                findViewById(R.id.progress).setVisibility(View.GONE);
                new AlertDialog.Builder(this).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            });
        } else if (select.equals("select")) {
            startActivity(new Intent(NewsActivity.this, DetailNewsActivity.class).putExtra("title", model.get(position).getJudul()).putExtra("date", model.get(position).getId()).putExtra("deskripsi", model.get(position).getBerita()));
        }

//        EditText editTitle = findViewById(R.id.title), editNews = findViewById(R.id.news);
//        RecyclerView recyclerView;
//        recyclerView = findViewById(R.id.an_rc);
//        Adapter adapter;
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new Adapter(li, lis, d);
//        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}