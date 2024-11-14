package com.apps.atokoku

import android.os.CountDownTimer
import androidx.compose.runtime.Composable
import com.apps.atokoku.base.BaseActivity
import com.apps.atokoku.base.Preference

class SplashScreen : BaseActivity() {

    @Composable
    override fun loadUi() {
        SplashActivity()
        val time = object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                if (Preference.getId().toString().isEmpty()) setActivity(LoginActivity::class.java)
                else setActivity(DashboardActivity::class.java)
            }

            override fun onTick(countDownTimer: Long) {}

        }
        time.start()
    }
}