package com.bride.tobe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bride.tobe.ui.theme.Background
import com.bride.tobe.ui.theme.BrideTobeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LaunchedEffect(Unit) {
                delay(2000) // Menunda selama 2 detik
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                this@MainActivity.startActivity(intent)
            }

            BrideTobeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_logo),
                            contentDescription = "Gambar Anda",
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}