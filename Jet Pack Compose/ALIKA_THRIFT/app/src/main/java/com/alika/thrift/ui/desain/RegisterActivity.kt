package com.alika.thrift.ui.desain

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alika.thrift.R
import com.alika.thrift.ui.desain.ui.theme.BACKGROUND_1
import com.alika.thrift.ui.theme.ALIKATHRIFTTheme
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALIKATHRIFTTheme {
                val email = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val nama = remember { mutableStateOf("") }
                val alamat = remember { mutableStateOf("") }
                val no_telepon = remember { mutableStateOf("") }

                Image(painter = painterResource(id = R.drawable.background), contentDescription = "", Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds)

                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "REGISTRASI", fontSize = 38.sp)

                    TextField(
                        label = { Text(text = "Nama") },
                        value = nama.value,
                        onValueChange = { value -> nama.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                bottom = 10.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        singleLine = true
                    )

                    TextField(
                        label = { Text(text = "Alamat") },
                        value = alamat.value,
                        onValueChange = { value -> alamat.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                bottom = 10.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        singleLine = true
                    )

                    TextField(
                        label = { Text(text = "No. Telepoin") },
                        value = no_telepon.value,
                        onValueChange = { value -> no_telepon.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        singleLine = true,
                        maxLines = 15
                    )

                    TextField(
                        label = { Text(text = "Email") },
                        value = email.value,
                        onValueChange = { value -> email.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                bottom = 10.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        singleLine = true
                    )

                    TextField(
                        label = { Text(text = "Password") },
                        value = password.value,
                        onValueChange = { value -> password.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true
                    )

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, end = 14.dp, top = 24.dp, bottom = 14.dp)
                            .clickable {
                                var data = HashMap<String, String>()
                                data.put("email", email.value)
                                data.put("password", password.value)
                                data.put("nama", nama.value)
                                data.put("alamat", alamat.value)
                                data.put("no_telepon", no_telepon.value)
                                FirebaseFirestore.getInstance().collection("login").add(data)
                                    .addOnSuccessListener { result ->
                                            intent = Intent(applicationContext, ShopActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                    }.addOnFailureListener {
                                        Toast.makeText(
                                            applicationContext,
                                            "Silahkan Periksa Kembali Koneksi Internet Anda",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }, shape = RoundedCornerShape(12.dp),
                        backgroundColor = BACKGROUND_1
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(4.dp), horizontalArrangement = Arrangement.Center) {
                            Text(text = "REGISTER", color = Color.White, fontSize = 24.sp)
                        }
                    }

                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 14.dp, end = 14.dp, top = 24.dp, bottom = 14.dp)
                            .clickable {
                                intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }, shape = RoundedCornerShape(12.dp),
                        backgroundColor = BACKGROUND_1
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(4.dp), horizontalArrangement = Arrangement.Center) {
                            Text(text = "LOGIN", color = Color.White, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}