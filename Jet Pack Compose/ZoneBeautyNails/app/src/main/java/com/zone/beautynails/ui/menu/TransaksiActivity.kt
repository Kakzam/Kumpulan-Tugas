package com.zone.beautynails.ui.menu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zone.beautynails.ui.menu.ui.theme.ZoneBeautyNailsTheme
import com.zone.beautynails.ui.theme.PRIMARY

class TransaksiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = PRIMARY
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Text(text = "Transaksi", Modifier.fillMaxWidth().absolutePadding(top = 24.dp), textAlign = TextAlign.Center)
                        Text(text = "Tanggal : ${intent.getStringExtra("date")}", Modifier.absolutePadding(top= 24.dp, left = 14.dp, right = 14.dp))
                        Text(text = "Menu : ${intent.getStringExtra("menu")}", Modifier.absolutePadding(left = 14.dp, right = 14.dp))
                        Text(text = "Harga : ${intent.getStringExtra("harga")}", Modifier.absolutePadding(left = 14.dp, right = 14.dp))
                        Text(text = "Metode Pembayaran : ${intent.getStringExtra("metode")}", Modifier.absolutePadding(left = 14.dp, right = 14.dp))
                        Text(text = "Silahkan Screenshot Tampilan ini dan kirim ke whatsapp dengan menekan tombol dibawah", Modifier.fillMaxWidth().absolutePadding(top = 24.dp), color = Color.Red, textAlign = TextAlign.Center)
                        Text(text = "Pesan Via Whatsapp", Modifier.padding(8.dp).absolutePadding(left = 14.dp, right = 14.dp, top = 24.dp).background(Color.Red, RoundedCornerShape(4.dp)).clickable {
                            try {
                                val ph = "6285294647878"
                                startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://api.whatsapp.com/send?phone=${ph}&text=")
                                    )
                                )
                            } catch (e: Exception) {
                                Toast
                                    .makeText(
                                        applicationContext,
                                        "Whatsapp belum terinstall",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        })
                    }
                }
            }
        }
    }
}