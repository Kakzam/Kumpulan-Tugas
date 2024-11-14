package com.mobile.skud_id.feature.ui.create;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.skud_id.base.BaseActivity;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.ActivityCreateItemBinding;
import com.mobile.skud_id.databinding.AdapterImageBinding;
import com.mobile.skud_id.feature.AdministratorActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UpdateItemActivity extends BaseActivity {

    ActivityCreateItemBinding binding;

    List<String> imageList = new ArrayList<>();
    Boolean checkNew = false;
    DBConfig config;
    Cursor cr;

    ActivityResultLauncher<Intent> getImageItem = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            imageList.add(BaseActivity.encodeTobase64(bitmap));
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                            binding.recycler.setLayoutManager(layoutManager);
                            binding.recycler.setAdapter(new AdapterImage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        config = new DBConfig(this, DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_item WHERE id " + getIntent().getStringExtra(INTENT_1), null);
        cr.moveToFirst();
        cr.moveToPosition(cr.getCount() - 1);
        binding.title.setText(cr.getString(1));
        binding.usage.setText(cr.getString(2));
        binding.price.setText(cr.getString(3));
        binding.advantageInput.setText(cr.getString(4));
        binding.deficiencyInput.setText(cr.getString(5));
        binding.checkNew.setChecked(Boolean.parseBoolean(cr.getString(7)));

        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_image WHERE id_item " + cr.getString(0), null);
        cr.moveToFirst();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            imageList.add(cr.getString(2));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(new AdapterImage());

        binding.addImage.setOnClickListener(view -> getImageItem.launch(Intent.createChooser(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT), "Gambar Barang")));
        binding.checkNew.setOnClickListener(view -> checkNew = binding.checkNew.isChecked());
        binding.save.setOnClickListener(view -> {
            if (!binding.title.getText().toString().isEmpty()
                    && !binding.usage.getText().toString().isEmpty()
                    && !binding.price.getText().toString().isEmpty()
                    && !binding.advantageInput.getText().toString().isEmpty()
                    && (imageList.size() > 0)
                    && !binding.deficiencyInput.getText().toString().isEmpty()) {
                setLoading("Sedang melakukan proses");

                config.getWritableDatabase().execSQL(
                        "UPDATE tbl_item SET " +
                                "title = '" + binding.title.getText().toString() + "'," +
                                "usage = '" + binding.usage.getText().toString() + "'," +
                                "price = '" + binding.price.getText().toString() + "'," +
                                "advantage = '" + binding.advantageInput.getText().toString() + "'," +
                                "deficiency = '" + binding.deficiencyInput.getText().toString() + "' " +
                                "image = '" + imageList.get(0) + "' " +
                                "check_new = '" + checkNew + "' " +
                                "WHERE id = " + getIntent().getStringExtra(INTENT_1));

                dismissLoading();
                setActivity(AdministratorActivity.class);
            } else if (binding.title.getText().toString().isEmpty())
                setToastLong("Belum mengisi judul barang");
            else if (binding.usage.getText().toString().isEmpty())
                setToastLong("Belum mengisi lama pemakaian");
            else if (binding.price.getText().toString().isEmpty())
                setToastLong("Belum mengisi harga");
            else if (binding.advantageInput.getText().toString().isEmpty())
                setToastLong("Belum mengisi kelebihan");
            else if (binding.deficiencyInput.getText().toString().isEmpty())
                setToastLong("Belum mengisi kekurangan");
            else if (imageList.size() == 0)
                setToastLong("Silahkan input gambar terlebih dahulu");
        });
    }

    @Override
    public void onBackPressed() {
        setActivity(AdministratorActivity.class);
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
            holder.binding.adapterImageDelete.setOnClickListener(view -> {
                imageList.remove(position);
                notifyDataSetChanged();
            });
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