package com.apps.atokoku.model.transaction

import com.google.gson.annotations.SerializedName


data class Transaction(
    @SerializedName("response")
    var response: List<TransactionEntity>
)