package com.gkebjb.gkebanjarbaru.fitur

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gkebjb.gkebanjarbaru.adapter.AdapterKitab
import com.gkebjb.gkebanjarbaru.databinding.ActivityKitabBinding
import com.gkebjb.gkebanjarbaru.home.HomeScreenActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KitabActivity : AppCompatActivity() {

    private lateinit var kitab: ActivityKitabBinding
    lateinit var adapter: AdapterKitab

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kitab = ActivityKitabBinding.inflate(layoutInflater)
        setContentView(kitab.root)
        kitab.title.text = "Alkitab"

        kitab.tombolKembali.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val request = JsonObjectRequest(Request.Method.GET, "https://gkebanjarbaru.org/api/beeble", null, { response ->
                    try {
                        val nomer = ArrayList<String>()
                        val name = ArrayList<String>()
                        val chapter = ArrayList<String>()
                        var json = response.getJSONArray("beeble")

                        for (index in 0 until json.length()) {
                            var item = json.getJSONObject(index)
                            nomer.add(item.getString("id"))
                            name.add(item.getString("name"))
                            chapter.add(item.getString("chapter"))
                        }

                        /* Display Data To View */
                        val layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                        kitab.recyclerView.layoutManager = layoutManager
                        adapter = AdapterKitab(nomer, name, chapter, this@KitabActivity)
                        kitab.recyclerView.adapter = adapter
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
}