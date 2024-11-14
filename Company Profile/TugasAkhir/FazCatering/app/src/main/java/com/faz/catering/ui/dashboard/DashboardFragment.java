package com.faz.catering.ui.dashboard;

import android.annotation.SuppressLint;
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

import com.faz.catering.databinding.FragmentDashboardBinding;
import com.faz.catering.databinding.FragmentListBinding;
import com.faz.catering.ui.BentukData;
import com.faz.catering.ui.DBConfig;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<BentukData> db = new ArrayList<>();
    Cursor cr;
    DBConfig config;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        config = new DBConfig(getContext(), DBConfig.FAZ_CATERING, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_menu_catering WHERE menu = 'Langganan'", null);
        cr.moveToFirst();
        db.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            db.add(new BentukData(cr.getString(0), cr.getString(1), cr.getString(3), cr.getString(2), cr.getString(6), cr.getString(5), cr.getString(4)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.catering.setLayoutManager(layoutManager);
        binding.catering.setAdapter(new CateringAdapter());
        binding.menuTambahCatering.setVisibility(View.GONE);
        return root;
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
        }

        @Override
        public int getItemCount() {
            return db.size();
        }
    }
}