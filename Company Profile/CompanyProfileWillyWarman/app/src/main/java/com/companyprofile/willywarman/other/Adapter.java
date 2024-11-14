package com.companyprofile.willywarman.other;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.companyprofile.willywarman.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<ModelBerita> a;
    private boolean select;
    private onListener listener;

    public Adapter(List<ModelBerita> a, boolean select, onListener listener) {
        this.a = a;
        this.select = select;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textDate.setText(a.get(position).getId());
        holder.textTitle.setText(a.get(position).getJudul());
        holder.textDescription.setText(a.get(position).getBerita());

        holder.relativeLayout.setOnClickListener(view -> {
            listener.onClick("select", position);
        });

        holder.buttonUpdate.setOnClickListener(view -> {
            listener.onClick("update", position);
        });

        holder.buttonDelete.setOnClickListener(view -> {
            listener.onClick("delete", position);
        });

        if (!select) {
            holder.buttonDelete.setVisibility(View.GONE);
            holder.buttonUpdate.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDate, textDescription;
        Button buttonUpdate, buttonDelete;
        RelativeLayout relativeLayout;

        ViewHolder(View v) {
            super(v);
            textDate = v.findViewById(R.id.date);
            textTitle = v.findViewById(R.id.title);
            textDescription = v.findViewById(R.id.deskripsi);
            buttonDelete = v.findViewById(R.id.delete);
            buttonUpdate = v.findViewById(R.id.update);
            relativeLayout = v.findViewById(R.id.item);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}