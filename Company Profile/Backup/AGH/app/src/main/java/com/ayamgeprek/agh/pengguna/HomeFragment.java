package com.ayamgeprek.agh.pengguna;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayamgeprek.agh.DialogSelect;
import com.ayamgeprek.agh.TambahGeprekActivity;
import com.ayamgeprek.agh.databinding.FragmentHomeBinding;
import com.ayamgeprek.agh.databinding.ListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    FirebaseFirestore data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        data = FirebaseFirestore.getInstance();
        data.collection(TambahGeprekActivity.EASTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();
                    list1.clear();
                    list2.clear();
                    list3.clear();

                    for (int a = 0; a < task.getResult().getDocuments().size(); a++) {
                        list.add(task.getResult().getDocuments().get(a).getId());
                        list1.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.EASTS).toString());
                        list2.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.PRICE).toString());
                        list3.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.IMAGE).toString());
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.home.setLayoutManager(layoutManager);
                    binding.home.setAdapter(new ItemAdapter());
                } else
                    Toast.makeText(getContext(), "Koneksinya jelek banget", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewGeprek, viewHarga;
        AppCompatImageView viewMenu, viewPesan;

        ViewHolder(ListBinding binding) {
            super(binding.getRoot());
            viewGeprek = binding.listGeprek;
            viewHarga = binding.listHarga;
            viewMenu = binding.appCompatImageView;
            viewPesan = binding.listPesan;
            binding.listMinus.setVisibility(View.GONE);
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            byte[] d = Base64.decode(list3.get(position), Base64.DEFAULT);
            holder.viewGeprek.setText(list1.get(position));
            holder.viewHarga.setText(list2.get(position));
            holder.viewMenu.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.viewPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

                    Map<String, String> geprek = new HashMap<>();
                    geprek.put(TambahGeprekActivity.EASTS, list1.get(position));
                    geprek.put(TambahGeprekActivity.PRICE, list2.get(position));
                    geprek.put(TambahGeprekActivity.IMAGE, list3.get(position));

                    data.collection(DialogSelect.PENDAFTARAN).document(sharedPreferences.getString("ID", "")).collection(TambahGeprekActivity.EASTS).add(geprek).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Berhasil menambahkan pesanan " + list1.get(position), Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(getContext(), "Koneksimu jelek, coba ulang", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }
}