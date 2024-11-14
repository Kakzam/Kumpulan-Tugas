package com.ayamgeprek.agh.pemilik;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.ayamgeprek.agh.R;
import com.ayamgeprek.agh.TambahGeprekActivity;
import com.ayamgeprek.agh.databinding.FragmentHomeBinding;
import com.ayamgeprek.agh.databinding.ListBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    DBConfig config;
    Cursor cr;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        config = new DBConfig(getContext(), DBConfig.AGH, null, DBConfig.DB_VERSION);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.dialog_select_2).setVisibility(View.VISIBLE);
        root.findViewById(R.id.dialog_select_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TambahGeprekActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        list.clear();
        list1.clear();
        list2.clear();
        list3.clear();

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_menu_geprek", null);
        cr.moveToFirst();

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
            viewPesan = binding.listMinus;
            binding.listPesan.setVisibility(View.GONE);
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
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_menu_geprek WHERE id = '" + list.get(position) + "'");
                    Toast.makeText(getContext(), "Min kamu berhasil hapus " + list1.get(position), Toast.LENGTH_SHORT).show();
                    list.remove(position);
                    list1.remove(position);
                    list2.remove(position);
                    list3.remove(position);
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}