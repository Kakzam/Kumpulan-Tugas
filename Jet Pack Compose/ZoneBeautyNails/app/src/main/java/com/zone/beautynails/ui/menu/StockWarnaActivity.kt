package com.zone.beautynails.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.zone.beautynails.R
import com.zone.beautynails.ui.theme.*

class StockWarnaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
                val sharedPref = getSharedPreferences("zone_beauty_nails", Context.MODE_PRIVATE)

                Scaffold(
                    bottomBar = {
                        Column(
                            Modifier
                                .background(PRIMARY)
                                .padding(top = 4.dp, bottom = 4.dp)
                        ) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 14.dp,
                                        end = 14.dp,
                                        top = 4.dp,
                                    ), shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp, bottom = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painterResource(R.drawable.home_home),
                                        "",
                                        Modifier
                                            .width(120.dp)
                                            .height(40.dp)
                                            .padding(start = 8.dp, end = 22.dp)
                                            .clickable {
                                                intent = Intent(
                                                    applicationContext,
                                                    HomeActivity::class.java
                                                )
                                                startActivity(intent)
                                                finish()
                                            },
                                        contentScale = ContentScale.Fit
                                    )

                                    Image(
                                        painterResource(R.drawable.home_setting),
                                        "",
                                        Modifier
                                            .width(120.dp)
                                            .height(40.dp)
                                            .padding(start = 22.dp, end = 8.dp)
                                            .clickable {
                                                intent = Intent(applicationContext, SettingActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            },
                                        contentScale = ContentScale.Fit
                                    )
                                }
                            }
                        }
                    }
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                            .background(PRIMARY)
                    ) {

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, top = 30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_back), "",
                                Modifier
                                    .width(38.dp)
                                    .height(38.dp)
                                    .clickable {
                                        intent = Intent(
                                            applicationContext,
                                            HomeActivity::class.java
                                        )
                                        startActivity(intent)
                                        finish()
                                    }
                            )

                            Text(text = "Stock Warna", color = Color.Black, fontSize = 24.sp)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Image(
                                    painterResource(R.drawable.icon_search), "",
                                    Modifier
                                        .width(38.dp)
                                        .height(38.dp)
                                )
                            }
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black)) {}

                        var load by remember {
                            mutableStateOf(true)
                        }

                        var warna by remember {
                            mutableStateOf("")
                        }

                        var data = remember {
                            mutableListOf<DocumentSnapshot>()
                        }

                        if (load) {
                            FirebaseFirestore.getInstance().collection("warna").get()
                                .addOnSuccessListener { documentReference ->
                                    data.addAll(documentReference.documents)
                                    load = false
                                }.addOnFailureListener { exception ->
                                Toast.makeText(
                                    applicationContext,
                                    "Silahkan Periksa Koneksi Internet Anda",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        } else {
                            if (sharedPref.getString("type", "0").equals("1")){
                                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                    TextField(
                                        label = { Text(text = "Nama Warna") },
                                        value = warna,
                                        onValueChange = { value -> warna = value },
                                        modifier = Modifier
                                            .absolutePadding(
                                                top = 8.dp,
                                                bottom = 10.dp,
                                                left = 16.dp,
                                                right = 16.dp
                                            )
                                            .weight(3f)
                                            .background(Color.White),
                                        singleLine = true
                                    )

                                    Text(
                                        text = "Tambah",
                                        Modifier
                                            .weight(1f)
                                            .clickable {
                                                FirebaseFirestore.getInstance().collection("warna").add(hashMapOf("warna" to warna, "status" to "Tersedia")).addOnSuccessListener { documentReference ->
                                                    intent = Intent(applicationContext, HomeActivity::class.java)
                                                    startActivity(intent)
                                                    finish()
                                                }.addOnFailureListener { exception ->
                                                    Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                                }
                                            },
                                        textAlign = TextAlign.Center,
                                        color = Color.Green
                                    )
                                }
                            }

                            LazyColumn {
                                itemsIndexed(data) { index: Int, item: DocumentSnapshot ->
                                    run {
                                        Column(Modifier.fillMaxWidth()) {
                                            Row(Modifier.fillMaxWidth()) {
                                                Text(
                                                    text = item.get("warna").toString(),
                                                    Modifier.weight(1f),
                                                    fontWeight = FontWeight.Bold,
                                                    textAlign = TextAlign.Center
                                                )

                                                if (sharedPref.getString("type", "0").equals("1")) {
                                                    Text(
                                                        text = item.get("status").toString(),
                                                        Modifier
                                                            .weight(1f)
                                                            .padding(
                                                                start = 12.dp,
                                                                end = 12.dp,
                                                                top = 8.dp,
                                                                bottom = 8.dp
                                                            )
                                                            .background(
                                                                UNGU,
                                                                RoundedCornerShape(8.dp)
                                                            )
                                                            .clickable {
                                                                if (item.get("status").toString().equals("Tersedia")) {
                                                                    FirebaseFirestore.getInstance().collection("warna").document(item.id).set(hashMapOf("warna" to item.get("warna").toString(), "status" to "Pending")).addOnSuccessListener { documentReference ->
                                                                        Toast.makeText(applicationContext, "Berhasil Update", Toast.LENGTH_LONG).show()
                                                                    }.addOnFailureListener { exception ->
                                                                        Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                                                    }
                                                                } else {
                                                                    FirebaseFirestore.getInstance().collection("warna").document(item.id).set(hashMapOf("warna" to item.get("warna").toString(), "status" to "Terdedia")).addOnSuccessListener { documentReference ->
                                                                        Toast.makeText(applicationContext, "Berhasil Update", Toast.LENGTH_LONG).show()
                                                                    }.addOnFailureListener { exception ->
                                                                        Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                                                    }
                                                                }
                                                            }, textAlign = TextAlign.Center, color = Color.White)

                                                    Text(
                                                        text = "Hapus",
                                                        Modifier
                                                            .weight(1f)
                                                            .padding(
                                                                start = 12.dp,
                                                                end = 12.dp,
                                                                top = 8.dp,
                                                                bottom = 8.dp
                                                            )
                                                            .background(
                                                                UNGU,
                                                                RoundedCornerShape(8.dp)
                                                            )
                                                            .clickable {
                                                                FirebaseFirestore.getInstance().collection("warna").document(item.id).delete().addOnSuccessListener { documentReference ->
                                                                    Toast.makeText(applicationContext, "Berhasil Menghapus", Toast.LENGTH_LONG).show()
                                                                    data.removeAt(index)
                                                                }.addOnFailureListener { exception ->
                                                                    Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                                                }
                                                            }, textAlign = TextAlign.Center, color = Color.White)
                                                } else Text(
                                                    text = item.get("status").toString(),
                                                    Modifier.weight(1f),
                                                    textAlign = TextAlign.Center,
                                                    color = Color.White
                                                )
                                            }
                                            Column(
                                                Modifier
                                                    .fillMaxWidth()
                                                    .height(1.dp)
                                                    .background(PUTIH)
                                            ) {}
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}