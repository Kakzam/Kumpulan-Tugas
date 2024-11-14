package com.apps.atokoku.network.presenter

import com.apps.atokoku.network.rest.ApiUtils
import com.apps.atokoku.network.rest.RetrofitCallback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestPresenter : RestInterface {

    /* Validation Login ------------------------------------------------------------------------- */
    override fun login(username: String, password: String, callback: RetrofitCallback) {
        ApiUtils.getBase().login(username, password).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature Dashboard ------------------------------------------------------------------------ */
    override fun getDashboard(callback: RetrofitCallback) {
        ApiUtils.getBase().getDashboard().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature Item ----------------------------------------------------------------------------- */
    override fun createItem(
        namaBarang: String,
        idCreated: String,
        harga: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().createItem(namaBarang, idCreated, harga, stock, warning).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun readItem(callback: RetrofitCallback) {
        ApiUtils.getBase().readItem().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun updateItem(
        idBarang: String,
        namaBarang: String,
        id: String,
        harga: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().updateItem(idBarang, namaBarang, id, harga, stock, warning).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun deleteItem(id: String, callback: RetrofitCallback) {
        ApiUtils.getBase().deleteItem(id).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature Notification --------------------------------------------------------------------- */
    override fun createNotification(
        judul: String,
        isi: String,
        tujuan: String,
        idCreated: String,
        idJenisCreated: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().createNotification(judul, isi, tujuan, idCreated, idJenisCreated).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun readNotification(callback: RetrofitCallback) {
        ApiUtils.getBase().readNotification().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun approveNotification(
        id: String,
        judul: String,
        isi: String,
        tujuan: String,
        idCreated: String,
        idJenisCreated: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().approveNotification(id, judul, isi, tujuan, idCreated, idJenisCreated).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun deleteNotification(id: String, callback: RetrofitCallback) {
        ApiUtils.getBase().deleteNotification(id).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature Transaction ---------------------------------------------------------------------- */
    override fun createTransaction(
        titleTransaction: String,
        idCreated: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().createTransaction(titleTransaction, idCreated).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun readTransaction(callback: RetrofitCallback) {
        ApiUtils.getBase().readTransaction().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun updateTransaction(
        idTransaction: String,
        idCreated: String,
        titleTransaction: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().updateTransaction(idTransaction, idCreated, titleTransaction).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun deleteTransaction(idTransaction: String, callback: RetrofitCallback) {
        ApiUtils.getBase().deleteTransaction(idTransaction).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature Transaction Item ----------------------------------------------------------------- */
    override fun createTransactionItem(
        idTransaction: String,
        idLogin: String,
        idItem: String,
        nameItem: String,
        buy: String,
        qty: String,
        total: String,
        idCreated: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().createTransactionItem(idTransaction, idLogin, idItem, nameItem, buy, qty, total, idCreated, stock, warning).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun readTransactionItem(idTransaction: String, callback: RetrofitCallback) {
        ApiUtils.getBase().readTransactionItem(idTransaction).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun updateTransactionItem(
        idTransaction: String,
        idUser: String,
        idTransactionItem: String,
        idItem: String,
        nameItem: String,
        buy: String,
        qty: String,
        total: String,
        qtyBefore: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().updateTransactionItem(idTransaction, idUser, idTransactionItem, idItem, nameItem, buy, qty, total, qtyBefore).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun deleteTransactionItem(
        idTransaction: String,
        idUser: String,
        idTransactionItem: String,
        qtyBefore: String,
        idItem: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().deleteTransactionItem(idTransaction, idUser, idTransactionItem, qtyBefore, idItem).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    /* Feature User ----------------------------------------------------------------------------- */
    override fun createUser(
        username: String,
        password: String,
        namaUser: String,
        jenis: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().createUser(username, password, namaUser, jenis).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun readUser(callback: RetrofitCallback) {
        ApiUtils.getBase().readUser().enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun updateUser(
        idUser: String,
        username: String,
        password: String,
        namaUser: String,
        jenis: String,
        callback: RetrofitCallback
    ) {
        ApiUtils.getBase().updateUser(idUser, username, password, namaUser, jenis).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }

    override fun deleteUser(id: String, callback: RetrofitCallback) {
        ApiUtils.getBase().deleteUser(id).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    if (response.isSuccessful) callback.onSuccess(response.body()!!.string())
                    else if (!response.isSuccessful) callback.onFailed(response.errorBody()!!.string())
                } catch (e: Exception) {
                    callback.onFailed(e.toString())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }
}