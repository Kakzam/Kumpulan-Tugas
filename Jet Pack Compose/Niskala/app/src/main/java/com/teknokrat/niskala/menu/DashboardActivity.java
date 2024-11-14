package com.teknokrat.niskala.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.teknokrat.niskala.R;
import com.teknokrat.niskala.databinding.ActivityDashboardBinding;
import com.teknokrat.niskala.dll.Base;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends Base {

    private ActivityDashboardBinding binding;
    private Boolean keluar = false;
    private Boolean coba = false;
    private List<String> name = new ArrayList<>();
    private MyAdapter adapter;
    private List<Boolean> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setLog("===============  " + getClass().getSimpleName() + " ===============");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        name.add("Januari");
        name.add("Februari");
        name.add("Maret");
        name.add("April");
        name.add("Mei");
        name.add("Juni");
        name.add("July");
        name.add("Agustus");
        name.add("September");
        name.add("Oktober");
        name.add("November");
        name.add("Desember");


        binding.topBar.setOnClickListener(view -> {
            if (coba) {
                clearO();
            } else coba = true;
        });

        binding.namaMahasiswa.setText("Hi, " + getN());
        binding.todayAvaiable.setOnClickListener(view -> setIntent(ListTodayActivity.class, "", true));
        binding.seeAll.setOnClickListener(view -> setIntent(ListTodayActivity.class, "", false));
        binding.bulan.setText("All bookings for " + name.get(month));

        db.collection("niskala").document("user").collection("-").document(getId()).collection("-").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() > 0){
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                            binding.imageBooking.setImageBitmap(imageBase64(documentSnapshot.get("gambar").toString()));
                            binding.textTanggal.setText(documentSnapshot.get("tanggal").toString() + " " + name.get(Integer.parseInt(documentSnapshot.get("bulan").toString())) + " " + documentSnapshot.get("tahun").toString());
                            binding.textName.setText(documentSnapshot.get("title").toString());
                            binding.textDeskripsi.setText(documentSnapshot.get("deskripsi").toString());
                        }
                    } else {
                        binding.imageBooking.setImageResource(R.drawable.ic_logo);
                        binding.textTanggal.setText(name.get(month));
                        binding.textName.setText("Kosong");
                        binding.textDeskripsi.setText("Kosong");
                    }
                })
                .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));

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

                                    if (check) dataList.add(true);
                                    else dataList.add(false);

                                    if ((size-1) == position){
                                        adapter = new MyAdapter(queryDocumentSnapshots.getDocuments(), dataList, getApplicationContext());
                                        binding.recycler.setAdapter(adapter);
                                        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                                        binding.recycler.setLayoutManager(layoutManager);
                                    }

                                    position++;
                                }
                            })
                            .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));
                })
                .addOnFailureListener(e -> setToast("Data Gagal Diambil, Silahkan Periksa Kembali Koneksi Internet Anda"));
    }

    @Override
    public void onBackPressed() {
        if (keluar) finishAffinity();
        else {
            keluar = true;
            setToast("Jika ingin keluar silahkan tekan kembali sekali lagi");
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<DocumentSnapshot> dataList;
        private List<Boolean> list;
        private Context context;

        public MyAdapter(List<DocumentSnapshot> dataList, List<Boolean> list, Context context) {
            this.dataList = dataList;
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room_avaiable, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            holder.title.setText(dataList.get(position).get("title").toString());
            holder.deskripsi.setText(dataList.get(position).get("deskripsi").toString());
            if (list.get(position)) holder.avaiable.setVisibility(View.GONE);
            else holder.unavaiable.setVisibility(View.GONE);
            holder.image.setImageBitmap(new Base().imageBase64(dataList.get(position).get("gambar").toString()));
            holder.cardView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailAvaiableActivity.class);
                intent.putExtra("DATA_1", dataList.get(position).getId());
                intent.putExtra("DATA_2", list.get(position));
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