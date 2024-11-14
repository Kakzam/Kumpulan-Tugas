package com.go.creative.ui.sekunder;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.go.creative.R;
import com.go.creative.other.DBConfig;
import com.go.creative.other.sekunder.ItemDesain;

import java.util.ArrayList;
import java.util.List;

public class DesainFragment extends Fragment {

    ProgressBar progress;
    EditText editCari;
    ImageView imageClose, imageCari, imageTambah;
    RecyclerView recycler;

    List<String> key = new ArrayList<>();
    List<String> nama = new ArrayList<>();
    List<String> harga = new ArrayList<>();
    List<String> terjual = new ArrayList<>();
    List<String> gambar = new ArrayList<>();

    List<String> filter_key = new ArrayList<>();
    List<String> filter_nama = new ArrayList<>();
    List<String> filter_harga = new ArrayList<>();
    List<String> filter_terjual = new ArrayList<>();
    List<String> filter_gambar = new ArrayList<>();

    ItemDesain itemDesain;
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_desain, container, false);
        config = new DBConfig(getContext(), DBConfig.GO_CREATIVE, null, DBConfig.DB_VERSION);
        progress = root.findViewById(R.id.fragment_desain_progressbar);
        editCari = root.findViewById(R.id.fragment_desain_edit);
        imageClose = root.findViewById(R.id.fragment_desain_close);
        imageCari = root.findViewById(R.id.fragment_desain_cari);
        imageTambah = root.findViewById(R.id.fragment_desain_add_image);
        recycler = root.findViewById(R.id.fragment_desain_recycler);
        imageTambah.setVisibility(View.GONE);

        imageCari.setOnClickListener(view -> {
            filter_key.clear();
            filter_nama.clear();
            filter_harga.clear();
            filter_terjual.clear();
            filter_gambar.clear();

            for (int it = 0; it < key.size(); it++) {
                if (nama.get(it).equals(editCari.getText().toString())
                        || harga.get(it).equals(editCari.getText().toString())
                        || terjual.get(it).equals(editCari.getText().toString())
                        || key.get(it).equals(editCari.getText().toString())
                ) {
                    filter_key.add(key.get(it));
                    filter_nama.add(nama.get(it));
                    filter_harga.add(harga.get(it));
                    filter_terjual.add(terjual.get(it));
                    filter_gambar.add(gambar.get(it));
                }
            }

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler.setLayoutManager(layoutManager);
            itemDesain = new ItemDesain(filter_nama, filter_key, filter_harga, filter_terjual, filter_gambar, getContext());
            recycler.setAdapter(itemDesain);
        });

        imageClose.setOnClickListener(view -> editCari.setText(""));

        progress.setVisibility(View.VISIBLE);
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_desain", null);
        cr.moveToFirst();
        key.clear();
        nama.clear();
        harga.clear();
        terjual.clear();
        gambar.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            key.add(cr.getString(0));
            nama.add(cr.getString(1));
            harga.add(cr.getString(2));
            terjual.add(cr.getString(3));
            gambar.add(cr.getString(4));
        }

        progress.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        itemDesain = new ItemDesain(nama, key, harga, terjual, gambar, getContext());
        recycler.setAdapter(itemDesain);
        return root;
    }
}