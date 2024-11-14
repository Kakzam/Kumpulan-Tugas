package com.apps.atokoku.model.notifikasi

import com.google.gson.annotations.SerializedName

data class NotificationEntity(

    @SerializedName("approve")
    var approve: String,

    @SerializedName("jenis_created")
    var jenisCreated: String,

    @SerializedName("created")
    var created: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("tujuan")
    var tujuan: String,

    @SerializedName("isi")
    var isi: String,

    @SerializedName("judul")
    var judul: String,

    @SerializedName("tanggal")
    var tanggal: String,

    @SerializedName("id")
    var id: String
)
