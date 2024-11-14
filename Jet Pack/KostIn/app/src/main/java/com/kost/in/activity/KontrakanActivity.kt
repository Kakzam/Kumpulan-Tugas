package com.kost.`in`.activity

import android.Manifest
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kost.`in`.adapter.MainAdapter
import com.kost.`in`.data.model.ModelResults
import com.kost.`in`.databinding.ActivityLocationBinding
import com.kost.`in`.viewmodel.ListResultViewModel
import java.util.*

class KontrakanActivity : AppCompatActivity() {

   private lateinit var fusedLocationClient: FusedLocationProviderClient
   private lateinit var binding: ActivityLocationBinding
           lateinit var strLokasi: String
   lateinit var progressDialog: ProgressDialog
   lateinit var mainAdapter: MainAdapter
   lateinit var listResultViewModel: ListResultViewModel
   var strLatitude = 0.0
   var strLongitude = 0.0

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = ActivityLocationBinding.inflate(layoutInflater)
      setContentView(binding.root)

      fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
      setSupportActionBar(binding.toolbar)
      assert(supportActionBar != null)
      supportActionBar?.setDisplayHomeAsUpEnabled(true)
      supportActionBar?.setDisplayShowTitleEnabled(false)

      progressDialog = ProgressDialog(this)
      progressDialog.setTitle("Mohon Tunggu...")
      progressDialog.setCancelable(false)
      progressDialog.setMessage("Sedang menampilkan lokasi")

      if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
         ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
         ) != PackageManager.PERMISSION_GRANTED
      ) {
         return
      }

      fusedLocationClient.lastLocation
         .addOnSuccessListener { location: Location? ->
            if (location != null) {
               strLatitude = location.latitude
               strLongitude = location.longitude

               strLokasi = "$strLatitude,$strLongitude"

               binding.tvTitle.setText("Kontrakan")

               mainAdapter = MainAdapter(this)
               binding.rvListResult.setLayoutManager(LinearLayoutManager(this))
               binding.rvListResult.setAdapter(mainAdapter)
               binding.rvListResult.setHasFixedSize(true)

               listResultViewModel = ViewModelProvider(this, NewInstanceFactory()).get(ListResultViewModel::class.java)
               listResultViewModel.setDoctorLocation(strLokasi)
               progressDialog.show()
               listResultViewModel.getDoctorLocation().observe(this, { modelResults: ArrayList<ModelResults> ->
                  if (modelResults.size != 0) {
                     mainAdapter.setResultAdapter(modelResults)
                     progressDialog.dismiss()
                  } else {
                     Toast.makeText(this, "Oops, lokasi Praktik Dokter tidak ditemukan!", Toast.LENGTH_SHORT).show()
                  }
               })
            } else {
            }
         }
         .addOnFailureListener { exception: Exception ->
            Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
         }
   }

   override fun onOptionsItemSelected(item: MenuItem): Boolean {
      if (item.itemId == android.R.id.home){
         finish()
         return true
      }
      return super.onOptionsItemSelected(item)
   }
   }