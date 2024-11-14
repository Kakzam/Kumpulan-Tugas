package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.R;
import com.freshmart.freshmart.TestPenyimpanan;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DialogLihat extends BottomSheetDialogFragment {

    RecyclerView list;

    List<String> id = new ArrayList<>();
    List<String> nama = new ArrayList<>();
    List<String> harga = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_konfirmasi, container, false);

        list = root.findViewById(R.id.recycler);

        FirebaseFirestore.getInstance().collection("-")
                .document("pengguna")
                .collection("-")
                .document(getArguments().getString("id"))
                .collection("-")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            id.add(document.getId());
                            nama.add(document.get("nama_sayuran").toString());
                            harga.add(document.get("harga").toString());
                            qty.add(document.get("qty").toString());
                            total.add(document.get("total").toString());
                            gambar.add(document.get("gambar").toString());
                        }

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        list.setLayoutManager(layoutManager);
                        list.setAdapter(new Lihat());
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });

        return root;
    }

    private class Lihat extends RecyclerView.Adapter<Lihat.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new Lihat.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            byte[] d = Base64.decode(gambar.get(position), Base64.DEFAULT);
            holder.imageSayuran.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.nama.setText(nama.get(position));
            holder.harga.setText("Harga : " + nama.get(position));
            holder.qty.setText("Qty : " + qty.get(position));
            holder.total.setText("Total : " + total.get(position));
            holder.selesai.setOnClickListener(view -> {

                FirebaseFirestore.getInstance().collection("-")
                        .document("pengguna")
                        .collection("-")
                        .document(getArguments().getString("id"))
                        .collection("-")
                        .document(id.get(position))
                        .delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                id.remove(position);
                                nama.remove(position);
                                harga.remove(position);
                                qty.remove(position);
                                total.remove(position);
                                gambar.remove(position);
                                notifyDataSetChanged();
                            } else {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Pemberitahuan")
                                        .setMessage("Pastikan koneksi anda bagus")
                                        .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                        .show();
                            }
                        });
            });
        }

        @Override
        public int getItemCount() {
            return id.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran;
            TextView nama, harga, qty, total, selesai;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                nama = v.findViewById(R.id.nama);
                harga = v.findViewById(R.id.no_handphone);
                total = v.findViewById(R.id.total);
                qty = v.findViewById(R.id.qty);
                selesai = v.findViewById(R.id.lihat);
                selesai.setText("Selesai");
                v.findViewById(R.id.hapus).setVisibility(View.GONE);
            }
        }
    }

}