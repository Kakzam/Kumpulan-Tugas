package com.catering.dessert.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;

public class DessertFragment extends Fragment {

    View view;
    RecyclerView recycler;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dessert, container, false);
        setUi();
        return view;
    }

    private void setUi() {
        recycler = view.findViewById(R.id.fragment_dashboard_recycler);
    }
}