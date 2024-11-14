package com.kost.`in`.data.response

import com.kost.`in`.data.model.ModelResults
import com.google.gson.annotations.SerializedName


class ModelResultNearby {

    @SerializedName("results")
    val modelResults: List<ModelResults> = emptyList()
}