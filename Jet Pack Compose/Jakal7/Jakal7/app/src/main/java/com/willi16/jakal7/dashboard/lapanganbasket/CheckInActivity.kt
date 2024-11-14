package com.willi16.jakal7.dashboard.lapanganbasket

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.willi16.jakal7.R
import com.willi16.jakal7.dashboard.model.Checkin
import com.willi16.jakal7.dashboard.model.LapanganFutsal
import com.willi16.jakal7.databinding.ActivityCheckInBinding
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.DateFormat
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.util.Calendar

class CheckInActivity : AppCompatActivity() {
    private lateinit var checkInBinding: ActivityCheckInBinding
    private lateinit var refData : DatabaseReference
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInBinding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(checkInBinding.root)

        val data = intent.getParcelableExtra<LapanganFutsal>("checkin")

        val jamPerson = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12)
        val arrayJam = ArrayAdapter(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, jamPerson)

        val statusPerson = arrayOf("Mahasiswa/Pelajar", "Umum")
        val arrayStatus = ArrayAdapter(this, com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, statusPerson)

        checkInBinding.back.setOnClickListener{
            finish()
        }

        checkInBinding.btnCheckout.setOnClickListener {
            saveData()
        }

        Glide.with(this)
            .load(data?.poster)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.ivCheckout)

        Glide.with(this)
            .load(data?.gambar1)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar1)

        Glide.with(this)
            .load(data?.gambar2)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar2)

        Glide.with(this)
            .load(data?.gambar3)
            .placeholder(R.drawable.progress_animation)
            .into(checkInBinding.gambar3)

        checkInBinding.tvlokasiCheckout.text = data?.alamat
        checkInBinding.tvrateCheckout.text = data?.rating
        checkInBinding.tvLapanganCheckout.text = data?.category

        val myCalendar = Calendar.getInstance()
        val  datePicker = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            systemCalendar(myCalendar)
        }

        checkInBinding.btnDate.setOnClickListener {
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        checkInBinding.spJam.adapter = arrayJam
        checkInBinding.spJam.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkInBinding.resultJam.text = jamPerson[position].toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan masukan jangka sewa",Toast.LENGTH_SHORT).show()
            }
            }

        checkInBinding.spStatus.adapter = arrayStatus
        checkInBinding.spStatus.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                checkInBinding.resultStatus.text = statusPerson[position]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Silahkan masukan status anda ",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveData() {
        refData = FirebaseDatabase.getInstance().getReference("UserCheckin")
        auth = FirebaseAuth.getInstance()
        val data = intent.getParcelableExtra<LapanganFutsal>("checkin")

        val nama = auth.currentUser?.displayName.toString()
        val idUser = auth.currentUser?.uid.toString()
        val emailUser = auth.currentUser?.email.toString()
        val photoUser = auth.currentUser?.photoUrl.toString()
        val poster = data?.poster
        val statusUser = checkInBinding.resultStatus.toString()
        val lapanganUser = checkInBinding.resultLapangan.toString()
        val sewaUser = checkInBinding.resultJam.toString()
        val phoneUser = checkInBinding.etPhone.toString()
        val alamatUser = checkInBinding.etAlamat.toString()
        val hasil = data?.price
        val priceUser = hasil!!.toInt() * sewaUser.toInt()
        val fasilitas = checkInBinding.tvFasilitas.text.toString()
        val pending = checkInBinding.pending.text.toString()
        val tanggal = checkInBinding.tvDate.text.toString()

        if (phoneUser.isEmpty()) {
            checkInBinding.etPhone.error = "silahkan masukan no WhatsApp"
            checkInBinding.etPhone.requestFocusFromTouch()
        }else if (alamatUser.isEmpty())  {
            checkInBinding.etAlamat.error = "silahkan masukan alamat lengkap"
            checkInBinding.etAlamat.requestFocusFromTouch()
        }

        val userId = refData.push().key
        refData.setValue("Free",fasilitas)
        refData.setValue("Proses",pending)
        val checkin = Checkin(nama, idUser, emailUser, photoUser, poster, statusUser, lapanganUser,sewaUser.toInt(),phoneUser, alamatUser, priceUser, fasilitas, pending, tanggal)

        if (phoneUser.isEmpty() && alamatUser.isEmpty()) {
            if (userId != null) {
                refData.child(userId).setValue(checkin).addOnCanceledListener {
                    refData.removeValue()
                }
            }
        }else {
            if (userId != null) {
                refData.child(userId).setValue(checkin).addOnCompleteListener{
                    val intent = Intent(this,CheckOutActivity::class.java)
                    startActivity(intent)
                    finishAndRemoveTask()
                }
            }
        }
    }

    private fun systemCalendar(myCalendar: Calendar) {
        val sdf = DateFormat.getDateInstance(SimpleDateFormat.FULL)
        checkInBinding.tvDate.text = sdf.format(myCalendar.time)
    }
}