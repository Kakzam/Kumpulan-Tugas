package com.apps.atokoku.network.rest

import com.apps.atokoku.util.ApiConfig

class ApiUtils {
    companion object {

        fun getBase(): BaseApiService {
            return RetrofitClient.getClient(ApiConfig.URL).create(BaseApiService::class.java)
        }

    }
}