package com.apps.atokoku.model

import com.google.gson.annotations.SerializedName

data class DashboardEntity(
    @SerializedName("barang")
    var barang: Int,

    @SerializedName("transaksi")
    var transaksi: Int,

    @SerializedName("user")
    var user: Int
)