package com.apps.atokoku.model.transaction_item

import com.google.gson.annotations.SerializedName

data class TransactionItem(
    @SerializedName("response")
    var response: List<TransactionItemEntity>
)