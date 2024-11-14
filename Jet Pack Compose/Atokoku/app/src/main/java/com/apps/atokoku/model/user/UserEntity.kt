package com.apps.atokoku.model.user

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("jenis")
    var jenis: String,

    @SerializedName("tanggal_buat")
    var tanggalBuat: String,

    @SerializedName("nama_user")
    var namaUser: String,

    @SerializedName("password")
    var password: String,

    @SerializedName("username")
    var username: String,

    @SerializedName("id_user")
    var idUser: String
)