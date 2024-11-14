package com.damai.futsal

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.damai.futsal.ui.theme.BLACK
import com.damai.futsal.ui.theme.DamaiFutsalTheme
import com.damai.futsal.ui.theme.WHITE
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.*

class BookingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {

                var vTanggal by remember { mutableStateOf("Pilih Tanggal") }
                var vJam by remember { mutableStateOf("Pilih Jam Mulai") }

                var nama_tim by remember { mutableStateOf("") }
                var bayar by remember { mutableStateOf("") }
                var lama by remember { mutableStateOf("1") }
                var waktu_m by remember { mutableStateOf(0) }
                var waktu_mulai by remember { mutableStateOf("") }
                var waktu_selesai by remember { mutableStateOf("") }
                var tanggal by remember { mutableStateOf("") }

                val context = LocalContext.current
                val calendar: Calendar = Calendar.getInstance()
                val date = DatePickerDialog(
                    context,
                    { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        tanggal = "$mDayOfMonth/${mMonth + 1}/$mYear"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )

                val time = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
                    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                        waktu_mulai = "${hourOfDay}:00"
                        waktu_m = hourOfDay
                        if (lama.isEmpty()){

                        } else if (lama.toInt() < 1){

                        } else {
                            waktu_selesai = "${waktu_m+lama.toInt()}:00"
                            val matauang = NumberFormat.getInstance()
                            matauang.maximumFractionDigits = 0
                            bayar = "${matauang.format(lama.toInt()*65000).replace(",",".")}"
                        }
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 19.dp, start = 34.dp, end = 34.dp)) {

                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Image(painterResource(R.drawable.header_dashboard), "",
                            Modifier
                                .height(80.dp)
                                .width(278.dp))
                    }

                    TextField(value = tanggal,
                        onValueChange = {
                                        vTanggal = "Tanggal"
                        },
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth()
                            .clickable { date.show() },
                        enabled = false,
                        label = { Text(text = vTanggal) },
                        placeholder = { Text(text = "Pilih Tanggal") })

                    TextField(value = nama_tim,
                        onValueChange = {
                            nama_tim = it
                        },
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Nama Tim") },
                        placeholder = { Text(text = "Input Nama Tim") })

                    TextField(value = waktu_mulai,
                        onValueChange = {
                            vJam = "Jam Mulai Main"
                        },
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth()
                            .clickable { time.show() },
                        enabled = false,
                        label = { Text(text = vJam) },
                        placeholder = { Text(text = "Pilih Jam Mulai") })

                    TextField(value = waktu_selesai,
                        onValueChange = {},
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Jam Selesai") },
                        placeholder = { Text(text = "Otomatis") })

                    TextField(value = lama,
                        onValueChange = {
                                        lama = it
                            val matauang = NumberFormat.getInstance()
                            matauang.maximumFractionDigits = 0
                            if (lama.isEmpty()){
                                bayar = "${matauang.format(65000).replace(",",".")}"
                            } else {
                                bayar = "${matauang.format(lama.toInt() * 65000).replace(",",".")}"
                            }
                        },
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.NumberPassword
                        ),
                        label = { Text(text = "Lama Main (Per Jam Rp 65.000)") },
                        placeholder = { Text(text = "Isi lama waktu main(Satuan Jam)") })

                    TextField(value = bayar,
                        onValueChange = {},
                        Modifier
                            .padding(top = 14.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Bayar") },
                        placeholder = { Text(text = "Harga Yang haru dibauar") })

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Card(
                            Modifier
                                .width(130.dp)
                                .height(45.dp)
                                .clickable{
                                    if (tanggal.isEmpty()){
                                        Toast.makeText(applicationContext, "Tanggal tidak boleh kosong", Toast.LENGTH_LONG).show()
                                    } else if (waktu_mulai.isEmpty()){
                                        Toast.makeText(applicationContext, "Waktu mulai tidak boleh kosong", Toast.LENGTH_LONG).show()
                                    } else if (lama.toInt() < 1){
                                        Toast.makeText(applicationContext, "Lama Bermain Tidak Boleh 0", Toast.LENGTH_LONG).show()
                                    } else {
                                        FirebaseFirestore.getInstance().collection("jadwal").document("${tanggal.replace("/","-")}dan${waktu_mulai}|${waktu_selesai}").set(hashMapOf("nama_tim" to nama_tim, "tanggal" to tanggal, "waktu_mulai" to waktu_mulai, "waktu_selesai" to waktu_selesai, "lama" to lama, "bayar" to bayar)).addOnSuccessListener { documentReference ->
                                            Toast.makeText(applicationContext, "Data berhasil disimpan", Toast.LENGTH_LONG).show()
                                            nama_tim= ""
                                            vTanggal= ""
                                            vJam= ""
                                            bayar= ""
                                            lama= "1"
                                            waktu_m= 1
                                            waktu_mulai= ""
                                            waktu_selesai= ""
                                            tanggal= ""

                                            try {
                                                startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse("https://api.whatsapp.com/send?phone=628981899311&text=")
                                                    )
                                                )
                                            } catch (e: Exception) {
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Whatsapp belum terinstall",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }

                                        }.addOnFailureListener { exception ->
                                            Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }, shape = RoundedCornerShape(12.dp), backgroundColor = BLACK
                        ) {
                            Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "SIMPAN", color = WHITE)
                            }
                        }
                    }
                }
            }
        }
    }
}