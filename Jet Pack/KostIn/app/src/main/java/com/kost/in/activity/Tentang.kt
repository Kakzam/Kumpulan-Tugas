package com.kost.`in`.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kost.`in`.databinding.ActivityTentangBinding

class Tentang : AppCompatActivity(){

    private lateinit var binding: ActivityTentangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTentangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

