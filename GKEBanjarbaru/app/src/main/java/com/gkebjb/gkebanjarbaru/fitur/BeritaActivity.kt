package com.gkebjb.gkebanjarbaru.fitur

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import com.github.barteksc.pdfviewer.PDFView
import com.gkebjb.gkebanjarbaru.databinding.ActivityBeritaBinding

import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class BeritaActivity : AppCompatActivity() {
    private lateinit var beritaBinding: ActivityBeritaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beritaBinding = ActivityBeritaBinding.inflate(layoutInflater)
        setContentView(beritaBinding.root)
        beritaBinding.title.text = intent.getStringExtra("key1")
        RetrievePDFFromURL(beritaBinding.pdfWebView).execute(intent.getStringExtra("key2"))

        // Mengatur click listener pada tombol
        beritaBinding.back.setOnClickListener {
            pindahKeActivityBeranda()
        }

        beritaBinding.unduh.setOnClickListener {
            val request = DownloadManager.Request(Uri.parse(intent.getStringExtra("key2")))
                .setTitle(intent.getStringExtra("key1") + ".pdf")
                .setDescription("Downloading PDF" + intent.getStringExtra("key1") + ".pdf")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, intent.getStringExtra("key1") + ".pdf")

            val downloadManager = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
        }
    }

    private fun pindahKeActivityBeranda() {
        val intent = Intent(this, HomeScreenActivity::class.java)
        startActivity(intent)
    }

    class RetrievePDFFromURL(pdfView: PDFView) :
        AsyncTask<String, Void, InputStream>() {

        val mypdfView: PDFView = pdfView

        override fun doInBackground(vararg params: String?): InputStream? {
            var inputStream: InputStream? = null
            try {
                val url = URL(params.get(0))
                val urlConnection: HttpURLConnection = url.openConnection() as HttpsURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                return null;
            }
            return inputStream;
        }

        override fun onPostExecute(result: InputStream?) {
            mypdfView.fromStream(result).load()
        }
    }
}