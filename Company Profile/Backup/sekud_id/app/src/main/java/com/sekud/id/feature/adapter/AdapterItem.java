package com.sekud.id.feature.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sekud.id.R;
import com.sekud.id.model.ModelItem;

import java.util.List;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {

    private List<ModelItem> a;
    private boolean select;
    private onListener listener;

    public AdapterItem(List<ModelItem> a, boolean select, onListener listener) {
        this.a = a;
        this.select = select;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTitle.setText(a.get(position).getTitle());
        holder.textKey.setText("Key : " + a.get(position).getId());
        holder.textUsage.setText("Pemakaian : " + a.get(position).getUsage());
        holder.textStatus.setText("Status : " + a.get(position).getStatus());
        holder.textPrice.setText("Harga : Rp" + a.get(position).getPrice());

        holder.textCopy.setOnClickListener(view -> {

        });

        holder.textBuy.setOnClickListener(view -> {
            if (select) listener.onClick("buy", position);
        });

        holder.textDetail.setOnClickListener(view -> {
            if (select) listener.onClick("detail", position);
        });

        holder.buttonUpdate.setOnClickListener(view -> {
            if (select) listener.onClick("update", position);
        });

        holder.buttonDelete.setOnClickListener(view -> {
            if (select) listener.onClick("delete", position);
        });

        if (!select) {
            holder.buttonDelete.setVisibility(View.GONE);
            holder.buttonUpdate.setVisibility(View.GONE);
        } else {
            holder.textBuy.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textKey, textCopy, textUsage, textStatus, textPrice, textBuy, textDetail;
        ImageView buttonUpdate, buttonDelete, image;

        ViewHolder(View v) {
            super(v);
            textTitle = v.findViewById(R.id.adapter_item_title);
            textKey = v.findViewById(R.id.adapter_item_key);
            textCopy = v.findViewById(R.id.adapter_item_copy_key);
            textUsage = v.findViewById(R.id.adapter_item_usage);
            textStatus = v.findViewById(R.id.adapter_item_status);
            textPrice = v.findViewById(R.id.adapter_item_price);
            textBuy = v.findViewById(R.id.adapter_item_buy);
            textDetail = v.findViewById(R.id.adapter_item_detail);
            buttonDelete = v.findViewById(R.id.adapter_item_delete);
            buttonUpdate = v.findViewById(R.id.adapter_item_update);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}