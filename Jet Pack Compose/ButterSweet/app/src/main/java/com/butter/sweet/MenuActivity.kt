package com.butter.sweet

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.butter.sweet.ui.theme.ButterSweetTheme
import com.butter.sweet.ui.theme.grey
import com.butter.sweet.ui.theme.pink
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.HashMap

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterSweetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    Scaffold(topBar = {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_utama),
                                contentDescription = "",
                                Modifier
                                    .weight(0.25f)
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                UtamaActivity::class.java
                                            )
                                                .putExtra(
                                                    "image",
                                                    intent
                                                        .getStringExtra("image")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "phone",
                                                    intent
                                                        .getStringExtra("phone")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "email",
                                                    intent
                                                        .getStringExtra("email")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "password",
                                                    intent
                                                        .getStringExtra("password")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "nama",
                                                    intent
                                                        .getStringExtra("nama")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "tanggal_lahir",
                                                    intent
                                                        .getStringExtra("tanggal_lahir")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "jenis_kelamin",
                                                    intent
                                                        .getStringExtra("jenis_kelamin")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "alamat",
                                                    intent
                                                        .getStringExtra("alamat")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "id",
                                                    intent
                                                        .getStringExtra("id")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "utama",
                                                    true
                                                )
                                                .putExtra(
                                                    "keranjang",
                                                    false
                                                )
                                        )
                                    }
                            )

                            Card(
                                Modifier
                                    .weight(0.5f)
                                    .padding(8.dp),
                                shape = RoundedCornerShape(20.dp),
                                backgroundColor = pink
                            ) {
                                Column(
                                    Modifier
                                        .weight(0.5f)
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    var title = ""
                                    if (intent.getStringExtra("menu").equals("terlaris")) title =
                                        "TERLARIS"
                                    if (intent.getStringExtra("menu").equals("terbaru")) title =
                                        "TERBARU"
                                    if (intent.getStringExtra("menu").equals("spesial")) title =
                                        "SPECIAL"
                                    if (intent.getStringExtra("menu").equals("promo")) title =
                                        "PROMO"
                                    if (intent.getStringExtra("menu")
                                            .equals("koreanxtaiwan")
                                    ) title = "KOREAN X TAIWAN"
                                    Text(text = title)
                                }
                            }

                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_keranjang),
                                contentDescription = "",
                                Modifier
                                    .weight(0.25f)
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        startActivity(
                                            Intent(
                                                applicationContext,
                                                UtamaActivity::class.java
                                            )
                                                .putExtra(
                                                    "image",
                                                    intent
                                                        .getStringExtra("image")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "phone",
                                                    intent
                                                        .getStringExtra("phone")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "email",
                                                    intent
                                                        .getStringExtra("email")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "password",
                                                    intent
                                                        .getStringExtra("password")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "nama",
                                                    intent
                                                        .getStringExtra("nama")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "tanggal_lahir",
                                                    intent
                                                        .getStringExtra("tanggal_lahir")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "jenis_kelamin",
                                                    intent
                                                        .getStringExtra("jenis_kelamin")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "alamat",
                                                    intent
                                                        .getStringExtra("alamat")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "id",
                                                    intent
                                                        .getStringExtra("id")
                                                        .toString()
                                                )
                                                .putExtra(
                                                    "utama",
                                                    false
                                                )
                                                .putExtra(
                                                    "keranjang",
                                                    true
                                                )
                                        )
                                    }
                            )
                        }
                    }, backgroundColor = grey) {
                        var data = remember { mutableListOf<DocumentSnapshot>() }
                        var load by remember { mutableStateOf(true) }

                        if (load) {
                            FirebaseFirestore
                                .getInstance()
                                .collection("ButterSweet")
                                .document("-")
                                .collection(intent.getStringExtra("menu").toString())
                                .get()
                                .addOnSuccessListener {
                                    load = false
                                    data.addAll(it.documents)
                                }
                                .addOnFailureListener {
                                    Toast
                                        .makeText(
                                            applicationContext,
                                            "Silahkan Periksa Kembali Koneksi Internet Anda",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                        } else {
                            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                                itemsIndexed(data) { index, item ->
                                    run {
                                        Card(
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(top = 8.dp, end = 12.dp, start = 12.dp),
                                            shape = RoundedCornerShape(20.dp)
                                        ) {
                                            Row(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 12.dp,
                                                        end = 12.dp,
                                                        top = 8.dp,
                                                        bottom = 8.dp
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                val d = android.util.Base64.decode(
                                                    item.get("image").toString(),
                                                    android.util.Base64.DEFAULT
                                                )

                                                Image(
                                                    bitmap = BitmapFactory.decodeByteArray(
                                                        d,
                                                        0,
                                                        d.size
                                                    ).asImageBitmap(),
                                                    contentDescription = "",
                                                    Modifier
                                                        .width(90.dp)
                                                        .height(90.dp)
                                                        .padding(4.dp)
                                                        .clip(RoundedCornerShape(12.dp))
                                                )

                                                Column(
                                                    Modifier
                                                        .fillMaxWidth()
                                                        .padding(4.dp),
                                                    horizontalAlignment = Alignment.End
                                                ) {
                                                    Text(
                                                        text = item.get("nama").toString(),
                                                        Modifier.fillMaxWidth(),
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    Text(
                                                        text = item.get("deskripsi").toString(),
                                                        Modifier.fillMaxWidth().padding(top = 4.dp)
                                                    )

                                                    val format = NumberFormat.getInstance()
                                                    format.maximumFractionDigits = 0
                                                    Text(
                                                        text = "Rp. ${format.format(item.get("harga").toString().toInt()).replace(",", ".")}",
                                                        Modifier.fillMaxWidth().padding(top = 4.dp)
                                                    )

                                                    Card(
                                                        Modifier
                                                            .padding(
                                                                top = 4.dp,
                                                                bottom = 4.dp,
                                                                start = 10.dp,
                                                                end = 10.dp
                                                            )
                                                            .clickable {

                                                                Toast
                                                                    .makeText(
                                                                        applicationContext,
                                                                        "Proses Sedang Berjalan",
                                                                        Toast.LENGTH_SHORT
                                                                    )
                                                                    .show()

                                                                var data = HashMap<String, String>()
                                                                data.put(
                                                                    "gambar",
                                                                    item
                                                                        .get("image")
                                                                        .toString()
                                                                )
                                                                data.put(
                                                                    "nama",
                                                                    item
                                                                        .get("nama")
                                                                        .toString()
                                                                )
                                                                data.put(
                                                                    "deskripsi",
                                                                    item
                                                                        .get("deskripsi")
                                                                        .toString()
                                                                )
                                                                data.put(
                                                                    "harga",
                                                                    item
                                                                        .get("harga")
                                                                        .toString()
                                                                )

                                                                FirebaseFirestore
                                                                    .getInstance()
                                                                    .collection("ButterSweet")
                                                                    .document("-")
                                                                    .collection("user")
                                                                    .document(
                                                                        intent
                                                                            .getStringExtra("id")
                                                                            .toString()
                                                                    )
                                                                    .collection("keranjang")
                                                                    .document(item.id)
                                                                    .set(data)
                                                                    .addOnSuccessListener {
                                                                        Toast
                                                                            .makeText(
                                                                                applicationContext,
                                                                                "Makanan Sudah Masuk Keranjang",
                                                                                Toast.LENGTH_LONG
                                                                            )
                                                                            .show()
                                                                    }
                                                                    .addOnFailureListener {
                                                                        Toast
                                                                            .makeText(
                                                                                applicationContext,
                                                                                "Silahkan Periksa Kembali Koneksi Internet Anda",
                                                                                Toast.LENGTH_LONG
                                                                            )
                                                                            .show()
                                                                    }
                                                            },
                                                        shape = RoundedCornerShape(8.dp),
                                                        backgroundColor = pink
                                                    ) {
                                                        Text(
                                                            text = "PESAN",
                                                            Modifier.padding(
                                                                top = 8.dp,
                                                                bottom = 8.dp,
                                                                end = 12.dp,
                                                                start = 12.dp
                                                            )
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

                    BackHandler {

                    }
                }
            }
        }
    }
}