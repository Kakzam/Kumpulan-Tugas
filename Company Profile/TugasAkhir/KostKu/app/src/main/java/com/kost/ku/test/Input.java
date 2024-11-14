package com.kost.ku.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kost.ku.DBConfig;
import com.kost.ku.InputActivity;
import com.kost.ku.R;

import java.util.List;

public class Input extends RecyclerView.Adapter<Input.ViewHolder> {

    List<String> user;
    List<String> gambar;
    List<String> kost;
    List<String> alamat;
    List<String> jenis;
    List<String> fasilitas;
    List<String> phone;
    Context context;
    DBConfig config;

    public Input(Context context, List<String> user, List<String> gambar, List<String> kost, List<String> alamat, List<String> jenis, List<String> fasilitas, List<String> phone) {
        this.context = context;
        this.user = user;
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
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.test_input, parent, false));
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        config = new DBConfig(context, DBConfig.KOST_KU, null, DBConfig.DB_VERSION);
        holder.kost.setText(kost.get(position));
        holder.alamat.setText(alamat.get(position));
        holder.jenis.setText(jenis.get(position));
        holder.fasilitas.setText(fasilitas.get(position));
        holder.phone.setText(phone.get(position));
        byte[] decodedString = Base64.decode(gambar.get(position), Base64.DEFAULT);
        holder.kostImage.setImageBitmap(BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.getWritableDatabase().execSQL("DELETE FROM tbl_kost WHERE id = '" + user.get(position) + "'");
                user.remove(position);
                gambar.remove(position);
                kost.remove(position);
                alamat.remove(position);
                jenis.remove(position);
                fasilitas.remove(position);
                phone.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InputActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nama", kost.get(position));
                intent.putExtra("alamat", alamat.get(position));
                intent.putExtra("jenis", jenis.get(position));
                intent.putExtra("fasilitas", fasilitas.get(position));
                intent.putExtra("phone", phone.get(position));
                intent.putExtra("gambar", gambar.get(position));
                intent.putExtra("user", user.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView kostImage;
        TextView kost, alamat, jenis, fasilitas, phone, hapus, ubah;

        ViewHolder(View v) {
            super(v);
            kostImage = v.findViewById(R.id.gambar_kost);
            kost = v.findViewById(R.id.nama);
            phone = v.findViewById(R.id.phone);
            alamat = v.findViewById(R.id.alamat);
            jenis = v.findViewById(R.id.jenis);
            fasilitas = v.findViewById(R.id.fasilitas);
            hapus = v.findViewById(R.id.hapus);
            ubah = v.findViewById(R.id.ubah);
        }
    }
}
