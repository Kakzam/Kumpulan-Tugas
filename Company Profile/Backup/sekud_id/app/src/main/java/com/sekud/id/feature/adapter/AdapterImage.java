package com.sekud.id.feature.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sekud.id.R;
import com.sekud.id.base.BaseActivity;
import com.sekud.id.model.ModelImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {

    private List<String> a;
//    private List<ModelImage> a;
    private boolean select;
    private onListener listener;

    public AdapterImage(List<String> a, boolean select, onListener listener) {
        this.a = a;
        this.select = select;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_image, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap bitmap = new BaseActivity().decodeTobase64(a.get(position));
        holder.image.setImageBitmap(bitmap);
//        try {
//            if (!a.get(position).getUrl().isEmpty())
//            else Picasso.get().load(a.get(position).getUri()).into(holder.image);
//        } catch (NullPointerException e) {
//            Picasso.get().load(a.get(position).getUri()).into(holder.image);
//        }

        holder.imageDelete.setOnClickListener(view -> {
            if (select) listener.onClick("delete", position);
        });

        if (!select) holder.imageDelete.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image, imageDelete;

        ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.adapter_image);
            imageDelete = v.findViewById(R.id.adapter_image_delete);
        }
    }

    public interface onListener {
        void onClick(String select, int position);
    }
}