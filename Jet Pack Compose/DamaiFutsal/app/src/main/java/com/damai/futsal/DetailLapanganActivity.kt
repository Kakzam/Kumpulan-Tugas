package com.damai.futsal

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damai.futsal.ui.theme.DamaiFutsalTheme

class DetailLapanganActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 19.dp, start = 34.dp, end = 34.dp)) {

                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Image(painterResource(R.drawable.header_dashboard), "",
                            Modifier
                                .height(80.dp)
                                .width(278.dp))
                    }

                    Image(painterResource(R.drawable.lapangan), "",
                        Modifier
                            .height(183.dp)
                            .width(339.dp)
                            .padding(bottom = 14.dp, top = 14.dp))

                    Text(text = "Damai Futsal merupakan lapangan outdoor yang beralamat di Jalan Imam Bonjol Gg. Damai, Sumber Rejo, kec. Kemiling, Bandar Lampung", Modifier.padding(start = 13.dp, top = 24.dp, bottom = 12.dp), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Text(text = "Cek Lokasi", Modifier.padding(start = 12.dp, top = 24.dp, bottom = 12.dp).clickable {
                            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/f8pVSxTeRXPvgjhq8?g_st=iw"))
                            startActivity(intent)
                        }, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Image(painterResource(R.drawable.detail_lapangan), "",
                        Modifier
                            .height(108.dp)
                            .width(347.dp))
                }
            }
        }
    }
}