package com.zone.beautynails.ui.menu

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zone.beautynails.R
import com.zone.beautynails.ui.theme.PRIMARY
import com.zone.beautynails.ui.theme.ZoneBeautyNailsTheme
import java.util.*

class PemesananActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var menu by remember { mutableStateOf("") }
            var harga by remember { mutableStateOf("") }
            var transfer by remember { mutableStateOf("") }
            val date = remember { mutableStateOf("") }


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
                                    
                                    Text(text = "Batal", Modifier.padding(4.dp).absolutePadding(right = 14.dp).background(Color.White, RoundedCornerShape(4.dp)).clickable {
                                        intent = Intent(
                                            applicationContext,
                                            HomeActivity::class.java
                                        )
                                        startActivity(intent)
                                        finish()
                                    })

                                    Text(text = "Pesan", Modifier.padding(4.dp).background(Color.Green, RoundedCornerShape(4.dp)).clickable {
                                        intent = Intent(
                                            applicationContext,
                                            TransaksiActivity::class.java
                                        )
                                        intent.putExtra("menu", menu)
                                        intent.putExtra("harga", harga)
                                        intent.putExtra("metode", transfer)
                                        intent.putExtra("date", date.value)
                                        startActivity(intent)
                                        finish()
                                    })
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

                        val calendar = Calendar.getInstance()
                        val mContext = LocalContext.current

                        var menu_e by remember { mutableStateOf(false) }
                        var transfer_e by remember { mutableStateOf(false) }

                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH)
                        val day = calendar.get(Calendar.DAY_OF_MONTH)

                        val dateD = DatePickerDialog(
                            mContext,
                            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                date.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            },
                            year,
                            month,
                            day
                        )

                        TextField(
                            label = { Text(text = "Pilih Tanggal") },
                            value = date.value,
                            onValueChange = { value -> date.value = value },
                            enabled = false,
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 10.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    dateD.show()
                                },
                            singleLine = true
                        )

                        TextField(
                            label = { Text(text = "Pilih Harga") },
                            value = menu,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 10.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    menu_e = true
                                },
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = menu_e,
                            onDismissRequest = { menu_e = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                menu = "Nails Zone 1"
                                harga = "50.000"
                            }) {
                                Text("Nails Zone 1 (50k)")
                            }

                            DropdownMenuItem(onClick = {
                                menu = "Nails Zone 2"
                                harga = "40.000"
                            }) {
                                Text("Nails Zone 2 (40k)")
                            }

                            DropdownMenuItem(onClick = {
                                menu = "Nails Full Motif"
                                harga = "65.000"
                            }) {
                                Text("Nails Full Motif (65k)")
                            }

                            DropdownMenuItem(onClick = {
                                menu = "Nails Polos"
                                harga = "30.000"
                            }) {
                                Text("Nails Polos (30k)")
                            }

                            DropdownMenuItem(onClick = {
                                menu = "Nails Zone Fresh"
                                harga = "30.000"
                            }) {
                                Text("Nails Zone Fresh (30k)")
                            }
                        }

                        TextField(
                            label = { Text(text = "Pilih Metode Pembayaran") },
                            value = transfer,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 10.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    transfer_e = true
                                },
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = transfer_e,
                            onDismissRequest = { transfer_e = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                transfer = "BCA"
                            }) {
                                Text("BCA")
                            }

                            DropdownMenuItem(onClick = {
                                transfer = "BNI"
                            }) {
                                Text("BNI")
                            }

                            DropdownMenuItem(onClick = {
                                transfer = "BRI"
                            }) {
                                Text("BRI")
                            }

                            DropdownMenuItem(onClick = {
                                transfer = "Mandiri"
                            }) {
                                Text("Mandiri")
                            }

                            DropdownMenuItem(onClick = {
                                transfer = "Go-pay"
                            }) {
                                Text("Go-pay")
                            }
                        }
                    }
                }
            }
        }
    }
}