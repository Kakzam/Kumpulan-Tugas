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
import com.mobile.skud_id.databinding.AdapterUserBinding;
import com.mobile.skud_id.databinding.FragmentUserBinding;
import com.mobile.skud_id.feature.VerificationActivity;
import com.mobile.skud_id.model.ModelUser;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends BaseFragment {

    private FragmentUserBinding binding;
    List<ModelUser> userList = new ArrayList<>();
    DBConfig config;
    Cursor cr;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        config = new DBConfig(getContext(), DBConfig.SEKUD_ID, null, DBConfig.DB_VERSION);
        binding.addAccount.setVisibility(View.VISIBLE);
        binding.addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivity(VerificationActivity.class);
            }
        });

        setLoading("Sedang melakukan proses");
        cr = config.getReadableDatabase().rawQuery("SELECT * FROM tbl_user", null);
        cr.moveToFirst();
        userList.clear();
        for (int count = 0; count < cr.getCount(); count++) {
            cr.moveToPosition(count);
            userList.add(new ModelUser(
                    cr.getString(0),
                    cr.getString(1),
                    cr.getString(2),
                    cr.getString(3),
                    cr.getString(4),
                    cr.getString(5)
            ));
        }
        dismissLoading();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.fragmentAccountRecycler.setLayoutManager(layoutManager);
        binding.fragmentAccountRecycler.setAdapter(new AdapterUser());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {

        @NonNull
        @Override
        public AdapterUser.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdapterUser.ViewHolder(AdapterUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Bitmap bitmap = BaseActivity.decodeTobase64(userList.get(position).getImage());
            holder.binding.adapterUserImage.setImageBitmap(bitmap);
            holder.binding.adapterUserName.setVisibility(View.GONE);
            holder.binding.adapterUserId.setText("Pemakaian : " + userList.get(position).getId());
            holder.binding.adapterUserUsername.setText(userList.get(position).getUsername());
            holder.binding.adapterUserPassword.setText(userList.get(position).getPassword());
            holder.binding.adapterUserStatus.setText(userList.get(position).getPermission());
            holder.binding.adapterUserDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setLoading("Sedang melakukan proses");
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_user WHERE id = '" + userList.get(position).getId() + "'");
                    setToastLong("Berhasil menghapus " + userList.get(position).getUsername());
                    userList.remove(position);
                    notifyDataSetChanged();
                    dismissLoading();
                }
            });

            holder.binding.adapterUserUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setToastLong("Menu ini masih tahap development");
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            AdapterUserBinding binding;

            ViewHolder(AdapterUserBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }
        }
    }
}