package com.mobile.skud_id.feature.ui.customer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.skud_id.base.BaseFragment;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.AdapterLocationBinding;
import com.mobile.skud_id.databinding.FragmentLocationBinding;
import com.mobile.skud_id.model.ModelLocation;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends BaseFragment {

    private FragmentLocationBinding binding;
    List<ModelLocation> locationList = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLocationBinding.inflate(inflater, container, false);
        config = new DBConfig(getContext(), DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);
        binding.addLocation.setVisibility(View.GONE);
        setLoading("Sedang melakukan proses");
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_location", null);
        cr.moveToFirst();
        locationList.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            locationList.add(new ModelLocation(cr.getString(0), cr.getString(1), cr.getString(2)));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(new AdapterLocation());
        dismissLoading();
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    class AdapterLocation extends RecyclerView.Adapter<AdapterLocation.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(AdapterLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.binding.adapterLocationTitle.setText(locationList.get(position).getId());
            holder.binding.adapterLocationDelete.setVisibility(View.GONE);
            holder.binding.adapterLocationUpdate.setVisibility(View.GONE);
            holder.binding.adapterLocationOpenMaps.setOnClickListener(view -> {
                Uri uri = Uri.parse(locationList.get(position).getMaps());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mapIntent);
            });
        }

        @Override
        public int getItemCount() {
            return locationList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            AdapterLocationBinding binding;

            ViewHolder(AdapterLocationBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}