package com.butter.sweet

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.butter.sweet.ui.theme.ButterSweetTheme
import com.butter.sweet.ui.theme.clickhere
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class DaftarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterSweetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    var phone by remember { mutableStateOf("") }
                    var email by remember { mutableStateOf("") }
                    var password by remember { mutableStateOf("") }
                    var nama by remember { mutableStateOf("") }
                    var tanggal_lahir by remember { mutableStateOf("") }
                    var jenis_kelamin by remember { mutableStateOf(true) }
                    var alamat by remember { mutableStateOf("") }

                    var state_1 by remember { mutableStateOf(true) }
                    var state_2 by remember { mutableStateOf(false) }
                    var state_3 by remember { mutableStateOf(false) }
                    var state_4 by remember { mutableStateOf(false) }
                    var drawable by remember { mutableStateOf(R.drawable.logo_2) }

                    val context = LocalContext.current
                    val calendar: Calendar = Calendar.getInstance()
                    val date = DatePickerDialog(
                        context,
                        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                            tanggal_lahir = "$mDayOfMonth/${mMonth + 1}/$mYear"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )

                    Surface(
                        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                    ) {
                        Column(
                            modifier = Modifier
                                .width(300.dp)
                                .height(400.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = drawable),
                                contentDescription = "Photo ThinkPad T480",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentScale = ContentScale.FillBounds
                            )

                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 33.dp, end = 33.dp
                                    )
                                    .weight(1f)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                if (state_1) {
                                    Text(
                                        text = "DAFTAR",
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(top = 24.dp)
                                            .clickable {
                                                context.startActivity(
                                                    Intent(
                                                        context, LoginActivity::class.java
                                                    )
                                                )
                                            },
                                        textAlign = TextAlign.Center
                                    )

                                    TextField(
                                        value = phone,
                                        onValueChange = {
                                            phone = it
                                        },
                                        Modifier
                                            .padding(top = 20.dp)
                                            .fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                        label = { Text(text = "Phone") },
                                        placeholder = { Text(text = "Cth : 088852185653") },
                                        maxLines = 15
                                    )
                                }

                                if (state_2) {
                                    TextField(
                                        value = nama,
                                        onValueChange = {
                                            nama = it
                                        },
                                        Modifier
                                            .padding(top = 24.dp)
                                            .fillMaxWidth(),
                                        label = { Text(text = "Nama Lengkap") },
                                        placeholder = { Text(text = "Cth : Reza Arab, Lukas Pratama") },
                                        maxLines = 25
                                    )

                                    TextField(value = tanggal_lahir,
                                        onValueChange = {},
                                        Modifier
                                            .padding(top = 14.dp)
                                            .fillMaxWidth()
                                            .clickable { date.show() },
                                        enabled = false,
                                        label = { Text(text = "Tanggal Lahir") },
                                        placeholder = { Text(text = "Pilih Tanggal") })

                                    Text(
                                        text = "Jenis Kelamin", Modifier.padding(top = 14.dp)
                                    )
                                    Row(Modifier.fillMaxWidth()) {
                                        Row(
                                            Modifier.weight(1f),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(selected = jenis_kelamin, onClick = {
                                                jenis_kelamin = true
                                            })

                                            Text(text = "Laki - Laki")
                                        }
                                        Row(
                                            Modifier.weight(1f),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            RadioButton(selected = !jenis_kelamin, onClick = {
                                                jenis_kelamin = false
                                            })
                                            Text(text = "Perempuan")
                                        }
                                    }

                                    TextField(
                                        value = alamat,
                                        onValueChange = {
                                            alamat = it
                                        },
                                        Modifier
                                            .padding(top = 14.dp)
                                            .height(100.dp)
                                            .fillMaxWidth(),
                                        label = { Text(text = "Alamat") },
                                        placeholder = { Text(text = "Cth : Jl. Nasution. RT.10, RW.20") },
                                        maxLines = 100
                                    )
                                }

                                if (state_3) {
                                    TextField(value = email,
                                        onValueChange = {
                                            email = it
                                        },
                                        Modifier.padding(top = 24.dp),
                                        label = { Text(text = "Email") },
                                        placeholder = { Text(text = "Cth : reza.sigit@teknokrat.ac.id") })

                                    TextField(
                                        value = password,
                                        onValueChange = {
                                            password = it
                                        },
                                        Modifier.padding(top = 14.dp),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                        visualTransformation = PasswordVisualTransformation(),
                                        label = { Text(text = "Password") },
                                        placeholder = { Text(text = "Cth : 123QWE#kl, rayo!n_1") },
                                        maxLines = 100
                                    )
                                }

                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 20.dp, bottom = 20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (state_1) {
                                        Text(text = "Login", Modifier.clickable {
                                            context.startActivity(
                                                Intent(
                                                    context, LoginActivity::class.java
                                                )
                                            )
                                        }, color = clickhere)
                                    }

                                    Image(painter = painterResource(id = R.drawable.icon_button_daftar),
                                        contentDescription = "Tombol Daftar",
                                        modifier = Modifier
                                            .width(240.dp)
                                            .height(50.dp)
                                            .padding(top = 14.dp)
                                            .clickable {
                                                if (state_1) {
                                                    if (phone.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Nomor Handphone Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (phone.length == 15) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Nomor Handphone Kurang Dari Minimum",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else {
                                                        state_1 = false
                                                        state_2 = true
                                                        drawable = R.drawable.daftar_1
                                                    }
                                                } else if (state_2) {
                                                    if (nama.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Nama Lengkap Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (nama.length < 5) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Minimal 5 kata",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (tanggal_lahir.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Tanggal Lahir Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (alamat.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Alamat Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (alamat.length < 10) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Alamat Minimal 10 Kata",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else {
                                                        state_2 = false
                                                        state_3 = true
                                                        drawable = R.drawable.daftar_2
                                                    }
                                                } else if (state_3) {
                                                    if (email.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Email Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (password.isEmpty()) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Password Wajib Diisi",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else if (password.length < 8) {
                                                        Toast
                                                            .makeText(
                                                                context,
                                                                "Minimal Password 8 Kata",
                                                                Toast.LENGTH_SHORT
                                                            )
                                                            .show()
                                                    } else {
                                                        state_3 = false
                                                        state_4 = true
                                                    }
                                                }
                                            })

                                    if (state_4) {
                                        Toast.makeText(
                                            context, "Proses Sedang Berjalan", Toast.LENGTH_LONG
                                        ).show()

                                        val data = HashMap<String, String>()
                                        data.put("image", "Empty")
                                        data.put("phone", phone)
                                        data.put("email", email)
                                        data.put("password", password)
                                        data.put("nama", nama)
                                        data.put("tanggal_lahir", tanggal_lahir)
                                        data.put("jenis_kelamin", jenis_kelamin.toString())
                                        data.put("alamat", alamat)

                                        FirebaseFirestore.getInstance()
                                            .collection("ButterSweet").document("-")
                                            .collection("user").add(data).addOnSuccessListener {
                                                startActivity(
                                                    Intent(
                                                        context, UtamaActivity::class.java
                                                    ).putExtra(
                                                        "id", it.id
                                                    ).putExtra(
                                                        "image", "Empty"
                                                    ).putExtra(
                                                        "phone", phone
                                                    ).putExtra(
                                                        "email", email
                                                    ).putExtra(
                                                        "password", password
                                                    ).putExtra(
                                                        "nama", nama
                                                    ).putExtra(
                                                        "tanggal_lahir", tanggal_lahir
                                                    ).putExtra(
                                                        "jenis_kelamin",
                                                        jenis_kelamin.toString()
                                                    ).putExtra(
                                                        "alamat", alamat
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
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}