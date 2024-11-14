package com.cash.crab

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.cash.crab.tampilan.LoginActivity
import com.cash.crab.ui.theme.PRIMER
import com.cash.crab.ui.theme.CashCrabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashCrabTheme {
                Column(Modifier.fillMaxSize().background(color = PRIMER), Arrangement.Center, Alignment.CenterHorizontally) { Image(painterResource(R.drawable.logo_kata), "", Modifier.fillMaxWidth()) }
                val time = object : CountDownTimer(4000, 1000) { override fun onFinish() { intent =  Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish() } override fun onTick(countDownTimer: Long) {}}
                time.start()
            }
        }
    }
}