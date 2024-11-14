package com.zone.beautynails

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zone.beautynails.ui.menu.ContinueActivity
import com.zone.beautynails.ui.menu.WarnaActivity
import com.zone.beautynails.ui.theme.PRIMARY
import com.zone.beautynails.ui.theme.ZoneBeautyNailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val time = object : CountDownTimer(2000, 1000) {
                override fun onFinish() {
                    intent = Intent(applicationContext, ContinueActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onTick(countDownTimer: Long) {}

            }
            time.start()

            ZoneBeautyNailsTheme {
                Column(Modifier.fillMaxSize().background(PRIMARY), Arrangement.Center) {
                        Image(painterResource(R.drawable.icon_depan), "", Modifier.fillMaxWidth())
                }
            }
        }
    }
}