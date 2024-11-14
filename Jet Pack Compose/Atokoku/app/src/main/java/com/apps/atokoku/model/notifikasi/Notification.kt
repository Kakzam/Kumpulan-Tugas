package com.apps.atokoku.model.notifikasi

import com.google.gson.annotations.SerializedName


data class Notification(
    @SerializedName("response")
    var response: List<NotificationEntity>
)