package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.MainActivity;
import com.freshmart.freshmart.R;
import com.freshmart.freshmart.TestPenyimpanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Konfirmasi extends Fragment {

    RecyclerView list;

    List<String> id = new ArrayList<>();
    List<String> nama = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> phone = new ArrayList<>();
    List<String> gambar = new ArrayList<>();
    List<String> harga = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_konfirmasi, container, false);

        list = root.findViewById(R.id.recycler);

        if (new TestPenyimpanan(getContext()).getLogin()) {
            FirebaseFirestore.getInstance().collection("-")
                    .document("pengguna")
                    .collection("-")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                FirebaseFirestore.getInstance().collection("-")
                                        .document("pengguna")
                                        .collection("-")
                                        .document(document.getId())
                                        .collection("-")
                                        .get()
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                if (task1.getResult().getDocuments().size() > 0) {
                                                    id.add(document.getId());
                                                    nama.add(document.get("nama").toString());
                                                    phone.add(document.get("handphone").toString());

                                                    int hqty = 0;
                                                    int htotal = 0;

                                                    for (DocumentSnapshot ds : task1.getResult().getDocuments()) {
//                                                        Log.v("ZAM", document.getId() + " Qty : " + ds.get("qty").toString());
//                                                        Log.v("ZAM", document.getId() + " Total : " + ds.get("total").toString());
                                                        hqty += Integer.parseInt(ds.get("qty").toString());
                                                        htotal += Integer.parseInt(ds.get("total").toString());
                                                    }

                                                    qty.add(hqty + "");
                                                    total.add(htotal + "");
                                                }

                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                                list.setLayoutManager(layoutManager);
                                                list.setAdapter(new User());
                                            } else {
                                                new AlertDialog.Builder(getContext())
                                                        .setTitle("Pemberitahuan")
                                                        .setMessage("Pastikan koneksi anda bagus")
                                                        .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                                        .show();
                                            }
                                        });
                            }
                        } else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Pemberitahuan")
                                    .setMessage("Pastikan koneksi anda bagus")
                                    .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                    });
        } else {
            FirebaseFirestore.getInstance().collection("-")
                    .document("pengguna")
                    .collection("-")
                    .document(new TestPenyimpanan(getContext()).getId())
                    .collection("-")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
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
                                list.setAdapter(new User());
                            } else {
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Pemberitahuan")
                                        .setMessage("Pastikan koneksi anda bagus")
                                        .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                        .show();
                            }
                        }
                    });
        }

        return root;
    }

    private class User extends RecyclerView.Adapter<User.ViewHolder> {

        @NonNull
        @Override
        public User.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            User.ViewHolder holder = new User.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull User.ViewHolder holder, int position) {

            holder.nama.setText(nama.get(position));
            holder.qty.setText("Qty : " + qty.get(position));
            holder.total.setText("Total : " + total.get(position));

            if (new TestPenyimpanan(getContext()).getLogin()) {
                holder.no_handphone.setText("Telepon : " + phone.get(position));
            } else {
                holder.lihat.setVisibility(View.GONE);
                byte[] d = Base64.decode(gambar.get(position), Base64.DEFAULT);
                holder.imageSayuran.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
                holder.no_handphone.setText("Harga : " + harga.get(position));
            }

            holder.imageDelete.setOnClickListener(view -> {
                if (new TestPenyimpanan(getContext()).getLogin()) {
                    FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").document(id.get(position)).collection("-").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").document(id.get(position)).collection("-").document(document.getId()).delete().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
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
                            }
                        } else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Pemberitahuan")
                                    .setMessage("Pastikan koneksi anda bagus")
                                    .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                    });
                } else {
                    FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").document(new TestPenyimpanan(getContext()).getId()).collection("-").document(id.get(position)).delete().addOnCompleteListener(task -> {
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
                }
            });

            holder.lihat.setOnClickListener(view -> {
                DialogLihat dialog = new DialogLihat();
                Bundle bundle = new Bundle();
                bundle.putString("id", id.get(position));
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "");
            });
        }

        @Override
        public int getItemCount() {
            return id.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran, imageDelete;
            TextView nama, no_handphone, qty, total, lihat;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                imageDelete = v.findViewById(R.id.hapus);
                nama = v.findViewById(R.id.nama);
                no_handphone = v.findViewById(R.id.no_handphone);
                qty = v.findViewById(R.id.qty);
                total = v.findViewById(R.id.total);
                lihat = v.findViewById(R.id.lihat);
            }
        }
    }

}