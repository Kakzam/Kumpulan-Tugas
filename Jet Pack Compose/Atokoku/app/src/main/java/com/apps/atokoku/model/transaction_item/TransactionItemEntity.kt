package com.apps.atokoku.model.transaction_item

import com.google.gson.annotations.SerializedName

data class TransactionItemEntity(
    @SerializedName("id_created")
    var idCreated: String,

    @SerializedName("tanggal_transaksi_barang")
    var tanggalTransaksiBarang: String,

    @SerializedName("total")
    var total: String,

    @SerializedName("qty")
    var qty: String,

    @SerializedName("harga_barang")
    var hargaBarang: String,

    @SerializedName("nama_barang")
    var namaBarang: String,

    @SerializedName("id_barang")
    var idBarang: String,

    @SerializedName("id_transaksi")
    var idTransaksi: String,

    @SerializedName("id_transaksi_barang")
    var idTransaksiBarang: String
)