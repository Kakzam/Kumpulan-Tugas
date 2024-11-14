package com.sekud.id.feature.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sekud.id.R;
import com.sekud.id.base.BaseFragment;
import com.sekud.id.base.Preference;
import com.sekud.id.base.StringUtil;
import com.sekud.id.feature.adapter.AdapterLocation;
import com.sekud.id.feature.alert.DialogLocationFragment;
import com.sekud.id.feature.alert.LoadingFragment;
import com.sekud.id.model.ModelLocation;
import com.sekud.id.network.BaseConfig;
import com.sekud.id.network.RestCallback;
import com.sekud.id.network.RestPresenter;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends BaseFragment implements AdapterLocation.onListener {

    List<ModelLocation> list = new ArrayList<>();
    AdapterLocation adapter;

    EditText editSearch;
    ImageView imageSearch;
    RecyclerView recycler;
    SwipeRefreshLayout swipe;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);
        loadingFragment = new LoadingFragment();
        context = getContext();
        activity = getActivity();
        setInisialisasi();
        return view;
    }

    private void setInisialisasi() {
        if (new Preference(context).getLogin())
            view.findViewById(R.id.fragment_location_add).setVisibility(View.VISIBLE);

        editSearch = view.findViewById(R.id.fragment_location_edit_search);
        imageSearch = view.findViewById(R.id.fragment_location_search);
        recycler = view.findViewById(R.id.fragment_location_recycler);
        swipe = view.findViewById(R.id.fragment_location_swipe);
        swipe.setOnRefreshListener(this::setData);

        view.findViewById(R.id.fragment_location_add).setOnClickListener(view -> {
            new Preference(context).setLocationTitle("");
            new Preference(context).setLocationId("");
            new Preference(context).setLocationLink("");
            DialogLocationFragment dialogLocationFragment = new DialogLocationFragment();
            dialogLocationFragment.show(activity.getSupportFragmentManager(), "DialogLocationFragment");
        });

        setData();
    }

    private void setData() {
        setLoading(StringUtil.LOADING_LOCATION);
        new RestPresenter().getLocation(BaseConfig.BASE, BaseConfig.LOCATION, new RestCallback() {
            @Override
            public void Success(QuerySnapshot task) {
                list.clear();
                for (QueryDocumentSnapshot document : task) {
                    list.add(new ModelLocation(document.getId(), document.get("location").toString(), document.get("title").toString()));
                }
                setRecycler();
            }

            @Override
            public void Failed(String failed) {
                setLog(failed);
                setAlerttDialogReload(() -> setData());
            }

            @Override
            public void Failure(String failure) {
                setLog(failure);
                setAlerttDialog(StringUtil.WARNING, failure);
            }
        });
    }

    private void setRecycler() {
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        adapter = new AdapterLocation(list, new Preference(context).getLogin(), LocationFragment.this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(String select, int position) {
        if (select.equals("maps")) {
            Uri uri = Uri.parse(list.get(position).getLocation());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(mapIntent);
        } else if (select.equals("update")) {
            new Preference(context).setLocationId(list.get(position).getId());
            new Preference(context).setLocationTitle(list.get(position).getTitle());
            new Preference(context).setLocationLink(list.get(position).getLocation());
            DialogLocationFragment dialogLocationFragment = new DialogLocationFragment();
            dialogLocationFragment.show(activity.getSupportFragmentManager(), "DialogLocationFragment");
        } else if (select.equals("delete")) {
            setLoading(StringUtil.LOADING_LOCATION);
            new RestPresenter().deleteLocation(BaseConfig.BASE, BaseConfig.LOCATION, list.get(position).getId(), new RestCallback() {
                @Override
                public void Success(QuerySnapshot task) {
                    dismissLoading();
                    setInisialisasi();
                    setAlerttDialog(StringUtil.WARNING, "Lokasi di " + list.get(position).getTitle() + " berhasil dihapus");
                }

                @Override
                public void Failed(String failed) {
                    dismissLoading();
                    setLog(failed);
                    setAlerttDialog(StringUtil.WARNING, "Lokasi di " + list.get(position).getTitle() + " gagal dihapus");
                }

                @Override
                public void Failure(String failure) {
                    dismissLoading();
                    setLog(failure);
                    setAlerttDialog(StringUtil.WARNING, failure);
                }
            });
        }
    }
}