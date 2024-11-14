package com.azhar.banksampah.view.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.azhar.banksampah.R
import com.azhar.banksampah.model.ModelDatabaseMasuk
import com.azhar.banksampah.utils.FunctionHelper.rupiahFormat
import kotlinx.android.synthetic.main.list_item_riwayat_jual.view.*

/**
 * Created by Azhar Rivaldi on 6-12-2021
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

class RiwayatAdapterJual(
    var mContext: Context,
    modelInputList: MutableList<ModelDatabaseMasuk>,
    adapterCallback: RiwayatAdapterCallback
) : RecyclerView.Adapter<RiwayatAdapterJual.ViewHolder>() {

    var modelDatabase: MutableList<ModelDatabaseMasuk>
    var mAdapterCallback: RiwayatAdapterCallback

    fun setDataAdapter(items: List<ModelDatabaseMasuk>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_riwayat_jual, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: ModelDatabaseMasuk = modelDatabase[position]

        holder.tvDate.setText(data.tanggal)
        holder.tvKategori.text = "Varian Rasa  " + data.jenisbarang
        holder.tvBerat.text = "Jumlah : " + data.berat.toString() + " Pcs"
        holder.tvSaldo.text = "Pendapatan : " + rupiahFormat(data.harga)

        if (data.berat ==0 ) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvStatus.text = "Masih dalam proses"
        } else {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            holder.tvStatus.text = "Sudah di konfirmasi"
        }
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDate: TextView = itemView.tvDate
        var tvKategori: TextView = itemView.tvKategori
        var tvBerat: TextView = itemView.tvBerat
        var tvSaldo: TextView = itemView.tvSaldo
        var tvStatus: TextView = itemView.tvStatus
        var imageDelete: ImageView = itemView.imageDelete

        init {
            imageDelete.setOnClickListener { view: View? ->
                val modelLaundry: ModelDatabaseMasuk = modelDatabase[adapterPosition]
                mAdapterCallback.onDelete(modelLaundry)
            }
        }
    }

    interface RiwayatAdapterCallback {
        fun onDelete(modelDatabase: ModelDatabaseMasuk?)
    }

    init {
        modelDatabase = modelInputList
        mAdapterCallback = adapterCallback
    }

}