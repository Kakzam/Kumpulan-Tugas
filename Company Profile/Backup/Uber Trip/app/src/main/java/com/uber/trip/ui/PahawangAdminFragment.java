package com.uber.trip.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.uber.trip.MainActivity3;
import com.uber.trip.Other;
import com.uber.trip.R;

import java.util.ArrayList;
import java.util.List;

public class PahawangAdminFragment extends Fragment {

    TextView textTambah;
    RecyclerView recyclerView;
    Other other = new Other();

    List<String> select = new ArrayList<>();
    List<Bitmap> jpeg = new ArrayList<>();
    List<String> trip = new ArrayList<>();
    List<String> penjelasan = new ArrayList<>();
    List<String> biaya = new ArrayList<>();
    List<String> hub = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        textTambah = root.findViewById(R.id.tambah_trip);
        recyclerView = root.findViewById(R.id.list);

        textTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity3.class);
                intent.putExtra("update", "false");
                startActivity(intent);
                getActivity().finish();
            }
        });

        other.setLoading(getContext());
        FirebaseFirestore.getInstance()
                .collection("collection")
                .document("trip")
                .collection("collection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            select.clear();
                            jpeg.clear();
                            trip.clear();
                            penjelasan.clear();
                            biaya.clear();
                            hub.clear();
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                select.add(documentSnapshot.getId());
                                byte[] d = Base64.decode(documentSnapshot.get("jpeg").toString(), Base64.DEFAULT);
                                jpeg.add(BitmapFactory.decodeByteArray(d, 0, d.length));
                                trip.add(documentSnapshot.get("trip").toString());
                                penjelasan.add(documentSnapshot.get("penjelasan").toString());
                                biaya.add(documentSnapshot.get("biaya").toString());
                                hub.add(documentSnapshot.get("contact").toString());
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new Pahawang());
                            other.dissmissLoading();
                        } else {
                            other.dissmissLoading();
                            other.setToast("Silahkan periksa koneksi anda", getContext());
                        }
                    }
                });

        return root;
    }

    private class Pahawang extends RecyclerView.Adapter<Pahawang.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pahawang, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.image.setImageBitmap(jpeg.get(position));
            holder.trip.setText(trip.get(position));
            holder.biaya.setText(biaya.get(position));
            holder.penjelasan.setText(penjelasan.get(position));
            holder.contact.setText(hub.get(position));

            holder.change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MainActivity3.class);
                    intent.putExtra("update", "true");
                    intent.putExtra("select", select.get(position));
                    getContext().startActivity(intent);
                    getActivity().finish();
                }
            });

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    other.setLoading(getContext());
                    FirebaseFirestore.getInstance()
                            .collection("collection")
                            .document("trip")
                            .collection("collection")
                            .document(select.get(position))
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        select.remove(position);
                                        jpeg.remove(position);
                                        trip.remove(position);
                                        penjelasan.remove(position);
                                        biaya.remove(position);
                                        hub.remove(position);
                                        notifyDataSetChanged();
                                        other.dissmissLoading();
                                    } else {
                                        other.setToast("Gagal menghapus gambar", getContext());
                                    }
                                }
                            });
                }
            });
        }

        @Override
        public int getItemCount() {
            return select.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView remove, change, trip, biaya, penjelasan, contact;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.jpeg);
                remove = v.findViewById(R.id.menghilang);
                change = v.findViewById(R.id.mengubah);
                trip = v.findViewById(R.id.trip);
                biaya = v.findViewById(R.id.biaya);
                penjelasan = v.findViewById(R.id.penjelasan);
                contact = v.findViewById(R.id.contact);
            }
        }
    }
}