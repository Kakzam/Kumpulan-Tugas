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
import com.mobile.skud_id.base.BaseFragment;
import com.mobile.skud_id.base.DBConfig;
import com.mobile.skud_id.databinding.AdapterItemBinding;
import com.mobile.skud_id.databinding.FragmentItemBinding;
import com.mobile.skud_id.feature.ui.create.CreateItemActivity;
import com.mobile.skud_id.feature.ui.create.UpdateItemActivity;
import com.mobile.skud_id.model.ModelItem;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends BaseFragment {

    private FragmentItemBinding binding;
    List<ModelItem> itemList = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        config = new DBConfig(getContext(), DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);
        setLoading("Sedang melakukan proses");
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_item", null);
        cr.moveToFirst();
        itemList.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            itemList.add(new ModelItem(
                    cr.getString(0),
                    cr.getString(1),
                    cr.getString(2),
                    cr.getString(3),
                    cr.getString(4),
                    cr.getString(5),
                    cr.getString(6),
                    cr.getString(7)
            ));
        }
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setAdapter(new AdapterItem());
        binding.addItem.setVisibility(View.VISIBLE);
        binding.addItem.setOnClickListener(view -> setActivity(CreateItemActivity.class));
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class AdapterItem extends RecyclerView.Adapter<AdapterItem.ViewHolder> {

        @NonNull
        @Override
        public AdapterItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterItem.ViewHolder(AdapterItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull AdapterItem.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Bitmap bitmap = BaseActivity.decodeTobase64(itemList.get(position).getImage());
            holder.binding.adapterItemImage.setImageBitmap(bitmap);
            holder.binding.adapterItemTitle.setText(itemList.get(position).getTitle());
            holder.binding.adapterItemUsage.setText("Pemakaian : " + itemList.get(position).getUsage());
            holder.binding.adapterItemStatus.setText("Statu : " + (itemList.get(position).getCheck_new().equals("true") ? "Baru" : "Second"));
            holder.binding.adapterItemPrice.setText(itemList.get(position).getTitle());
            holder.binding.adapterItemDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLoading("Sedang melakukan proses");
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_item WHERE id = '" + itemList.get(position).getId() + "'");
                    setToastLong("Berhasil menghapus " + itemList.get(position).getTitle());
                    itemList.remove(position);
                    notifyDataSetChanged();
                    dismissLoading();
                }
            });

            holder.binding.adapterItemUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setActivity(UpdateItemActivity.class, itemList.get(position).getId(), "");
                }
            });

            holder.binding.adapterItemDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.binding.adapterItemBuy.setVisibility(View.GONE);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            AdapterItemBinding binding;

            ViewHolder(AdapterItemBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}