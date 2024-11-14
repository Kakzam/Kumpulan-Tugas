package com.alika.thrift

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.alika.thrift.ui.desain.LoginActivity
import com.alika.thrift.ui.theme.ALIKATHRIFTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val time = object : CountDownTimer(2000, 1000) {
                override fun onFinish() {
                    intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                override fun onTick(countDownTimer: Long) {}

            }
            time.start()

            ALIKATHRIFTTheme {
                Image(painter = painterResource(id = R.drawable.icon_main), contentDescription = "", Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)
            }
        }
    }
}