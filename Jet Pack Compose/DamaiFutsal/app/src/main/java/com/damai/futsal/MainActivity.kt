package com.damai.futsal

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.damai.futsal.ui.theme.DamaiFutsalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                Image(painterResource(R.drawable.background_main), "", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

                Column(Modifier.fillMaxSize(), Arrangement.Bottom) {
                    Row(Modifier.fillMaxWidth().padding(bottom = 50.dp), Arrangement.Center) {
                        Image(painterResource(R.drawable.button_masuk), "", Modifier.width(100.dp).height(40.dp).clickable {
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        })
                    }
                }
            }
        }
    }
}