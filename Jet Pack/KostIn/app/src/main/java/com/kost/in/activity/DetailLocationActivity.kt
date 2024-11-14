package com.kost.`in`.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.kost.`in`.R
import com.kost.`in`.data.model.ModelDetail
import com.kost.`in`.viewmodel.ListResultViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kost.`in`.databinding.ActivityDetailLocationBinding

class DetailLocationActivity : AppCompatActivity(), OnMapReadyCallback {

   private lateinit var binding: ActivityDetailLocationBinding
   lateinit var progressDialog: ProgressDialog
   lateinit var listResultViewModel: ListResultViewModel
   var googleMaps: GoogleMap? = null
   var strPlaceID: String? = null
   var strAlamat: String? = null
   var strTelepon: String? = null
   var strLokasi: String? = null
   var strLatitude = 0.0
   var strLongitude = 0.0

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityDetailLocationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      progressDialog = ProgressDialog(this)
      progressDialog.setTitle("Mohon Tunggu...")
      progressDialog.setCancelable(false)
      progressDialog.setMessage("sedang menampilkan lokasi")

      setSupportActionBar(binding.toolbar)
      assert(supportActionBar != null)
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowTitleEnabled(false)

      val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
      mapFragment.getMapAsync(this)

      val intent = intent
      val bundle = intent.extras

      if (bundle != null) {
         strPlaceID = bundle["placeId"] as String
         strLatitude = bundle["lat"] as Double
         strLongitude = bundle["lng"] as Double

         listResultViewModel = ViewModelProvider(this, NewInstanceFactory()).get(ListResultViewModel::class.java)
         listResultViewModel.setDetailLocation(strPlaceID!!)
         progressDialog.show()
         listResultViewModel.getDetailLocation().observe(this, { modelResults: ModelDetail ->
            strLokasi = modelResults.name.toString()
            strAlamat = modelResults.formatted_address.toString()
            strTelepon = modelResults.formatted_phone_number.toString()

            binding.tvNamaLokasi.setText(strLokasi)
            binding.tvAlamat.setText(strAlamat)
            binding.tvTelepon.setText(strTelepon)
            binding.tvLatitude.text = "Latitude " + strLatitude.toString()
            binding.tvLongitude.text = "Longitude " + strLongitude.toString()


            binding.btnRute.setOnClickListener { view: View? ->
               val intentRute = Intent(Intent.ACTION_VIEW,
                  Uri.parse("http://maps.google.com/maps?daddr=$strLatitude,$strLongitude"))
               startActivity(intentRute)
            }
            progressDialog.dismiss()
         })
      }
   }

   override fun onMapReady(googleMap: GoogleMap) {
      googleMaps = googleMap
      val latLng = LatLng(strLatitude, strLongitude)
      googleMaps?.addMarker(MarkerOptions().position(latLng).title(strLokasi))
      googleMaps?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
      googleMaps?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
      googleMaps?.uiSettings?.setAllGesturesEnabled(true)
      googleMaps?.uiSettings?.isZoomGesturesEnabled = true
      googleMaps?.isTrafficEnabled = true
   }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
      if (item.itemId == android.R.id.home) {
         finish()
         return true
      }
      return super.onOptionsItemSelected(item)
   }
}