package com.sekud.id.feature.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sekud.id.R;
import com.sekud.id.model.ModelUser;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.ViewHolder> {

    private List<ModelUser> a;
    private onListener listener;

    public AdapterUser(List<ModelUser> a, onListener listener) {
        this.a = a;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textName.setText(a.get(position).getName());
        holder.textId.setText(a.get(position).getName());
        holder.textUsername.setText(a.get(position).getName());
        holder.textPassword.setText(a.get(position).getName());
        holder.textStatus.setText(a.get(position).getName());

        holder.buttonDelete.setOnClickListener(view -> listener.onClick("delete", position));
        holder.buttonUpdate.setOnClickListener(view -> listener.onClick("update", position));
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName, textId, textUsername, textPassword, textStatus;
        ImageView buttonUpdate, buttonDelete, imageUser;

        ViewHolder(View v) {
            super(v);
            textName = v.findViewById(R.id.adapter_user_name);
            textId = v.findViewById(R.id.adapter_user_id);
            textUsername = v.findViewById(R.id.adapter_user_username);
            textPassword = v.findViewById(R.id.adapter_user_password);
            textStatus = v.findViewById(R.id.adapter_user_status);
            buttonDelete = v.findViewById(R.id.adapter_user_delete);
            buttonUpdate = v.findViewById(R.id.adapter_user_update);
            imageUser = v.findViewById(R.id.adapter_user_image);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}