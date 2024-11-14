package com.catering.dessert.ui.item;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ItemDessert extends RecyclerView.Adapter<ItemDessert.ViewHolder> {

    private List<String> dessert;
    private List<String> price;
    private List<Bitmap> image;
    private onListener listener;

    public ItemDessert(List<String> dessert, List<String> price, List<Bitmap> image, onListener listener) {
        this.dessert = dessert;
        this.image = image;
        this.price = price;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dessert, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textOrder.setOnClickListener(view -> listener.onClick(position));
        holder.textPrice.setText(getCurrency(Integer.parseInt(price.get(position))));
        holder.textName.setText(dessert.get(position));
        holder.imageView.setImageBitmap(image.get(position));
    }

    public String getCurrency(int currency) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        int len = kursIndonesia.format(currency).length();
        return new StringBuffer(kursIndonesia.format(currency)).delete(len - 3, len).toString();
    }

    @Override
    public int getItemCount() {
        return dessert.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textPrice, textOrder;
        ImageView imageView;

        ViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.item_dessert_name);
            textPrice = v.findViewById(R.id.item_dessert_harga);
            textOrder = v.findViewById(R.id.item_dessert_order);
            imageView = v.findViewById(R.id.item_dessert_image);
        }
    }

    public interface onListener {
        void onClick(int position);
    }
}