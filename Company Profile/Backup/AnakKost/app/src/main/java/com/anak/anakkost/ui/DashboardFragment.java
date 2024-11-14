package com.anak.anakkost.ui;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anak.anakkost.R;
import com.anak.anakkost.TambahActivity;
import com.anak.anakkost.databinding.FragmentDashboardBinding;
import com.anak.anakkost.databinding.FragmentItemListDialogListDialogItemBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<Data> list = new ArrayList<>();
    FirebaseFirestore cloud;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        cloud = FirebaseFirestore.getInstance();
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        root.findViewById(R.id.fragment_add).setVisibility(View.VISIBLE);
        root.findViewById(R.id.fragment_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), TambahActivity.class).putExtra("COLLECTION", "APARTEMEN"));
            }
        });

        cloud.collection("APARTEMEN").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();
                    for (DocumentSnapshot isi : task.getResult().getDocuments()) {
                        list.add(new Data(isi.get("k1").toString(), isi.get("k2").toString(), isi.get("k3").toString(), isi.get("k4").toString(), isi.get("k5").toString(), isi.getId()));
                    }

//                    has.put("k1", judul.getText().toString());
//                    has.put("k2", harga.getText().toString());
//                    has.put("k3", deskripsi.getText().toString());
//                    has.put("k4", lokasi.getText().toString());
//                    has.put("k5", kost);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.dashboard.setLayoutManager(layoutManager);
                    binding.dashboard.setAdapter(new ItemAdapter());
                } else
                    Toast.makeText(getContext(), "Koneksimu mati cuy", Toast.LENGTH_SHORT).show();
            }
        });

//        list.addAll(cloud.collection("APARTEMEN").get().getResult().getDocuments());
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (list.size() == 0) Toast.makeText(getContext(), "Tambah Data Apartemen", Toast.LENGTH_SHORT).show();
//            }
//        }, 2000);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView judul, harga, deskripsi, lokasi, hapus;
        AppCompatImageView kost;
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
            holder.hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cloud.collection("APARTEMEN").document(list.get(position).getDelete()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Menghapus " + list.get(position).getJudul(), Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged();
                            } else
                                Toast.makeText(getContext(), "Gagal menghapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            Log.v("ZAM", list.get(position).getGambar());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}