package com.gkebjb.gkebanjarbaru.fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.gkebjb.gkebanjarbaru.databinding.ActivityJadwalIbadahBinding
import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity

class JadwalIbadahActivity : AppCompatActivity() {
    private lateinit var jadwalIbadahBinding: ActivityJadwalIbadahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jadwalIbadahBinding = ActivityJadwalIbadahBinding.inflate(layoutInflater)
        setContentView(jadwalIbadahBinding.root)

        // Mengatur click listener pada tombol
        jadwalIbadahBinding.tombolKembali.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }
    }
}