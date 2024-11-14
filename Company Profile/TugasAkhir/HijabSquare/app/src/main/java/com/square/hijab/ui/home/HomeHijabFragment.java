package com.square.hijab.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.square.hijab.MainActivity3;
import com.square.hijab.databinding.FragmentHomeBinding;
import com.square.hijab.ui.Adapter;
import com.square.hijab.ui.DBConfig;
import com.square.hijab.ui.ModelHijab;

import java.util.ArrayList;
import java.util.List;

public class HomeHijabFragment extends Fragment {

    private FragmentHomeBinding binding;
    List<ModelHijab> list = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        config = new DBConfig(getContext(), DBConfig.HIJAB_SQUARE, null, DBConfig.DB_VERSION);
        binding.a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity3.class);
                startActivity(intent);
            }
        });

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_hijab", null);
        cr.moveToFirst();
        list.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            list.add(new ModelHijab(cr.getString(0), cr.getString(1), cr.getString(2)));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.b.setLayoutManager(layoutManager);
        binding.b.setAdapter(new Adapter(getContext(), list, false));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}