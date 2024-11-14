package com.faz.catering.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faz.catering.MainActivity2;
import com.faz.catering.TambahActivity;
import com.faz.catering.databinding.FragmentDashboardBinding;
import com.faz.catering.databinding.FragmentListBinding;
import com.faz.catering.ui.BentukData;
import com.faz.catering.ui.home.HomeHarianFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DashboardLanggananFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<BentukData> db = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        binding.menuTambahCatering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("NAME", "Langganan");
                startActivity(intent);
            }
        });

        FirebaseFirestore.getInstance().collection("Langganan").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().getDocuments().size() > 0) {
                        db.clear();
                        for (DocumentSnapshot getData : task.getResult().getDocuments())
                            db.add(new BentukData(getData.getId(), getData.get("nama").toString(), getData.get("harga").toString(), getData.get("deskripsi").toString(), getData.get("lokasi").toString(), getData.get("handphone").toString(), getData.get("gambar").toString()));

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        binding.catering.setLayoutManager(layoutManager);
                        binding.catering.setAdapter(new CateringAdapter());
                    } else {
                        Intent intent = new Intent(getContext(), TambahActivity.class);
                        intent.putExtra("NAME", "Langganan");
                        startActivity(intent);
                    }
                } else Log.v("Dashboard : ", "Koneksinya");
            }
        });

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
                    FirebaseFirestore.getInstance().collection("Langganan").document(db.get(position).getCatering()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                db.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                    });
                }
            });

        }

        @Override
        public int getItemCount() {
            return db.size();
        }
    }
}