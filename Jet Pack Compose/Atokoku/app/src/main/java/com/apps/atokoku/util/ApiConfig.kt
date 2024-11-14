package com.apps.atokoku.util

class ApiConfig {
    companion object {

        /* URL ---------------------------------------------------------------------------------- */
        const val URL = "http://20.189.120.49/atokoku/index.php/mobile/"
//        const val URL = "http://192.168.43.181/atokoku/mobile/"

        /* Validation Login */
        const val LOGIN = "validationUser"

        /* Feature Dashboard */
        const val GET_DASHBOARD = "getDashboard"

        /* Feature Item */
        const val CREATE_ITEM = "addItem"
        const val READ_ITEM = "getItem"
        const val UPDATE_ITEM = "updateItem"
        const val DELETE_ITEM = "deleteItem"

        /* Feature Notification */
        const val CREATE_NOTIFICATION = "addNotification"
        const val READ_NOTIFICATION = "getNotifikasi"
        const val APPROVE_NOTIFICATION = "approveNotification"
        const val DELETE_NOTIFICATION = "deleteNotification"

        /* Feature Transaction Nota */
        const val CREATE_TRANSACTION_NOTA = "addTransaction"
        const val READ_TRANSACTION_NOTA = "getTransaction"
        const val UPDATE_TRANSACTION_NOTA = "updateTransaction"
        const val DELETE_TRANSACTION_NOTA = "deleteTransaction"

        /* Feature Transaction Barang */
        const val CREATE_TRANSACTION_ITEM = "addTransactionBarang"
        const val READ_TRANSACTION_ITEM = "getTransaksiBarang"
        const val UPDATE_TRANSACTION_ITEM = "updateTransactionBarang"
        const val DELETE_TRANSACTION_ITEM = "deleteTransactionBarang"

        /* Feature User */
        const val CREATE_USER = "addUser"
        const val READ_USER = "getUser"
        const val UPDATE_USER = "updateUser"
        const val DELETE_USER = "deleteUser"
    }
}