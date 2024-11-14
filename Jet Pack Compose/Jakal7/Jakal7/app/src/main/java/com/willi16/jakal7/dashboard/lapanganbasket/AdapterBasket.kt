package com.willi16.jakal7.dashboard.lapanganbasket

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
import com.willi16.jakal7.dashboard.model.LapanganBasket

class AdapterBasket(private var dataBasket: ArrayList<LapanganBasket>,
                    private var listenerBasket: (LapanganBasket) -> Unit): RecyclerView.Adapter<AdapterBasket.ViewHolder>() {


    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterBasket.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        context = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.list_item_basket, parent, false)
        return  ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: AdapterBasket.ViewHolder, position: Int) {
        holder.bindItem(dataBasket[position],listenerBasket,context)
    }

    override fun getItemCount(): Int = dataBasket.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var poster : ImageView = view.findViewById(R.id.ivBasket)
        private var lokasi : TextView = view.findViewById(R.id.tvLokasiBasket)
        private var harga : TextView = view.findViewById(R.id.tvHargaBasket)
        private var rating : TextView = view.findViewById(R.id.tvRatingBasket)

        fun bindItem(dataBasket: LapanganBasket, listenerFutsal: (LapanganBasket) -> Unit, context: Context) {
            Glide.with(context)
                .load(dataBasket.poster)
                .placeholder(R.drawable.progress_animation)
                .into(poster)

            lokasi.text = dataBasket.alamat
            harga.formatPrice(dataBasket.price)
            rating.text = dataBasket.rating

            itemView.setOnClickListener{
                listenerFutsal(dataBasket)
            }
        }

    }
}