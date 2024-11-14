package com.gkebjb.gkebanjarbaru

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.gkebjb.gkebanjarbaru.databinding.ActivityMainBinding
import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}