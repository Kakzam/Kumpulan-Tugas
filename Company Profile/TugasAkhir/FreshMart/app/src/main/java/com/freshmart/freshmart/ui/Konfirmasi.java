package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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

import com.freshmart.freshmart.DBConfig;
import com.freshmart.freshmart.MainActivity;
import com.freshmart.freshmart.R;
import com.freshmart.freshmart.TestPenyimpanan;

import java.util.ArrayList;
import java.util.List;

public class Konfirmasi extends Fragment {

    RecyclerView list;
    DBConfig config_user, config_beli;
    Cursor cr_user, cr_beli;

    List<String> id = new ArrayList<>();
    List<String> nama = new ArrayList<>();
    List<String> total = new ArrayList<>();
    List<String> qty = new ArrayList<>();
    List<String> phone = new ArrayList<>();
    List<String> gambar = new ArrayList<>();
    List<String> harga = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_konfirmasi, container, false);

        list = root.findViewById(R.id.recycler);
        config_user = new DBConfig(getContext(), DBConfig.FRESH_MART, null, DBConfig.DB_VERSION);
        config_beli = new DBConfig(getContext(), DBConfig.FRESH_MART, null, DBConfig.DB_VERSION);

        if (new TestPenyimpanan(getContext()).getLogin()) {

            cr_user = config_user.getReadableDatabase().rawQuery("SELECT * FROM tbl_pemakai", null);
            cr_user.moveToFirst();

            for (int count = 0; count < cr_user.getCount(); count++) {
                cr_user.moveToPosition(count);
                cr_beli = config_beli.getReadableDatabase().rawQuery("SELECT * FROM tbl_sayur_beli WHERE id_pemakai = " + cr_user.getString(0), null);
                cr_beli.moveToFirst();

                if (cr_beli.getCount() > 0) {
                    id.add(cr_user.getString(0));
                    nama.add(cr_user.getString(1));
                    phone.add(cr_user.getString(2));

                    int hqty = 0;
                    int htotal = 0;
                    for (int c = 0; c < cr_beli.getCount(); c++) {
                        cr_beli.moveToPosition(c);
                        hqty += Integer.parseInt(cr_beli.getString(3));
                        htotal += Integer.parseInt(cr_beli.getString(4));
                    }
                    qty.add(hqty + "");
                    total.add(htotal + "");
                }
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            list.setLayoutManager(layoutManager);
            list.setAdapter(new User());
        } else {

            cr_beli = config_beli.getReadableDatabase().rawQuery("SELECT * FROM tbl_sayur_beli WHERE id_pemakai = '" + new TestPenyimpanan(getContext()).getId() + "'", null);
            cr_beli.moveToFirst();

            for (int count = 0; count < cr_beli.getCount(); count++) {
                cr_beli.moveToPosition(count);
                id.add(cr_beli.getString(0));
                nama.add(cr_beli.getString(1));
                harga.add(cr_beli.getString(2));
                qty.add(cr_beli.getString(3));
                total.add(cr_beli.getString(4));
                gambar.add(cr_beli.getString(6));
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            list.setLayoutManager(layoutManager);
            list.setAdapter(new User());
        }

        return root;
    }

    private class User extends RecyclerView.Adapter<User.ViewHolder> {

        @NonNull
        @Override
        public User.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            User.ViewHolder holder = new User.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull User.ViewHolder holder, int position) {

            holder.nama.setText(nama.get(position));
            holder.qty.setText("Qty : " + qty.get(position));
            holder.total.setText("Total : " + total.get(position));

            if (new TestPenyimpanan(getContext()).getLogin()) {
                holder.no_handphone.setText("Telepon : " + phone.get(position));
            } else {
                holder.lihat.setVisibility(View.GONE);
                byte[] d = Base64.decode(gambar.get(position), Base64.DEFAULT);
                holder.imageSayuran.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
                holder.no_handphone.setText("Harga : " + harga.get(position));
            }

            holder.imageDelete.setOnClickListener(view -> {
                if (new TestPenyimpanan(getContext()).getLogin()) {
                    config_user.getWritableDatabase().execSQL("DELETE FROM tbl_sayur_beli WHERE id_pemakai = '" + id.get(position) + "'");
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    config_user.getWritableDatabase().execSQL("DELETE FROM tbl_sayur_beli WHERE id = '" + id.get(position) + "'");
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });

            holder.lihat.setOnClickListener(view -> {
                DialogLihat dialog = new DialogLihat();
                Bundle bundle = new Bundle();
                bundle.putString("id", id.get(position));
                dialog.setArguments(bundle);
                dialog.show(getActivity().getSupportFragmentManager(), "");
            });
        }

        @Override
        public int getItemCount() {
            return id.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran, imageDelete;
            TextView nama, no_handphone, qty, total, lihat;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                imageDelete = v.findViewById(R.id.hapus);
                nama = v.findViewById(R.id.nama);
                no_handphone = v.findViewById(R.id.no_handphone);
                qty = v.findViewById(R.id.qty);
                total = v.findViewById(R.id.total);
                lihat = v.findViewById(R.id.lihat);
            }
        }
    }

}