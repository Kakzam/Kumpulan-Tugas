package com.budget.pro.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import com.budget.pro.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.budget.pro.ui.ui.theme.BudgetProTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import java.util.HashMap

class TampilanPengeluaran : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetProTheme {
                Scaffold(bottomBar = {
                    Row(
                    Modifier
                        .fillMaxWidth()
                        .background(Color.White)) {
                    Image(
                        painterResource(R.drawable.icon_home_active), "",
                        Modifier
                            .weight(1f)
                            .height(48.dp)
                            .width(48.dp)
                            .padding(4.dp)
                            .clickable {
                                intent = Intent(applicationContext, TampilanBeranda::class.java)
                                startActivity(intent)
                                finish()
                            })

                    Image(
                        painterResource(R.drawable.icon_catatan_off), "",
                        Modifier
                            .weight(1f)
                            .height(48.dp)
                            .width(48.dp)
                            .padding(4.dp)
                            .clickable {
                                intent = Intent(applicationContext, TampilanTransaksi::class.java)
                                startActivity(intent)
                                finish()
                            })

                    Image(
                        painterResource(R.drawable.icon_statistik_off), "",
                        Modifier
                            .weight(1f)
                            .height(48.dp)
                            .width(48.dp)
                            .padding(4.dp)
                            .clickable {
                                intent = Intent(applicationContext, TampilanStatistik::class.java)
                                startActivity(intent)
                                finish()
                            })
                }
                }, content = {

                    val mContext = LocalContext.current
                    val rp = remember { mutableStateOf("") }
                    val catatan = remember { mutableStateOf("") }
                    val date = remember { mutableStateOf("") }
                    val calendar: Calendar = Calendar.getInstance()

                    var year = calendar.get(Calendar.YEAR)
                    var month = calendar.get(Calendar.MONTH)
                    var day = calendar.get(Calendar.DAY_OF_MONTH)

                    val dateS = DatePickerDialog(
                        mContext,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            date.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                        },
                        year,
                        month,
                        day
                    )

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding()),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        Image(
                            painterResource(R.drawable.title_pengeluaran), "",
                            Modifier
                                .fillMaxWidth()
                                .height(44.dp))

                        TextField(label = { Text(text = "Rp. ") },
                            value = rp.value,
                            onValueChange = { value -> rp.value = value },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true)

                        TextField(label = { Text(text = "Catatan") },
                            value = catatan.value,
                            onValueChange = { value -> catatan.value = value },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 10.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true)

                        TextField(label = { Text(text = "Tanggal Pengeluaran") },
                            value = date.value,
                            onValueChange = { value -> date.value = value },
                            enabled = false,
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 10.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    dateS.show()
                                },
                            singleLine = true)

                        Image(
                            painterResource(R.drawable.button_save), "",
                            Modifier
                                .height(100.dp)
                                .width(150.dp)
                                .clickable {
                                    if (rp.value.isEmpty()){
                                        Toast.makeText(applicationContext, "Pengeluaran Tidak Boleh Kosong",
                                            Toast.LENGTH_LONG).show()
                                    } else if (catatan.value.isEmpty()){
                                        Toast.makeText(applicationContext, "Catatan Tidak Boleh Kosong",
                                            Toast.LENGTH_LONG).show()
                                    } else if (date.value.isEmpty()){
                                        Toast.makeText(applicationContext, "Tanggal Tidak Boleh Kosong",
                                            Toast.LENGTH_LONG).show()
                                    } else {

                                        Toast.makeText(applicationContext, "Sedang di Proses",
                                            Toast.LENGTH_LONG).show()

                                        var data = HashMap<String, String>()
                                        data.put("pengeluaran", rp.value)
                                        data.put("catatan", catatan.value)
                                        data.put("tanggal", date.value)

                                        FirebaseFirestore.getInstance().collection("BudgetPro").document(Storage.getToken().toString()).collection("Pengeluaran").add(data)
                                            .addOnSuccessListener {
                                                startActivity(
                                                    Intent(
                                                        this@TampilanPengeluaran,
                                                        TampilanBeranda::class.java
                                                    )
                                                )
                                            }.addOnFailureListener {
                                                Toast.makeText(
                                                    applicationContext,
                                                    "Silahkan Periksa Kembali Koneksi Internet Anda",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                    }
                                },
                            contentScale = ContentScale.Crop)
                    }
                })

            }
        }
    }
}