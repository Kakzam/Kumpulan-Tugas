package com.cakas.ku.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cakas.ku.R
import com.cakas.ku.ui.ui.theme.CakaskuTheme

class Tampilan_Beranda : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CakaskuTheme {
                Scaffold(bottomBar = {

                    /* Bar Bawah */
                    Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)) {
                    Image(painterResource(R.drawable.icon_home_active), "",
                        Modifier
                            .weight(1f)
                            .height(48.dp)
                            .width(48.dp)
                            .padding(4.dp)
                            .clickable {
                                intent = Intent(applicationContext, Tampilan_Beranda::class.java)
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
                                intent = Intent(applicationContext, Tampilan_Transaksi::class.java)
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
                                intent = Intent(applicationContext, Tampilan_Statistik::class.java)
                                startActivity(intent)
                                finish()
                            })
                }}, content = {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())) {

                        Image(painterResource(R.drawable.title_beranda), "",
                            Modifier
                                .fillMaxWidth()
                                .height(44.dp))

                        Image(painterResource(R.drawable.icon_beranda_atas_bar), "",
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, top = 14.dp, end = 14.dp))

                        Row(Modifier.fillMaxWidth()) {
                            Image(painterResource(R.drawable.icon_pendapatan), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, Tampilan_Pendapatan::class.java)
                                        startActivity(intent)
                                        finish()
                                    })

                            Image(painterResource(R.drawable.icon_pengeluaran), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, Tampilan_Pengeluaran::class.java)
                                        startActivity(intent)
                                        finish()
                                    })
                        }

                        Row(Modifier.fillMaxWidth()) {
                            Image(painterResource(R.drawable.icon_hutang), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, Tampilan_Hutang::class.java)
                                        startActivity(intent)
                                        finish()
                                    })

                            Image(painterResource(R.drawable.icon_piutang), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, Tampilan_Piutang::class.java)
                                        startActivity(intent)
                                        finish()
                                    })
                        }
                    }
                })
                
            }
        }
    }
}