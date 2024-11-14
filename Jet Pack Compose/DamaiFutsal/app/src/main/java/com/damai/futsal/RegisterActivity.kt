package com.damai.futsal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damai.futsal.ui.theme.BLUE
import com.damai.futsal.ui.theme.DamaiFutsalTheme
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val email = remember { mutableStateOf("") }
                val disable_button = remember { mutableStateOf(true) }

                Column(Modifier.fillMaxSize()) {
//                    Text(text = "Welcome Back,", Modifier.padding(start = 19.dp, top = 34.dp, bottom = 33.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Row(Modifier.fillMaxWidth().padding(top = 34.dp), Arrangement.Center) {
                        Image(painterResource(R.drawable.logo_login), "", Modifier.height(200.dp).width(160.dp))
                    }

                    TextField(label = { Text(text = "Email") },
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
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        singleLine = true)

                    TextField(label = { Text(text = "Username") },
                        value = username.value,
                        onValueChange = { value -> username.value = value },
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

                    TextField(label = { Text(text = "Password") },
                        value = password.value,
                        onValueChange = { value -> password.value = value },
                        modifier = Modifier
                            .absolutePadding(
                                top = 8.dp,
                                bottom = 10.dp,
                                left = 16.dp,
                                right = 16.dp
                            )
                            .fillMaxWidth()
                            .background(Color.White),
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        singleLine = true)


                    Row(Modifier.fillMaxWidth().padding(top = 12.dp), Arrangement.Center) {
                        Image(painterResource(R.drawable.button_register), "", Modifier.height(52.dp).width(133.dp).clickable(enabled = disable_button.value) {
                            if (email.value.isEmpty()) Toast.makeText(applicationContext, "Email tidak boleh kosong", Toast.LENGTH_LONG).show()
                            else if (username.value.isEmpty()) Toast.makeText(applicationContext, "Username tidak boleh kosong", Toast.LENGTH_LONG).show()
                            else if (password.value.isEmpty()) Toast.makeText(applicationContext, "Password tidak boleh kosong", Toast.LENGTH_LONG).show()
                            else if (!email.value.isEmpty() && !username.value.isEmpty() && !password.value.isEmpty()) {
                                disable_button.value = false
                                FirebaseFirestore.getInstance().collection("user").add(hashMapOf("username" to username.value, "password" to password.value, "email" to email.value)).addOnSuccessListener { documentReference ->
                                    intent = Intent(applicationContext, DashboardActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }.addOnFailureListener { exception ->
                                    disable_button.value = true
                                    Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                                }
                            }

                        })
                    }

                    Row(Modifier.fillMaxWidth().padding(12.dp), Arrangement.Center) {
                        Text(text = "You have an account?", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text(text = "Sign", Modifier.clickable {
                            intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                        }, color = BLUE, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}