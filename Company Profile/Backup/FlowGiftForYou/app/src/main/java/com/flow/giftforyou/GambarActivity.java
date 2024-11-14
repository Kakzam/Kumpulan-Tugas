package com.flow.giftforyou;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flow.giftforyou.ui.UtilString;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class GambarActivity extends RecyclerView.Adapter<GambarActivity.ViewHolder> {

    List<DocumentSnapshot> list;
    Context context;
    String login;
    String collection;
    BucketActivity activity;

    public GambarActivity(List<DocumentSnapshot> list, String login, String collection, Context context, BucketActivity activity) {
        this.list = list;
        this.context = context;
        this.login = login;
        this.collection = collection;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_gambar, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        byte[] d = Base64.decode(list.get(position).get(UtilString.JPEG).toString(), Base64.DEFAULT);
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(d, 0, d.length));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(UtilString.BUCKET, collection).putExtra(UtilString.LOGIN, login).putExtra("SELECT", list.get(position).getId()));
                activity.finish();
            }
        });

        if (login.equals(UtilString.UBAH)) {
            holder.image.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new UtilString().deleteData(collection, list.get(position).getId(), context);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.view);
        }
    }
}