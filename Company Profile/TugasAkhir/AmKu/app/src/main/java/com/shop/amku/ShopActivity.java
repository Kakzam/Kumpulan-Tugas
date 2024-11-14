package com.shop.amku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

import com.shop.amku.other.Loading;
import com.shop.amku.other.Shop;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    RecyclerView widget;

    public static String BELI = "BELI";
    String id = "";
    public String menu = "";
    String permission = "";

    List<Shop> jual = new ArrayList<>();
    Loading loading = new Loading();
    DBConfig config;
    Cursor cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        config = new DBConfig(this, DBConfig.AMKU, null, DBConfig.DB_VERSION);
        widget = findViewById(R.id.widget);

        try {
            permission = getIntent().getStringExtra(DashboardActivity.PERMISSION);
            menu = getIntent().getStringExtra(DashboardActivity.MENU);
            id = getIntent().getStringExtra(DashboardActivity.ID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (menu.equals("1"))
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_car WHERE menu = 1", null);
        else
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_car WHERE menu = 2", null);

        cr.moveToFirst();
        loading.setLoading(this);
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            byte[] decodedString = Base64.decode(cr.getString(6), Base64.DEFAULT);
            jual.add(new Shop(cr.getString(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), cr.getString(5), BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length)));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        widget.setLayoutManager(layoutManager);
        widget.setAdapter(new RcShop());
        loading.dissmissLoading();

        if (permission.equals("customer")) findViewById(R.id.button).setVisibility(View.GONE);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahActivity.class);
                intent.putExtra(DashboardActivity.MENU, menu);
                intent.putExtra(DashboardActivity.ID, id);
                intent.putExtra(DashboardActivity.PERMISSION, permission);
                startActivity(intent);
                finish();
            }
        });
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
            holder.image.setImageBitmap(jual.get(position).getPhoto());
            holder.tNama.setText(jual.get(position).getNama());
            holder.tStatus.setText(jual.get(position).getStatus());
            holder.tHarga.setText(jual.get(position).getHarga());
            holder.tDeskripsi.setText(jual.get(position).getDeskripsi());
            holder.tDilihat.setText(jual.get(position).getDilihat());

            if (!permission.equals("admin")) holder.bHapus.setVisibility(View.GONE);
            else holder.bBeli.setVisibility(View.GONE);

            holder.bBeli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setLoading(ShopActivity.this);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    jual.get(position).getPhoto().compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    byte[] b = baos.toByteArray();

                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_transaction (nama,status,harga,deskripsi,dilihat,photo,id_pelanggan) VALUES(" +
                                    "'" + jual.get(position).getNama() + "'," +
                                    "'" + jual.get(position).getStatus() + "'," +
                                    "'" + jual.get(position).getHarga() + "'," +
                                    "'" + jual.get(position).getDeskripsi() + "'," +
                                    "'" + jual.get(position).getDilihat() + "'," +
                                    "'" + Base64.encodeToString(b, Base64.DEFAULT) + "'," +
                                    "'" + id + "')");

                    Toast.makeText(ShopActivity.this, "Anda berhasil membeli " + jual.get(position).getNama(), Toast.LENGTH_SHORT).show();
                    loading.dissmissLoading();
                }
            });

            holder.bHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setLoading(ShopActivity.this);
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_car WHERE id = '" + jual.get(position).getId() + "'");
                    Toast.makeText(ShopActivity.this, "Anda berhasil menghapus " + jual.get(position).getNama(), Toast.LENGTH_SHORT).show();
                    jual.remove(position);
                    notifyDataSetChanged();
                    loading.dissmissLoading();
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