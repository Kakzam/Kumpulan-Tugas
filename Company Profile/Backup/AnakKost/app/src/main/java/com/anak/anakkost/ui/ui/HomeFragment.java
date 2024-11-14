package com.anak.anakkost.ui.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.anak.anakkost.R;
import com.anak.anakkost.TambahActivity;
import com.anak.anakkost.databinding.FragmentHomeBinding;
import com.anak.anakkost.databinding.FragmentItemListDialogListDialogItemBinding;
import com.anak.anakkost.ui.Data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<Data> list = new ArrayList<>();
    FirebaseFirestore cloud;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cloud = FirebaseFirestore.getInstance();
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        cloud.collection("KOST").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();
                    for (DocumentSnapshot isi : task.getResult().getDocuments()) {
                        list.add(new Data(isi.get("k1").toString(), isi.get("k2").toString(), isi.get("k3").toString(), isi.get("k4").toString(), isi.get("k5").toString(), isi.getId()));
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.home.setLayoutManager(layoutManager);
                    binding.home.setAdapter(new ItemAdapter());
                } else
                    Toast.makeText(getContext(), "Koneksimu mati cuy", Toast.LENGTH_SHORT).show();
            }
        });

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