package com.go.creative.ui.sekunder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.go.creative.R;
import com.go.creative.other.DBConfig;
import com.go.creative.other.Fungsi;
import com.go.creative.other.sekunder.ItemSosmed;
import com.go.creative.ui.slide.FragmentVeryfy;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    TextView buttonAdd;
    RecyclerView rc;
    ItemSosmed itemSosmed;
    DBConfig config;
    Cursor cr;

    List<String> key = new ArrayList<>();
    List<String> sosmed = new ArrayList<>();
    List<String> akun = new ArrayList<>();
    List<String> link = new ArrayList<>();
    int number = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        config = new DBConfig(getContext(), DBConfig.GO_CREATIVE, null, DBConfig.DB_VERSION);
        buttonAdd = view.findViewById(R.id.fragment_contact_add_sosmed);
        rc = view.findViewById(R.id.fragment_contact_recycler_view);

        buttonAdd.setText("Whatsapp");
        buttonAdd.setOnLongClickListener(view1 -> {
            number += 1;
            if (number > 2) {
                new FragmentVeryfy().show(getActivity().getSupportFragmentManager(), "open");
            }
            return false;
        });

        buttonAdd.setOnClickListener(view12 -> {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + Fungsi.NUMBER);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        key.clear();
        sosmed.clear();
        akun.clear();
        link.clear();
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_sosmed", null);
        cr.moveToFirst();

        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            key.add(cr.getString(0));
            sosmed.add(cr.getString(1));
            akun.add(cr.getString(2));
            link.add(cr.getString(3));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rc.setLayoutManager(layoutManager);
        itemSosmed = new ItemSosmed(key, sosmed, akun, link, getContext());
        rc.setAdapter(itemSosmed);
        return view;
    }
}