package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.fitur.ArsipActivity

class AdapterArsipKegiatan(
    var title: ArrayList<String>,
    var description: ArrayList<String>,
    var thumnail_renungan: ArrayList<String>,
    var link_renungan: ArrayList<String>,
    var context: Context?
) : RecyclerView.Adapter<AdapterArsipKegiatan.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_arsip_kegiatan, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.title.text = title[position]
        holder.description.text = description[position]

        Glide.with(context!!)
            .load("https://gkebanjarbaru.org/" + thumnail_renungan[position])
            .into(holder.image)

        holder.button.setOnClickListener {
            var intent = Intent(context, ArsipActivity::class.java)
            intent.putExtra("key1", "Arsip Kegiatan")
            intent.putExtra("key2", link_renungan[position])
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return thumnail_renungan.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var description: TextView = itemView.findViewById(R.id.description)
        var image: ImageView = itemView.findViewById(R.id.imageView)
        var button: Button = itemView.findViewById(R.id.button)
    }
}