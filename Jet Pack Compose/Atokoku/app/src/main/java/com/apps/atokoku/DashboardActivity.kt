package com.apps.atokoku

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.apps.atokoku.base.BaseActivity
import com.apps.atokoku.base.Preference
import com.apps.atokoku.ui.theme.BUTTON

class DashboardActivity : BaseActivity() {

    @Composable
    override fun loadUi() {

        var dashboard by remember { mutableStateOf(true) }
        var item by remember { mutableStateOf(false) }
        var transaksi by remember { mutableStateOf(false) }
        var notifikasi by remember { mutableStateOf(false) }
        var pengguna by remember { mutableStateOf(false) }
        var akun by remember { mutableStateOf(false) }

        Scaffold(bottomBar = {
            BottomNavigation(Modifier.fillMaxWidth()) {
                BottomNavigationItem(
                    icon = {
                        if (dashboard){
                            Icon(
                                painterResource(id = R.drawable.ic_home_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_home_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(UTAMA) },
                    selected = (dashboard),
                    onClick = {
                        dashboard = true
                        item = false
                        transaksi = false
                        notifikasi = false
                        pengguna = false
                        akun = false
                    }
                )

                if (Preference.getIdType().equals("1") || Preference.getIdType()
                        .equals("2")
                ) BottomNavigationItem(
                    icon = {
                        if (item){
                            Icon(
                                painterResource(id = R.drawable.ic_item_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_item_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(BARANG) },
                    selected = (item),
                    onClick = {
                        dashboard = false
                        item = true
                        transaksi = false
                        notifikasi = false
                        pengguna = false
                        akun = false
                    }
                )

                if (Preference.getIdType().equals("1") || Preference.getIdType().equals("3")
                ) BottomNavigationItem(
                    icon = {
                        if (transaksi){
                            Icon(
                                painterResource(id = R.drawable.ic_transaction_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_transaction_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(TRANSACTION) },
                    selected = (transaksi),
                    onClick = {
                        dashboard = false
                        item = false
                        transaksi = true
                        notifikasi = false
                        pengguna = false
                        akun = false
                    }
                )


                BottomNavigationItem(
                    icon = {
                        if (notifikasi){
                            Icon(
                                painterResource(id = R.drawable.ic_notification_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_notification_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(NOTIFICATION) },
                    selected = (notifikasi),
                    onClick = {
                        dashboard = false
                        item = false
                        transaksi = false
                        notifikasi = true
                        pengguna = false
                        akun = false
                    }
                )

                if (Preference.getIdType().equals("1")) BottomNavigationItem(
                    icon = {
                        if (pengguna){
                            Icon(
                                painterResource(id = R.drawable.ic_user_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_user_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(USER) },
                    selected = (pengguna),
                    onClick = {
                        dashboard = false
                        item = false
                        transaksi = false
                        notifikasi = false
                        pengguna = true
                        akun = false
                    }
                )

                BottomNavigationItem(
                    icon = {
                        if (akun){
                            Icon(
                                painterResource(id = R.drawable.ic_account_white),
                                contentDescription = null
                            )
                        } else {
                            Icon(
                                painterResource(id = R.drawable.ic_account_black),
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.background(BUTTON),
                    label = { Text(AKUN) },
                    selected = (akun),
                    onClick = {
                        dashboard = false
                        item = false
                        transaksi = false
                        notifikasi = false
                        pengguna = false
                        akun = true
                    }
                )
            }
        }, content = {
            if (dashboard) UtamaActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
            if (item) BarangActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
            if (transaksi) TransaksiActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
            if (notifikasi) NotifikasiActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
            if (pengguna) UserActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
            if (akun) AkunActivity(Modifier.padding(bottom = it.calculateBottomPadding()))
        })
        AlertBack()
    }
}