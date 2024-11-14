package com.go.creative.other.sekunder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.go.creative.R;

import java.util.List;

public class ItemSosmed extends RecyclerView.Adapter<ItemSosmed.ViewHolder> {

    List<String> sosmed;
    List<String> akun;
    List<String> link;
    List<String> key;
    Context context;

    public ItemSosmed(List<String> key, List<String> sosmed, List<String> akun, List<String> link, Context context) {
        this.key = key;
        this.sosmed = sosmed;
        this.akun = akun;
        this.link = link;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sosmed, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textSosmed.setText(sosmed.get(position));
        holder.textAkun.setText(akun.get(position));
        holder.hapus.setVisibility(View.GONE);

        holder.buka.setOnClickListener(view -> {
            Uri uri = Uri.parse(link.get(position));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return sosmed.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textSosmed, textAkun, hapus, buka;

        ViewHolder(View v) {
            super(v);
            textSosmed = v.findViewById(R.id.item_sosmed);
            textAkun = v.findViewById(R.id.item_sosmed_akun);
            hapus = v.findViewById(R.id.item_sosmed_hapus);
            buka = v.findViewById(R.id.item_sosmed_open);
        }
    }
}