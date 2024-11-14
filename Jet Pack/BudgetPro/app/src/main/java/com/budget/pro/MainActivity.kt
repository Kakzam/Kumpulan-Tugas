package com.budget.pro

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
import com.budget.pro.ui.Storage
import com.budget.pro.ui.TampilanBeranda
import com.budget.pro.ui.theme.BudgetProTheme
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val time = object : CountDownTimer(3000, 1000) {
                override fun onFinish() {
                    Storage.init(applicationContext).build()
                    if (Storage.getToken().equals("EMPTY")){
                        Storage.setToken(UUID.randomUUID().toString())
                        intent = Intent(applicationContext, TampilanBeranda::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        intent = Intent(applicationContext, TampilanBeranda::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onTick(countDownTimer: Long) {}

            }
            time.start()

            BudgetProTheme {
                Image(painterResource(R.drawable.tampilan_logo), "", Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            }
        }
    }
}