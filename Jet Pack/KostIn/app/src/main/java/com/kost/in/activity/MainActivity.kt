package com.kost.`in`.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.kost.`in`.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    var permissionArrays = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val setPermission = Build.VERSION.SDK_INT
        if (setPermission > Build.VERSION_CODES.LOLLIPOP_MR1){
            if (checkIfAlreadyhavePermission() && checkIfAlreadyhavePermission2()) {
            } else {
                requestPermissions(permissionArrays, 101)
            }
        }

        binding.layoutApotek.setOnClickListener{view: View? ->
            val intent = Intent(this@MainActivity, KostPutriActivity::class.java)
            startActivity(intent)
        }
        binding.layoutHospital.setOnClickListener{view: View? ->
            val intent = Intent(this@MainActivity, KostPutraActivity::class.java)
            startActivity(intent)
        }
        binding.layoutDoctor.setOnClickListener{view: View? ->
            val intent = Intent(this@MainActivity, KontrakanActivity::class.java)
            startActivity(intent)
        }
        binding.layoutDrugStore.setOnClickListener{view: View? ->
            val intent = Intent(this@MainActivity, KostBebasActivity::class.java)
            startActivity(intent)
        }
        binding.layoutTentang.setOnClickListener{view: View? ->
            val intent = Intent(this@MainActivity, Tentang::class.java)
            startActivity(intent)
        }
    }

    private fun checkIfAlreadyhavePermission(): Boolean{
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }
    private fun checkIfAlreadyhavePermission2(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                val intent = intent
                finish()
                startActivity(intent)
            }
        }
    }

}