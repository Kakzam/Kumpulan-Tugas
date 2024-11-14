package com.gkebjb.gkebanjarbaru.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.github.barteksc.pdfviewer.PDFView
import com.gkebjb.gkebanjarbaru.R
import com.gkebjb.gkebanjarbaru.fitur.BeritaActivity
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class AdapterBerita(var tanggal: ArrayList<String>,
                    var bulan: ArrayList<String>,
                    var tahun: ArrayList<String>,
                    var pdf: ArrayList<String>,
                    var context: Context?
                    ) : RecyclerView.Adapter<AdapterBerita.ItemViewHolder>() {

    val namaBulan = arrayOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_berita, parent, false))
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tanggal.text = tanggal.get(position) + " " + namaBulan.get(Integer.valueOf(bulan.get(position))-1) + " " + tahun.get(position)
        var pdfUrl = pdf.get(position)
        RetrievePDFFromURL(holder.pdf).execute("https://gkebanjarbaru.org/$pdfUrl#page=1")
        holder.pdf.setOnClickListener {
            var intent = Intent(context, BeritaActivity::class.java)
            intent.putExtra("key1", "Warta " + tanggal.get(position) + " " + namaBulan.get(Integer.valueOf(bulan.get(position))-1) + " " + tahun.get(position))
            intent.putExtra("key2", "https://gkebanjarbaru.org/$pdfUrl#page=1")
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  tanggal.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tanggal: TextView = itemView.findViewById(R.id.tanggal)
        var pdf: PDFView = itemView.findViewById(R.id.pdfWebView)
        var card: CardView = itemView.findViewById(R.id.card)
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