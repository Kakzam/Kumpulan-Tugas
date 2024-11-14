package com.apps.atokoku.model.login

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("response")
    var response: LoginEntity
)