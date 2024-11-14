package com.damai.futsal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damai.futsal.ui.theme.DamaiFutsalTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                Image(painterResource(R.drawable.background_dashboard), "", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                Column(Modifier.fillMaxSize().padding(top = 19.dp, start = 34.dp, end = 34.dp)) {
                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Image(painterResource(R.drawable.header_dashboard), "", Modifier.height(80.dp).width(278.dp))
                    }

                    Text(text = "Let's Go!", Modifier.padding(start = 16.dp, top = 24.dp, bottom = 12.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)

                    Image(painterResource(R.drawable.menu_informasi_lapangan), "", Modifier.fillMaxWidth().padding(bottom = 14.dp).clickable {
                        intent = Intent(applicationContext, DetailLapanganActivity::class.java)
                        startActivity(intent)
                    })

                    Image(painterResource(R.drawable.menu_booking), "", Modifier.fillMaxWidth().padding(bottom = 14.dp).clickable {
                        intent = Intent(applicationContext, BookingActivity::class.java)
                        startActivity(intent)
                    })

                    Image(painterResource(R.drawable.menu_jadwal_sewa_lapangan), "", Modifier.fillMaxWidth().padding(bottom = 14.dp).clickable {
                        intent = Intent(applicationContext, JadwalActivity::class.java)
                        startActivity(intent)
                    })
                }
            }
        }
    }
}