package com.uber.trip.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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

import com.uber.trip.DBConfig;
import com.uber.trip.Other;
import com.uber.trip.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GaleryAdminFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> images = new ArrayList<>();
    List<Bitmap> bitmaps = new ArrayList<>();
    DBConfig config;
    Cursor cr;
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


                            config.getWritableDatabase().execSQL("INSERT INTO tbl_gambar (gambar) VALUES('" + Base64.encodeToString(b, Base64.DEFAULT) + "')");
                            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_gambar", null);
                            cr.moveToFirst();
                            cr.moveToPosition(cr.getCount() - 1);

                            bitmaps.add(bitmap);
                            images.add(cr.getString(0));
                            other.dissmissLoading();
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new Galery());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_galery, container, false);
        recyclerView = root.findViewById(R.id.list);
        config = new DBConfig(getActivity(), DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);

        root.findViewById(R.id.tambah_gambar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                dicoba.launch(Intent.createChooser(intent, "Pahawang"));
            }
        });

        other = new Other();
        other.setLoading(getContext());
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_gambar", null);
        cr.moveToFirst();
        images.clear();
        bitmaps.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            images.add(cr.getString(0));
            byte[] d = Base64.decode(cr.getString(1), Base64.DEFAULT);
            bitmaps.add(BitmapFactory.decodeByteArray(d, 0, d.length));
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new Galery());
        }
        other.dissmissLoading();
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
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_gambar WHERE id = '" + images.get(position) + "'");
                    images.remove(position);
                    bitmaps.remove(position);
                    notifyDataSetChanged();
                    other.dissmissLoading();
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