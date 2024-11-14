package com.cash.crab.tampilan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cash.crab.R
import com.cash.crab.ui.theme.CashCrabTheme
import com.cash.crab.ui.theme.PRIMER
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashCrabTheme {

                var input by remember { mutableStateOf(false) }
                var data by remember { mutableStateOf(true) }
                var user by remember { mutableStateOf(false) }

                Scaffold(topBar = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(PRIMER)
                            .absolutePadding(left = 4.dp, right = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "",
                            Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .padding(start = 30.dp, top = 24.dp, bottom = 24.dp, end = 24.dp),
                            contentScale = ContentScale.FillBounds
                        )

                        Text(
                            text = "CashCarb",
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                }, bottomBar = {
                    BottomNavigation(Modifier.fillMaxWidth(), backgroundColor = PRIMER ) {
                        BottomNavigationItem(icon = {
                            if (input) {
                                Icon(
                                    painterResource(id = R.drawable.ic_input_white),
                                    contentDescription = null
                                )
                            } else {
                                Icon(
                                    painterResource(id = R.drawable.ic_input_black),
                                    contentDescription = null
                                )
                            }
                        },
                            label = { Text("") },
                            selected = input,
                            onClick = {
                                input = true
                                data = false
                                user = false
                            })

                        BottomNavigationItem(icon = {
                            if (input) {
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
                            label = { Text("") },
                            selected = input,
                            onClick = {
                                input = false
                                data = true
                                user = false
                            })

                        BottomNavigationItem(icon = {
                            if (input) {
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
                            label = { Text("") },
                            selected = input,
                            onClick = {
                                input = false
                                data = false
                                user = true
                            })
                    }
                }) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                            .background(Color.Gray)
                    ) {

                        if (input) inputData()
                        if (data) lihatData()
                        if (user) profile()
                    }
                }
            }
        }
    }

    @Composable
    private fun profile() {
        Column(
            Modifier
                .fillMaxSize()
                .absolutePadding(right = 24.dp, left = 24.dp, bottom = 24.dp, top = 24.dp)
                .padding(top = 14.dp, bottom = 14.dp, start = 14.dp, end = 14.dp)
                .background(Color.White, RoundedCornerShape(12.dp))) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(top = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(
                    Modifier
                        .weight(1f)
                        .background(Color.White, shape = CircleShape), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "", Modifier.clip(CircleShape).padding(10.dp))
                }

                Text(text = intent.getStringExtra("username").toString(), Modifier.weight(3f), textAlign = TextAlign.Center)
            }

            Column(Modifier.fillMaxWidth().absolutePadding(top = 14.dp, bottom = 14.dp, right = 14.dp, left = 14.dp).height(1.dp).background(Color.Black)) {}

            Row(
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(top = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(
                    Modifier
                        .weight(1f)
                    , verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.ic_loop), contentDescription = "")
                }

                Text(text = "Singkronasi Data", Modifier.weight(3f))
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(top = 14.dp)
                    .clickable {
                        FirebaseFirestore.getInstance().collection("pengguna").document(intent.getStringExtra("id").toString()).delete()
                            .addOnSuccessListener {
                                Toast.makeText(
                                    applicationContext,
                                    "User ini berhasil di hapus",
                                    Toast.LENGTH_LONG
                                ).show()

                                intent =
                                    Intent(
                                        applicationContext,
                                        LoginActivity::class.java
                                    )
                                startActivity(intent)
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    applicationContext,
                                    "Silahkan Periksa Kembali Koneksi Internet Anda",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }, verticalAlignment = Alignment.CenterVertically) {
                Column(
                    Modifier
                        .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.ic_delete), contentDescription = "")
                }

                Text(text = "Hapus Data", Modifier.weight(3f))
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                Row(Modifier.absolutePadding(top = 14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        Modifier
                            .weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.ic_profile_icon_bg), contentDescription = "")
                    }
                }
            }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End){
                Row(Modifier.absolutePadding(top = 14.dp, right = 14.dp).clickable {
                    intent =
                        Intent(
                            applicationContext,
                            LoginActivity::class.java
                        )
                    startActivity(intent)
                    finish()
                }, verticalAlignment = Alignment.CenterVertically) {
                    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "")
                    }

                    Text(text = "Log Out")
                }
            }
        }
    }

    @Composable
    private fun lihatData() {
        var loading by remember { mutableStateOf(true) }
        var data = remember { mutableListOf<DocumentSnapshot>() }

        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) CircularProgressIndicator()
            }

            if (loading){
                FirebaseFirestore.getInstance().collection("barang").get()
                    .addOnSuccessListener {
                        loading = false
                        data.addAll(it.documents)
                    }.addOnFailureListener {
                        loading = false
                        Toast.makeText(
                            applicationContext,
                            "Silahkan Periksa Kembali Koneksi Internet Anda",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            } else {

                Text(
                    text = "Tabel Barang",
                    Modifier
                        .fillMaxWidth()
                        .absolutePadding(top = 4.dp, bottom = 8.dp),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .absolutePadding(right = 8.dp, left = 8.dp)
                        .background(Color.White), verticalAlignment = Alignment.CenterVertically) {

                    Text(text = "No", Modifier.weight(1f), textAlign = TextAlign.Center)

                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(Color.Black)
                            .absolutePadding(top = 8.dp, bottom = 8.dp)) {}

                    Text(text = "Nama", Modifier.weight(3f), textAlign = TextAlign.Center)

                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(Color.Black)
                            .absolutePadding(top = 8.dp, bottom = 8.dp)) {}

                    Text(text = "Jumlah", Modifier.weight(2f), textAlign = TextAlign.Center)

                    Column(
                        Modifier
                            .absolutePadding(top = 8.dp, bottom = 8.dp)
                            .fillMaxHeight()
                            .width(1.dp)
                            .background(Color.Black)) {}

                    Text(text = "Harga", Modifier.weight(2f), textAlign = TextAlign.Center)

                }

                LazyColumn{
                    itemsIndexed(data){
                        index: Int, item: DocumentSnapshot ->
                        run {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .absolutePadding(top = 4.dp, right = 8.dp, left = 8.dp)
                                    .background(Color.White), verticalAlignment = Alignment.CenterVertically) {

                                Text(text = (index+1).toString(), Modifier.weight(1f), textAlign = TextAlign.Center)

                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(Color.Black)
                                        .absolutePadding(top = 4.dp, bottom = 4.dp)) {}

                                Text(text = item.get("nama").toString(),
                                    Modifier
                                        .weight(3f)
                                        .padding(start = 4.dp, end = 4.dp))

                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(Color.Black)
                                        .absolutePadding(top = 4.dp, bottom = 4.dp)) {}

                                Text(text = item.get("qty").toString(), Modifier.weight(2f), textAlign = TextAlign.Center)

                                Column(
                                    Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                        .background(Color.Black)
                                        .absolutePadding(top = 4.dp, bottom = 4.dp)) {}

                                Text(text = item.get("harga").toString(), Modifier.weight(2f), textAlign = TextAlign.Center)

                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun inputData() {

        var loading by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf(false) }

        var nama by remember { mutableStateOf("") }
        var qty by remember { mutableStateOf("") }
        var harga by remember { mutableStateOf("") }
        var total by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (loading) CircularProgressIndicator()
            }

            if (message) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                        .absolutePadding(left = 30.dp, right = 30.dp, top = 8.dp, bottom = 8.dp)
                        .background(Color.White, RoundedCornerShape(4.dp)),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Save data successful !")
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "",
                        Modifier
                            .absolutePadding(left = 24.dp)
                            .clickable {
                                message = false
                            })
                }
            }

            Text(
                text = "Tabel Input Barang",
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(top = 4.dp, bottom = 8.dp),
                textAlign = TextAlign.Center,
                color = Color.White
            )

            TextField(
                label = { Text(text = "Nama Barang") },
                value = nama,
                onValueChange = { value ->
                    nama = value
                },
                modifier = Modifier
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true
            )

            TextField(
                label = { Text(text = "Jumlah Beli") },
                value = qty,
                onValueChange = { value ->
                    qty = value
                    if (!qty.isEmpty() && !harga.isEmpty()) total = harga.toInt() * qty.toInt()
                },
                modifier = Modifier
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            TextField(
                label = { Text(text = "Harga Barang") },
                value = harga,
                onValueChange = { value ->
                    harga = value
                    if (!qty.isEmpty() && !harga.isEmpty()) total = harga.toInt() * qty.toInt()
                },
                modifier = Modifier
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Column(
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(left = 16.dp), horizontalAlignment = Alignment.End) {
                Button(
                    onClick = {
                        loading = true
                        if (nama.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Nama Barang Tidak Boleh Kosong",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (qty.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Qty Tidak Boleh Kosong",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (harga.isEmpty()) {
                            Toast.makeText(
                                applicationContext,
                                "Harga Tidak Boleh Kosong",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            var data = HashMap<String, String>()
                            data.put("nama", nama)
                            data.put("qty", qty)
                            data.put("harga", harga)

                            FirebaseFirestore.getInstance().collection("barang").add(data)
                                .addOnSuccessListener {
                                    loading = false
                                    message = true
                                }.addOnFailureListener {
                                    loading = false
                                    Toast.makeText(
                                        applicationContext,
                                        "Silahkan Periksa Kembali Koneksi Internet Anda",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    },
                    Modifier
                        .fillMaxWidth(0.4f)
                        .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(text = "Save")
                }
            }

            TextField(
                label = { Text(text = "Total Bayar") },
                value = total.toString(),
                onValueChange = {},
                modifier = Modifier
                    .absolutePadding(top = 8.dp, bottom = 0.dp, left = 16.dp, right = 16.dp)
                    .fillMaxWidth()
                    .background(Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )
        }
    }
}