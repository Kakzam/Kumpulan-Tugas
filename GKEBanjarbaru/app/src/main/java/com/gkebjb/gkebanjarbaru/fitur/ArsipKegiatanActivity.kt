package com.gkebjb.gkebanjarbaru.fitur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gkebjb.gkebanjarbaru.adapter.AdapterArsipKegiatan
import com.gkebjb.gkebanjarbaru.databinding.ActivityArsipKegiatanBinding
import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity

class ArsipKegiatanActivity : AppCompatActivity() {

    private lateinit var arsip: ActivityArsipKegiatanBinding
    lateinit var adapter: AdapterArsipKegiatan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arsip = ActivityArsipKegiatanBinding.inflate(layoutInflater)
        setContentView(arsip.root)

        // Mengatur click listener pada tombol
        arsip.back.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
        }

        try {
            /* Inisialisasi GET API FROM VOLLEY */
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            val request = JsonObjectRequest(Request.Method.GET, "https://gkebanjarbaru.org/api/arsip_kegiatan", null, { response ->
                try {

                    /* Inisialisasi Array Data */
                    var thumnail = ArrayList<String>()
                    var link = ArrayList<String>()
                    var title = ArrayList<String>()
                    var description = ArrayList<String>()

                    var json = response.getJSONArray("arsip")

                    for (index in 0 until json.length()) {
                        var item = json.getJSONObject(index)
                        link.add(item.getString("link_google_drive"))
                        thumnail.add(item.getString("foto_kegiatan"))
                        title.add(item.getString("nama_kegiatan"))
                        description.add(item.getString("deskripsi"))
                    }

                    /* Display Data To View */
                    val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    arsip.recyclerView.layoutManager = layoutManager
                    adapter = AdapterArsipKegiatan(title, description, thumnail, link, this@ArsipKegiatanActivity)
                    arsip.recyclerView.adapter = adapter
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, { error -> Log.e("TAG", "RESPONSE IS $error") })
            queue.add(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}