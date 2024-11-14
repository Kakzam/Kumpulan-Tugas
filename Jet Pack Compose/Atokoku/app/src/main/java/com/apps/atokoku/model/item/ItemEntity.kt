package com.apps.atokoku.model.item

import com.google.gson.annotations.SerializedName

data class ItemEntity(
    @SerializedName("warning")
    var warning: String,

    @SerializedName("stock")
    var stock: String,

    @SerializedName("harga")
    var harga: String,

    @SerializedName("id_created")
    var idCreated: String,

    @SerializedName("nama_barang")
    var namaBarang: String,

    @SerializedName("tanggal_buat")
    var tanggalBuat: String,

    @SerializedName("id_barang")
    var idBarang: String
)
