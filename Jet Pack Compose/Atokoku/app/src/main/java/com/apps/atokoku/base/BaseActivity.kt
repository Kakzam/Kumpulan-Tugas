package com.apps.atokoku.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.apps.atokoku.*
import com.apps.atokoku.R
import com.apps.atokoku.model.Dashboard
import com.apps.atokoku.model.item.Item
import com.apps.atokoku.model.item.ItemEntity
import com.apps.atokoku.model.login.Login
import com.apps.atokoku.model.notifikasi.Notification
import com.apps.atokoku.model.notifikasi.NotificationEntity
import com.apps.atokoku.model.transaction.Transaction
import com.apps.atokoku.model.transaction.TransactionEntity
import com.apps.atokoku.model.user.User
import com.apps.atokoku.model.user.UserEntity
import com.apps.atokoku.model.validation.Validation
import com.apps.atokoku.network.presenter.RestPresenter
import com.apps.atokoku.network.rest.RetrofitCallback
import com.apps.atokoku.ui.theme.*
import com.google.gson.Gson
import java.text.NumberFormat

open class BaseActivity : ComponentActivity() {

    /* String Bottom Navigation */
    companion object {
        const val UTAMA = "Home"
        const val BARANG = "Item"
        const val TRANSACTION = "Nota"
        const val NOTIFICATION = "Notif"
        const val USER = "User"
        const val AKUN = "Akun"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Preference.init(this).build()
        setContent {
            AtokokuTheme {
                try {
                    loadData()
                } catch (e: Exception) {
                    setLog("BaseActivity : ${"" + e.printStackTrace()}")
//                    setLog("BaseActivity : ${temp + "" + e.printStackTrace()}")
                }

                loadUi()
                AlertBack()
//                setLog("BaseActivity : ${temp} Inisialisasi UI ----======")
            }
        }
    }

    open fun loadData() {}

    @Composable
    open fun loadUi() {
    }

    fun setLog(message: String) {
        Log.v("ZAM", message)
    }

