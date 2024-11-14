package com.apps.atokoku.model.validation

import com.google.gson.annotations.SerializedName

data class Validation(
    @SerializedName("notifikasi")
    var notifikasi: String,

    @SerializedName("data")
    var data: Boolean
)