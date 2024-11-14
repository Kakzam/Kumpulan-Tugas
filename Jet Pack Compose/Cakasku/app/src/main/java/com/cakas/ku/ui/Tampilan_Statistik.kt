package com.cakas.ku.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakas.ku.R
import com.cakas.ku.ui.ui.theme.CakaskuTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal

class Tampilan_Statistik : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakaskuTheme {
                Scaffold(bottomBar = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Image(painterResource(R.drawable.icon_home_off), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, Tampilan_Beranda::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(painterResource(R.drawable.icon_catatan_off), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, Tampilan_Transaksi::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(painterResource(R.drawable.icon_statistik_active), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, Tampilan_Statistik::class.java)
                                    startActivity(intent)
                                    finish()
                                })
                    }
                }, content = {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(it.calculateBottomPadding()),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val pendapatan = remember { mutableStateOf(true) }
                        val pengeluaran = remember { mutableStateOf(false) }
                        val hutang = remember { mutableStateOf(false) }
                        val piutang = remember { mutableStateOf(false) }
                        val hitung = remember { mutableStateOf(false) }

                        val pendapatanH = remember { mutableStateOf(BigDecimal("0.0")) }
                        val pengeluaranH = remember { mutableStateOf(BigDecimal("0.0")) }
                        val hutangH = remember { mutableStateOf(BigDecimal("0.0")) }
                        val piutangH = remember { mutableStateOf(BigDecimal("0.0")) }

                        if (pendapatan.value) {
                            FirebaseFirestore.getInstance().collection("Cakasku")
                                .document(Storage.getToken().toString()).collection("Pendapatan").get()
                                .addOnSuccessListener {
                                    for (data in it.documents){
                                        pendapatanH.value+= data.data?.get("pendapatan").toString().toBigDecimal()
                                    }

                                    pendapatan.value = false
                                    pengeluaran.value = true
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Silahkan Periksa Koneksi Internet Anda",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        if (pengeluaran.value){
                            FirebaseFirestore.getInstance().collection("Cakasku")
                                .document(Storage.getToken().toString()).collection("Pengeluaran").get()
                                .addOnSuccessListener {
                                    for (data in it.documents){
                                        pengeluaranH.value+= data.data?.get("pengeluaran").toString().toBigDecimal()
                                    }

                                    pengeluaran.value = false
                                    hutang.value = true
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Silahkan Periksa Koneksi Internet Anda",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        if (hutang.value){
                            FirebaseFirestore.getInstance().collection("Cakasku")
                                .document(Storage.getToken().toString()).collection("Hutang").get()
                                .addOnSuccessListener {
                                    for (data in it.documents){
                                        if (data.data?.get("status").toString().equals("Belum Lunas")) hutangH.value+= data.data?.get("hutang").toString().toBigDecimal()
                                    }

                                    hutang.value = false
                                    piutang.value = true
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Silahkan Periksa Koneksi Internet Anda",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        if (piutang.value){
                            FirebaseFirestore.getInstance().collection("Cakasku")
                                .document(Storage.getToken().toString()).collection("Piutang").get()
                                .addOnSuccessListener {
                                    for (data in it.documents){
                                        if (data.data?.get("status").toString().equals("Belum Lunas")) piutangH.value+= data.data?.get("piutang").toString().toBigDecimal()
                                    }

                                    piutang.value = false
                                    hitung.value = true
                                }.addOnFailureListener {
                                    Toast.makeText(
                                        applicationContext,
                                        "Silahkan Periksa Koneksi Internet Anda",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }

                        if (hitung.value){
                            var itung = pendapatanH.value + pengeluaranH.value + hutangH.value + piutangH.value
                            itung/= BigDecimal("100")

                            pendapatanH.value /= itung
                            pengeluaranH.value /=  itung
                            hutangH.value /= itung
                            piutangH.value /= itung

                            hitung.value = false
                        }

                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Image(painterResource(R.drawable.rc_pendapatan), "",
                                Modifier
                                    .height(48.dp)
                                    .width(48.dp)
                                    .padding(4.dp))

                            Text(text = "Pendapatan : ${pendapatanH.value}%", color = Color.Black)
                        }

                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Image(painterResource(R.drawable.rc_pengeluaran), "",
                                Modifier
                                    .height(48.dp)
                                    .width(48.dp)
                                    .padding(4.dp))
                            Text(text = "Pengeluaran : ${pengeluaranH.value}%", color = Color.Black)
                        }

                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Image(painterResource(R.drawable.rc_hutang), "",
                                Modifier
                                    .height(48.dp)
                                    .width(48.dp)
                                    .padding(4.dp))
                            Text(text = "Hutang : ${hutangH.value}%", color = Color.Black)
                        }

                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Image(painterResource(R.drawable.rc_piutang), "",
                                Modifier
                                    .height(48.dp)
                                    .width(48.dp)
                                    .padding(4.dp))
                            Text(text = "Piutang : ${piutangH.value}%", color = Color.Black)
                        }
                    }
                })
            }
        }
    }
}