package com.cash.crab.tampilan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.cash.crab.R
import com.cash.crab.ui.theme.CashCrabTheme
import com.cash.crab.ui.theme.PRIMER
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashCrabTheme {
                val loading = remember { mutableStateOf(false) }
                val username = remember { mutableStateOf("") }
                val password = remember { mutableStateOf("") }

                Image(
                    painter = painterResource(R.drawable.background), contentDescription = "",
                    Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                        .background(color = PRIMER)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (loading.value) CircularProgressIndicator()
                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_kata),
                                contentDescription = ""
                            )
                        }

                        TextField(
                            label = { Text(text = "Username") },
                            value = username.value,
                            onValueChange = { value ->
                                username.value = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
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
                            onValueChange = { value ->
                                password.value = value
                            },
                            modifier = Modifier
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                )
                                .fillMaxWidth()
                                .background(Color.White),
                            singleLine = true,
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
                        )

                        Button(
                            onClick = {
                                if (username.value.isEmpty()) Toast.makeText(
                                    applicationContext,
                                    "Username Tidak Boleh Kosong",
                                    Toast.LENGTH_LONG
                                ).show()
                                else if (password.value.isEmpty()) Toast.makeText(
                                    applicationContext,
                                    "Password Tidak Boleh Kosong",
                                    Toast.LENGTH_LONG
                                ).show()
                                else if (!username.value.isEmpty() && !password.value.isEmpty()) {
                                    loading.value = true
                                    FirebaseFirestore.getInstance().collection("pengguna").get()
                                        .addOnSuccessListener { result ->
                                            loading.value = false
                                            var check = false
                                            var id = ""

                                            for (document in result) {
                                                if (document.data
                                                        .get("username")
                                                        .toString()
                                                        .equals(username.value) && document.data
                                                        .get(
                                                            "password"
                                                        )
                                                        .toString()
                                                        .equals(password.value)
                                                ) {
                                                    check = true
                                                    id = document.id
                                                }
                                            }

                                            if (check) {
                                                intent =
                                                    Intent(
                                                        applicationContext,
                                                        DashboardActivity::class.java
                                                    )
                                                intent.putExtra("id", id)
                                                intent.putExtra("username", username.value)
                                                startActivity(intent)
                                                finish()
                                            } else Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Silahkan Periksa Username dan Password Anda",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }.addOnFailureListener {
                                        loading.value = false
                                        Toast.makeText(
                                            applicationContext,
                                            "Silahkan Periksa Kembali Koneksi Internet Anda",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }
                            },
                            Modifier
                                .fillMaxWidth(0.4f)
                                .absolutePadding(
                                    top = 8.dp,
                                    bottom = 0.dp,
                                    left = 16.dp,
                                    right = 16.dp
                                ),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                        ) {
                            Text(text = "MASUK")
                        }
                    }
                }
            }
        }
    }
}