package com.gkebjb.gkebanjarbaru.fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.gkebjb.gkebanjarbaru.databinding.ActivityBeritaBinding

class ArsipActivity : AppCompatActivity() {
    private lateinit var beritaBinding: ActivityBeritaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beritaBinding = ActivityBeritaBinding.inflate(layoutInflater)
        setContentView(beritaBinding.root)
        beritaBinding.title.text = intent.getStringExtra("key1")

        // Mengatur click listener pada tombol
        beritaBinding.back.setOnClickListener {
            val intent = Intent(this, ArsipKegiatanActivity::class.java)
            startActivity(intent)
        }

        beritaBinding.web.visibility = View.VISIBLE
        beritaBinding.pdfWebView.visibility = View.GONE
        beritaBinding.unduh.visibility = View.GONE

        beritaBinding.web.settings.javaScriptEnabled = true
        beritaBinding.web.settings.pluginState = WebSettings.PluginState.ON
        beritaBinding.web.webViewClient = WebViewClient()
        beritaBinding.web.loadUrl(intent.getStringExtra("key2")!!)
    }
}