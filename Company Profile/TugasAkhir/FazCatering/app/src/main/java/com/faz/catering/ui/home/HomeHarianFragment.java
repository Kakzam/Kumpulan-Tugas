package com.faz.catering.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faz.catering.MainActivity2;
import com.faz.catering.TambahActivity;
import com.faz.catering.databinding.FragmentHomeBinding;
import com.faz.catering.databinding.FragmentListBinding;
import com.faz.catering.ui.BentukData;
import com.faz.catering.ui.DBConfig;

import java.util.ArrayList;
import java.util.List;

public class HomeHarianFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<BentukData> db = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        config = new DBConfig(getContext(), DBConfig.FAZ_CATERING, null, DBConfig.DB_VERSION);
        binding.menuTambahCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TambahActivity.class);
                intent.putExtra("NAME", "Harian");
                startActivity(intent);
            }
        });

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_menu_catering WHERE menu = 'Harian'", null);
        cr.moveToFirst();
        db.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            db.add(new BentukData(cr.getString(0), cr.getString(1), cr.getString(3), cr.getString(2), cr.getString(6), cr.getString(5), cr.getString(4)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.catering.setLayoutManager(layoutManager);
        binding.catering.setAdapter(new CateringAdapter());
        if (cr.getCount() == 0) {
            Intent intent = new Intent(getContext(), TambahActivity.class);
            intent.putExtra("NAME", "Harian");
            startActivity(intent);
        }
        return binding.getRoot();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        FragmentListBinding binding;

        ViewHolder(FragmentListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    private class CateringAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(FragmentListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            byte[] d = Base64.decode(db.get(position).getGambar(), Base64.DEFAULT);
            holder.binding.listNama.setText(db.get(position).getNama());
            holder.binding.listDeskripsi.setText(db.get(position).getDeskripsi());
            holder.binding.listHarga.setText(db.get(position).getHarga());
            holder.binding.listHandphone.setText(db.get(position).getPhone());
            holder.binding.listLokasi.setText(db.get(position).getLokasi());
            holder.binding.listGambar.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));

            holder.binding.listHapus.setVisibility(View.VISIBLE);
            holder.binding.listHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_menu_catering WHERE id = '" + db.get(position).getCatering() + "'");
                    db.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return db.size();
        }
    }
}