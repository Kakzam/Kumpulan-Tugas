package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkebjb.gkebanjarbaru.R

class AdapterKitab(var nomer: ArrayList<String>,
                   var name: ArrayList<String>,
                   var chapter: ArrayList<String>,
                   var context: Context?
                    ) : RecyclerView.Adapter<AdapterKitab.ItemViewHolder>() {

    lateinit var adapter: AdapterNoChap
    var hidden = false;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_kitab, parent, false))
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.name.text = name[position] + " (" + chapter[position] + ")"

        var chapte = ArrayList<String>()
        for (i in 1..chapter[position].toInt()){
            chapte.add("$i")
        }

        val layoutManager = GridLayoutManager(context, 5)
        holder.recycler.layoutManager = layoutManager
        adapter = AdapterNoChap(name[position], nomer[position], chapter[position], chapte, context)
        holder.recycler.adapter = adapter

        holder.image.setOnClickListener {
            if (hidden) {
                hidden = false
                holder.image.setImageDrawable(getDrawable(context!!, R.drawable.ic_back))
                holder.recycler.visibility = View.GONE
            } else {
                hidden = true
                holder.image.setImageDrawable(getDrawable(context!!, R.drawable.ic_hidden))
                holder.recycler.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return  nomer.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name: TextView = itemView.findViewById(R.id.name)
        var image: ImageView = itemView.findViewById(R.id.show)
        var recycler: RecyclerView = itemView.findViewById(R.id.recyclerView)
    }
}