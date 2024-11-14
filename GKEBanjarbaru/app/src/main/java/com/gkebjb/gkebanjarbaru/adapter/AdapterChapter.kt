package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gkebjb.gkebanjarbaru.R

class AdapterChapter(
    var nomer: ArrayList<String>,
    var type: ArrayList<String>,
    var content: ArrayList<String>
) : RecyclerView.Adapter<AdapterChapter.ItemViewHolder>() {

    var no = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chapter, parent, false)
        )
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (type[position].equals("title")){
            holder.name.text = content[position]
            holder.name.visibility = View.VISIBLE
            holder.card.visibility = View.GONE
            holder.nomer.visibility = View.GONE
        } else {
            holder.nomer.text = nomer[position]
            holder.card.text = content[position]
            holder.card.visibility = View.VISIBLE
            holder.nomer.visibility = View.VISIBLE
            holder.name.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return content.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.title)
        var card: TextView = itemView.findViewById(R.id.content)
        var nomer: TextView = itemView.findViewById(R.id.nomer)
    }
}