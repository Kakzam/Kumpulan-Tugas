package com.sekud.id.feature.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sekud.id.R;
import com.sekud.id.model.ModelLocation;

import java.util.List;

public class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.ViewHolder> {

    private List<ModelLocation> a;
    private boolean select;
    private onListener listener;

    public AdapterLocation(List<ModelLocation> a, boolean select, onListener listener) {
        this.a = a;
        this.select = select;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_location, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textTitle.setText(a.get(position).getTitle());

        holder.textOpenMaps.setOnClickListener(view -> listener.onClick("maps", position));

        holder.buttonUpdate.setOnClickListener(view -> {
            if (select) listener.onClick("update", position);
        });

        holder.buttonDelete.setOnClickListener(view -> {
            if (select) listener.onClick("delete", position);
        });

        if (!select) holder.linearLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textOpenMaps;
        ImageView buttonUpdate, buttonDelete;
        LinearLayout linearLayout;

        ViewHolder(View v) {
            super(v);
            textTitle = v.findViewById(R.id.adapter_location_title);
            textOpenMaps = v.findViewById(R.id.adapter_location_open_maps);
            buttonDelete = v.findViewById(R.id.adapter_location_delete);
            buttonUpdate = v.findViewById(R.id.adapter_location_update);
            linearLayout = v.findViewById(R.id.adapter_location_action);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}