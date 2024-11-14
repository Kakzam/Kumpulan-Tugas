package com.shop.amku;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shop.amku.other.Loading;
import com.shop.amku.other.Shop;
import com.shop.amku.ui.RegisterFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {

    RecyclerView widget;

    public static String BELI = "BELI";
    String id = "";
    String menu = "";
    String permission = "";

    List<Shop> jual = new ArrayList<>();
    Loading loading = new Loading();
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        firestore = FirebaseFirestore.getInstance();
        widget = findViewById(R.id.widget);

        try {
            permission = getIntent().getStringExtra(DashboardActivity.PERMISSION);
            menu = getIntent().getStringExtra(DashboardActivity.MENU);
            id = getIntent().getStringExtra(DashboardActivity.ID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        loading.setLoading(this);
        firestore.collection(RegisterFragment.REGISTER).document(getIntent().getStringExtra(TransactionActivity.ID_DETAIL)).collection(ShopActivity.BELI).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot d : task.getResult()) {
                        byte[] decodedString = Base64.decode(d.get(TambahActivity.PHOTO).toString(), Base64.DEFAULT);
                        jual.add(new Shop(d.getId(), d.get(TambahActivity.NAMA).toString(), d.get(TambahActivity.STATUS).toString(), d.get(TambahActivity.HARGA).toString(), d.get(TambahActivity.DESKRIPSI).toString(), d.get(TambahActivity.DILIHAT).toString(), BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length)));
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    widget.setLayoutManager(layoutManager);
                    widget.setAdapter(new RcShop());
                    loading.dissmissLoading();
                } else {
                    Toast.makeText(DetailActivity.this, "Silahkan periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.button).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
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

            holder.bBeli.setVisibility(View.GONE);

            holder.bHapus.setText("Selesai");
            holder.bHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loading.setLoading(DetailActivity.this);
                    firestore.collection(RegisterFragment.REGISTER).document(getIntent().getStringExtra(TransactionAdminActivity.ID_DETAIL)).collection(BELI).document(jual.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            loading.dissmissLoading();
                            if (task.isSuccessful()) {
                                Toast.makeText(DetailActivity.this, "Anda berhasil menyelesaikan " + jual.get(position).getNama(), Toast.LENGTH_SHORT).show();
                                jual.remove(position);
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(DetailActivity.this, "Silahkan periksa koneksi internet anda.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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