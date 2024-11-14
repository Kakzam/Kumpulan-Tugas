package com.anak.anakkost.ui.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anak.anakkost.databinding.FragmentDashboardBinding;
import com.anak.anakkost.databinding.FragmentItemListDialogListDialogItemBinding;
import com.anak.anakkost.ui.DBConfig;
import com.anak.anakkost.ui.Data;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<Data> list = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        config = new DBConfig(getContext(), DBConfig.ANAK_KOST, null, DBConfig.DB_VERSION);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        list.clear();
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_kost WHERE menu = 'APARTEMEN'", null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            list.add(new Data(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), cr.getString(0)));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.dashboard.setLayoutManager(layoutManager);
        binding.dashboard.setAdapter(new ItemAdapter());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView judul, harga, deskripsi, lokasi;
        AppCompatImageView kost, hapus;
//        FragmentItemListDialogListDialogItemBinding binding;

        ViewHolder(FragmentItemListDialogListDialogItemBinding binding) {
            super(binding.getRoot());
//            this.binding = binding;
            judul = binding.judul;
            harga = binding.harga;
            deskripsi = binding.deskripsi;
            lokasi = binding.lokasi;
            hapus = binding.hapus;
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
            holder.kost.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.judul.setText(list.get(position).getJudul());
            holder.harga.setText(list.get(position).getHarga());
            holder.deskripsi.setText(list.get(position).getDeskripsi());
            holder.lokasi.setText(list.get(position).getLokasi());
            holder.hapus.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}