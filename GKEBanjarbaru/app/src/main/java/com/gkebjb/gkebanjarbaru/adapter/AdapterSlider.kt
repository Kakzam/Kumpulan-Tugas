package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkebjb.gkebanjarbaru.R

class AdapterSlider(
    var url: ArrayList<String>,
    var context: Context?
) : RecyclerView.Adapter<AdapterSlider.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context!!)
            .load("https://gkebanjarbaru.org/" + url[position])
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return url.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.select)
    }
}