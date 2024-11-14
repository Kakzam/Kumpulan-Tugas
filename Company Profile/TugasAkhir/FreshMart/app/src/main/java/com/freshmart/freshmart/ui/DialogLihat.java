package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.DBConfig;
import com.freshmart.freshmart.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class DialogLihat extends BottomSheetDialogFragment {

    RecyclerView list;
    DBConfig config;
    Cursor cr;

    List<String> id = new ArrayList<>();
    List<String> nama = new ArrayList<>();
    List<String> harga = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_konfirmasi, container, false);

        list = root.findViewById(R.id.recycler);
        config = new DBConfig(getContext(), DBConfig.FRESH_MART, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_sayur_beli WHERE id_pemakai = " + getArguments().getString("id"), null);
        cr.moveToFirst();

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            id.add(cr.getString(0));
            nama.add(cr.getString(1));
            harga.add(cr.getString(2));
            qty.add(cr.getString(3));
            total.add(cr.getString(4));
            gambar.add(cr.getString(6));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(new Lihat());
        return root;
    }

    private class Lihat extends RecyclerView.Adapter<Lihat.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new Lihat.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            byte[] d = Base64.decode(gambar.get(position), Base64.DEFAULT);
            holder.imageSayuran.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.nama.setText(nama.get(position));
            holder.harga.setText("Harga : " + nama.get(position));
            holder.qty.setText("Qty : " + qty.get(position));
            holder.total.setText("Total : " + total.get(position));
            holder.selesai.setOnClickListener(view -> {
                config.getWritableDatabase().execSQL("DELETE FROM tbl_sayur_beli WHERE id = " + id.get(position));
                id.remove(position);
                nama.remove(position);
                harga.remove(position);
                qty.remove(position);
                total.remove(position);
                gambar.remove(position);
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return id.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran;
            TextView nama, harga, qty, total, selesai;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                nama = v.findViewById(R.id.nama);
                harga = v.findViewById(R.id.no_handphone);
                total = v.findViewById(R.id.total);
                qty = v.findViewById(R.id.qty);
                selesai = v.findViewById(R.id.lihat);
                selesai.setText("Selesai");
                v.findViewById(R.id.hapus).setVisibility(View.GONE);
            }
        }
    }

}