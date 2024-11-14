package com.uber.trip.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uber.trip.DBConfig;
import com.uber.trip.Other;
import com.uber.trip.R;

import java.util.ArrayList;
import java.util.List;

public class GaleryFragment extends Fragment {

    RecyclerView recyclerView;
    List<Bitmap> bitmaps = new ArrayList<>();
    Other other;
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_galery, container, false);
        recyclerView = root.findViewById(R.id.list);
        config = new DBConfig(getActivity(), DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);

        root.findViewById(R.id.tambah_gambar).setVisibility(View.GONE);

        other = new Other();
        other.setLoading(getContext());
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_gambar", null);
        cr.moveToFirst();
        bitmaps.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
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
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.image.setImageBitmap(bitmaps.get(position));
        }

        @Override
        public int getItemCount() {
            return bitmaps.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.gambar);
                v.findViewById(R.id.hapus).setVisibility(View.GONE);
            }
        }
    }
}