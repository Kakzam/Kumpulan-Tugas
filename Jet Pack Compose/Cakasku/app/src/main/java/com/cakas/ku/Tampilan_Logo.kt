package com.cakas.ku

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
import com.cakas.ku.ui.Storage
import com.cakas.ku.ui.Tampilan_Beranda
import com.cakas.ku.ui.theme.CakaskuTheme
import java.util.*

class Tampilan_Logo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val time = object : CountDownTimer(3000, 1000) {
                override fun onFinish() {
                    Storage.init(applicationContext).build()
                    if (Storage.getToken().equals("EMPTY")){
                        Storage.setToken(UUID.randomUUID().toString())
                        intent = Intent(applicationContext, Tampilan_Beranda::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        intent = Intent(applicationContext, Tampilan_Beranda::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onTick(countDownTimer: Long) {}

            }
            time.start()

            CakaskuTheme {
                    Image(painterResource(R.drawable.tampilan_logo), "", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }
    }
}