package com.budget.pro.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.budget.pro.ui.ui.theme.BudgetProTheme
import com.budget.pro.R
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat

class TampilanDetail : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetProTheme {
                Scaffold(bottomBar = {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Image(
                            painterResource(R.drawable.icon_home_off),
                            "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, TampilanBeranda::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(
                            painterResource(R.drawable.icon_catatan_off),
                            "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, TampilanTransaksi::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(
                            painterResource(R.drawable.icon_statistik_active),
                            "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent =
                                        Intent(applicationContext, TampilanStatistik::class.java)
                                    startActivity(intent)
                                    finish()
                                })
                    }
                }, content = {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())
                    ) {
                        Card(modifier = Modifier.fillMaxWidth().background(Color(0xFFE2DFDE))) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Detail Info",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 6.dp),
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                                Image(painter = painterResource(id = R.drawable.detail_delete),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .padding(4.dp)
                                        .height(30.dp)
                                        .width(30.dp)
                                        .clickable {
                                            var collection = ""

                                            if (intent
                                                    .getStringExtra("jenis")
                                                    .toString()
                                                    .equals("1")
                                            ) collection = "Pendapatan"
                                            if (intent
                                                    .getStringExtra("jenis")
                                                    .toString()
                                                    .equals("2")
                                            ) collection = "Pengeluaran"
                                            if (intent
                                                    .getStringExtra("jenis")
                                                    .toString()
                                                    .equals("3")
                                            ) collection = "Hutang"
                                            if (intent
                                                    .getStringExtra("jenis")
                                                    .toString()
                                                    .equals("4")
                                            ) collection = "Piutang"

                                            FirebaseFirestore
                                                .getInstance()
                                                .collection("BudgetPro")
                                                .document(
                                                    Storage
                                                        .getToken()
                                                        .toString()
                                                )
                                                .collection(collection)
                                                .document(
                                                    intent
                                                        .getStringExtra("id")
                                                        .toString()
                                                )
                                                .delete()
                                                .addOnSuccessListener {
                                                    intent =
                                                        Intent(
                                                            applicationContext,
                                                            TampilanTransaksi::class.java
                                                        )
                                                    startActivity(intent)
                                                    finish()

                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Berhasil Menghapus",
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
                                        })
                            }
                        }

                        val format = NumberFormat.getInstance()
                        format.maximumFractionDigits = 0

                        if (intent.getStringExtra("tampilan").equals("1")) {
                            Text(
                                text = "${
                                    format.format(
                                        intent.getStringExtra("uang").toString().toInt()
                                    ).replace(",", ".")
                                }",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 45.dp, bottom = 45.dp),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_catatan),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("catatan")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_tanggal),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("tanggal")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }
                        }

                        if (intent.getStringExtra("tampilan").equals("2")) {
                            Text(
                                text = "${
                                    format.format(
                                        intent.getStringExtra("uang").toString().toInt()
                                    ).replace(",", ".")
                                }",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 45.dp, bottom = 45.dp),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_nama),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("nama")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_catatan),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("catatan")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_tanggal),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("tanggal_pinjam")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_tanggal),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("tanggal_batas")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.detail_status),
                                    contentDescription = "",
                                    Modifier
                                        .weight(1f)
                                        .height(30.dp)
                                        .width(30.dp)
                                )
                                Text(
                                    text = "${intent.getStringExtra("status")}",
                                    Modifier
                                        .weight(6f)
                                        .padding(top = 8.dp)
                                )
                            }
                        }
                    }
                })
            }
        }
    }
}