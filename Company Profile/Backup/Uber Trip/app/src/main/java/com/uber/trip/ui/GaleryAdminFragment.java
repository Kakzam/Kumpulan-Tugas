package com.uber.trip.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.uber.trip.Other;
import com.uber.trip.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaleryAdminFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> images = new ArrayList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    Other other;

    ActivityResultLauncher<Intent> dicoba = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        other.setLoading(getContext());
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();

                            Map<String, String> map = new HashMap<>();
                            map.put("gambar", Base64.encodeToString(b, Base64.DEFAULT));
                            FirebaseFirestore.getInstance()
                                    .collection("collection")
                                    .document("galery")
                                    .collection("collection")
                                    .add(map)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                bitmaps.add(bitmap);
                                                images.add(task.getResult().getId());
                                                other.dissmissLoading();
                                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                                recyclerView.setLayoutManager(layoutManager);
                                                recyclerView.setAdapter(new Galery());
                                            } else {
                                                other.dissmissLoading();
                                                other.setToast("Gagal upload gambar, silahkan perika koneksi anda", getContext());
                                            }
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_galery, container, false);
        recyclerView = root.findViewById(R.id.list);

        root.findViewById(R.id.tambah_gambar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                dicoba.launch(Intent.createChooser(intent, "Pahawang"));
            }
        });

        other = new Other();
        other.setLoading(getContext());
        FirebaseFirestore.getInstance()
                .collection("collection")
                .document("galery")
                .collection("collection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                images.add(documentSnapshot.getId());
                                byte[] d = Base64.decode(documentSnapshot.get("gambar").toString(), Base64.DEFAULT);
                                bitmaps.add(BitmapFactory.decodeByteArray(d, 0, d.length));
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(new Galery());
                            }
                            other.dissmissLoading();
                        } else {
                            other.dissmissLoading();
                            other.setToast("Silahkan periksa koneksi internet anda", getContext());
                        }
                    }
                });

        return root;
    }

    private class Galery extends RecyclerView.Adapter<Galery.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.image.setImageBitmap(bitmaps.get(position));
            holder.text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    other.setLoading(getContext());
                    FirebaseFirestore.getInstance()
                            .collection("collection")
                            .document("galery")
                            .collection("collection")
                            .document(images.get(position))
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        images.remove(position);
                                        bitmaps.remove(position);
                                        notifyDataSetChanged();
                                        other.dissmissLoading();
                                    } else {
                                        other.setToast("Gagal menghapus gambar", getContext());
                                    }
                                }
                            });
                }
            });
        }

        @Override
        public int getItemCount() {
            return bitmaps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView text;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.gambar);
                text = v.findViewById(R.id.hapus);
            }
        }
    }
}