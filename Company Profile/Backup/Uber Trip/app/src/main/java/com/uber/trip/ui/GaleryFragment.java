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

public class GaleryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Bitmap> bitmaps = new ArrayList<>();
    Other other;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_galery, container, false);
        recyclerView = root.findViewById(R.id.list);

        root.findViewById(R.id.tambah_gambar).setVisibility(View.GONE);

        other = new Other();
        other.setLoading(getContext());
        FirebaseFirestore.getInstance()
                .collection("collection")
                .document("galery")
                .collection("collection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                byte[] d = Base64.decode(documentSnapshot.get("gambar").toString(), Base64.DEFAULT);
                                bitmaps.add(BitmapFactory.decodeByteArray(d, 0, d.length));
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(new Galery());
                            }
                            other.dissmissLoading();
                        } else {
                            other.dissmissLoading();
                            other.setToast("Silahkan periksa koneksi internet anda", getContext());
                        }
                    }
                });

        return root;
    }

    private class Galery extends RecyclerView.Adapter<Galery.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.image.setImageBitmap(bitmaps.get(position));
        }

        @Override
        public int getItemCount() {
            return bitmaps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.gambar);
                v.findViewById(R.id.hapus).setVisibility(View.GONE);
            }
        }
    }
}