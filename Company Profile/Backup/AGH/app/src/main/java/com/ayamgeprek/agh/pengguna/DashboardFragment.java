package com.ayamgeprek.agh.pengguna;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayamgeprek.agh.DialogSelect;
import com.ayamgeprek.agh.TambahGeprekActivity;
import com.ayamgeprek.agh.databinding.FragmentDashboardBinding;
import com.ayamgeprek.agh.databinding.List2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    List<String> list = new ArrayList<>();
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    List<String> list3 = new ArrayList<>();
    List<String> list4 = new ArrayList<>();
    FirebaseFirestore data;
    SharedPreferences sharedPreferences;
    String message = "";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        data = FirebaseFirestore.getInstance();
        data.collection(DialogSelect.PENDAFTARAN).document(sharedPreferences.getString("ID", "")).collection(TambahGeprekActivity.EASTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list.clear();
                    list1.clear();
                    list2.clear();
                    list3.clear();
                    list4.clear();

                    for (int a = 0; a < task.getResult().getDocuments().size(); a++) {
                        list.add(task.getResult().getDocuments().get(a).getId());
                        list1.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.EASTS).toString());
                        list2.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.PRICE).toString());
                        list3.add(task.getResult().getDocuments().get(a).get(TambahGeprekActivity.IMAGE).toString());
                        list4.add("0");
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    binding.dashboard.setLayoutManager(layoutManager);
                    binding.dashboard.setAdapter(new ItemAdapter());
                } else
                    Toast.makeText(getContext(), "Koneksinya jelek banget", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "Pesan \n\n";
                for (int posisi = 0; posisi < list.size(); posisi++)
                    message += "\n" + list1.get(posisi) + "\n" + "Qty : " + list4.get(posisi) + "\n" + "Harga : " + list2.get(posisi);
                openWhatsApp("+6285218566153", message);
            }
        });
        return root;
    }

    private void openWhatsApp(String numero, String mensaje) {
        try {
            PackageManager packageManager = getActivity().getPackageManager();
            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + numero + "&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i);
            } else {
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        TextView viewGeprek, viewHarga;
        AppCompatImageView viewMenu, viewPlus, viewMinus, viewDelete;
        EditText editQty;

        ViewHolder(List2Binding binding) {
            super(binding.getRoot());
            viewGeprek = binding.listGeprek;
            viewHarga = binding.listHarga;
            viewMenu = binding.appCompatImageView;
            viewPlus = binding.listPesan;
            viewMinus = binding.listMinus;
            viewDelete = binding.listHapus;
            editQty = binding.listQty;
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(List2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            byte[] d = Base64.decode(list3.get(position), Base64.DEFAULT);
            holder.viewGeprek.setText(list1.get(position));
            holder.viewHarga.setText(list2.get(position));
            holder.viewMenu.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
            holder.viewPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.editQty.setText((Integer.parseInt(holder.editQty.getText().toString()) + 1) + "");
                    list4.add(position, holder.editQty.getText().toString());
                }
            });

            holder.viewMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Integer.parseInt(holder.editQty.getText().toString()) >= 0) {
                        holder.editQty.setText((Integer.parseInt(holder.editQty.getText().toString()) - 1) + "");
                        list4.add(position, holder.editQty.getText().toString());
                    }
                }
            });

            holder.viewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    data.collection(DialogSelect.PENDAFTARAN).document(sharedPreferences.getString("ID", "")).collection(TambahGeprekActivity.EASTS).document(list.get(position)).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Bro kamu berhasil mengahpus " + list1.get(position), Toast.LENGTH_SHORT).show();
                                list.remove(position);
                                list1.remove(position);
                                list2.remove(position);
                                list3.remove(position);
                                list4.remove(position);
                                notifyDataSetChanged();
                            } else
                                Toast.makeText(getContext(), "Koneksimu Jelek Banget Bro", Toast.LENGTH_SHORT).show();
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