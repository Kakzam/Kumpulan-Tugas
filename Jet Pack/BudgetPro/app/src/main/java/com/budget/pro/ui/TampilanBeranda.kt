package com.budget.pro.ui

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.budget.pro.R
import com.budget.pro.ui.ui.theme.BudgetProTheme

class TampilanBeranda : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetProTheme {
                Scaffold(bottomBar = {

                    /* Bar Bawah */
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)) {
                        Image(
                            painterResource(R.drawable.icon_home_active), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent = Intent(applicationContext, TampilanBeranda::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(
                            painterResource(R.drawable.icon_catatan_off), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent = Intent(applicationContext, TampilanTransaksi::class.java)
                                    startActivity(intent)
                                    finish()
                                })

                        Image(
                            painterResource(R.drawable.icon_statistik_off), "",
                            Modifier
                                .weight(1f)
                                .height(48.dp)
                                .width(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    intent = Intent(applicationContext, TampilanStatistik::class.java)
                                    startActivity(intent)
                                    finish()
                                })
                    }
                }, content = {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding())) {

                        Image(
                            painterResource(R.drawable.title_beranda), "",
                            Modifier
                                .fillMaxWidth()
                                .height(44.dp))

                        Image(
                            painterResource(R.drawable.icon_beranda_atas_bar), "",
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 0.dp, top = 14.dp, end = 0.dp)
                                .scale(1.2f))

                        Row(Modifier.fillMaxWidth()) {
                            Image(
                                painterResource(R.drawable.icon_pendapatan), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, TampilanPendapatan::class.java)
                                        startActivity(intent)
                                        finish()
                                    })

                            Image(
                                painterResource(R.drawable.icon_pengeluaran), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, TampilanPengeluaran::class.java)
                                        startActivity(intent)
                                        finish()
                                    })
                        }

                        Row(Modifier.fillMaxWidth()) {
                            Image(
                                painterResource(R.drawable.icon_hutang), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, TampilanHutang::class.java)
                                        startActivity(intent)
                                        finish()
                                    })

                            Image(
                                painterResource(R.drawable.icon_piutang), "",
                                Modifier
                                    .weight(1f)
                                    .height(169.dp)
                                    .width(130.dp)
                                    .clickable {
                                        intent = Intent(applicationContext, TampilanPiutang::class.java)
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