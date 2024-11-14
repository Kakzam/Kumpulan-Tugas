package com.ayamgeprek.agh.pengguna;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayamgeprek.agh.DBConfig;
import com.ayamgeprek.agh.databinding.FragmentHomeBinding;
import com.ayamgeprek.agh.databinding.ListBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        config = new DBConfig(getContext(), DBConfig.AGH, null, DBConfig.DB_VERSION);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_menu_geprek", null);
        cr.moveToFirst();

        list.clear();
        list1.clear();
        list2.clear();
        list3.clear();

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            list.add(cr.getString(0));
            list1.add(cr.getString(1));
            list2.add(cr.getString(2));
            list3.add(cr.getString(3));
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

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewGeprek, viewHarga;
        AppCompatImageView viewMenu, viewPesan;

        ViewHolder(ListBinding binding) {
            super(binding.getRoot());
            viewGeprek = binding.listGeprek;
            viewHarga = binding.listHarga;
            viewMenu = binding.appCompatImageView;
            viewPesan = binding.listPesan;
            binding.listMinus.setVisibility(View.GONE);
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            byte[] d = Base64.decode(list3.get(position), Base64.DEFAULT);
            holder.viewGeprek.setText(list1.get(position));
            holder.viewHarga.setText(list2.get(position));
            holder.viewMenu.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.viewPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_pesan_geprek (id_geprek,east,price,image) VALUES(" +
                                    "'" + sharedPreferences.getString("ID", "") + "'," +
                                    "'" + list1.get(position) + "'," +
                                    "'" + list2.get(position) + "'," +
                                    "'" + list3.get(position) + "')");
                    Toast.makeText(getContext(), "Berhasil menambahkan pesanan " + list1.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}