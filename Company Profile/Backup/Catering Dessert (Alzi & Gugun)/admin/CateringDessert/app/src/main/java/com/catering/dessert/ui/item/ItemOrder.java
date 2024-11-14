package com.catering.dessert.ui.item;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ItemOrder extends RecyclerView.Adapter<ItemOrder.ViewHolder> {

    private List<String> id;
    private List<String> name;
    private List<String> phone;
    private List<String> address;
    private List<String> nik;
    private List<Integer> total;
    private List<Integer> pesanan;

    private onListener listener;

    public ItemOrder(List<String> id, List<String> name, List<String> phone, List<String> address, List<String> nik, List<Integer> total, List<Integer> pesanan, onListener listener) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nik = nik;
        this.total = total;
        this.pesanan = pesanan;
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
        holder.textConfirm.setOnClickListener(view -> listener.onClick(position, true));
        holder.textDetail.setOnClickListener(view -> listener.onClick(position, false));

        holder.textId.setText("ID : " + id.get(position));
        holder.textName.setText("Nama Pelanggan : " + name.get(position));
        holder.textPhone.setText("No. Handphone : " + phone.get(position));
        holder.textAddress.setText("Alamat : " + address.get(position));
        holder.textNik.setText("NIK : " + nik.get(position));
        holder.textOrder.setText("Jumlah : " + pesanan.get(position));
        holder.textTotal.setText("Total : " + total.get(position));
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

        TextView textId, textName, textPhone, textAddress, textNik, textOrder, textTotal, textConfirm, textDetail;

        ViewHolder(View v) {
            super(v);
            textId = v.findViewById(R.id.item_order_id);
            textName = v.findViewById(R.id.item_order_name);
            textPhone = v.findViewById(R.id.item_order_phone);
            textAddress = v.findViewById(R.id.item_order_address);
            textNik = v.findViewById(R.id.item_order_nik);
            textOrder = v.findViewById(R.id.item_order_order);
            textTotal = v.findViewById(R.id.item_order_total);
            textConfirm = v.findViewById(R.id.item_order_confirm);
            textDetail = v.findViewById(R.id.item_order_detail);
        }
    }

    public interface onListener {
        void onClick(int position, Boolean select);
    }
}