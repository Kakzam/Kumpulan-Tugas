package com.apps.atokoku.network.rest

import com.apps.atokoku.util.ApiConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BaseApiService {

    /* LOGIN -------------------------------------------------------------------------------------*/
    
    @FormUrlEncoded
    @POST(ApiConfig.LOGIN)
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseBody>

    /* DASHBOARD ---------------------------------------------------------------------------------*/

    @GET(ApiConfig.GET_DASHBOARD)
    fun getDashboard(): Call<ResponseBody>

    @GET(ApiConfig.LOGIN)
    fun getProfile(
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    /* ITEM --------------------------------------------------------------------------------------*/

    @FormUrlEncoded
    @POST(ApiConfig.CREATE_ITEM)
    fun createItem(
        @Field("nama_barang") namaBarang: String,
        @Field("id_created") idCreated: String,
        @Field("harga") harga: String,
        @Field("stock") stock: String,
        @Field("warning") warning: String
    ): Call<ResponseBody>

    @GET(ApiConfig.READ_ITEM)
    fun readItem(): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.UPDATE_ITEM)
    fun updateItem(
        @Field("id_barang") idBarang: String,
        @Field("nama_barang") namaBarang: String,
        @Field("id") id: String,
        @Field("harga") harga: String,
        @Field("stock") stock: String,
        @Field("warning") warning: String
    ): Call<ResponseBody>


    @FormUrlEncoded
    @POST(ApiConfig.DELETE_ITEM)
    fun deleteItem(
        @Field("id") idBarang: String
    ): Call<ResponseBody>

    /* Notification ------------------------------------------------------------------------------*/

    @FormUrlEncoded
    @POST(ApiConfig.CREATE_NOTIFICATION)
    fun createNotification(
        @Field("judul") judul: String,
        @Field("isi") isi: String,
        @Field("tujuan") tujuan: String,
        @Field("id_created") idCreated: String,
        @Field("id_jenis_created") idJenisCreated: String
    ): Call<ResponseBody>

    @GET(ApiConfig.READ_NOTIFICATION)
    fun readNotification(): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.APPROVE_NOTIFICATION)
    fun approveNotification(
        @Field("id_notifikasi") id: String,
        @Field("judul") judul: String,
        @Field("isi") isi: String,
        @Field("tujuan") tujuan: String,
        @Field("id_created") idCreated: String,
        @Field("id_jenis_created") idJenisCreated: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.DELETE_NOTIFICATION)
    fun deleteNotification(@Field("id") idNotification: String): Call<ResponseBody>

    /* Transaction -------------------------------------------------------------------------------*/

    @FormUrlEncoded
    @POST(ApiConfig.CREATE_TRANSACTION_NOTA)
    fun createTransaction(
        @Field("judul_transaksi") titleTransaction: String,
        @Field("id_created") idCreated: String
    ): Call<ResponseBody>

    @GET(ApiConfig.READ_TRANSACTION_NOTA)
    fun readTransaction(): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.UPDATE_TRANSACTION_NOTA)
    fun updateTransaction(
        @Field("id_transaction") idTransaction: String,
        @Field("id_created") idCreated: String,
        @Field("judul_transaksi") titleTransaction: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.DELETE_TRANSACTION_NOTA)
    fun deleteTransaction(@Field("id") idTransaction: String): Call<ResponseBody>

    /* Transaction Item --------------------------------------------------------------------------*/

    @FormUrlEncoded
    @POST(ApiConfig.CREATE_TRANSACTION_NOTA)
    fun createTransactionItem(
        @Field("id_transaksi") idTransaction: String,
        @Field("id_login") idLogin: String,
        @Field("id") idItem: String,
        @Field("nama_barang") nameItem: String,
        @Field("harga") buy: String,
        @Field("qty") qty: String,
        @Field("total") total: String,
        /* Barang */
        @Field("id_created") idCreated: String,
        @Field("stock") stock: String,
        @Field("warning") warning: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.READ_TRANSACTION_ITEM)
    fun readTransactionItem(@Field("id") idTransaction: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.UPDATE_TRANSACTION_NOTA)
    fun updateTransactionItem(
        @Field("id_transaksi") idTransaction: String,
        @Field("id_login") idUser: String,
        @Field("id_transaksi_barang") idTransactionItem: String,
        @Field("id_barang") idItem: String,
        @Field("nama_barang") nameItem: String,
        @Field("harga") buy: String,
        @Field("qty") qty: String,
        @Field("total") total: String,
        @Field("qty_before") qtyBefore: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.DELETE_TRANSACTION_NOTA)
    fun deleteTransactionItem(
        @Field("id_transaksi") idTransaction: String,
        @Field("id_login") idUser: String,
        @Field("id_transaksi_barang") idTransactionItem: String,
        @Field("qty_before") qtyBefore: String,
        @Field("id_barang") idItem: String
    ): Call<ResponseBody>

    /* User --------------------------------------------------------------------------------------*/

    @FormUrlEncoded
    @POST(ApiConfig.CREATE_USER)
    fun createUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("nama_user") namaUser: String,
        @Field("jenis") jenis: String
    ): Call<ResponseBody>

    @GET(ApiConfig.READ_USER)
    fun readUser(): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.UPDATE_USER)
    fun updateUser(
        @Field("id") idUser: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("nama_user") namaUser: String,
        @Field("jenis") jenis: String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST(ApiConfig.DELETE_USER)
    fun deleteUser(@Field("id") idUser: String): Call<ResponseBody>

}