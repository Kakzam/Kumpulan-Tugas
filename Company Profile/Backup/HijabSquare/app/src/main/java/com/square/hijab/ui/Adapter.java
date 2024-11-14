package com.square.hijab.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.square.hijab.databinding.FragmentGambarBinding;
import com.square.hijab.databinding.FragmentNotificationsBinding;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public static String JILBAB = "JILBAB";
    public static String GAMBAR = "GAMBAR";
    public static String NAMA_JILBAB = "NAMA_JILBAB";
    public static String HARGA = "HARGA";

    Context context;
    List<DocumentSnapshot> list;
    FirebaseFirestore fs = FirebaseFirestore.getInstance();
    Boolean hijab = false;

    public Adapter(Context context, List<DocumentSnapshot> list, Boolean hijab) {
        this.context = context;
        this.list = list;
        this.hijab = hijab;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        FragmentNotificationsBinding binding;

        ViewHolder(FragmentNotificationsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentNotificationsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.a.setText(list.get(position).get(NAMA_JILBAB).toString());
        holder.binding.b.setText("Harga : " + list.get(position).get(HARGA).toString());
        if (hijab) holder.binding.d.setVisibility(View.GONE);
        holder.binding.d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fs.collection(JILBAB).document(list.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Jilbab berhasil dihapus", Toast.LENGTH_SHORT).show();
                            list.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        });

        fs.collection(JILBAB).document(list.get(position).getId()).collection(GAMBAR).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.binding.c.setLayoutManager(layoutManager);
                holder.binding.c.setAdapter(new Gambar(context, task.getResult().getDocuments(), list.get(position).getId()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Gambar extends RecyclerView.Adapter<Gambar.ViewHolder> {

        Context context;
        List<DocumentSnapshot> list;
        String id;

        public Gambar(Context context, List<DocumentSnapshot> list, String id) {
            this.context = context;
            this.list = list;
            this.id = id;
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            FragmentGambarBinding binding;

            ViewHolder(FragmentGambarBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(FragmentGambarBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (hijab) holder.binding.a.setVisibility(View.GONE);
            holder.binding.a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fs.collection(JILBAB).document(id).collection(GAMBAR).document(list.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                list.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Gambar Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            byte[] d = Base64.decode(list.get(position).get(GAMBAR).toString(), Base64.DEFAULT);
            holder.binding.b.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
