package com.apps.atokoku.model.transaction

import com.google.gson.annotations.SerializedName

data class TransactionEntity(
    @SerializedName("id_created")
    var idCreated: String,

    @SerializedName("total_transaksi")
    var totalTransaksi: String,

    @SerializedName("tanggal_transaksi")
    var tanggalTransaksi: String,

    @SerializedName("judul_transaksi")
    var judulTransaksi: String,

    @SerializedName("id_transaksi")
    var idTransaksi: String
)
