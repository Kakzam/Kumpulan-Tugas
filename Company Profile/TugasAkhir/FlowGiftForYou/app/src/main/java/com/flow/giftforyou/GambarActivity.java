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

import com.flow.giftforyou.ui.DBConfig;
import com.flow.giftforyou.ui.ModelBuket;
import com.flow.giftforyou.ui.UtilString;

import java.util.List;

public class GambarActivity extends RecyclerView.Adapter<GambarActivity.ViewHolder> {

    List<ModelBuket> list;
    Context context;
    String login;
    String collection;
    BucketActivity activity;
    DBConfig config;

    public GambarActivity(List<ModelBuket> list, String login, String collection, Context context, BucketActivity activity) {
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
        byte[] d = Base64.decode(list.get(position).getJpeg(), Base64.DEFAULT);
        config = new DBConfig(context, DBConfig.FLOW_GIFT_FOR_YOU, null, DBConfig.DB_VERSION);
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
                    config.getWritableDatabase().execSQL("DELETE FROM tbl_buket WHERE id = '" + list.get(position).getId() + "'");
                    list.remove(position);
                    notifyDataSetChanged();
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