package com.shop.amku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.amku.other.Loading;
import com.shop.amku.other.Shop;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    RecyclerView widget;

    String id = "";
    String menu = "";
    String permission = "";
    public static String ID_DETAIL = "ID_DETAIL";

    List<Shop> jual = new ArrayList<>();
    Loading loading = new Loading();
    DBConfig config, configCheck;
    Cursor cr, check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        widget = findViewById(R.id.widget);
        config = new DBConfig(this, DBConfig.AMKU, null, DBConfig.DB_VERSION);
        configCheck = new DBConfig(this, DBConfig.AMKU, null, DBConfig.DB_VERSION);

        try {
            permission = getIntent().getStringExtra(DashboardActivity.PERMISSION);
            menu = getIntent().getStringExtra(DashboardActivity.MENU);
            id = getIntent().getStringExtra(DashboardActivity.ID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        loading.setLoading(this);
        if (permission.equals("admin")) {

            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
            cr.moveToFirst();

            for (int count = 0; count < cr.getCount(); count++) {
                cr.moveToPosition(count);

                check = configCheck.getReadableDatabase().rawQuery("SELECT * FROM tbl_transaction WHERE id_pelanggan = " + cr.getString(0), null);
                if (check.getCount() > 0) {
                    jual.add(new Shop(cr.getString(0),
                            cr.getString(3),
                            cr.getString(4),
                            "",
                            "",
                            "",
                            null));

                    Log.v("ZAM", "" + jual.size());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    widget.setLayoutManager(layoutManager);
                    widget.setAdapter(new RcShop());
                }
            }
            loading.dissmissLoading();
        } else {
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_transaction WHERE id_pelanggan = " + getIntent().getStringExtra(DashboardActivity.ID), null);
            cr.moveToFirst();

            for (int count = 0; count < cr.getCount(); count++) {
                cr.moveToPosition(count);
                byte[] decodedString = Base64.decode(cr.getString(6), Base64.DEFAULT);
                jual.add(new Shop(cr.getString(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length)));
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            widget.setLayoutManager(layoutManager);
            widget.setAdapter(new RcShop());
            loading.dissmissLoading();
        }

        findViewById(R.id.button).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.putExtra(DashboardActivity.MENU, menu);
        intent.putExtra(DashboardActivity.PERMISSION, permission);
        intent.putExtra(DashboardActivity.ID, id);
        startActivity(intent);
        finish();
    }

    private class RcShop extends RecyclerView.Adapter<RcShop.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_shop, parent, false));
            return holder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (permission.equals("admin")) {
                holder.tNama.setText(jual.get(position).getNama());
                holder.tStatus.setText(jual.get(position).getStatus());
            } else {
                holder.image.setImageBitmap(jual.get(position).getPhoto());
                holder.tNama.setText(jual.get(position).getNama());
                holder.tStatus.setText(jual.get(position).getStatus());
                holder.tHarga.setText(jual.get(position).getHarga());
                holder.tDeskripsi.setText(jual.get(position).getDeskripsi());
                holder.tDilihat.setText(jual.get(position).getDilihat());
            }

            holder.bBeli.setVisibility(View.GONE);
            if (permission.equals("admin")) holder.bHapus.setText("Detail");
            holder.bHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (permission.equals("admin")) {
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra(DashboardActivity.MENU, menu);
                        intent.putExtra(DashboardActivity.PERMISSION, permission);
                        intent.putExtra(ID_DETAIL, jual.get(position).getId());
                        intent.putExtra(DashboardActivity.ID, id);
                        startActivity(intent);
                        finish();
                    } else {
                        loading.setLoading(TransactionActivity.this);
                        config.getWritableDatabase().execSQL("DELETE FROM tbl_transaction WHERE id_pelanggan = '" + jual.get(position).getId() + "'");
                        loading.dissmissLoading();
                        Toast.makeText(TransactionActivity.this, "Anda berhasil menghapus " + jual.get(position).getNama(), Toast.LENGTH_SHORT).show();
                        jual.remove(position);
                        notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return jual.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView tNama, tStatus, tHarga, tDeskripsi, tDilihat, bHapus, bBeli;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.photo);
                tNama = v.findViewById(R.id.judul);
                tStatus = v.findViewById(R.id.status);
                tHarga = v.findViewById(R.id.harga);
                tDeskripsi = v.findViewById(R.id.deskripsi);
                tDilihat = v.findViewById(R.id.dilihat);
                bHapus = v.findViewById(R.id.hapus);
                bBeli = v.findViewById(R.id.beli);
            }
        }
    }
}