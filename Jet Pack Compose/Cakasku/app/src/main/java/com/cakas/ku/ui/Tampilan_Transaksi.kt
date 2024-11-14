package com.cakas.ku.ui

import android.content.Intent
import android.graphics.Color.rgb
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cakas.ku.R
import com.cakas.ku.ui.ui.theme.CakaskuTheme
import com.cakas.ku.ui.theme.WHITE
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat

class Tampilan_Transaksi : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakaskuTheme {
                Scaffold(bottomBar = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(WHITE)
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

                        Image(painterResource(R.drawable.icon_catatan_active), "",
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

                        Image(painterResource(R.drawable.icon_statistik_off), "",
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

                    val pendapatan = remember { mutableStateOf(true) }
                    val pengeluaran = remember { mutableStateOf(false) }
                    val hutang = remember { mutableStateOf(false) }
                    val piutang = remember { mutableStateOf(false) }

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                    ) {
                        Image(
                            painterResource(R.drawable.title_transaksi),
                            "",
                            Modifier.fillMaxWidth()
                        )

                        /* Scrolbar Row */
                        LazyRow {
                            item {
                                Image(
                                    painterResource(R.drawable.icon_t_pendapatan), "",
                                    Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .clickable {
                                            pendapatan.value = true
                                            pengeluaran.value = false
                                            hutang.value = false
                                            piutang.value = false
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Image(
                                    painterResource(R.drawable.icon_t_pengeluaran), "",
                                    Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .clickable {
                                            pendapatan.value = false
                                            pengeluaran.value = true
                                            hutang.value = false
                                            piutang.value = false
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Image(
                                    painterResource(R.drawable.icon_t_hutang), "",
                                    Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .clickable {
                                            pendapatan.value = false
                                            pengeluaran.value = false
                                            hutang.value = true
                                            piutang.value = false
                                        },
                                    contentScale = ContentScale.Crop
                                )
                                Image(
                                    painterResource(R.drawable.icon_t_piutang), "",
                                    Modifier
                                        .width(100.dp)
                                        .height(100.dp)
                                        .clickable {
                                            pendapatan.value = false
                                            pengeluaran.value = false
                                            hutang.value = false
                                            piutang.value = true
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        if (pendapatan.value) {
                            Pendapatan()
                        }

                        if (pengeluaran.value) {
                            Pengeluaran()
                        }

                        if (hutang.value) {
                            Hutang()
                        }

                        if (piutang.value) {
                            Piutang()
                        }

                    }
                })
            }
        }
    }

    @Composable
    fun Pendapatan() {
        val item = remember { mutableListOf<DocumentSnapshot>() }
        val check = remember { mutableStateOf(false) }

        FirebaseFirestore.getInstance().collection("Cakasku")
            .document(Storage.getToken().toString()).collection("Pendapatan").get()
            .addOnSuccessListener {
                item.addAll(it.documents)
                check.value = true
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Periksa Koneksi Internet Anda",
                    Toast.LENGTH_LONG
                ).show()
            }

        if (check.value){
            Column(Modifier.fillMaxSize()) {
                LazyColumn {
                    itemsIndexed(item){
                            index, route -> run {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                                .clickable {
                                    intent = Intent(applicationContext, Tampilan_Detail::class.java)
                                    intent.putExtra("tampilan","1")
                                    intent.putExtra("jenis","1")
                                    intent.putExtra("catatan", item.get(index).data?.get("catatan").toString())
                                    intent.putExtra("tanggal", item.get(index).data?.get("tanggal").toString())
                                    intent.putExtra("uang", item.get(index).data?.get("pendapatan").toString())
                                    intent.putExtra("id", item.get(index).id)
                                    startActivity(intent)
                                    finish()
                                },
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color(0xFFDFD8D7)
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Card(Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                    backgroundColor = Color(0xFFBDB7B6)
                                ) {
                                    Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                        Text(text = item.get(index).data?.get("tanggal").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                        Text(text = "Income", Modifier.weight(1f), textAlign = TextAlign.End)
                                    }
                                }
                                Text(text = item.get(index).data?.get("catatan").toString(), Modifier.padding(top = 6.dp, bottom = 4.dp, start = 16.dp, end = 16.dp))
                                val format = NumberFormat.getInstance()
                                format.maximumFractionDigits = 0
                                Text(text = "Rp ${format.format(item.get(index).data?.get("pendapatan").toString().toInt()).replace(",",".")}", Modifier.padding(top = 6.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
                            }
                        }
                    }}
                }
            }
        }
    }

    @Composable
    fun Pengeluaran() {
        val item = remember { mutableListOf<DocumentSnapshot>() }
        val check = remember { mutableStateOf(false) }

        FirebaseFirestore.getInstance().collection("Cakasku")
            .document(Storage.getToken().toString()).collection("Pengeluaran").get()
            .addOnSuccessListener {
                item.addAll(it.documents)
                check.value = true
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Periksa Koneksi Internet Anda",
                    Toast.LENGTH_LONG
                ).show()
            }

        if (check.value){
            Column(Modifier.fillMaxSize()) {
                LazyColumn {
                    itemsIndexed(item){
                            index, route -> run {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                                .clickable {
                                    intent = Intent(applicationContext, Tampilan_Detail::class.java)
                                    intent.putExtra("tampilan","1")
                                    intent.putExtra("jenis","2")
                                    intent.putExtra("catatan", item.get(index).data?.get("catatan").toString())
                                    intent.putExtra("tanggal", item.get(index).data?.get("tanggal").toString())
                                    intent.putExtra("uang", item.get(index).data?.get("pengeluaran").toString())
                                    intent.putExtra("id", item.get(index).id)
                                    startActivity(intent)
                                    finish()
                                },
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color(0xFFDFD8D7)
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Card(Modifier
                                    .fillMaxWidth(),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                    backgroundColor = Color(0xFFBDB7B6)
                                ) {
                                    Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                        Text(text = item.get(index).data?.get("tanggal").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                        Text(text = "Spending", Modifier.weight(1f), textAlign = TextAlign.End)
                                    }
                                }
                                Text(text = item.get(index).data?.get("catatan").toString(), Modifier.padding(top = 6.dp, bottom = 4.dp, start = 16.dp, end = 16.dp))
                                val format = NumberFormat.getInstance()
                                format.maximumFractionDigits = 0
                                Text(text = "Rp ${format.format(item.get(index).data?.get("pengeluaran").toString().toInt()).replace(",",".")}", Modifier.padding(top = 6.dp, bottom = 8.dp, start = 16.dp, end = 16.dp))
                            }
                        }
                    }}
                }
            }
        }
    }

    @Composable
    fun Hutang() {
        val item = remember { mutableListOf<DocumentSnapshot>() }
        val check = remember { mutableStateOf(false) }

        FirebaseFirestore.getInstance().collection("Cakasku")
            .document(Storage.getToken().toString()).collection("Hutang").get()
            .addOnSuccessListener {
                item.addAll(it.documents)
                check.value = true
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Periksa Koneksi Internet Anda",
                    Toast.LENGTH_LONG
                ).show()
            }

        if (check.value){
            Column(Modifier.fillMaxSize()) {
                LazyColumn {
                    itemsIndexed(item){
                            index, route -> run {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                                .clickable {
                                    intent = Intent(applicationContext, Tampilan_Detail::class.java)
                                    intent.putExtra("tampilan","2")
                                    intent.putExtra("jenis","3")
                                    intent.putExtra("uang", item.get(index).data?.get("hutang").toString())
                                    intent.putExtra("nama", item.get(index).data?.get("nama").toString())
                                    intent.putExtra("catatan", item.get(index).data?.get("catatan").toString())
                                    intent.putExtra("tanggal_pinjam", item.get(index).data?.get("tanggal_pinjam").toString())
                                    intent.putExtra("tanggal_batas", item.get(index).data?.get("tanggal_batas").toString())
                                    intent.putExtra("status", item.get(index).data?.get("status").toString())
                                    intent.putExtra("id", item.get(index).id)
                                    startActivity(intent)
                                    finish()
                                },
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color(0xFFDFD8D7)
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Card(Modifier
                                    .fillMaxWidth(),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                    backgroundColor = Color(0xFFBDB7B6)
                                ) {
                                    Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                        Text(text = item.get(index).data?.get("tanggal_pinjam").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                        Text(text = "Debt", Modifier.weight(1f), textAlign = TextAlign.End)
                                    }
                                }

                                Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                    Text(text = item.get(index).data?.get("catatan").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                    val format = NumberFormat.getInstance()
                                    format.maximumFractionDigits = 0
                                    Text(text = "Rp ${format.format(item.get(index).data?.get("hutang").toString().toInt()).replace(",",".")}", Modifier.weight(1f), textAlign = TextAlign.Center)
                                    Text(text = item.get(index).data?.get("status").toString(), Modifier.weight(1f), textAlign = TextAlign.End)
                                }
                            }
                        }
                    }}
                }
            }
        }
    }

    @Composable
    fun Piutang() {
        val check = remember { mutableStateOf(false) }
        val item = remember { mutableListOf<DocumentSnapshot>() }

        FirebaseFirestore.getInstance().collection("Cakasku")
            .document(Storage.getToken().toString()).collection("Piutang").get()
            .addOnSuccessListener {
                item.addAll(it.documents)
                check.value = true
            }.addOnFailureListener {
                Toast.makeText(
                    applicationContext,
                    "Silahkan Periksa Koneksi Internet Anda",
                    Toast.LENGTH_LONG
                ).show()
            }

        if (check.value){
            Column(Modifier.fillMaxSize()) {
                LazyColumn {
                    itemsIndexed(item){
                            index, route -> run {
                        Card(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                                .clickable {
                                    intent = Intent(applicationContext, Tampilan_Detail::class.java)
                                    intent.putExtra("tampilan","2")
                                    intent.putExtra("jenis","4")
                                    intent.putExtra("uang", item.get(index).data?.get("piutang").toString())
                                    intent.putExtra("nama", item.get(index).data?.get("nama").toString())
                                    intent.putExtra("catatan", item.get(index).data?.get("catatan").toString())
                                    intent.putExtra("tanggal_pinjam", item.get(index).data?.get("tanggal_pinjam").toString())
                                    intent.putExtra("tanggal_batas", item.get(index).data?.get("tanggal_batas").toString())
                                    intent.putExtra("status", item.get(index).data?.get("status").toString())
                                    intent.putExtra("id", item.get(index).id)
                                    startActivity(intent)
                                    finish()
                                },
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color(0xFFDFD8D7)
                        ) {
                            Column(Modifier.fillMaxWidth()) {
                                Card(Modifier
                                    .fillMaxWidth(),
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
                                    backgroundColor = Color(0xFFBDB7B6)
                                ) {
                                    Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                        Text(text = item.get(index).data?.get("tanggal_pinjam").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                        Text(text = "Claim", Modifier.weight(1f), textAlign = TextAlign.End)
                                    }
                                }

                                Row(Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                                    Text(text = item.get(index).data?.get("catatan").toString(), Modifier.weight(1f), textAlign = TextAlign.Start)
                                    val format = NumberFormat.getInstance()
                                    format.maximumFractionDigits = 0
                                    Text(text = "Rp ${format.format(item.get(index).data?.get("piutang").toString().toInt()).replace(",",".")}", Modifier.weight(1f), textAlign = TextAlign.Center)
                                    Text(text = item.get(index).data?.get("status").toString(), Modifier.weight(1f), textAlign = TextAlign.End)
                                }
                            }
                        }
                    }}
                }
            }
        }
    }
}