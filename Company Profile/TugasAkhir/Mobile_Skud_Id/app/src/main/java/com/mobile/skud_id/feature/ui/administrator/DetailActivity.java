package com.mobile.skud_id.feature.ui.administrator;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.ActivityDetailBinding;
import com.mobile.skud_id.databinding.AdapterImageBinding;
import com.mobile.skud_id.feature.ui.create.UpdateItemActivity;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {

    ActivityDetailBinding binding;
    DBConfig config, configImage;
    Cursor cr, crImage;
    List<String> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        config = new DBConfig(this, DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);
        setContentView(binding.getRoot());

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_item WHERE id = " + getIntent().getStringExtra(INTENT_1), null);
        crImage = configImage.getReadableDatabase().rawQuery("SELECT * FROM tbl_image WHERE id_item = " + cr.getString(0), null);
        cr.moveToFirst();
        crImage.moveToFirst();
        cr.moveToPosition(cr.getCount() - 1);

        for (int count = 0; count < cr.getCount(); count++) {
            crImage.moveToPosition(count);
            imageList.add(crImage.getString(1));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.activityDetailItemRecycler.setLayoutManager(layoutManager);
        binding.activityDetailItemRecycler.setAdapter(new AdapterImage());
        binding.activityDetailItemBuy.setVisibility(View.GONE);
        binding.activityDetailItemTitle.setText(cr.getString(1));
        binding.activityDetailItemUsage.setText(cr.getString(2));
        binding.activityDetailItemPrice.setText(cr.getString(3));
        binding.activityDetailItemAdvantage.setText(cr.getString(4));
        binding.activityDetailItemDeficiency.setText(cr.getString(5));
        binding.activityDetailItemStatus.setText(cr.getString(7));
        binding.activityDetailItemEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivity(UpdateItemActivity.class, cr.getString(0), "");
            }
        });
    }

    class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(AdapterImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Bitmap bitmap = decodeTobase64(imageList.get(position));
            holder.binding.adapterImage.setImageBitmap(bitmap);
            holder.binding.adapterImageDelete.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            AdapterImageBinding binding;

            ViewHolder(AdapterImageBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}