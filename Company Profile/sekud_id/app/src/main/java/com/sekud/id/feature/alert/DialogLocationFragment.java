package com.sekud.id.feature.alert;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.QuerySnapshot;
import com.sekud.id.R;
import com.sekud.id.base.BaseBottomSheetDialogFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.fragment.LocationFragment;
import com.sekud.id.network.BaseConfig;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;

public class DialogLocationFragment extends BaseBottomSheetDialogFragment {

    TextInputEditText textTitle, textMaps;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        activity = getActivity();
        view = inflater.inflate(R.layout.dialog_location, container, false);
        loadingFragment = new LoadingFragment();

        setInisialisasi();
        return view;
    }

    private void setInisialisasi() {
        textTitle = view.findViewById(R.id.dialog_location_title);
        textMaps = view.findViewById(R.id.dialog_location_maps);

        if (!new Preference(context).getLocationId().isEmpty()) {
            textTitle.setText(new Preference(context).getLocationTitle());
            textMaps.setText(new Preference(context).getLocationLink());
        }

        view.findViewById(R.id.dialog_location_back).setOnClickListener(view -> {
            getDialog().dismiss();
        });

        view.findViewById(R.id.dialog_location_save).setOnClickListener(view -> {
            if (!textTitle.getText().toString().isEmpty() || !textMaps.getText().toString().isEmpty()) {
                setLoading("Sedang menambahkan lokasi " + textTitle.getText().toString() + ".");
                new Preference(context).setLocationTitle(textTitle.getText().toString());
                new Preference(context).setLocationLink(textMaps.getText().toString());
                new RestPresenter().addLocation(BaseConfig.BASE,
                        BaseConfig.LOCATION,
                        new Preference(context).getLocationId(),
                        new Preference(context).getLocationTitle(),
                        new Preference(context).getLocationLink(),
                        new RestCallback() {
                            @Override
                            public void Success(QuerySnapshot task) {
                                dismissLoading();
                                getDialog().dismiss();
                                setAlerttDialog(StringUtil.WARNING, "Lokasi di " + textTitle.getText().toString() + " berhasil ditambah");
                            }

                            @Override
                            public void Failed(String failed) {
                                dismissLoading();
                                setLog(failed);
                                getDialog().dismiss();
                                if (new Preference(context).getLocationId().isEmpty())
                                    setAlerttDialog(StringUtil.WARNING, "Lokasi di " + textTitle.getText().toString() + " gagal ditambah");
                                else
                                    setAlerttDialog(StringUtil.WARNING, "Lokasi di " + textTitle.getText().toString() + " gagal ditambah");
                            }

                            @Override
                            public void Failure(String failure) {
                                dismissLoading();
                                setLog(failure);
                                getDialog().dismiss();
                                setAlerttDialog(StringUtil.WARNING, failure);
                            }
                        });
            } else setAlerttDialog(StringUtil.WARNING, StringUtil.NOT_EMPTY);
        });
    }
}