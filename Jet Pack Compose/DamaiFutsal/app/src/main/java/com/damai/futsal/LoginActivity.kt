package com.damai.futsal

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }
                val disable_button = remember { mutableStateOf(true) }

                Column(Modifier.fillMaxSize()) {
                    Text(
                        text = "Welcome Back,",
                        Modifier.padding(start = 19.dp, top = 34.dp, bottom = 33.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Image(
                            painterResource(R.drawable.logo_login),
                            "",
                            Modifier
                                .height(200.dp)
                                .width(160.dp)
                        )
                    }

                    TextField(
                        label = { Text(text = "Username") },
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
                        singleLine = true
                    )

                    TextField(
                        label = { Text(text = "Password") },
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
                        singleLine = true
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp), Arrangement.End
                    ) {
                        Text(
                            text = "Forget Password ?",
                            color = BLUE,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp), Arrangement.Center
                    ) {
                        Image(
                            painterResource(R.drawable.button_submit_login),
                            "",
                            Modifier
                                .height(52.dp)
                                .width(133.dp)
                                .clickable(enabled = disable_button.value) {
                                    if (username.value.isEmpty()) Toast
                                        .makeText(
                                            applicationContext,
                                            "Username tidak boleh kosong",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                    else if (password.value.isEmpty()) Toast
                                        .makeText(
                                            applicationContext,
                                            "Password tidak boleh kosong",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                    else if (!username.value.isEmpty() && !password.value.isEmpty()) {
                                        disable_button.value = false
                                        FirebaseFirestore
                                            .getInstance()
                                            .collection("user")
                                            .get()
                                            .addOnSuccessListener { result ->
                                                var check = false;
                                                for (document in result) {
//                                    Log.v("Damai Futsal : ", "Datanya : ${document.id} => ${document.data}")
                                                    if (document.data
                                                            .get("username")
                                                            .toString()
                                                            .equals(username.value) && document.data
                                                            .get(
                                                                "password"
                                                            )
                                                            .toString()
                                                            .equals(password.value)
                                                    ) check = true
                                                }

                                                if (check) {
                                                    intent =
                                                        Intent(
                                                            applicationContext,
                                                            DashboardActivity::class.java
                                                        )
                                                    startActivity(intent)
                                                    finish()
                                                } else Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Silahkan Periksa Username dan Password Anda",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                            .addOnFailureListener { exception ->
                                                disable_button.value = true
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Silahkan Periksa Koneksi Internet Anda",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                    }
                                })
                    }

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp), Arrangement.Center
                    ) {
                        Text(
                            text = "Don't have an account?",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(text = "Sign Up", Modifier.clickable {
                            intent = Intent(applicationContext, RegisterActivity::class.java)
                            startActivity(intent)
                        }, color = BLUE, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}