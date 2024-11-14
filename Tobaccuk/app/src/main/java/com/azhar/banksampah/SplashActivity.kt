package com.azhar.banksampah
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.azhar.banksampah.view.main.MainActivity
import com.azhar.banksampah.R
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity<MainActivity>()
        },2000)
    }
}