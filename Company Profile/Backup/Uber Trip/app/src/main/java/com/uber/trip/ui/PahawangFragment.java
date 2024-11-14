package com.uber.trip.ui;

import android.annotation.SuppressLint;
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
import com.uber.trip.Other;
import com.uber.trip.R;

import java.util.ArrayList;
import java.util.List;

public class PahawangFragment extends Fragment {

    TextView textTambah;
    RecyclerView recyclerView;
    Other other = new Other();

    List<Bitmap> jpeg = new ArrayList<>();
    List<String> trip = new ArrayList<>();
    List<String> penjelasan = new ArrayList<>();
    List<String> biaya = new ArrayList<>();
    List<String> hub = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        root.findViewById(R.id.tambah_trip).setVisibility(View.GONE);
        recyclerView = root.findViewById(R.id.list);

        other.setLoading(getContext());
        FirebaseFirestore.getInstance()
                .collection("collection")
                .document("trip")
                .collection("collection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        other.dissmissLoading();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
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
                        } else {
                            other.setToast("Silahkan periksa koneksi anda", getContext());
                        }
                    }
                });

        return root;
    }

    private class Pahawang extends RecyclerView.Adapter<Pahawang.ViewHolder> {

        @NonNull
        @Override
        public Pahawang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Pahawang.ViewHolder holder = new Pahawang.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pahawang, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull Pahawang.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.image.setImageBitmap(jpeg.get(position));
            holder.trip.setText(trip.get(position));
            holder.biaya.setText(biaya.get(position));
            holder.contact.setText(hub.get(position));
            holder.penjelasan.setText(penjelasan.get(position));
        }

        @Override
        public int getItemCount() {
            return trip.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView trip, biaya, penjelasan, contact;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.jpeg);
                v.findViewById(R.id.aksi).setVisibility(View.GONE);
                trip = v.findViewById(R.id.trip);
                biaya = v.findViewById(R.id.biaya);
                penjelasan = v.findViewById(R.id.penjelasan);
                contact = v.findViewById(R.id.contact);
            }
        }
    }
}