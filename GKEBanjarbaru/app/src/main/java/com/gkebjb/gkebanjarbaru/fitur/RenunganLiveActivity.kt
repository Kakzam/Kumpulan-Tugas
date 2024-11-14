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
import com.gkebjb.gkebanjarbaru.adapter.AdapterRenunganLive
import com.gkebjb.gkebanjarbaru.databinding.ActivityRenunganLiveBinding
import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity

class RenunganLiveActivity : AppCompatActivity() {

    private lateinit var renunganLiveBinding: ActivityRenunganLiveBinding
    lateinit var adapter: AdapterRenunganLive

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renunganLiveBinding = ActivityRenunganLiveBinding.inflate(layoutInflater)
        setContentView(renunganLiveBinding.root)

        // Mengatur click listener pada tombol
        renunganLiveBinding.tombolKembali.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        try {

            renunganLiveBinding.title.text = intent.getStringExtra("key1")

            /* Inisialisasi GET API FROM VOLLEY */
            val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
            val request = JsonObjectRequest(Request.Method.GET, "https://gkebanjarbaru.org/api/" + intent.getStringExtra("key2"), null, { response ->
                try {

                    /* Inisialisasi Array Data */
                    var thumnail = ArrayList<String>()
                    var link = ArrayList<String>()

                    var hari = ArrayList<String>()
                    var tanggal = ArrayList<String>()
                    var bulan = ArrayList<String>()
                    var tahun = ArrayList<String>()
                    var nama_narasumber = ArrayList<String>()

                    var json = response.getJSONArray(intent.getStringExtra("key2"))

                    for (index in 0 until json.length()) {
                        var item = json.getJSONObject(index)
                        link.add(item.getString("link_youtube"))
                        thumnail.add(item.getString("gambar_thumnail"))
                        if (intent.getStringExtra("key2").equals("renungan_pagi")){
                            tanggal.add(item.getString("tanggal"))
                            bulan.add(item.getString("bulan"))
                            tahun.add(item.getString("tahun"))
                            hari.add(item.getString("hari"))
                            nama_narasumber.add(item.getString("nama_narasumber"))
                        }
                    }

                    /* Display Data To View */
                    val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                    renunganLiveBinding.recyclerView.layoutManager = layoutManager
                    adapter = AdapterRenunganLive(tanggal, bulan, tahun, hari, thumnail, link, nama_narasumber, this@RenunganLiveActivity)
                    renunganLiveBinding.recyclerView.adapter = adapter
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