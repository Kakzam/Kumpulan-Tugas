package com.various.bags.ui.customer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.various.bags.DBConfig;
import com.various.bags.R;
import com.various.bags.RegistrasiActivity;
import com.various.bags.databinding.BagsBinding;
import com.various.bags.databinding.FragmentBagsBinding;
import com.various.bags.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class BagsFragment extends Fragment {

    private FragmentBagsBinding binding;
    DBConfig config;
    Cursor cr;

    List<Model> list = new ArrayList<>();
    SharedPreferences sharedPreferences;

    /* Verificaiton Data */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBagsBinding.inflate(inflater, container, false);
        binding.addBags.setVisibility(View.GONE);
        config = new DBConfig(getContext(), DBConfig.VARIOUS_BAGS, null, DBConfig.DB_VERSION);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_bags", null);
        cr.moveToFirst();

        list.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            byte[] d = Base64.decode(cr.getString(5), Base64.DEFAULT);
            list.add(new Model(cr.getString(0), cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4), BitmapFactory.decodeByteArray(d, 0, d.length), cr.getString(5)));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerBags.setLayoutManager(layoutManager);
        binding.recyclerBags.setAdapter(new Bags());

        return binding.getRoot();
    }

    class Bags extends RecyclerView.Adapter<Bags.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BagsBinding binding = BagsBinding.inflate(getLayoutInflater());
            return new ViewHolder(binding.getRoot());
        }

        @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.image.setImageBitmap(list.get(position).getGambar());
            holder.title.setText(list.get(position).getNamaItem());
            holder.price.setText(list.get(position).getHarga());
            holder.status.setText(list.get(position).getStatus());
            holder.see.setText(list.get(position).getDilihat());

            holder.button1.setText("Beli");
            holder.button1.setBackground(getResources().getDrawable(R.drawable.ic_button_3));
            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Menu ini masih dalam development", Toast.LENGTH_SHORT).show();
                }
            });

            holder.button2.setText("Trolly");
            holder.button2.setBackground(getResources().getDrawable(R.drawable.ic_button_4));
            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    config.getWritableDatabase().execSQL(
                            "INSERT INTO tbl_trolly (id_user,nama_item,price,status,see,image) VALUES(" +
                                    "'" + sharedPreferences.getString(RegistrasiActivity.USER, "kosong") + "'," +
                                    "'" + list.get(position).getNamaItem() + "'," +
                                    "'" + list.get(position).getHarga() + "'," +
                                    "'" + list.get(position).getStatus() + "'," +
                                    "'" + list.get(position).getDilihat() + "'," +
                                    "'" + list.get(position).getDataGambar() + "')");

                    Toast.makeText(getContext(), "Anda berhasil menambahkan ke trolley " + list.get(position).getNamaItem(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title, price, status, see, button2, button1;

            ViewHolder(View v) {
                super(v);
                image = v.findViewById(R.id.gambar);
                title = v.findViewById(R.id.title);
                price = v.findViewById(R.id.harga);
                status = v.findViewById(R.id.status);
                see = v.findViewById(R.id.dilihat);
                button1 = v.findViewById(R.id.button1);
                button2 = v.findViewById(R.id.button2);
            }
        }
    }
}