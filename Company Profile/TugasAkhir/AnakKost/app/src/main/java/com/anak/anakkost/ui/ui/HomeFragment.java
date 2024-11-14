package com.anak.anakkost.ui.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anak.anakkost.databinding.FragmentHomeBinding;
import com.anak.anakkost.databinding.FragmentItemListDialogListDialogItemBinding;
import com.anak.anakkost.ui.DBConfig;
import com.anak.anakkost.ui.Data;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<Data> list = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        config = new DBConfig(getContext(), DBConfig.ANAK_KOST, null, DBConfig.DB_VERSION);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list.clear();
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_kost WHERE menu = 'KOST'", null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            list.add(new Data(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(0)));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.home.setLayoutManager(layoutManager);
        binding.home.setAdapter(new ItemAdapter());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView judul, harga, deskripsi, lokasi, hapus;
        AppCompatImageView kost;
        FragmentItemListDialogListDialogItemBinding binding;

        ViewHolder(FragmentItemListDialogListDialogItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            judul = binding.judul;
            harga = binding.harga;
            deskripsi = binding.deskripsi;
            lokasi = binding.lokasi;
            kost = binding.compat;
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new ViewHolder(FragmentItemListDialogListDialogItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            byte[] d = Base64.decode(list.get(position).getGambar(), Base64.DEFAULT);
            holder.binding.compat.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.binding.judul.setText(list.get(position).getJudul());
            holder.binding.harga.setText(list.get(position).getHarga());
            holder.binding.deskripsi.setText(list.get(position).getDeskripsi());
            holder.binding.lokasi.setText(list.get(position).getLokasi());
            holder.binding.hapus.setVisibility(View.GONE);

            Log.v("ZAM", list.get(position).getGambar());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}