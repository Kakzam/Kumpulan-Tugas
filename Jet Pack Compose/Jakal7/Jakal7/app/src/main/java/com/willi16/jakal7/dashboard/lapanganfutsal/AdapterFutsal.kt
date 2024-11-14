package com.willi16.jakal7.dashboard.lapanganfutsal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.willi16.jakal7.Helper.formatPrice
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.model.LapanganFutsal

class AdapterFutsal(private var dataFutsal: ArrayList<LapanganFutsal>,
                    private var listenerFutsal: (LapanganFutsal) -> Unit):RecyclerView.Adapter<AdapterFutsal.ViewHolder>() {


    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFutsal.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.list_item_futsal, parent, false)
        return  ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterFutsal.ViewHolder, position: Int) {
        holder.bindItem(dataFutsal[position],listenerFutsal,context)
    }

    override fun getItemCount(): Int = dataFutsal.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private var poster : ImageView = view.findViewById(R.id.ivPoster)
        private var lokasi : TextView = view.findViewById(R.id.tvLokasi)
        private var harga : TextView = view.findViewById(R.id.tvHarga)
        private var rating : TextView = view.findViewById(R.id.tvRate)

        fun bindItem(dataFutsal: LapanganFutsal, listenerFutsal: (LapanganFutsal) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataFutsal.poster)
                .placeholder(R.drawable.progress_animation)
                .into(poster)

            lokasi.text = dataFutsal.alamat
            harga.formatPrice(dataFutsal.price)
            rating.text = dataFutsal.rating

            itemView.setOnClickListener{
                listenerFutsal(dataFutsal)
            }
        }

    }
}