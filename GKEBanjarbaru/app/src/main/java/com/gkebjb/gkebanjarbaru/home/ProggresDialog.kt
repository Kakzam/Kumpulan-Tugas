package com.gkebjb.gkebanjarbaru.home

import android.app.Activity
import android.app.AlertDialog
import com.gkebjb.gkebanjarbaru.R

class ProggresDialog(val loading: Activity) {
    private lateinit var isAlertDialog: AlertDialog

    fun showLoading() {
        val inflater = loading.layoutInflater
        val dialogView = inflater.inflate(R.layout.proggres_bar, null)

        val buildAlert = AlertDialog.Builder(loading)
        buildAlert.setView(dialogView)
        buildAlert.setCancelable(false)
        isAlertDialog = buildAlert.create()
        isAlertDialog.window?.setBackgroundDrawableResource(R.color.hilang)
        isAlertDialog.show()
    }

    fun dismissLoading() {
        isAlertDialog.dismiss()
    }
}