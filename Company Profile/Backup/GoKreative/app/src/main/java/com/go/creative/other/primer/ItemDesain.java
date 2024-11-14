package com.go.creative.other.primer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.go.creative.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ItemDesain extends RecyclerView.Adapter<ItemDesain.ViewHolder> {

    List<String> key;
    List<String> nama;
    List<String> harga;
    List<String> terjual;
    List<String> gambar;
    Context context;

    public ItemDesain(List<String> nama,
                      List<String> key,
                      List<String> harga,
                      List<String> terjual,
                      List<String> gambar, Context context) {
        this.key = key;
        this.nama = nama;
        this.harga = harga;
        this.terjual = terjual;
        this.gambar = gambar;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_desain, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textHapus.setOnClickListener(view -> FirebaseFirestore.getInstance().collection("go_creative").document("desain").collection("-").document(key.get(position)).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(context, "Kamu berhasil menghapus data", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context, "Kamu gagal menghapus data", Toast.LENGTH_LONG).show();
        }));
        holder.textNama.setText(nama.get(position));
        holder.textHarga.setText("Harga : " + getCurrency(Integer.parseInt(harga.get(position))));
        holder.textTerjual.setText("Terjual " + terjual.get(position));
        byte[] decode = Base64.decode(gambar.get(position), Base64.DEFAULT);
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(decode, 0, decode.length));
    }

    public String getCurrency(int currency) {
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("IDR. ");
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        int len = kursIndonesia.format(currency).length();
        return new StringBuffer(kursIndonesia.format(currency)).delete(len - 3, len).toString();
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textNama, textHarga, textTerjual, textHapus;
        ImageView imageView;

        ViewHolder(View v) {
            super(v);
            textNama = v.findViewById(R.id.item_desain_nama);
            textHarga = v.findViewById(R.id.item_desain_harga);
            textTerjual = v.findViewById(R.id.item_desain_terjual);
            textHapus = v.findViewById(R.id.item_desain_hapus);
            imageView = v.findViewById(R.id.item_desain_gambar);
        }
    }
}