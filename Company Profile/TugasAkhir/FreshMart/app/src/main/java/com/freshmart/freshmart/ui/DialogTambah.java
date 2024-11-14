package com.freshmart.freshmart.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freshmart.freshmart.DBConfig;
import com.freshmart.freshmart.TestPenyimpanan;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.freshmart.freshmart.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogTambah extends BottomSheetDialogFragment {

    TextInputEditText nama, harga, total, qty;
    TextInputLayout hTotal, hQty;
    RecyclerView list;
    TextView action, tambahGambar;
    ProgressBar progressBar;
    DBConfig config;
    Cursor cr;

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
        config = new DBConfig(getContext(), DBConfig.FRESH_MART, null, DBConfig.DB_VERSION);

        if (!new TestPenyimpanan(getContext()).getLogin()) {
            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_sayur WHERE id = " + getArguments().getString("id"), null);
            cr.moveToFirst();
            cr.moveToPosition(cr.getCount() - 1);
            nama.setText(cr.getString(1));
            harga.setText(cr.getString(2));
            nama.setFocusableInTouchMode(true);
            harga.setFocusableInTouchMode(true);
            hQty.setVisibility(View.VISIBLE);
            hTotal.setVisibility(View.VISIBLE);
            tambahGambar.setVisibility(View.GONE);

            cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_image_sayur WHERE id_sayur = '" + getArguments().getString("id") + "'", null);
            cr.moveToFirst();
            listGambar.clear();
            for (int count = 0; count < cr.getCount(); count++) {
                cr.moveToPosition(count);
                listUpload.add(cr.getString(1));
                byte[] d = Base64.decode(cr.getString(1), Base64.DEFAULT);
                listGambar.add(BitmapFactory.decodeByteArray(d, 0, d.length));
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            list.setLayoutManager(layoutManager);
            tambah = new Tambah(listGambar, getContext());
            list.setAdapter(tambah);

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @SuppressLint("SetTextI18n")
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    total.setText((Integer.parseInt(qty.getText().toString()) * Integer.parseInt(harga.getText().toString())) + "");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };
            qty.addTextChangedListener(textWatcher);
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

                if (listUpload.size() > 0) config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_sayur (nama_sayuran,harga,gambar) VALUES(" +
                                "'" + nama.getText().toString() + "'," +
                                "'" + harga.getText().toString() + "'," +
                                "'" + listUpload.get(0) + "')");
                else
                    config.getWritableDatabase().execSQL("INSERT INTO tbl_sayur (nama_sayuran,harga,gambar) VALUES(" + "'" + nama.getText().toString() + "'," + "'" + harga.getText().toString() + "'," + "'')");

                cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_sayur", null);
                cr.moveToFirst();
                cr.moveToPosition(cr.getCount() - 1);

                for (String image : listUpload)
                    config.getWritableDatabase().execSQL("INSERT INTO tbl_image_sayur (image,id_sayur) VALUES(" + "'" + image + "'," + "'" + cr.getString(0) + "')");

                dismiss();
            } else {
                if (listUpload.size() > 0) config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_sayur_beli (nama_sayuran,harga,qty,total,id_pemakai,gambar) VALUES(" +
                                "'" + nama.getText().toString() + "'," +
                                "'" + harga.getText().toString() + "'," +
                                "'" + qty.getText().toString() + "'," +
                                "'" + total.getText().toString() + "'," +
                                "'" + new TestPenyimpanan(getContext()).getId() + "'," +
                                "'" + listUpload.get(0) + "')");
                else config.getWritableDatabase().execSQL(
                        "INSERT INTO tbl_sayur_beli (nama_sayuran,harga,qty,total,id_pemakai,gambar) VALUES(" +
                                "'" + nama.getText().toString() + "'," +
                                "'" + harga.getText().toString() + "'," +
                                "'" + qty.getText().toString() + "'," +
                                "'" + total.getText().toString() + "'," +
                                "'" + new TestPenyimpanan(getContext()).getId() + "'," +
                                "'')");
                dismiss();
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