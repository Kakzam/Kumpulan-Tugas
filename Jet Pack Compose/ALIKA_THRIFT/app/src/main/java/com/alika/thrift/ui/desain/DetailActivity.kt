package com.alika.thrift.ui.desain

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alika.thrift.R
import com.alika.thrift.ui.desain.ui.theme.ALIKATHRIFTTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALIKATHRIFTTheme {
                var detail by remember { mutableStateOf(false) }
                var deskripsi by remember { mutableStateOf("") }
                var harga by remember { mutableStateOf("") }
                var gambar by remember { mutableStateOf("") }
                var nama by remember { mutableStateOf("") }
                var alamat by remember { mutableStateOf("") }
                var no_telepon by remember { mutableStateOf("") }
                var pilihan by remember { mutableStateOf("") }

                Scaffold(
                    bottomBar = {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(Modifier.weight(1f)) {
                                Image(
                                    painter = painterResource(id = R.drawable.bar_chat),
                                    contentDescription = "",
                                    Modifier.size(38.dp).clickable {
                                        try {
                                            val ph = "6285384809938"

                                            startActivity(
                                                Intent(
                                                    Intent.ACTION_VIEW,
                                                    Uri.parse("https://api.whatsapp.com/send?phone=${ph}&text=")
                                                )
                                            )
                                        } catch (e: Exception) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Whatsapp belum terinstall",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                    }
                                )
                            }

                            Row(
                                Modifier
                                    .weight(1f)
                                    .clickable {
                                        intent =
                                            Intent(applicationContext, ShopActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, horizontalArrangement = Arrangement.Center) {
                                Image(
                                    painter = painterResource(id = R.drawable.bar_home),
                                    contentDescription = "",
                                    Modifier.size(38.dp)
                                )
                            }

                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                                Card(
                                    Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            try {
                                                val ph = "6285384809938"
                                                val ms =
                                                    "*Beli baju dengan id (${intent.getStringExtra("id")}(${
                                                        intent.getStringExtra("menu")
                                                    })*" + "\n\nNama : ${nama}"  + "\nAlamat : ${alamat}"  + "\nNo Telepon : ${no_telepon}" + "\nPembayaran : ${pilihan}"

                                                startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse("https://api.whatsapp.com/send?phone=${ph}&text=${ms}")
                                                    )
                                                )
                                            } catch (e: Exception) {
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Whatsapp belum terinstall",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                        }, shape = RoundedCornerShape(4.dp), backgroundColor = Color.Gray) {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                top = 4.dp,
                                                bottom = 4.dp,
                                                start = 12.dp,
                                                end = 12.dp
                                            ), horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "CHECKOUT", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                ) {
                    if (detail){
                        Column(
                            Modifier
                                .padding(bottom = it.calculateBottomPadding())
                                .verticalScroll(rememberScrollState())
                        ) {

                            Column(Modifier.fillMaxWidth()) {
                                Row(Modifier.fillMaxWidth()) {
                                    Column(
                                        Modifier.weight(2f)) {
                                        Image(
                                            bitmap = getGambar(gambar),
                                            contentDescription = "",
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(14.dp)
                                        )
                                        Text(text = harga, Modifier.padding(start = 14.dp))
                                        Text(text = "Deskripsi: ${deskripsi}", Modifier.padding(start = 14.dp))
                                    }

                                    Column(Modifier.weight(1f)) {}
                                }
                            }

                            TextField(
                                label = { Text(text = "Nama Lengkap") },
                                value = nama,
                                onValueChange = { value -> nama = value },
                                modifier = Modifier
                                    .absolutePadding(
                                        top = 24.dp,
                                        bottom = 10.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .background(Color.White),
                                singleLine = true
                            )

                            TextField(
                                label = { Text(text = "Alamat") },
                                value = alamat,
                                onValueChange = { value -> alamat = value },
                                modifier = Modifier
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 10.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .background(Color.White),
                                singleLine = true
                            )

                            TextField(
                                label = { Text(text = "No. Telepoin") },
                                value = no_telepon,
                                onValueChange = { value -> no_telepon = value },
                                modifier = Modifier
                                    .absolutePadding(
                                        top = 8.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .background(Color.White),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                singleLine = true,
                                maxLines = 15
                            )

                            TextField(
                                label = { Text(text = "Pilihan Transaksi") },
                                value = pilihan,
                                onValueChange = { value -> pilihan = value },
                                modifier = Modifier
                                    .absolutePadding(
                                        top = 8.dp,
                                        bottom = 10.dp,
                                        left = 16.dp,
                                        right = 16.dp
                                    )
                                    .fillMaxWidth()
                                    .background(Color.White),
                                singleLine = true
                            )
                        }
                    } else {
                        FirebaseFirestore
                            .getInstance()
                            .collection("barang")
                            .document("baju")
                            .collection(intent.getStringExtra("menu").toString())
                            .document(intent.getStringExtra("id").toString())
                            .get()
                            .addOnSuccessListener {
                                gambar = it.get("gambar").toString()
                                deskripsi = it.get("deskripsi").toString()
                                harga = "Rp ${getCurrency(it.get("harga").toString())}"
                                detail = true
                            }
                            .addOnFailureListener {
                                Toast
                                    .makeText(
                                        applicationContext,
                                        "Koneksi internetmu sepertinya mati",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                    }
                }

                BackHandler {
                    intent = Intent(applicationContext, ShopActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    fun getGambar(gambar: String): ImageBitmap {
        val base = Base64.decode(gambar, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            base,
            0,
            base.size
        ).asImageBitmap()
    }

    fun getCurrency(nominal: String): String {
        val format = NumberFormat.getInstance()
        format.maximumFractionDigits = 0
        return format.format(nominal.toInt()).replace(",", ".")
    }
}