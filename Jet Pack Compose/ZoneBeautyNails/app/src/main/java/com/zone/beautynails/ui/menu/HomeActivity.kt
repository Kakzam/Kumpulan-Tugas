package com.zone.beautynails.ui.menu

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zone.beautynails.R
import com.zone.beautynails.ui.theme.PRIMARY
import com.zone.beautynails.ui.theme.ZoneBeautyNailsTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
//                val sharedPref = getSharedPreferences("zone_beauty_nails", Context.MODE_PRIVATE)
//                Toast.makeText(applicationContext, sharedPref.getString("type", "0"), Toast.LENGTH_SHORT).show()

                val semua = remember { mutableStateOf(R.drawable.semua_active) }
                val populer = remember { mutableStateOf(R.drawable.populer_off) }
                val terbaru = remember { mutableStateOf(R.drawable.terbaru_off) }

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
                                    Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painterResource(R.drawable.home_home),
                                        "",
                                        Modifier
                                            .width(120.dp)
                                            .height(40.dp)
                                            .padding(start = 8.dp, end = 22.dp).clickable {
                                                intent = Intent(applicationContext, HomeActivity::class.java)
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
                                            .padding(start = 22.dp, end = 8.dp).clickable {
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
                            .verticalScroll(rememberScrollState())
                    ) {

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, top = 30.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Home", color = Color.Black, fontSize = 24.sp)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Image(
                                    painterResource(R.drawable.icon_search), "",
                                    Modifier
                                        .width(38.dp)
                                        .height(38.dp)
                                )
                            }
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, bottom = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painterResource(semua.value), "",
                                Modifier
                                    .width(84.dp)
                                    .height(40.dp)
                                    .padding(end = 12.dp)
                                    .clickable {
                                        semua.value = R.drawable.semua_active
                                        populer.value = R.drawable.populer_off
                                        terbaru.value = R.drawable.terbaru_off
                                    })

                            Image(painterResource(populer.value), "",
                                Modifier
                                    .width(90.dp)
                                    .height(40.dp)
                                    .padding(end = 12.dp)
                                    .clickable {
                                        semua.value = R.drawable.semua_off
                                        populer.value = R.drawable.populer_active
                                        terbaru.value = R.drawable.terbaru_off
                                    })

                            Image(painterResource(terbaru.value), "",
                                Modifier
                                    .width(80.dp)
                                    .height(40.dp)
                                    .clickable {
                                        semua.value = R.drawable.semua_off
                                        populer.value = R.drawable.populer_off
                                        terbaru.value = R.drawable.terbaru_active
                                    })
                        }

                        if (semua.value == R.drawable.semua_active || populer.value == R.drawable.populer_active) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, WarnaActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.menu_warna),
                                    "",
                                    Modifier
                                        .padding(start = 12.dp, end = 12.dp),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }

                        if (semua.value == R.drawable.semua_active) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 8.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, PaketUsahaActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.menu_paket_usaha), "",
                                    Modifier
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 4.dp,
                                            bottom = 4.dp
                                        ), contentScale = ContentScale.Fit
                                )
                            }

                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 8.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, PriceListActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.menu_price_list), "",
                                    Modifier
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 4.dp,
                                            bottom = 4.dp
                                        ), contentScale = ContentScale.Fit
                                )
                            }
                        }

                        if (semua.value == R.drawable.semua_active || populer.value == R.drawable.populer_active || terbaru.value == R.drawable.terbaru_active) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 8.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, ReferensiMotifActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.menu_referensi_motif), "",
                                    Modifier
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 4.dp,
                                            bottom = 4.dp
                                        ), contentScale = ContentScale.Fit
                                )
                            }
                        }

                        if (semua.value == R.drawable.semua_active) {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(start = 14.dp, end = 14.dp, top = 8.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, StockWarnaActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, shape = RoundedCornerShape(12.dp)
                            ) {
                                Image(
                                    painterResource(R.drawable.menu_stock_warna), "",
                                    Modifier
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 4.dp,
                                            bottom = 4.dp
                                        ), contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}