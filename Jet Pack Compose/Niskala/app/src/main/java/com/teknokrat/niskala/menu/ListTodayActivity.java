package com.teknokrat.niskala.menu;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.teknokrat.niskala.R;
import com.teknokrat.niskala.databinding.ActivityListTodayBinding;
import com.teknokrat.niskala.dll.Base;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListTodayActivity extends Base {

    private ActivityListTodayBinding binding;
    private MyAdapter adapter;
    private List<DocumentSnapshot> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListTodayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (getIntent().getBooleanExtra("DATA_2", false)){
            db.collection("niskala").document("ruangan").collection("-").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        db.collection("niskala").document("pinjam").collection(day + "-" + month + "-" + year).get()
                                .addOnSuccessListener(queryDocumentSnapshotsPinjam -> {
                                    int size = queryDocumentSnapshots.getDocuments().size();
                                    int position = 0;
                                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()){
                                        boolean check = false;
                                        for (DocumentSnapshot documentPinjam : queryDocumentSnapshotsPinjam.getDocuments()){
                                            if (document.getId().equals(documentPinjam.get("id").toString())) check = true;
                                        }

                                        if (!check){
                                            dataList.add(document);
                                        }

                                        if ((size-1) == position){
                                            adapter = new MyAdapter(dataList, getApplicationContext(), getIntent().getBooleanExtra("DATA_2", false));
                                            binding.recyclerView.setAdapter(adapter);
                                            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                                            binding.recyclerView.setLayoutManager(layoutManager);
                                        }

                                        position++;
                                    }
                                })
                                .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));
                    })
                    .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));
        } else {
            db.collection("niskala").document("user").collection("-").document(getId()).collection("-").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        adapter = new MyAdapter(queryDocumentSnapshots.getDocuments(), getApplicationContext(), getIntent().getBooleanExtra("DATA_2", false));
                        binding.recyclerView.setAdapter(adapter);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                        binding.recyclerView.setLayoutManager(layoutManager);
                    })
                    .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));
        }
    }

    @Override
    public void onBackPressed() {
        setIntent(DashboardActivity.class);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<DocumentSnapshot> dataList;
        private Context context;
        private Boolean check;

        public MyAdapter(List<DocumentSnapshot> dataList, Context context, Boolean check) {
            this.dataList = dataList;
            this.context = context;
            this.check = check;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_avaiable, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.title.setText(dataList.get(position).get("title").toString());
            holder.deskripsi.setText(dataList.get(position).get("deskripsi").toString());
            holder.avaiable.setVisibility(View.GONE);
            holder.unavaiable.setVisibility(View.GONE);
            holder.image.setImageBitmap(new Base().imageBase64(dataList.get(position).get("gambar").toString()));
            holder.cardView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("DATA_1", dataList.get(position).getId());
                intent.putExtra("DATA_2", check);
                startActivity(intent);
                finish();
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView title, deskripsi, avaiable, unavaiable;
            private ImageView image;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                deskripsi = itemView.findViewById(R.id.deskripsi);
                avaiable = itemView.findViewById(R.id.avaiable);
                unavaiable = itemView.findViewById(R.id.unavaiable);
                image = itemView.findViewById(R.id.image);
                cardView = itemView.findViewById(R.id.card_select);
            }
        }
    }
}