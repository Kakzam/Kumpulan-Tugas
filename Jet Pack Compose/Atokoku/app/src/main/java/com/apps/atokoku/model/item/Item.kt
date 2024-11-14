package com.apps.atokoku.model.item

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("response")
    var response: List<ItemEntity>
)