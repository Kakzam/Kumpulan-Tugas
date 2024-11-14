package com.uber.trip.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uber.trip.DBConfig;
import com.uber.trip.MainActivity3;
import com.uber.trip.Other;
import com.uber.trip.R;

import java.util.ArrayList;
import java.util.List;

public class PahawangAdminFragment extends Fragment {

    TextView textTambah;
    RecyclerView recyclerView;
    Other other = new Other();
    DBConfig config;
    Cursor cr;

    List<String> select = new ArrayList<>();
    List<Bitmap> jpeg = new ArrayList<>();
    List<String> trip = new ArrayList<>();
    List<String> penjelasan = new ArrayList<>();
    List<String> biaya = new ArrayList<>();
    List<String> hub = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        config = new DBConfig(getActivity(), DBConfig.UBER_TRIP, null, DBConfig.DB_VERSION);
        textTambah = root.findViewById(R.id.tambah_trip);
        recyclerView = root.findViewById(R.id.list);

        textTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity3.class);
                intent.putExtra("update", "false");
                startActivity(intent);
                getActivity().finish();
            }
        });

        other.setLoading(getContext());
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_trip", null);
        cr.moveToFirst();
        select.clear();
        jpeg.clear();
        trip.clear();
        penjelasan.clear();
        biaya.clear();
        hub.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            select.add(cr.getString(0));
            byte[] d = Base64.decode(cr.getString(2), Base64.DEFAULT);
            jpeg.add(BitmapFactory.decodeByteArray(d, 0, d.length));
            trip.add(cr.getString(1));
            penjelasan.add(cr.getString(3));
            biaya.add(cr.getString(4));
            hub.add(cr.getString(5));

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new Pahawang());
        }
        other.dissmissLoading();

        return root;
    }

    private class Pahawang extends RecyclerView.Adapter<Pahawang.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pahawang, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.image.setImageBitmap(jpeg.get(position));
            holder.trip.setText(trip.get(position));
            holder.biaya.setText(biaya.get(position));
            holder.penjelasan.setText(penjelasan.get(position));
            holder.contact.setText(hub.get(position));

            holder.change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), MainActivity3.class);
                    intent.putExtra("update", "true");
                    intent.putExtra("select", select.get(position));
                    getContext().startActivity(intent);
                    getActivity().finish();
                }
            });

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    other.setLoading(getContext());
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_trip WHERE id = '" + select.get(position) + "'");
                    select.remove(position);
                    jpeg.remove(position);
                    trip.remove(position);
                    penjelasan.remove(position);
                    biaya.remove(position);
                    hub.remove(position);
                    notifyDataSetChanged();
                    other.dissmissLoading();
                }
            });
        }

        @Override
        public int getItemCount() {
            return select.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView remove, change, trip, biaya, penjelasan, contact;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.jpeg);
                remove = v.findViewById(R.id.menghilang);
                change = v.findViewById(R.id.mengubah);
                trip = v.findViewById(R.id.trip);
                biaya = v.findViewById(R.id.biaya);
                penjelasan = v.findViewById(R.id.penjelasan);
                contact = v.findViewById(R.id.contact);
            }
        }
    }
}