package com.apps.atokoku.network.rest

interface RetrofitCallback {

    fun onSuccess(response: String)

    fun onFailed(response: String)

    fun onFailure(throwable: Throwable)

}