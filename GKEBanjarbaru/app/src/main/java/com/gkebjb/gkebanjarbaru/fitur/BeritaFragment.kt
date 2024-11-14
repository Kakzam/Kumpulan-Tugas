package com.gkebjb.gkebanjarbaru.fitur

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.barteksc.pdfviewer.PDFView
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.adapter.AdapterBerita
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BeritaFragment : Fragment() {

    private lateinit var pdfView: PDFView
    lateinit var adapter: AdapterBerita

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_berita, container, false)

        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            // Izin sudah diberikan, Anda dapat melanjutkan aktivitas yang memerlukan izin.
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val queue: RequestQueue = Volley.newRequestQueue(requireContext())
                val request = JsonObjectRequest(Method.GET, "https://gkebanjarbaru.org/api/berita_jemaat", null, { response ->
                    try {
                        val tanggal = ArrayList<String>()
                        val bulan = ArrayList<String>()
                        val tahun = ArrayList<String>()
                        val pdf = ArrayList<String>()
                        var json = response.getJSONArray("berita")

                        for (index in 0 until json.length()) {
                            var item = json.getJSONObject(index)
                            tanggal.add(item.getString("tanggal"))
                            bulan.add(item.getString("bulan"))
                            tahun.add(item.getString("tahun"))
                            pdf.add(item.getString("nama_file_pdf"))
                        }

                        val layoutManager = GridLayoutManager(requireContext(), 3)
                        recyclerView.layoutManager = layoutManager
                        adapter = AdapterBerita(tanggal, bulan, tahun, pdf, context)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }, { error -> Log.e("TAG", "RESPONSE IS $error") })
                queue.add(request)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return rootView
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            BeritaFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
