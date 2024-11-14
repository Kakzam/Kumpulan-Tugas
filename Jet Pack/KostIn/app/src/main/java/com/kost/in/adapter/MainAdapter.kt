package com.kost.`in`.adapter

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.kost.`in`.activity.DetailLocationActivity
import com.kost.`in`.adapter.MainAdapter.SearchViewHolder
import com.kost.`in`.data.model.ModelResults
import com.kost.`in`.utils.HitungJarak.getDistance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kost.`in`.R
import com.kost.`in`.databinding.ListItemMainBinding
import java.text.DecimalFormat
import java.util.*


class MainAdapter(private val context: Context) : RecyclerView.Adapter<SearchViewHolder>() {

    private val modelResults = ArrayList<ModelResults>()
    private lateinit var binding: ListItemMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var strLatitude = 0.0
    var strLongitude = 0.0

    fun setResultAdapter(items: ArrayList<ModelResults>){
        modelResults.clear()
        modelResults.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ListItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = modelResults[position]

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    strLatitude = location.latitude
                    strLongitude = location.longitude

                    val strPlaceID = modelResults[position].placeId

                    val strLat = modelResults[position].modelGeometry?.modelLocation?.lat!!
                    val strLong = modelResults[position].modelGeometry?.modelLocation?.lng!!
                    val strJarak = getDistance(strLat, strLong, strLatitude, strLongitude)

                    holder.tvNamaLokasi.text = item.name
                    holder.tvAlamat.text = item.vicinity
                    holder.tvJarak.text = DecimalFormat("#.##").format(strJarak) + " KM"

                    holder.cvListLocation.setOnClickListener { view: View? ->
                        val intent = Intent(context, DetailLocationActivity::class.java)
                        intent.putExtra("placeId", strPlaceID)
                        intent.putExtra("lat", strLat)
                        intent.putExtra("lng", strLong)
                        context?.startActivity(intent)
                    }
                } else {
                }
            }
            .addOnFailureListener { exception: Exception ->
                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
        return modelResults.size
    }

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cvListLocation: CardView = itemView.findViewById(R.id.cvListLocation)
        val tvNamaLokasi: TextView = itemView.findViewById(R.id.tvNamaLokasi)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
        val tvJarak: TextView = itemView.findViewById(R.id.tvJarak)
    }
}