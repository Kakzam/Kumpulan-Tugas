package com.square.hijab.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.hijab.databinding.FragmentGambarBinding;
import com.square.hijab.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public static String JILBAB = "JILBAB";

    Context context;
    List<ModelHijab> list, gambar = new ArrayList<>();
    Boolean hijab = false;
    DBConfig config;
    Cursor cr;

    public Adapter(Context context, List<ModelHijab> list, Boolean hijab) {
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
        holder.binding.a.setText(list.get(position).getName());
        holder.binding.b.setText("Harga : " + list.get(position).getPrice());
        if (hijab) holder.binding.d.setVisibility(View.GONE);
        config = new DBConfig(context, DBConfig.HIJAB_SQUARE, null, DBConfig.DB_VERSION);
        holder.binding.d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.getWritableDatabase().execSQL("DELETE FROM tbl_hijab WHERE id = '" + list.get(position).getId() + "'");
                config.getWritableDatabase().execSQL("DELETE FROM tbl_gambar_hijab WHERE id_hijab = '" + list.get(position).getId() + "'");
                Toast.makeText(context, "Jilbab berhasil dihapus", Toast.LENGTH_SHORT).show();
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_gambar_hijab", null);
        cr.moveToFirst();

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            gambar.add(new ModelHijab(cr.getString(0), cr.getString(2), ""));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.binding.c.setLayoutManager(layoutManager);
        holder.binding.c.setAdapter(new Gambar(context, gambar, list.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Gambar extends RecyclerView.Adapter<Gambar.ViewHolder> {

        Context context;
        List<ModelHijab> list;
        String id;

        public Gambar(Context context, List<ModelHijab> list, String id) {
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
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_gambar_hijab WHERE id = '" + list.get(position).getId() + "'");
                    list.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context, "Gambar Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                }
            });

            byte[] d = Base64.decode(list.get(position).getName(), Base64.DEFAULT);
            holder.binding.b.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
