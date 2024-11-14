package com.kost.ku.test;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kost.ku.R;

import java.util.List;

public class Kost extends RecyclerView.Adapter<Kost.ViewHolder> {

    List<String> gambar;
    List<String> kost;
    List<String> alamat;
    List<String> jenis;
    List<String> fasilitas;
    List<String> phone;

    public Kost(List<String> gambar, List<String> kost, List<String> alamat, List<String> jenis, List<String> fasilitas, List<String> phone) {
        this.gambar = gambar;
        this.kost = kost;
        this.alamat = alamat;
        this.jenis = jenis;
        this.fasilitas = fasilitas;
        this.phone = phone;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false));
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.kost.setText(kost.get(position));
        holder.alamat.setText(alamat.get(position));
        holder.jenis.setText("Jenis : " + jenis.get(position));
        holder.fasilitas.setText(fasilitas.get(position));
        holder.phone.setText("Hubungi : " + phone.get(position));
        byte[] decodedString = Base64.decode(gambar.get(position), Base64.DEFAULT);
        holder.kostImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
    }

    @Override
    public int getItemCount() {
        return kost.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView kostImage;
        TextView kost, alamat, jenis, fasilitas, phone;

        ViewHolder(View v) {
            super(v);
            kostImage = v.findViewById(R.id.gambar_kost);
            kost = v.findViewById(R.id.nama);
            phone = v.findViewById(R.id.phone);
            alamat = v.findViewById(R.id.alamat);
            jenis = v.findViewById(R.id.jenis);
            fasilitas = v.findViewById(R.id.fasilitas);
        }
    }
}
