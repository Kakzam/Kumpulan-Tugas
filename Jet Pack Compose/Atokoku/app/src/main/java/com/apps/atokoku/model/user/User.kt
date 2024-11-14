package com.apps.atokoku.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("response")
    var response: List<UserEntity>
)