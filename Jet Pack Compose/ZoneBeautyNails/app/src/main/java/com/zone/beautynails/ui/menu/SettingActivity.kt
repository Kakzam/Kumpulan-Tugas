package com.zone.beautynails.ui.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

class SettingActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
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
                                                intent = Intent(
                                                    applicationContext,
                                                    SettingActivity::class.java
                                                )
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
                        }

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(Color.Black)) {}

                        var setting by remember {
                            mutableStateOf(R.drawable.ic_arrow_left)
                        }

                        var pemesanan by remember {
                            mutableStateOf(R.drawable.ic_arrow_left)
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, top = 30.dp)
                                .clickable {
                                    if (setting == R.drawable.ic_arrow_down) setting = R.drawable.ic_arrow_down
                                    else setting = R.drawable.ic_arrow_left
                                }, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_setting), "",
                                Modifier
                                    .width(38.dp)
                                    .height(38.dp)
                            )
                            
                            Text(text = "Setting Account", Modifier.absolutePadding(left = 14.dp), color = Color.White)

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Image(
                                    painterResource(id = setting), "",
                                    Modifier
                                        .width(38.dp)
                                        .height(38.dp)
                                )
                            }
                        }

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(start = 22.dp, end = 22.dp, top = 30.dp)
                                .clickable {
                                    intent = Intent(
                                        applicationContext,
                                        PemesananActivity::class.java
                                    )
                                    startActivity(intent)
                                    finish()
                                }, verticalAlignment = Alignment.CenterVertically

                        ) {
                            Image(
                                painterResource(id = R.drawable.ic_pesanan), "",
                                Modifier
                                    .width(38.dp)
                                    .height(38.dp)
                            )

                            Text(text = "Pemesanan", Modifier.absolutePadding(left = 14.dp), color = Color.White)

                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                                Image(
                                    painterResource(id = pemesanan), "",
                                    Modifier
                                        .width(38.dp)
                                        .height(38.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}