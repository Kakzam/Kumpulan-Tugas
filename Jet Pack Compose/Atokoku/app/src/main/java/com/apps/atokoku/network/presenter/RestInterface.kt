package com.apps.atokoku.network.presenter

import com.apps.atokoku.network.rest.RetrofitCallback

interface RestInterface {

    /* Validation Login ------------------------------------------------------------------------- */
    fun login(username: String, password: String, callback: RetrofitCallback)

    /* Feature Dashboard ------------------------------------------------------------------------ */
    fun getDashboard(callback: RetrofitCallback)

    /* Feature Item ----------------------------------------------------------------------------- */
    fun createItem(
        namaBarang: String,
        idCreated: String,
        harga: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    )

    fun readItem(callback: RetrofitCallback)
    fun updateItem(
        idBarang: String,
        namaBarang: String,
        id: String,
        harga: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    )

    fun deleteItem(id: String, callback: RetrofitCallback)

    /* Feature Notification --------------------------------------------------------------------- */
    fun createNotification(
        judul: String,
        isi: String,
        tujuan: String,
        idCreated: String,
        idJenisCreated: String,
        callback: RetrofitCallback
    )

    fun readNotification(callback: RetrofitCallback)
    fun approveNotification(
        id: String,
        judul: String,
        isi: String,
        tujuan: String,
        idCreated: String,
        idJenisCreated: String,
        callback: RetrofitCallback
    )

    fun deleteNotification(id: String, callback: RetrofitCallback)

    /* Feature Transaction ---------------------------------------------------------------------- */
    fun createTransaction(
        titleTransaction: String,
        idCreated: String,
        callback: RetrofitCallback
    )

    fun readTransaction(callback: RetrofitCallback)
    fun updateTransaction(
        idTransaction: String,
        idCreated: String,
        titleTransaction: String,
        callback: RetrofitCallback
    )

    fun deleteTransaction(idTransaction: String, callback: RetrofitCallback)

    /* Feature Transaction Item ----------------------------------------------------------------- */
    fun createTransactionItem(
        idTransaction: String,
        idLogin: String,
        idItem: String,
        nameItem: String,
        buy: String,
        qty: String,
        total: String,  /* Barang */
        idCreated: String,
        stock: String,
        warning: String,
        callback: RetrofitCallback
    )

    fun readTransactionItem(idTransaction: String, callback: RetrofitCallback)
    fun updateTransactionItem(
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
    )

    fun deleteTransactionItem(
        idTransaction: String,
        idUser: String,
        idTransactionItem: String,
        qtyBefore: String,
        idItem: String,
        callback: RetrofitCallback
    )

    /* Feature User ----------------------------------------------------------------------------- */
    fun createUser(
        username: String,
        password: String,
        namaUser: String,
        jenis: String,
        callback: RetrofitCallback
    )

    fun readUser(callback: RetrofitCallback)

    fun updateUser(
        idUser: String,
        username: String,
        password: String,
        namaUser: String,
        jenis: String,
        callback: RetrofitCallback
    )

    fun deleteUser(id: String, callback: RetrofitCallback)

}