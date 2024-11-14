package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.TestPenyimpanan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freshmart.freshmart.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogTambah extends BottomSheetDialogFragment {

    TextInputEditText nama, harga, total, qty;
    TextInputLayout hTotal, hQty;
    RecyclerView list;
    TextView action, tambahGambar;
    ProgressBar progressBar;

    List<Bitmap> listGambar = new ArrayList<>();
    List<String> listUpload = new ArrayList<>();
    Tambah tambah;
    int i = 0;

    ActivityResultLauncher<Intent> test = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            listUpload.add(Base64.encodeToString(b, Base64.DEFAULT));
                            listGambar.add(bitmap);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            list.setLayoutManager(layoutManager);
                            tambah = new Tambah(listGambar, getContext());
                            list.setAdapter(tambah);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_tambah, container, false);

        nama = root.findViewById(R.id.nama_sayuran);
        harga = root.findViewById(R.id.harga);
        total = root.findViewById(R.id.total);
        qty = root.findViewById(R.id.qty);
        hQty = root.findViewById(R.id.hilang_qty);
        hTotal = root.findViewById(R.id.hilang_total);
        action = root.findViewById(R.id.tombol);
        tambahGambar = root.findViewById(R.id.gambar);
        list = root.findViewById(R.id.recycler);
        progressBar = root.findViewById(R.id.progressbar);

        if (!new TestPenyimpanan(getContext()).getLogin()) {
            progressBar.setVisibility(View.VISIBLE);
            FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").document(getArguments().getString("id")).get().addOnCompleteListener(task -> {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    nama.setText(task.getResult().get("nama_sayuran").toString());
                    harga.setText(task.getResult().get("harga").toString());
                    nama.setFocusableInTouchMode(true);
                    harga.setFocusableInTouchMode(true);
                    hQty.setVisibility(View.VISIBLE);
                    hTotal.setVisibility(View.VISIBLE);
                    tambahGambar.setVisibility(View.GONE);

                    FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").document(getArguments().getString("id")).collection("-").get().addOnCompleteListener(task1 -> {
                        if (task.isSuccessful()) {
                            listGambar.clear();
                            for (DocumentSnapshot document : task1.getResult().getDocuments()) {
                                listUpload.add(document.get("gambar").toString());
                                byte[] d = Base64.decode(document.get("gambar").toString(), Base64.DEFAULT);
                                listGambar.add(BitmapFactory.decodeByteArray(d, 0, d.length));
                            }

                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            list.setLayoutManager(layoutManager);
                            tambah = new Tambah(listGambar, getContext());
                            list.setAdapter(tambah);
                        } else {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Pemberitahuan")
                                    .setMessage("Pastikan koneksi anda bagus")
                                    .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                    .show();
                        }
                    });

                    TextWatcher textWatcher = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            total.setText((Integer.parseInt(charSequence.toString()) * Integer.parseInt(harga.getText().toString())) + "");
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    };
                    qty.addTextChangedListener(textWatcher);
                } else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Pemberitahuan")
                            .setMessage("Pastikan koneksi anda bagus")
                            .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            });
        } else {
            hQty.setVisibility(View.GONE);
            hTotal.setVisibility(View.GONE);
        }

        tambahGambar.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            test.launch(Intent.createChooser(intent, "Pilih"));
        });

        action.setOnClickListener(view -> {
            if (new TestPenyimpanan(getContext()).getLogin()) {
                Map<String, String> map = new HashMap<>();
                map.put("nama_sayuran", nama.getText().toString());
                map.put("harga", harga.getText().toString());
                if (listUpload.size() > 0) map.put("gambar", listUpload.get(0));
                else map.put("gambar", "");

                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").add(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (; i < listUpload.size(); i++) {
                            Map<String, String> upload = new HashMap<>();
                            upload.put("gambar", listUpload.get(i));

                            FirebaseFirestore.getInstance().collection("-").document("sayuran").collection("-").document(task.getResult().getId()).collection("-").add(upload).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                }
                            });
                        }

                        progressBar.setVisibility(View.GONE);
                        dismiss();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
            } else {
                Map<String, String> map = new HashMap<>();
                map.put("nama_sayuran", nama.getText().toString());
                map.put("harga", harga.getText().toString());
                map.put("qty", qty.getText().toString());
                map.put("total", total.getText().toString());
                map.put("id", getArguments().getString("id"));
                if (listUpload.size() > 0) map.put("gambar", listUpload.get(0));
                else map.put("gambar", "");

                progressBar.setVisibility(View.VISIBLE);
                FirebaseFirestore.getInstance().collection("-").document("pengguna").collection("-").document(new TestPenyimpanan(getContext()).getId()).collection("-").add(map).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        dismiss();
                    } else {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Pemberitahuan")
                                .setMessage("Pastikan koneksi anda bagus")
                                .setPositiveButton("Ya", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                });
            }
        });

        return root;
    }

    private class Tambah extends RecyclerView.Adapter<Tambah.ViewHolder> {

        private List<Bitmap> gambar;
        private Context context;

        public Tambah(List<Bitmap> gambar, Context context) {
            this.gambar = gambar;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tambah, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (new TestPenyimpanan(context).getLogin()) {
                holder.imageDelete.setVisibility(View.VISIBLE);
            }

            holder.imageDelete.setOnClickListener(view -> {
                gambar.remove(position);
                DialogTambah.this.listGambar = gambar;
                tambah.notifyDataSetChanged();
            });

            holder.imageSayuran.setImageBitmap(gambar.get(position));
        }

        @Override
        public int getItemCount() {
            return gambar.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageSayuran, imageDelete;

            ViewHolder(View v) {
                super(v);
                imageSayuran = v.findViewById(R.id.gambar);
                imageDelete = v.findViewById(R.id.hapus);
            }
        }
    }

}