package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.fitur.ChapterActivity

class AdapterNoChap(
    var title: String,
    var nomer: String,
    var chapter: String,
    var name: ArrayList<String>,
    var context: Context?
) : RecyclerView.Adapter<AdapterNoChap.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_no_chapter, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name.text = name[position]

        holder.card.setOnClickListener {
            var intent = Intent(context, ChapterActivity::class.java)
            intent.putExtra("key1", nomer)
            intent.putExtra("key2", chapter)
            intent.putExtra("key3", name[position])
            intent.putExtra("key4", title)
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return name.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var card: CardView = itemView.findViewById(R.id.card)
    }
}