package com.sekud.id.feature.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sekud.id.R;
import com.sekud.id.base.BaseFragment;
import com.sekud.id.feature.alert.DialogVerifikasiFragment;
import com.sekud.id.feature.alert.LoadingFragment;

public class AboutFragment extends BaseFragment {

    int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        loadingFragment = new LoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("", "false");
        loadingFragment.setArguments(bundle);
        view = inflater.inflate(R.layout.fragment_about, container, false);
        setInisialisasi();
        return view;
    }

    private void setInisialisasi() {

        view.findViewById(R.id.fragment_about_image).setOnClickListener(view -> {
            count += 1;
            if (count == 10) {
                new DialogVerifikasiFragment().show(activity.getSupportFragmentManager(), "DialogVerificationFragment");
            }
        });

    }
}