    fun setToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun setActivity(activity: Class<*>) {
        intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

    /* SplashScreen */
    @Composable
    fun SplashActivity() {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = BACKGROUND), Arrangement.Center, Alignment.CenterHorizontally) {
            Image(painterResource(R.drawable.ic_splash), "", Modifier.fillMaxWidth())
        }
    }

    /* Activity Utama */
    @Composable
    fun UtamaActivity(modifier: Modifier) {
        var user by remember { mutableStateOf("") }
        var transaksi by remember { mutableStateOf("") }
        var barang by remember { mutableStateOf("") }

        fun load() {
            RestPresenter().getDashboard(object : RetrofitCallback {
                override fun onSuccess(response: String) {
                    val validation = Gson().fromJson(response, Validation::class.java)
                    if (validation.data) {
                        val data = Gson().fromJson(response, Dashboard::class.java)
                        user = data.response.user.toString()
                        transaksi = data.response.transaksi.toString()
                        barang = data.response.barang.toString()
                        setToast(validation.notifikasi)
                    } else {
                        setToast(validation.notifikasi)
                    }
                    setLog("UtamaActivity: getDashboard: onSuccess:" + response)
                }

                override fun onFailed(response: String) {
                    setLog("UtamaActivity: getDashboard: onFailed:" + response)
                }

                override fun onFailure(throwable: Throwable) {
                    setLog("UtamaActivity: getDashboard: onFailure:" + throwable.message)
                }

            })
        }
        load()

        Column(
            modifier
                .fillMaxSize()
                .background(color = BACKGROUND)) {

            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp, bottom = 25.dp, top = 25.dp)
                    .background(color = GREY, shape = RoundedCornerShape(16.dp))) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(BLUE1, RoundedCornerShape(16.dp)), Arrangement.Center) {
                    Image(painter = painterResource(id = R.drawable.ic_d_user), contentDescription = "",
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp))
                }

                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()) {

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "User", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(color = BLUE3, shape = RoundedCornerShape(16.dp)), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "${user}", fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.White)
                    }
                }
            }

            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp, bottom = 25.dp)
                    .background(color = GREY, shape = RoundedCornerShape(16.dp))) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(BLUE1, RoundedCornerShape(16.dp)), Arrangement.Center) {
                    Image(painter = painterResource(id = R.drawable.ic_d_nota), contentDescription = "",
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp))
                }

                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()) {

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "Nota", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(color = BLUE3, shape = RoundedCornerShape(16.dp)), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "${transaksi}", fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.White)
                    }
                }
            }

            Row(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(start = 64.dp, end = 64.dp, bottom = 25.dp)
                    .background(color = GREY, shape = RoundedCornerShape(16.dp))) {
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(BLUE1, RoundedCornerShape(16.dp)), Arrangement.Center) {
                    Image(painter = painterResource(id = R.drawable.ic_d_item), contentDescription = "",
                        Modifier
                            .fillMaxWidth()
                            .padding(20.dp))
                }

                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()) {

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "Item", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    }

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .background(color = BLUE3, shape = RoundedCornerShape(16.dp)), Arrangement.Center, Alignment.CenterHorizontally) {
                        Text(text = "${barang}", fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.White)
                    }
                }
            }
        }
    }

    /* Activity Barang */
    @Composable
    fun BarangActivity(modifier: Modifier) {

        var openDalog by remember { mutableStateOf(false) }
        var update by remember { mutableStateOf(false) }
        var loading by remember { mutableStateOf(true) }
        var check by remember { mutableStateOf(false) }
        val item = remember { mutableListOf<ItemEntity>() }

        var idBarang by remember { mutableStateOf("") }
        var namaBarang by remember { mutableStateOf("") }
        var harga by remember { mutableStateOf("") }
        var stock by remember { mutableStateOf("") }
        var warning by remember { mutableStateOf("") }

        fun load(){
            RestPresenter().readItem(object : RetrofitCallback {
                override fun onSuccess(response: String) {
                    val validation = Gson().fromJson(response, Validation::class.java)
                    if (validation.data) {
                        val data = Gson().fromJson(response, Item::class.java)
                        item.clear()
                        item.addAll(data.response)
                        loading = false
                        check = true
                        setToast(validation.notifikasi)
                    } else {
                        loading = false
                        setToast(validation.notifikasi)
                    }
                    setLog("BarangActivity: readItem: onSuccess:" + response)
                }

                override fun onFailed(response: String) {
                    loading = false
                    setLog("BarangActivity: readItem: onFailed:" + response)
                }

                override fun onFailure(throwable: Throwable) {
                    loading = false
                    setLog("BarangActivity: readItem: onFailure:" + throwable.message)
                }

            })
        }

        if (loading){
            load()
        }

        if (check) {
            Column(
                modifier
                    .fillMaxSize()
                    .background(color = BACKGROUND)) {
                if (loading) {
                    CircularProgressIndicator(modifier.align(alignment = Alignment.CenterHorizontally))
                }

                Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "", Modifier.absolutePadding(right = 14.dp, left = 14.dp, top = 10.dp))

                LazyColumn {
                    itemsIndexed(item) { index, route ->
                        run {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 10.dp),
                                RoundedCornerShape(12.dp),
                                backgroundColor = Color(0xFFEEE6E5)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 8.dp,
                                            bottom = 4.dp
                                        )
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "ID ${item.get(index).idBarang}",
                                            Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Tanggal ${item.get(index).tanggalBuat}",
                                            Modifier.weight(3f),
                                            textAlign = TextAlign.End
                                        )
                                    }

                                    Text(text = "${item.get(index).namaBarang.toUpperCase()}",
                                        Modifier
                                            .background(Color.White, RoundedCornerShape(4.dp))
                                            .padding(
                                                top = 4.dp,
                                                bottom = 4.dp,
                                                start = 8.dp,
                                                end = 8.dp
                                            ))

                                    val numberFormat = NumberFormat.getCurrencyInstance()
                                        numberFormat.maximumFractionDigits = 0
                                    Text(
                                        text = "Harga : ${numberFormat.format(route.harga.toInt()).replace("$", "Rp ")}",
                                        Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Stock : ${item.get(index).stock}",
                                        Modifier.fillMaxWidth()
                                    )

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "HAPUS",
                                            Modifier
                                                .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                                                .clickable {
                                                    RestPresenter().deleteItem(
                                                        route.idBarang,
                                                        object : RetrofitCallback {
                                                            override fun onSuccess(response: String) {
                                                                setToast("Berhasil Menghapus " + route.namaBarang)
                                                                load()
                                                                setLog("BarangActivity: readItem: onSuccess:" + response)
                                                            }

                                                            override fun onFailed(response: String) {
                                                                loading = false
                                                                setLog("BarangActivity: readItem: onFailed:" + response)
                                                            }

                                                            override fun onFailure(throwable: Throwable) {
                                                                loading = false
                                                                setLog("BarangActivity: readItem: onFailure:" + throwable.message)
                                                            }

                                                        })
                                                },
                                            color = Color(0xFFFF0000)
                                        )
                                        Text(text = "UBAH",
                                            Modifier
                                                .padding(end = 12.dp, bottom = 12.dp, top = 12.dp)
                                                .clickable {
                                                    namaBarang = route.namaBarang
                                                    harga = route.harga
                                                    stock = route.stock
                                                    warning = route.warning
                                                    idBarang = route.idBarang
                                                    openDalog = true
                                                    update = true
                                                },
                                            color = Color(0xFF029F12)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "",
                Modifier
                    .width(110.dp)
                    .height(141.dp)
                    .padding(bottom = 80.dp, end = 50.dp)
                    .clickable {
                        openDalog = true
                    }, contentScale = ContentScale.FillBounds)
        }

        if (openDalog) {
            Dialog(onDismissRequest = {}) {
                Column(Modifier.padding(top = 100.dp, bottom = 100.dp)) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White, RoundedCornerShape(12.dp)), verticalArrangement = Arrangement.Center) {

                        var title = ""
                        if (update) title = "Update Barang"
                        else title = "Tambah Barang"

                        Text(text = title,
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                        OutlinedTextField(
                            label = { Text(text = "Nama Barang") },
                            value = namaBarang,
                            onValueChange = { value ->
                                namaBarang = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Harga") },
                            value = harga,
                            onValueChange = { value ->
                                harga = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Stock") },
                            value = stock,
                            onValueChange = { value ->
                                stock = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Warning") },
                            value = warning,
                            onValueChange = { value ->
                                warning = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true
                        )

                        Row() {
                            Button(
                                onClick = {
                                    openDalog = false
                                    update = false
                                    namaBarang = ""
                                    harga = ""
                                    stock = ""
                                    warning = ""
                                    idBarang = ""
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "BATAL", color = Color.White)
                            }

                            Button(
                                onClick = {
                                    loading = true
                                    if (update) {
                                        RestPresenter().updateItem(
                                            idBarang,
                                            namaBarang,
                                            Preference.getId().toString(),
                                            harga,
                                            stock,
                                            warning,
                                            object : RetrofitCallback {
                                                override fun onSuccess(response: String) {
                                                    load()
                                                    namaBarang = ""
                                                    harga = ""
                                                    stock = ""
                                                    warning = ""
                                                    idBarang = ""
                                                    openDalog = false
                                                    update = false
                                                    setLog("BarangActivity: updateItem: onSuccess:" + response)
                                                }

                                                override fun onFailed(response: String) {
                                                    loading = false
                                                    setLog("BarangActivity: updateItem: onFailed:" + response)
                                                }

                                                override fun onFailure(throwable: Throwable) {
                                                    loading = false
                                                    setLog("BarangActivity: updateItem: onFailure:" + throwable.message)
                                                }

                                            })
                                    } else {
                                        RestPresenter().createItem(
                                            namaBarang,
                                            Preference.getId().toString(),
                                            harga,
                                            stock,
                                            warning,
                                            object : RetrofitCallback {
                                                override fun onSuccess(response: String) {
                                                    load()
                                                    namaBarang = ""
                                                    harga = ""
                                                    stock = ""
                                                    warning = ""
                                                    openDalog = false
                                                    setLog("BarangActivity: createItem: onSuccess:" + response)
                                                }

                                                override fun onFailed(response: String) {
                                                    loading = false
                                                    setLog("BarangActivity: createItem: onFailed:" + response)
                                                }

                                                override fun onFailure(throwable: Throwable) {
                                                    loading = false
                                                    setLog("BarangActivity: createItem: onFailure:" + throwable.message)
                                                }

                                            })
                                    }
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "SIMPAN", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    /* Activity Transaksi */
    @Composable
    fun TransaksiActivity(modifier: Modifier) {

        var openDalog by remember { mutableStateOf(false) }
        var update by remember { mutableStateOf(false) }
        var loading by remember { mutableStateOf(true) }
        val check = remember { mutableStateOf(false) }
        val transaksi = remember { mutableListOf<TransactionEntity>() }

        var idtransaksi by remember { mutableStateOf("") }
        var judul by remember { mutableStateOf("") }

        fun load(){
            RestPresenter().readTransaction(object : RetrofitCallback {
                override fun onSuccess(response: String) {
                    val validation = Gson().fromJson(response, Validation::class.java)
                    if (validation.data) {
                        val data = Gson().fromJson(response, Transaction::class.java)
                        transaksi.clear()
                        transaksi.addAll(data.response)
                        loading = false
                        check.value = true
                        setToast(validation.notifikasi)
                    } else {
                        loading = false
                        setToast(validation.notifikasi)
                    }
                    setLog("TransaksiActivity: readTransaction: onSuccess:" + response)
                }

                override fun onFailed(response: String) {
                    loading = false
                    setLog("TransaksiActivity: readTransaction: onFailed:" + response)
                }

                override fun onFailure(throwable: Throwable) {
                    loading = false
                    setLog("TransaksiActivity: readTransaction: onFailure:" + throwable.message)
                }

            })
        }

        if (loading){
            load()
        }

        if (check.value) {
            Column(
                modifier
                    .fillMaxSize()
                    .background(color = BACKGROUND)) {
                if (loading) {
                    CircularProgressIndicator(Modifier.align(alignment = Alignment.CenterHorizontally))
                }

                Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "", Modifier.absolutePadding(right = 14.dp, left = 14.dp, top = 10.dp))

                LazyColumn {
                    itemsIndexed(transaksi) { index, route ->
                        run {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 10.dp),
                                RoundedCornerShape(12.dp),
                                backgroundColor = Color(0xFFEEE6E5)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 8.dp,
                                            bottom = 4.dp
                                        )
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "ID ${route.idTransaksi}",
                                            Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Tanggal ${route.tanggalTransaksi}",
                                            Modifier.weight(3f),
                                            textAlign = TextAlign.End
                                        )
                                    }

                                    val numberFormat = NumberFormat.getCurrencyInstance()
                                    numberFormat.maximumFractionDigits = 0

                                    Text(text = route.judulTransaksi,
                                        Modifier
                                            .background(Color.White, RoundedCornerShape(4.dp))
                                            .padding(
                                                top = 4.dp,
                                                bottom = 4.dp,
                                                start = 8.dp,
                                                end = 8.dp
                                            ))
                                    Text(
                                        text = "Total : ${numberFormat.format(route.totalTransaksi.toInt()).replace("$", "Rp ")}",
                                        Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Bold
                                    )

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "HAPUS",
                                            Modifier
                                                .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                                                .clickable {
                                                    RestPresenter().deleteTransaction(
                                                        route.idTransaksi,
                                                        object : RetrofitCallback {
                                                            override fun onSuccess(response: String) {
                                                                setToast("Berhasil Menghapus " + route.judulTransaksi)
                                                                load()
                                                                setLog("TransaksiActivity: deleteTransaction: onSuccess:" + response)
                                                            }

                                                            override fun onFailed(response: String) {
                                                                loading = false
                                                                setLog("TransaksiActivity: deleteTransaction: onFailed:" + response)
                                                            }

                                                            override fun onFailure(throwable: Throwable) {
                                                                loading = false
                                                                setLog("TransaksiActivity: deleteTransaction: onFailure:" + throwable.message)
                                                            }

                                                        })
                                                },
                                            color = Color(0xFFFF0000)
                                        )
                                        Text(text = "UBAH",
                                            Modifier
                                                .padding(end = 12.dp, bottom = 12.dp, top = 12.dp)
                                                .clickable {
                                                    idtransaksi = route.idTransaksi
                                                    judul = route.judulTransaksi
                                                    update = true
                                                    openDalog = true
                                                },
                                            color = Color(0xFF029F12)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "",
                Modifier
                    .width(110.dp)
                    .height(141.dp)
                    .padding(bottom = 80.dp, end = 50.dp)
                    .clickable {
                        openDalog = true
                    }, contentScale = ContentScale.FillBounds)
        }

        if (openDalog) {
            Dialog(onDismissRequest = {}) {
                Column(Modifier.padding(top = 100.dp, bottom = 100.dp)) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White, RoundedCornerShape(12.dp)), verticalArrangement = Arrangement.Center) {

                        var title = ""
                        if (update) title = "Update Transaksi"
                        else title = "Tambah Transaksi"

                        Text(text = title,
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                        OutlinedTextField(
                            label = { Text(text = "Judul") },
                            value = judul,
                            onValueChange = { value ->
                                judul = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        Column() {
                            Row() {
                                Button(
                                    onClick = {
                                        openDalog = false
                                        update = false
                                        judul = ""
                                        idtransaksi = ""
                                    },
                                    Modifier
                                        .weight(1f)
                                        .absolutePadding(
                                            top = 8.dp,
                                            bottom = 0.dp,
                                            left = 16.dp,
                                            right = 16.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(BUTTON)
                                ) {
                                    Text(text = "BATAL", color = Color.White)
                                }

                                Button(
                                    onClick = {
                                        loading = true
                                        if (update) {
                                            RestPresenter().updateTransaction(
                                                idtransaksi,
                                                Preference.getId().toString(),
                                                judul,
                                                object : RetrofitCallback {
                                                    override fun onSuccess(response: String) {
                                                        load()
                                                        idtransaksi = ""
                                                        judul = ""
                                                        update = false
                                                        openDalog = false
                                                        setLog("TransaksiActivity: updateTransaction: onSuccess:" + response)
                                                    }

                                                    override fun onFailed(response: String) {
                                                        loading = false
                                                        setLog("TransaksiActivity: updateTransaction: onFailed:" + response)
                                                    }

                                                    override fun onFailure(throwable: Throwable) {
                                                        loading = false
                                                        setLog("TransaksiActivity: updateTransaction: onFailure:" + throwable.message)
                                                    }

                                                })
                                        } else {
                                            RestPresenter().createTransaction(
                                                judul,
                                                Preference.getId().toString(),
                                                object : RetrofitCallback {
                                                    override fun onSuccess(response: String) {
                                                        load()
                                                        judul = ""
                                                        idtransaksi = ""
                                                        openDalog = false
                                                        setLog("LoginActivity: login: onSuccess:" + response)
                                                    }

                                                    override fun onFailed(response: String) {
                                                        loading = false
                                                        setLog("LoginActivity: login: onFailed:" + response)
                                                    }

                                                    override fun onFailure(throwable: Throwable) {
                                                        loading = false
                                                        setLog("LoginActivity: login: onFailure:" + throwable.message)
                                                    }

                                                })
                                        }
                                    },
                                    Modifier
                                        .weight(1f)
                                        .absolutePadding(
                                            top = 8.dp,
                                            bottom = 0.dp,
                                            left = 16.dp,
                                            right = 16.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(BUTTON)
                                ) {
                                    Text(text = "SIMPAN", color = Color.White)
                                }
                            }

                            if (update){
                                Button(
                                    onClick = {
                                        setToast("Menu ini masih tahap development")
                                    },
                                    Modifier
                                        .fillMaxWidth()
                                        .absolutePadding(
                                            top = 4.dp,
                                            bottom = 0.dp,
                                            left = 16.dp,
                                            right = 16.dp
                                        ),
                                    colors = ButtonDefaults.buttonColors(BUTTON)
                                ) {
                                    Text(text = "Tambah Barang", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /* Activity Notifikasi */
    @Composable
    fun NotifikasiActivity(modifier: Modifier) {

        var openDalog by remember { mutableStateOf(false) }
        var loading by remember { mutableStateOf(true) }
        val check = remember { mutableStateOf(false) }
        val notifikasi = remember { mutableListOf<NotificationEntity>() }

        fun load(){
            RestPresenter().readNotification(object : RetrofitCallback {
                override fun onSuccess(response: String) {
                    val validation = Gson().fromJson(response, Validation::class.java)
                    if (validation.data) {
                        val data = Gson().fromJson(response, Notification::class.java)
                        notifikasi.clear()
                        notifikasi.addAll(data.response)
                        loading = false
                        check.value = true
                        setToast(validation.notifikasi)
                    } else {
                        loading = false
                        setToast(validation.notifikasi)
                    }
                    setLog("NotifikasiActivity: readNotification: onSuccess:" + response)
                }

                override fun onFailed(response: String) {
                    loading = false
                    setLog("NotifikasiActivity: readNotification: onFailed:" + response)
                }

                override fun onFailure(throwable: Throwable) {
                    loading = false
                    setLog("NotifikasiActivity: readNotification: onFailure:" + throwable.message)
                }

            })
        }

        if (loading){
            load()
        }

        if (check.value) {
            Column(
                modifier
                    .fillMaxSize()
                    .background(color = BACKGROUND)) {
                if (loading) {
                    CircularProgressIndicator(Modifier.align(alignment = Alignment.CenterHorizontally))
                }

                Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "", Modifier.absolutePadding(right = 14.dp, left = 14.dp, top = 10.dp))

                LazyColumn {
                    itemsIndexed(notifikasi) { index, route ->
                        run {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 10.dp),
                                RoundedCornerShape(12.dp),
                                backgroundColor = Color(0xFFEEE6E5)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 8.dp,
                                            bottom = 4.dp
                                        )
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "ID ${route.id}",
                                            Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Tanggal ${route.tanggal}",
                                            Modifier.weight(3f),
                                            textAlign = TextAlign.End
                                        )
                                    }

                                    Text(text = route.judul.uppercase(),
                                        Modifier
                                            .background(Color.White, RoundedCornerShape(4.dp))
                                            .padding(
                                                top = 4.dp,
                                                bottom = 4.dp,
                                                start = 8.dp,
                                                end = 8.dp
                                            ))
                                    Text(
                                        text = "Deskripsi : ${route.isi}",
                                        Modifier.fillMaxWidth()
                                    )

                                    var a_tujuan = "Pemilik Toko"
                                    if (route.tujuan.equals("2")) a_tujuan = "Staff Gudang"
                                    if (route.tujuan.equals("3")) a_tujuan = "Staff Kasir"

                                    Text(
                                        text = "Tujuan : ${a_tujuan}",
                                        Modifier.fillMaxWidth()
                                    )

                                    var a_pembuat = ""
                                    if (route.jenisCreated.equals("1")){
                                        if (route.created.equals("0")) a_pembuat = "Robot"
                                        else a_pembuat = "Pemilik Toko"
                                    }

                                    if (route.jenisCreated.equals("2")) a_pembuat = "Staff Gudang"
                                    if (route.jenisCreated.equals("3")) a_pembuat = "Staff Kasir"

                                    Text(
                                        text = "Pembuat : ${a_pembuat}",
                                        Modifier.fillMaxWidth()
                                    )

                                    var a_status = "Belum dikerjakan"
                                    if (route.status.equals("0")) a_status = " Sudah dikerjakan"

                                    Text(
                                        text = "Status : ${a_status}",
                                        Modifier.fillMaxWidth()
                                    )

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (route.created.equals(Preference.getId()) || Preference.getIdType().equals("1")) {
                                            Text(text = "HAPUS",
                                                Modifier
                                                    .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                                                    .clickable {
                                                        RestPresenter().deleteNotification(
                                                            route.id,
                                                            object : RetrofitCallback {
                                                                override fun onSuccess(response: String) {
                                                                    setToast("Berhasil Menghapus " + route.judul)
                                                                    load()
                                                                    setLog("NotifikasiActivity: deleteNotification: onSuccess:" + response)
                                                                }

                                                                override fun onFailed(response: String) {
                                                                    loading = false
                                                                    setLog("NotifikasiActivity: deleteNotification: onFailed:" + response)
                                                                }

                                                                override fun onFailure(throwable: Throwable) {
                                                                    loading = false
                                                                    setLog("NotifikasiActivity: deleteNotification: onFailure:" + throwable.message)
                                                                }

                                                            })
                                                    },
                                                color = Color(0xFFFF0000)
                                            )
                                        }

                                        if (route.tujuan.equals(Preference.getIdType())){
                                            Text(text = "APPROVE",
                                                Modifier
                                                    .padding(end = 12.dp, bottom = 12.dp, top = 12.dp)
                                                    .clickable {
                                                        RestPresenter().approveNotification(
                                                            route.id,
                                                            route.judul,
                                                            route.isi,
                                                            route.jenisCreated,
                                                            Preference.getId().toString(),
                                                            route.tujuan,
                                                            object : RetrofitCallback {
                                                                override fun onSuccess(response: String) {
                                                                    setToast("Berhasil Menyelesaikan " + route.judul)
                                                                    load()
                                                                    setLog("NotifikasiActivity: approveNotification: onSuccess:" + response)
                                                                }

                                                                override fun onFailed(response: String) {
                                                                    loading = false
                                                                    setLog("NotifikasiActivity: approveNotification: onFailed:" + response)
                                                                }

                                                                override fun onFailure(throwable: Throwable) {
                                                                    loading = false
                                                                    setLog("NotifikasiActivity: approveNotification: onFailure:" + throwable.message)
                                                                }

                                                            })
                                                    },
                                                color = Color(0xFF029F12)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "",
                Modifier
                    .width(110.dp)
                    .height(141.dp)
                    .padding(bottom = 80.dp, end = 50.dp)
                    .clickable {
                        openDalog = true
                    }, contentScale = ContentScale.FillBounds)
        }

        if (openDalog) {
            Dialog(onDismissRequest = {}) {
                Column(Modifier.padding(top = 100.dp, bottom = 100.dp)) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White, RoundedCornerShape(12.dp)), verticalArrangement = Arrangement.Center) {

                        Text(text = "Tambah Notifikasi",
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)

                        var judul = ""
                        var isi = ""
                        var tujuan = ""

                        OutlinedTextField(
                            label = { Text(text = "Judul") },
                            value = judul,
                            onValueChange = { value ->
                                judul = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Isi") },
                            value = isi,
                            onValueChange = { value ->
                                isi = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Tujuan : 1. Admin, 2. Gudang, 3. Kasir") },
                            value = tujuan,
                            onValueChange = { value ->
                                tujuan = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true
                        )

                        Row() {
                            Button(
                                onClick = {
                                    openDalog = false
                                    judul = ""
                                    isi = ""
                                    tujuan = ""
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "BATAL", color = Color.White)
                            }

                            Button(
                                onClick = {
                                    loading = true
                                        RestPresenter().createNotification(
                                            judul,
                                            isi,
                                            tujuan,
                                            Preference.getId().toString(),
                                            Preference.getIdType().toString(),
                                            object : RetrofitCallback {
                                                override fun onSuccess(response: String) {
                                                    load()
                                                    judul = ""
                                                    isi = ""
                                                    tujuan = ""
                                                    openDalog = false
                                                    setLog("NotifikasiActivity: createNotification: onSuccess:" + response)
                                                }

                                                override fun onFailed(response: String) {
                                                    loading = false
                                                    setLog("NotifikasiActivity: createNotification: onFailed:" + response)
                                                }

                                                override fun onFailure(throwable: Throwable) {
                                                    loading = false
                                                    setLog("NotifikasiActivity: createNotification: onFailure:" + throwable.message)
                                                }

                                            })
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "SIMPAN", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    /* Activity User */
    @Composable
    fun UserActivity(modifier: Modifier) {

        var openDalog by remember { mutableStateOf(false) }
        var update by remember { mutableStateOf(false) }
        var loading by remember { mutableStateOf(true) }
        val check = remember { mutableStateOf(false) }
        val user = remember { mutableListOf<UserEntity>() }

        var idUser by remember { mutableStateOf("") }
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var nama_lengkap by remember { mutableStateOf("") }

        /*
        * IdType :
        * 1. admin
        * 2. Gudang
        * 3. Kasir
        * */
        var jabatan by remember { mutableStateOf("") }

        fun load(){
            RestPresenter().readUser(object : RetrofitCallback {
                override fun onSuccess(response: String) {
                    val validation = Gson().fromJson(response, Validation::class.java)
                    if (validation.data) {
                        val data = Gson().fromJson(response, User::class.java)
                        user.addAll(data.response)
                        loading = false
                        check.value = true
                        setToast(validation.notifikasi)
                    } else {
                        loading = false
                        setToast(validation.notifikasi)
                    }
                    setLog("BarangActivity: readItem: onSuccess:" + response)
                }

                override fun onFailed(response: String) {
                    loading = false
                    setLog("BarangActivity: readItem: onFailed:" + response)
                }

                override fun onFailure(throwable: Throwable) {
                    loading = false
                    setLog("BarangActivity: readItem: onFailure:" + throwable.message)
                }

            })
        }

        if (loading){
            load()
        }

        if (check.value) {
            Column(
                modifier
                    .fillMaxSize()
                    .background(color = BACKGROUND)) {
                if (loading) {
                    CircularProgressIndicator(modifier.align(alignment = Alignment.CenterHorizontally))
                }

                Image(painter = painterResource(id = R.drawable.ic_search), contentDescription = "", Modifier.absolutePadding(right = 14.dp, left = 14.dp, top = 10.dp))

                LazyColumn {
                    itemsIndexed(user) { index, route ->
                        run {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 10.dp),
                                RoundedCornerShape(12.dp),
                                backgroundColor = Color(0xFFEEE6E5)
                            ) {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 8.dp,
                                            bottom = 4.dp
                                        )
                                ) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = "ID ${user.get(index).idUser}",
                                            Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "Tanggal ${user.get(index).tanggalBuat}",
                                            Modifier.weight(3f),
                                            textAlign = TextAlign.End
                                        )
                                    }

                                    var jenis = ""
                                    if (user.get(index).jenis.equals("1")) jenis = "Pimpinan"
                                    if (user.get(index).jenis.equals("2")) jenis = "Gudang"
                                    if (user.get(index).jenis.equals("3")) jenis = "Kasir"
                                    Text(text = jenis,
                                        Modifier
                                            .background(Color.White, RoundedCornerShape(4.dp))
                                            .padding(
                                                top = 4.dp,
                                                bottom = 4.dp,
                                                start = 8.dp,
                                                end = 8.dp
                                            ))
                                    Text(
                                        text = "Username : ${user.get(index).username}",
                                        Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "Password : ********",
                                        Modifier.fillMaxWidth()
                                    )

                                    Row(
                                        Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "HAPUS",
                                            Modifier
                                                .padding(end = 12.dp, top = 12.dp, bottom = 12.dp)
                                                .clickable {
                                                    RestPresenter().deleteUser(
                                                        route.idUser,
                                                        object : RetrofitCallback {
                                                            override fun onSuccess(response: String) {
                                                                setToast("Berhasil Menghapus " + route.namaUser)
                                                                load()
                                                                setLog("BarangActivity: readItem: onSuccess:" + response)
                                                            }

                                                            override fun onFailed(response: String) {
                                                                loading = false
                                                                setLog("BarangActivity: readItem: onFailed:" + response)
                                                            }

                                                            override fun onFailure(throwable: Throwable) {
                                                                loading = false
                                                                setLog("BarangActivity: readItem: onFailure:" + throwable.message)
                                                            }

                                                        })
                                                },
                                            color = Color(0xFFFF0000)
                                        )
                                        Text(text = "UBAH",
                                            Modifier
                                                .padding(end = 12.dp, bottom = 12.dp, top = 12.dp)
                                                .clickable {
                                                    username = route.username
                                                    password = route.password
                                                    nama_lengkap = route.namaUser
                                                    jabatan = route.jenis
                                                    openDalog = true
                                                },
                                            color = Color(0xFF029F12)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Column(modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.End) {
            Image(painter = painterResource(id = R.drawable.ic_add), contentDescription = "",
                Modifier
                    .width(110.dp)
                    .height(141.dp)
                    .padding(bottom = 80.dp, end = 50.dp)
                    .clickable {
                        openDalog = true
                    }, contentScale = ContentScale.FillBounds)
        }

        if (openDalog) {
            Dialog(onDismissRequest = {}) {
                Column(Modifier.padding(top = 100.dp, bottom = 100.dp)) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.White, RoundedCornerShape(12.dp)), verticalArrangement = Arrangement.Center) {

                        var title = ""
                        if (update) title = "Update User"
                        else title = "Tambah User"

                        Text(text = title,
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp, bottom = 10.dp), fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                        
                        OutlinedTextField(
                            label = { Text(text = "Username") },
                            value = username,
                            onValueChange = { value ->
                                username = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Password") },
                            value = password,
                            onValueChange = { value ->
                                password = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Nama Lengkap") },
                            value = nama_lengkap,
                            onValueChange = { value ->
                                nama_lengkap = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true
                        )

                        OutlinedTextField(
                            label = { Text(text = "Permission : 1. Admin, 2. Gudang, 3. Kasir") },
                            value = jabatan,
                            onValueChange = { value ->
                                jabatan = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true
                        )

                        Row() {
                            Button(
                                onClick = {
                                    openDalog = false
                                    update = false
                                    username = ""
                                    password = ""
                                    nama_lengkap = ""
                                    jabatan = ""
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "BATAL", color = Color.White)
                            }

                            Button(
                                onClick = {
                                loading = true
                                if (update) {
                                    RestPresenter().updateUser(
                                        idUser,
                                        username,
                                        password,
                                        nama_lengkap,
                                        jabatan,
                                        object : RetrofitCallback {
                                            override fun onSuccess(response: String) {
                                                load()
                                                idUser = ""
                                                username = ""
                                                password = ""
                                                nama_lengkap = ""
                                                jabatan = ""
                                                openDalog = false
                                                setLog("LoginActivity: login: onSuccess:" + response)
                                            }

                                            override fun onFailed(response: String) {
                                                loading = false
                                                setLog("LoginActivity: login: onFailed:" + response)
                                            }

                                            override fun onFailure(throwable: Throwable) {
                                                loading = false
                                                setLog("LoginActivity: login: onFailure:" + throwable.message)
                                            }

                                        })
                                } else {
                                    RestPresenter().createUser(
                                        username,
                                        password,
                                        nama_lengkap,
                                        jabatan,
                                        object : RetrofitCallback {
                                            override fun onSuccess(response: String) {
                                                load()
                                                username = ""
                                                password = ""
                                                nama_lengkap = ""
                                                jabatan = ""
                                                openDalog = false
                                                setLog("LoginActivity: login: onSuccess:" + response)
                                            }

                                            override fun onFailed(response: String) {
                                                loading = false
                                                setLog("LoginActivity: login: onFailed:" + response)
                                            }

                                            override fun onFailure(throwable: Throwable) {
                                                loading = false
                                                setLog("LoginActivity: login: onFailure:" + throwable.message)
                                            }

                                        })
                                }
                                },
                                Modifier
                                    .weight(1f)
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 0.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    ),
                                colors = ButtonDefaults.buttonColors(BUTTON)
                            ) {
                                Text(text = "SIMPAN", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }

    /* Activity Akun */
    @Composable
    fun AkunActivity(modifier: Modifier) {
        var username by remember { mutableStateOf(Preference.getUsername()) }
        var password by remember { mutableStateOf(Preference.getPassword()) }
        var type by remember { mutableStateOf("") }

        if (Preference.getIdType().equals("1")) type = "Admin"
        if (Preference.getIdType().equals("2")) type = "Gudang"
        if (Preference.getIdType().equals("3")) type = "Kasir"

        Column(
            modifier
                .fillMaxSize()
                .background(color = BACKGROUND), Arrangement.Center, Alignment.CenterHorizontally) {

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    Modifier
                        .width(110.dp)
                        .height(110.dp))

                Text(
                    text = type,
                    Modifier
                        .absolutePadding(
                            left = 16.dp,
                            right = 16.dp
                        ),
                    color = Color(R.color.primer),
                    fontSize = 35.sp
                )

                OutlinedTextField(
                    label = { Text(text = "Username") },
                    value = username.toString(),
                    onValueChange = {},
                    modifier = Modifier
                        .absolutePadding(top = 8.dp, left = 16.dp, right = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    enabled = false,
                    singleLine = true
                )

                OutlinedTextField(
                    label = { Text(text = "Password") },
                    value = password.toString(),
                    onValueChange = { value ->
                        password = value
                    },
                    modifier = Modifier
                        .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White),
                    enabled = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )

                Button(
                    onClick = {
                        Preference.removeAllData()
                        setActivity(LoginActivity::class.java)
                    },
                    Modifier
                        .fillMaxWidth()
                        .absolutePadding(top = 14.dp, bottom = 0.dp, left = 16.dp, right = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(text = "KELUAR")
                }
            }
        }
    }

    /* Activity Login --------------------------------------------------------------------------- */
    @Composable
    fun LoginActivity() {
        var username by remember { mutableStateOf("admin") }
        var password by remember { mutableStateOf("admin") }
        var loading by remember { mutableStateOf(false) }
        var alertMessage by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }

        Column(
            Modifier
                .fillMaxSize()
                .background(color = BACKGROUND), Arrangement.Center, Alignment.CenterHorizontally) {
            if (alertMessage) {
                AlertDialog(
                    onDismissRequest = { alertMessage = false },
                    title = { Text(text = "Pesan") },
                    text = { Text(text = message) },
                    confirmButton = {
                        TextButton(
                            onClick = { alertMessage = false },
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text("Ya")
                        }
                    }
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) CircularProgressIndicator()
            }

            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ATOKOKU",
                    Modifier
                        .absolutePadding(
                            left = 16.dp,
                            right = 16.dp
                        ),
                    color = Color(R.color.primer),
                    fontSize = 45.sp
                )

                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "",
                    Modifier
                        .width(200.dp)
                        .height(200.dp))
            }

            Text(
                text = "Selamat Datang diAtokoku",
                Modifier.absolutePadding(top = 8.dp, left = 16.dp, right = 16.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Silahkan Masuk ke Akun Anda",
                Modifier.absolutePadding(top = 8.dp, bottom = 8.dp, left = 16.dp, right = 16.dp),
                fontSize = 20.sp
            )

            OutlinedTextField(
                label = { Text(text = "Username") },
                value = username,
                onValueChange = { value ->
                    username = value
                },
                modifier = Modifier
                    .absolutePadding(top = 8.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true
            )

            OutlinedTextField(
                label = { Text(text = "Password") },
                value = password,
                onValueChange = { value ->
                    password = value
                },
                modifier = Modifier
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            Button(
                onClick = {
                    loading = true
                    RestPresenter().login(
                        username,
                        password,
                        object : RetrofitCallback {
                            override fun onSuccess(response: String) {
                                val validation =
                                    Gson().fromJson(response, Validation::class.java)
                                if (validation.data) {
                                    val login = Gson().fromJson(response, Login::class.java)

                                    Preference.setUsername(login.response.username)
                                    Preference.setPassword(login.response.password)
                                    Preference.setName(login.response.namaUser)
                                    Preference.setId(login.response.idUser)

                                    /*
                                 * IdType :
                                 * 1. admin
                                 * 2. Gudang
                                 * 3. Kasir
                                 * */
                                    Preference.setIdType(login.response.jenis)

                                    loading = false
                                    setToast(validation.notifikasi)
                                    setActivity(DashboardActivity::class.java)
                                } else {
                                    loading = false
                                    setToast(validation.notifikasi)
                                }
                                setLog("LoginActivity: login: onSuccess:" + response)
                            }

                            override fun onFailed(response: String) {
                                loading = false
                                alertMessage = true
                                message = "Silahkan Periksa Koneksi Internet Anda"
                                setLog("LoginActivity: login: onFailed:" + response)
                            }

                            override fun onFailure(throwable: Throwable) {
                                loading = false
                                alertMessage = true
                                message = "Silahkan Periksa Koneksi Internet Anda"
                                setLog("LoginActivity: login: onFailure:" + throwable.message)
                            }

                        })
                },
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = BUTTON)
            ) {
                Text(text = "MASUK", color = Color.White)
            }
        }
    }


    /* Other ------------------------------------------------------------------------------------ */
    @Composable
    fun AlertBack() {
        var showDialog = remember { mutableStateOf(false) }

        BackHandler {
            showDialog.value = true
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Pesan") },
                text = { Text(text = "Apakah anda mau keluar dari aplikasi?") },
                confirmButton = {
                    TextButton(
                        onClick = { finishAffinity() },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text("Ya, Keluar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showDialog.value = false },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text("Tidak")
                    }
                }
            )
        }
    }

    @Composable
    fun AlertMessage(message: String) {
        val showDialog = remember { mutableStateOf(false) }

        BackHandler {
            showDialog.value = true
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Pesan") },
                text = { Text(text = message) },
                confirmButton = {
                    TextButton(
                        onClick = { showDialog.value = false },
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        Text("Ya")
                    }
                }
            )
        }
    }
}