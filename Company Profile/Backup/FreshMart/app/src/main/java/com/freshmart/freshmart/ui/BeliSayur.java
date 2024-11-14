package com.freshmart.freshmart.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.MainActivity;
import com.freshmart.freshmart.R;
import com.freshmart.freshmart.TestPenyimpanan;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class BeliSayur extends Fragment {

    TextView tambah;
    EditText search;
    RecyclerView list;
    public ProgressBar progressBar;
    Sayur sayur;

    List<String> id = new ArrayList<>();
    List<String> sayuran = new ArrayList<>();
    List<String> harga = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    List<String> select_id = new ArrayList<>();
    List<String> select_sayuran = new ArrayList<>();
    List<String> select_harga = new ArrayList<>();
    List<String> select_gambar = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_beli_sayur, container, false);

        tambah = root.findViewById(R.id.tambah);
        search = root.findViewById(R.id.search);
        list = root.findViewById(R.id.recycler);
        progressBar = root.findViewById(R.id.progressbar);

        if (new TestPenyimpanan(getContext()).getLogin()) {
            tambah.setVisibility(View.VISIBLE);
        }

        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").get().addOnCompleteListener(task -> {
            progressBar.setVisibility(View.GONE);
            if (task.isSuccessful()) {

                id.clear();
                sayuran.clear();
                harga.clear();
                gambar.clear();

                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                    id.add(document.getId());
                    sayuran.add(document.get("nama_sayuran").toString());
                    harga.add(document.get("harga").toString());
                    gambar.add(document.get("gambar").toString());
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                list.setLayoutManager(layoutManager);
                sayur = new Sayur(gambar, harga, sayuran, id);
                list.setAdapter(sayur);
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Pemberitahuan")
                        .setMessage("Pastikan koneksi anda bagus")
                        .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });

        tambah.setOnClickListener(view -> {
            new DialogTambah().show(getActivity().getSupportFragmentManager(), "");
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                select_id.clear();
                select_sayuran.clear();
                select_harga.clear();
                select_gambar.clear();

                for (int ii = 0; ii < id.size(); ii++) {
                    if (id.get(i).equals(search.getText().toString())
                            || sayuran.get(ii).equals(search.getText().toString())
                            || harga.get(ii).equals(search.getText().toString())
                            || gambar.get(ii).equals(search.getText().toString())
                    ) {
                        select_id.add(id.get(ii));
                        select_sayuran.add(sayuran.get(ii));
                        select_harga.add(harga.get(ii));
                        select_gambar.add(gambar.get(ii));
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                list.setLayoutManager(layoutManager);
                sayur = new Sayur(select_gambar, select_harga, select_sayuran, select_id);
                list.setAdapter(sayur);

                if (search.getText().toString().length() == 0) {
                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    list.setLayoutManager(layoutManager);
                    sayur = new Sayur(gambar, harga, sayuran, id);
                    list.setAdapter(sayur);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        search.addTextChangedListener(textWatcher);

        return root;
    }

    private class Sayur extends RecyclerView.Adapter<Sayur.ViewHolder> {

        private List<String> id;
        private List<String> sayuran;
        private List<String> harga;
        private List<String> gambar;

        public Sayur(List<String> gambar, List<String> harga, List<String> sayuran, List<String> id) {
            this.gambar = gambar;
            this.id = id;
            this.sayuran = sayuran;
            this.harga = harga;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sayuran, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (new TestPenyimpanan(getContext()).getLogin()) {
                holder.imageDelete.setVisibility(View.VISIBLE);
                holder.beli.setVisibility(View.GONE);
            }

            holder.imageDelete.setOnClickListener(view -> {
                BeliSayur.this.progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").document(id.get(position)).delete().addOnCompleteListener(task -> {
                    BeliSayur.this.progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
            });

            holder.beli.setOnClickListener(view -> {
                DialogTambah dialog = new DialogTambah();
                Bundle bundle = new Bundle();
                bundle.putString("id", id.get(position));
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "");
            });

            byte[] d = Base64.decode(gambar.get(position), Base64.DEFAULT);
            holder.imageSayuran.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.nama.setText(sayuran.get(position));
            holder.harga.setText(harga.get(position));
        }

        @Override
        public int getItemCount() {
            return gambar.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran, imageDelete;
            TextView nama, harga, beli;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                imageDelete = v.findViewById(R.id.hapus);
                nama = v.findViewById(R.id.nama);
                harga = v.findViewById(R.id.harga);
                beli = v.findViewById(R.id.buy);
            }
        }
    }
}