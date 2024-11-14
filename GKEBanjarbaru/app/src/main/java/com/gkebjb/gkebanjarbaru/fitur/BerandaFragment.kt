package com.gkebjb.gkebanjarbaru.fitur

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.gkebjb.gkebanjarbaru.adapter.AdapterBeranda
import com.gkebjb.gkebanjarbaru.adapter.AdapterSlider
import com.gkebjb.gkebanjarbaru.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {
    private lateinit var berandaBinding: FragmentBerandaBinding
    lateinit var adapterRenungan: AdapterBeranda
    lateinit var adapterLive: AdapterBeranda
    lateinit var adapterSlider: AdapterSlider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        berandaBinding = FragmentBerandaBinding.inflate(layoutInflater)

        try {
            /* Inisialisasi GET API FROM VOLLEY */
            val queue: RequestQueue = Volley.newRequestQueue(requireContext())
            val request = JsonObjectRequest(Request.Method.GET, "https://gkebanjarbaru.org/api/getRenungLive", null, { response ->
                try {
                    /* Inisialisasi Array Data */
                    var hari = ArrayList<String>()
                    var tanggal = ArrayList<String>()
                    var bulan = ArrayList<String>()
                    var tahun = ArrayList<String>()
                    var thumnail_renungan = ArrayList<String>()
                    var link_renungan = ArrayList<String>()
                    var nama_narasumber = ArrayList<String>()
                    var jsonRenunganPagi = response.getJSONArray("renungan_pagi")

                    var thumnail_live_streaming = ArrayList<String>()
                    var link_live = ArrayList<String>()
                    var jsonLiveStreaming = response.getJSONArray("live_streaming")

                    /* Input Data To Array */
                    for (index in 0 until jsonRenunganPagi.length()) {
                        var item = jsonRenunganPagi.getJSONObject(index)
                        tanggal.add(item.getString("tanggal"))
                        bulan.add(item.getString("bulan"))
                        tahun.add(item.getString("tahun"))
                        hari.add(item.getString("hari"))
                        link_renungan.add(item.getString("link_youtube"))
                        thumnail_renungan.add(item.getString("gambar_thumnail"))
                        nama_narasumber.add(item.getString("nama_narasumber"))
                    }

                    for (index in 0 until jsonLiveStreaming.length()) {
                        var item = jsonLiveStreaming.getJSONObject(index)
                        link_live.add(item.getString("link_youtube"))
                        thumnail_live_streaming.add(item.getString("gambar_thumnail"))
                    }

                    /* Display Data To View Renungan */
                    val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    berandaBinding.rvRenunganpagi.layoutManager = layoutManager
                    adapterRenungan = AdapterBeranda(tanggal, bulan, tahun, hari, thumnail_renungan, link_renungan, nama_narasumber, context)
                    berandaBinding.rvRenunganpagi.adapter = adapterRenungan

                    /* Display Data To View Live Streaming */
                    tanggal.clear()
                    bulan.clear()
                    tahun.clear()
                    hari.clear()
                    nama_narasumber.clear()
                    val layoutManagerS = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    berandaBinding.rvStreamingibadah.layoutManager = layoutManagerS
                    adapterLive = AdapterBeranda(tanggal, bulan, tahun, hari, thumnail_live_streaming, link_live, nama_narasumber, context)
                    berandaBinding.rvStreamingibadah.adapter = adapterLive

                    var url = ArrayList<String>()
                    var jsonSlider = response.getJSONArray("slider")

                    /* Input Data To Array */
                    for (index in 0 until jsonSlider.length()) {
                        var item = jsonSlider.getJSONObject(index)
                        url.add(item.getString("gambar"))
                    }

                    /* Display Data To Slider */
                    adapterSlider = AdapterSlider(url, context)
                    berandaBinding.viewPager.adapter = adapterSlider
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, { error -> Log.e("TAG", "RESPONSE IS $error") })
            queue.add(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Menambahkan listener untuk tombol
        berandaBinding.jadwalIbadah.setOnClickListener {
            val intent = Intent(activity, JadwalIbadahActivity::class.java)
            startActivity(intent)
        }

        berandaBinding.arsipKegiatan.setOnClickListener {
            val intent = Intent(activity, ArsipKegiatanActivity::class.java)
            startActivity(intent)
        }

        berandaBinding.tentang.setOnClickListener {
            Toast.makeText(context, "Under Construction", Toast.LENGTH_LONG).show()
        }

        berandaBinding.alkitab.setOnClickListener {
            val intent = Intent(activity, KitabActivity::class.java)
            startActivity(intent)
        }

        berandaBinding.seemore.setOnClickListener {
            val intent = Intent(activity, RenunganLiveActivity::class.java)
            intent.putExtra("key1", "Renungan Pagi")
            intent.putExtra("key2", "renungan_pagi")
            startActivity(intent)
        }

        berandaBinding.seemoreLive.setOnClickListener {
            val intent = Intent(activity, RenunganLiveActivity::class.java)
            intent.putExtra("key1", "Live Streaming")
            intent.putExtra("key2", "live_streaming")
            startActivity(intent)
        }

        return (berandaBinding.root)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BerandaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}