package com.companyprofile.ristianingsih.lain;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.companyprofile.ristianingsih.R;
import com.companyprofile.ristianingsih.model.History;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.ViewHolder> {

    private final List<History> a;
    private Boolean select;
    private onListener listener;

    public AdapterHistory(List<History> a, Boolean select, onListener listener) {
        this.a = a;
        this.select = select;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textTitle.setText(a.get(position).getYear() + "");
        holder.textDescription.setText(a.get(position).getHistory());

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

        TextView textTitle, textDescription;
        Button buttonUpdate, buttonDelete;
        LinearLayout linearLayout;
        onListener listener;

        ViewHolder(View v) {
            super(v);
            textTitle = v.findViewById(R.id.title);
            textDescription = v.findViewById(R.id.description);
            buttonDelete = v.findViewById(R.id.button_delete);
            buttonUpdate = v.findViewById(R.id.button_edit);

            buttonUpdate.setOnClickListener(view -> listener.onClick("update", getAdapterPosition()));
            buttonDelete.setOnClickListener(view -> listener.onClick("delete", getAdapterPosition()));
            linearLayout = v.findViewById(R.id.linear);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}