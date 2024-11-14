package com.various.bags.ui.customer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.various.bags.AddBagsActivity;
import com.various.bags.R;
import com.various.bags.RegistrasiActivity;
import com.various.bags.databinding.BagsBinding;
import com.various.bags.databinding.FragmentTrollyBinding;
import com.various.bags.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class TrollyFragment extends Fragment {

    private FragmentTrollyBinding binding;
    List<Model> list = new ArrayList<>();
    FirebaseFirestore ff;
    SharedPreferences sharedPreferences;

    /* Verificaiton Data */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrollyBinding.inflate(inflater, container, false);
        ff = FirebaseFirestore.getInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ff.collection(RegistrasiActivity.USER).document(sharedPreferences.getString(RegistrasiActivity.USER, "kosong")).collection(BagsFragment.TROLLEY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();

                    for (DocumentSnapshot ds : task.getResult()) {
                        byte[] d = Base64.decode(ds.get(AddBagsActivity.IMAGE).toString(), Base64.DEFAULT);
                        list.add(new Model(ds.getId(), ds.get(AddBagsActivity.NAME_ITEM).toString(), ds.get(AddBagsActivity.PRICE).toString(), ds.get(AddBagsActivity.STATUS).toString(), ds.get(AddBagsActivity.SEE).toString(), BitmapFactory.decodeByteArray(d, 0, d.length), ds.get(AddBagsActivity.SEE).toString()));
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.trolly.setLayoutManager(layoutManager);
                    binding.trolly.setAdapter(new Trolley());
                } else
                    Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    class Trolley extends RecyclerView.Adapter<Trolley.ViewHolder> {

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

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Menu ini masih dalam development", Toast.LENGTH_SHORT).show();
                }
            });

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ff.collection(RegistrasiActivity.USER).document(sharedPreferences.getString(RegistrasiActivity.USER, "kosong")).collection(BagsFragment.TROLLEY).document(list.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Anda berhasil menghapus " + list.get(position).getNamaItem(), Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                notifyDataSetChanged();
                            } else
                                Toast.makeText(getContext(), "Silahkan periksa koneksi internet anda", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView title, price, status, see, button1, button2;

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