package com.apps.atokoku.model

import com.google.gson.annotations.SerializedName

data class Dashboard(
    @SerializedName("response")
    var response: DashboardEntity
)