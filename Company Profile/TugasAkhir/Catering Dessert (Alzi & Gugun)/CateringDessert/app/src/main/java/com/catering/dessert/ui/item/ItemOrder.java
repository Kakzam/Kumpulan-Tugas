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

public class ItemOrder extends RecyclerView.Adapter<ItemOrder.ViewHolder> {

    private List<String> idItem;
    private List<String> idOrder;
    private List<String> name;
    private List<String> price;
    private List<Bitmap> image;
    private List<String> imageBase64;
    private List<String> total;
    private List<String> number;
    private onListener listener;

    public ItemOrder(List<String> idItem, List<String> idOrder, List<String> name, List<String> price, List<Bitmap> image, List<String> imageBase64, List<String> total, List<String> number, onListener listener) {
        this.idItem = idItem;
        this.idOrder = idOrder;
        this.name = name;
        this.price = price;
        this.image = image;
        this.imageBase64 = imageBase64;
        this.total = total;
        this.number = number;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageBitmap(image.get(position));
        holder.textName.setText(name.get(position));
//        holder.textName.setText(name.get(position) + "\n(" + idOrder.get(position) + ")");
        holder.textPrice.setText("Harga : " + getCurrency(Integer.parseInt(price.get(position))));
        holder.textAmount.setText("Jumlah : " + number.get(position));
        holder.textTotal.setText("Total : " + getCurrency(Integer.parseInt(total.get(position))));
        holder.textDelete.setOnClickListener(view -> listener.onClick(position));
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
        return idOrder.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textPrice, textAmount, textTotal, textDelete;
        ImageView imageView;

        ViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.item_order_name);
            textPrice = v.findViewById(R.id.item_order_price);
            textAmount = v.findViewById(R.id.item_order_amount);
            textTotal = v.findViewById(R.id.item_order_total);
            textDelete = v.findViewById(R.id.item_order_delete);
            imageView = v.findViewById(R.id.item_order_image);
        }
    }

    public interface onListener {
        void onClick(int position);
    }
}