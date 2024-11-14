package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkebjb.gkebanjarbaru.R

class AdapterBeranda(var tanggal: ArrayList<String>,
                     var bulan: ArrayList<String>,
                     var tahun: ArrayList<String>,
                     var hari: ArrayList<String>,
                     var thumnail_renungan: ArrayList<String>,
                     var link_renungan: ArrayList<String>,
                     var nama_narasumber: ArrayList<String>,
                     var context: Context?
                    ) : RecyclerView.Adapter<AdapterBeranda.ItemViewHolder>() {

    val namaBulan = arrayOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_beranda, parent, false))
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        if (tanggal.size > 0){
            holder.narasumber.text = nama_narasumber[position]
            holder.tanggal.text = hari[position] + tanggal[position] + " " + namaBulan.get(Integer.valueOf(bulan[position])-1) + " " + tahun[position]
        }

        Glide.with(context!!)
            .load("https://gkebanjarbaru.org/" + thumnail_renungan[position])
            .into(holder.image)

        holder.image.setOnClickListener {
            openUrlInBrowser(link_renungan[position])
        }
        Log.v("URL", "https://gkebanjarbaru.org/" + thumnail_renungan[position])
    }

    override fun getItemCount(): Int {
        return  thumnail_renungan.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tanggal: TextView = itemView.findViewById(R.id.tanggal)
        var narasumber: TextView = itemView.findViewById(R.id.narasumber)
        var image: ImageView = itemView.findViewById(R.id.imageView)
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context!!.startActivity(intent)
    }
}