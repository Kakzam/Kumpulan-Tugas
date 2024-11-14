package com.gkebjb.gkebanjarbaru.fitur

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.adapter.AdapterChapter
import com.gkebjb.gkebanjarbaru.databinding.ActivityKitabBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChapterActivity : AppCompatActivity() {

    private lateinit var kitab: ActivityKitabBinding
    lateinit var adapter: AdapterChapter
    var hidden = false
    var pertama = true
    var position = 0
    var no = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        kitab = ActivityKitabBinding.inflate(layoutInflater)
        setContentView(kitab.root)
        kitab.tombolMenu.visibility = View.VISIBLE
        updateData(intent.getStringExtra("key3")!!)
        position = intent.getStringExtra("key3")!!.toInt()

        kitab.tombolMenu.setOnClickListener {
            if (hidden) {
                hidden = false
                kitab.tombolMenu.setImageDrawable(getDrawable(R.drawable.ic_invisible))
                kitab.menu.visibility = View.GONE
            } else {
                hidden = true
                kitab.tombolMenu.setImageDrawable(getDrawable(R.drawable.ic_visible))
                kitab.menu.visibility = View.VISIBLE
            }
        }

        kitab.sebelumnya.setOnClickListener {
            if (position > 1) {
                position--
                no = 0
                pertama = true
                updateData(position.toString())
            }
        }

        kitab.selanjutnya.setOnClickListener {
            if (position < intent.getStringExtra("key2")!!.toInt()) {
                position++
                no = 0
                pertama = true
                updateData(position.toString())
            }
        }

        kitab.tombolKembali.setOnClickListener {
            val intent = Intent(this, KitabActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateData(position: String) {
        kitab.title.text = intent.getStringExtra("key4") + " | " + position

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val queue: RequestQueue = Volley.newRequestQueue(applicationContext)
                val request = JsonObjectRequest(
                    Request.Method.GET,
                    "https://gkebanjarbaru.org/api/chapter/" + intent.getStringExtra("key1") + "/" + position,
                    null,
                    { response ->
                        try {
                            val nomer = ArrayList<String>()
                            val type = ArrayList<String>()
                            val content = ArrayList<String>()
                            var json = response.getJSONArray("chapter")

                            for (index in 0 until json.length()) {
                                var item = json.getJSONObject(index)
                                type.add(item.getString("type"))
                                content.add(item.getString("content"))

                                if (type[0].equals("title")) {
                                    if (item.getString("type")
                                            .equals("title") && index != 0
                                    ) no += 1
                                    nomer.add((index - no).toString())
                                } else {
                                    if (pertama){
                                        no = 1
                                        nomer.add((index + no).toString())
                                        if (type[index].equals("title")) {
                                            no = 0
                                            pertama = false
                                        }
                                    } else {
                                        if (type[index].equals("title")) no += 1
                                        nomer.add((index - no).toString())
                                    }
                                }
                            }

                            /* Display Data To View */
                            val layoutManager = LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            kitab.recyclerView.layoutManager = layoutManager
                            adapter = AdapterChapter(nomer, type, content)
                            kitab.recyclerView.adapter = adapter
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    },
                    { error -> Log.e("TAG", "RESPONSE IS $error") })
                queue.add(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}