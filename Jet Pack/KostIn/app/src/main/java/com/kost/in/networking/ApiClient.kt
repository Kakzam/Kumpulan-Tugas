package com.kost.`in`.networking

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class ApiClient {

    companion object{

        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"

        fun getClient(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
        }
    }