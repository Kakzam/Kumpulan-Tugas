package com.eventic.lampungtourism;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eventic.lampungtourism.databinding.ActivityCustomerBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private ActivityCustomerBinding binding;
    FirebaseFirestore firestore;
    Boolean filter = false;

    List<String> getId = new ArrayList<>();
    List<String> getTitle = new ArrayList<>();
    List<String> getDescription = new ArrayList<>();
    List<String> getPrice = new ArrayList<>();
    List<String> getType = new ArrayList<>();
    List<Bitmap> getGambar = new ArrayList<>();
    List<Bitmap> getGambar2 = new ArrayList<>();

    List<String> getFilterId = new ArrayList<>();
    List<String> getFilterTitle = new ArrayList<>();
    List<String> getFilterDescription = new ArrayList<>();
    List<String> getFilterPrice = new ArrayList<>();
    List<String> getFilterType = new ArrayList<>();
    List<Bitmap> getFilterGambar = new ArrayList<>();
    List<Bitmap> getFilterGambar2 = new ArrayList<>();

    List<DocumentSnapshot> documentSnapshotList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestore = FirebaseFirestore.getInstance();
        binding = ActivityCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonTambah.setVisibility(View.GONE);

        firestore.collection("tourism").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                documentSnapshotList.addAll(task.getResult().getDocuments());
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    getId.add(documentSnapshot.getId());
                    getTitle.add(documentSnapshot.get("judul").toString());
                    getDescription.add(documentSnapshot.get("description").toString());
                    getPrice.add(documentSnapshot.get("price").toString());
                    if (documentSnapshot.get("type").toString().equals("1"))
                        getType.add("Lokasi Hits");
                    if (documentSnapshot.get("type").toString().equals("2"))
                        getType.add("Event Soon");
                    if (documentSnapshot.get("type").toString().equals("3")) getType.add("Makanan");

                    byte[] image = Base64.decode(documentSnapshot.get("image").toString(), Base64.DEFAULT);
                    getGambar.add(BitmapFactory.decodeByteArray(image, 0, image.length));
                    byte[] image2 = Base64.decode(documentSnapshot.get("image2").toString(), Base64.DEFAULT);
                    getGambar2.add(BitmapFactory.decodeByteArray(image2, 0, image2.length));
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(new Tourism());
            }
        });


        binding.buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = false;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(new Tourism());
            }
        });

        binding.buttonEat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = true;
                getFilterId.clear();
                getFilterTitle.clear();
                getFilterDescription.clear();
                getFilterPrice.clear();
                getFilterType.clear();
                getFilterGambar.clear();
                getFilterGambar2.clear();
                for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                    if (documentSnapshot.get("type").toString().equals("3")) {
                        getFilterId.add(documentSnapshot.getId());
                        getFilterTitle.add(documentSnapshot.get("judul").toString());
                        getFilterDescription.add(documentSnapshot.get("description").toString());
                        getFilterPrice.add(documentSnapshot.get("price").toString());
                        getFilterType.add("Makanan");
                        byte[] image = Base64.decode(documentSnapshot.get("image").toString(), Base64.DEFAULT);
                        getFilterGambar.add(BitmapFactory.decodeByteArray(image, 0, image.length));
                        byte[] image2 = Base64.decode(documentSnapshot.get("image2").toString(), Base64.DEFAULT);
                        getFilterGambar2.add(BitmapFactory.decodeByteArray(image2, 0, image2.length));
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(new Tourism());
            }
        });

        binding.buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = true;
                getFilterId.clear();
                getFilterTitle.clear();
                getFilterDescription.clear();
                getFilterPrice.clear();
                getFilterType.clear();
                getFilterGambar.clear();
                getFilterGambar2.clear();
                for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                    if (documentSnapshot.get("type").toString().equals("2")) {
                        getFilterId.add(documentSnapshot.getId());
                        getFilterTitle.add(documentSnapshot.get("judul").toString());
                        getFilterDescription.add(documentSnapshot.get("description").toString());
                        getFilterPrice.add(documentSnapshot.get("price").toString());
                        getFilterType.add("Event Soon");
                        byte[] image = Base64.decode(documentSnapshot.get("image").toString(), Base64.DEFAULT);
                        getFilterGambar.add(BitmapFactory.decodeByteArray(image, 0, image.length));
                        byte[] image2 = Base64.decode(documentSnapshot.get("image2").toString(), Base64.DEFAULT);
                        getFilterGambar2.add(BitmapFactory.decodeByteArray(image2, 0, image2.length));
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(new Tourism());
            }
        });

        binding.buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter = true;
                getFilterId.clear();
                getFilterTitle.clear();
                getFilterDescription.clear();
                getFilterPrice.clear();
                getFilterType.clear();
                getFilterGambar.clear();
                getFilterGambar2.clear();
                for (DocumentSnapshot documentSnapshot : documentSnapshotList) {
                    if (documentSnapshot.get("type").toString().equals("1")) {
                        getFilterId.add(documentSnapshot.getId());
                        getFilterTitle.add(documentSnapshot.get("judul").toString());
                        getFilterDescription.add(documentSnapshot.get("description").toString());
                        getFilterPrice.add(documentSnapshot.get("price").toString());
                        getFilterType.add("Lokasi Hits");
                        byte[] image = Base64.decode(documentSnapshot.get("image").toString(), Base64.DEFAULT);
                        getFilterGambar.add(BitmapFactory.decodeByteArray(image, 0, image.length));
                        byte[] image2 = Base64.decode(documentSnapshot.get("image2").toString(), Base64.DEFAULT);
                        getFilterGambar2.add(BitmapFactory.decodeByteArray(image2, 0, image2.length));
                    }
                }

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                binding.recyclerView.setLayoutManager(layoutManager);
                binding.recyclerView.setAdapter(new Tourism());
            }
        });

    }

    private class Tourism extends RecyclerView.Adapter<Tourism.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tourism, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (filter) {
                holder.image.setImageBitmap(getFilterGambar.get(position));
                holder.image2.setImageBitmap(getFilterGambar2.get(position));
                holder.setTitle.setText(getFilterTitle.get(position));
                holder.setDescription.setText(getFilterDescription.get(position));
                holder.setPrice.setText(getFilterPrice.get(position));
                holder.setType.setText(getFilterType.get(position));
            } else {
                holder.image.setImageBitmap(getGambar.get(position));
                holder.image2.setImageBitmap(getGambar2.get(position));
                holder.setTitle.setText(getTitle.get(position));
                holder.setDescription.setText(getDescription.get(position));
                holder.setPrice.setText(getPrice.get(position));
                holder.setType.setText(getType.get(position));
            }

            holder.setPhone.setText("Hubungi : +628-5667-6789");
        }

        @Override
        public int getItemCount() {
            return filter ? getFilterId.size() : getId.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image, image2;
            TextView setTitle, setDescription, setPrice, setType, setPhone, buttonDelete;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.gambar);
                image2 = v.findViewById(R.id.gambar2);
                setTitle = v.findViewById(R.id.title);
                setDescription = v.findViewById(R.id.description);
                setPrice = v.findViewById(R.id.harga);
                setType = v.findViewById(R.id.type);
                setPhone = v.findViewById(R.id.telp);
                v.findViewById(R.id.hapus).setVisibility(View.GONE);
            }
        }
    }

}