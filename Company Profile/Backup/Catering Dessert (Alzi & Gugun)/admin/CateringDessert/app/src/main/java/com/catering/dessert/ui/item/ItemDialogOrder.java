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

public class ItemDialogOrder extends RecyclerView.Adapter<ItemDialogOrder.ViewHolder> {

    private List<String> id;
    private List<String> name;
    private List<Bitmap> photo;
    private List<String> number;
    private List<String> total;
    private List<String> price;
    private onListener listener;

    public ItemDialogOrder(List<String> id, List<String> name, List<Bitmap> photo, List<String> number, List<String> total, List<String> price, onListener listener) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.number = number;
        this.total = total;
        this.price = price;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_order, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textName.setText(name.get(position));
        holder.textPrice.setText("Harga : " + getCurrency(Integer.parseInt(price.get(position))));
        holder.textAmount.setText("Qty : " + number.get(position));
        holder.textTotal.setText("Total Barang : " + getCurrency(Integer.parseInt(total.get(position))));
        holder.imageView.setImageBitmap(photo.get(position));
        holder.textFinish.setOnClickListener(view -> listener.onClick(position));
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
        return id.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textPrice, textAmount, textTotal, textFinish;
        ImageView imageView;

        ViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.item_order_name);
            textPrice = v.findViewById(R.id.item_order_price);
            textAmount = v.findViewById(R.id.item_order_amount);
            textTotal = v.findViewById(R.id.item_order_total);
            textFinish = v.findViewById(R.id.item_order_finish);
            imageView = v.findViewById(R.id.item_order_image);
        }
    }

    public interface onListener {
        void onClick(int position);
    }
}