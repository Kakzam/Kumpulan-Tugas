package com.companyprofile.alzigugun.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.companyprofile.alzigugun.DetailNewsActivity;
import com.companyprofile.alzigugun.R;
import com.companyprofile.alzigugun.another.Adapter;
import com.companyprofile.alzigugun.another.ModelBerita;
import com.companyprofile.alzigugun.another.Preference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment implements Adapter.onListener {

    List<ModelBerita> model = new ArrayList<>();
    List<String> l = new ArrayList<>();
    Adapter adapter;
    RecyclerView recyclerView;
    Context context;

    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = getContext();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.an_rc);
        view.findViewById(R.id.progress).setVisibility(View.VISIBLE);

        try {
            db.collection("alzi").document("berita").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        model.add(new ModelBerita(document.getData().get("da") + "", document.getData().get("title") + "", document.getData().get("news") + ""));
                        l.add(document.getId());
                    }
                    setRec();
                } else new AlertDialog.Builder(context)
                        .setMessage("Periksa koneksi anda.")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            });

            setRec();
            if (new Preference(context).getLogin())
                view.findViewById(R.id.add_news).setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            db.collection("alzi").document("berita").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        l.add(document.getId());
                        model.add(new ModelBerita(document.getData().get("da") + "", document.getData().get("title") + "", document.getData().get("news") + ""));
                    }
                    setRec();
                } else new AlertDialog.Builder(context)
                        .setMessage("Periksa koneksi anda.")
                        .setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create()
                        .show();
            });
        }

        view.findViewById(R.id.add_news).setOnClickListener(view1 -> {
            Button button = view.findViewById(R.id.button_action);
            button.setText("Unggah");
            view.findViewById(R.id.ll).setVisibility(View.VISIBLE);
        });

        view.findViewById(R.id.button_action).setOnClickListener(view1 -> {
            Button button = view.findViewById(R.id.button_action);
            if (button.getText().toString().equals("Unggah")) {
                EditText editTitle = view.findViewById(R.id.title), editNews = view.findViewById(R.id.news);
                String getEditTitle = editTitle.getText().toString(), getEditNews = editNews.getText().toString();
                if (!getEditNews.isEmpty() || !getEditNews.isEmpty()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("title", getEditTitle);
                    map.put("news", getEditNews);
                    map.put("da", new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

                    view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                    db.collection("alzi").document("berita").collection("-").add(map).addOnCompleteListener(task -> {
                        view.findViewById(R.id.progress).setVisibility(View.GONE);
                        view.findViewById(R.id.ll).setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            editTitle.setText("");
                            editNews.setText("");
                            new AlertDialog.Builder(context).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        } else
                            new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    }).addOnFailureListener(e -> {
                        view.findViewById(R.id.progress).setVisibility(View.GONE);
                        new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                    });
                } else
                    new AlertDialog.Builder(context).setMessage("Isi semua terlebih dahulu..").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }
        });

        return view;
    }

    private void setRec() {
        try {
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new Adapter(model, new Preference(context).getLogin(), HomeFragment.this);
            recyclerView.setAdapter(adapter);
        } catch (NullPointerException e) {
            view.findViewById(R.id.progress).setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new Adapter(model, false, HomeFragment.this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(String select, int position) {
        if (select.equals("update")) {
            Button button = view.findViewById(R.id.button_action);
            button.setText("UPDATE");
            view.findViewById(R.id.ll).setVisibility(View.VISIBLE);
            EditText editTitle = view.findViewById(R.id.title), editNews = view.findViewById(R.id.news);
            editNews.setText(model.get(position).getBerita());
            editTitle.setText(model.get(position).getJudul());
            button.setOnClickListener(view1 -> {
                if (button.getText().toString().equals("UPDATE")) {
                    String getEditTitle = editTitle.getText().toString(), getEditNews = editNews.getText().toString();
                    if (!getEditNews.isEmpty() || !getEditNews.isEmpty()) {
                        String second = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                        Map<String, String> map = new HashMap<>();
                        map.put("title", getEditTitle);
                        map.put("news", getEditNews);
                        map.put("da", second);

                        view.findViewById(R.id.progress).setVisibility(View.VISIBLE);
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("alzi").document("berita").collection("-").document(l.get(position)).set(map).addOnCompleteListener(task -> {
                            view.findViewById(R.id.progress).setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                editTitle.setText("");
                                editNews.setText("");
                                view.findViewById(R.id.ll).setVisibility(View.GONE);
                                model.set(position, new ModelBerita(second, getEditTitle, getEditNews));
                                setRec();
                                new AlertDialog.Builder(context).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                }).create().show();
                            } else
                                new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        }).addOnFailureListener(e -> {
                            view.findViewById(R.id.progress).setVisibility(View.GONE);
                            new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                        });
                    } else
                        new AlertDialog.Builder(context).setMessage("Isi semua terlebih dahulu..").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                }
            });
        } else if (select.equals("delete")) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("alzi").document("berita").collection("-").document(l.get(position)).delete().addOnCompleteListener(task -> {
                view.findViewById(R.id.progress).setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    model.remove(position);
                    setRec();
                    new AlertDialog.Builder(context).setMessage("Berita berhasil di upload!").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
                } else
                    new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            }).addOnFailureListener(e -> {
                view.findViewById(R.id.progress).setVisibility(View.GONE);
                new AlertDialog.Builder(context).setMessage("Silahkan periksa koneksi anda").setNegativeButton("Ya, mengerti", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
            });
        } else if (select.equals("select")) {
            startActivity(new Intent(context, DetailNewsActivity.class).putExtra("title", model.get(position).getJudul()).putExtra("date", model.get(position).getId()).putExtra("deskripsi", model.get(position).getBerita()));
        }
    }
}