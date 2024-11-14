package com.butter.sweet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.butter.sweet.ui.theme.ButterSweetTheme
import com.butter.sweet.ui.theme.clickhere
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterSweetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var password by remember { mutableStateOf("") }
                    var email by remember { mutableStateOf("") }
                    val context = LocalContext.current

                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Column(
                            modifier = Modifier
                                .width(300.dp)
                                .height(400.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Photo ThinkPad T480",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                                contentScale = ContentScale.FillBounds
                            )

                            Column(
                                modifier = Modifier
                                    .padding(
                                        start = 33.dp,
                                        end = 33.dp
                                    )
                                    .weight(1f)
                            ) {

                                TextField(
                                    value = email,
                                    onValueChange = {
                                        email = it
                                    },
                                    Modifier.padding(top = 24.dp),
                                    label = { Text(text = "Email") },
                                    placeholder = { Text(text = "Masukkan email") }
                                )

                                TextField(
                                    value = password,
                                    onValueChange = {
                                        password = it
                                    },
                                    Modifier.padding(top = 14.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    visualTransformation = PasswordVisualTransformation(),
                                    label = { Text(text = "Password") },
                                    placeholder = { Text(text = "Masukkan password") }
                                )

                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.icon_button_login),
                                        contentDescription = "Tombol Login",
                                        modifier = Modifier
                                            .width(186.dp)
                                            .height(38.dp)
                                            .clickable {
                                                if (email.isEmpty()) {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Email Tidak Boleh Kosong",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                } else if (password.isEmpty()) {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Password Tidak Boleh Kosong",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                } else {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Proses Sedang Berlangsung",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()

                                                    FirebaseFirestore
                                                        .getInstance()
                                                        .collection("ButterSweet")
                                                        .document("-")
                                                        .collection("user")
                                                        .get()
                                                        .addOnSuccessListener {
                                                            var check = false;
                                                            for (document in it.documents) {
                                                                if (document.data
                                                                        ?.get("email")
                                                                        .toString()
                                                                        .equals(email) && document.data
                                                                        ?.get(
                                                                            "password"
                                                                        )
                                                                        .toString()
                                                                        .equals(password)
                                                                ) {
                                                                    check = true
                                                                    startActivity(
                                                                        Intent(
                                                                            context,
                                                                            UtamaActivity::class.java
                                                                        )
                                                                            .putExtra(
                                                                                "image",
                                                                                document.data
                                                                                    ?.get("image")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "phone",
                                                                                document.data
                                                                                    ?.get("phone")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "email",
                                                                                document.data
                                                                                    ?.get("email")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "password",
                                                                                document.data
                                                                                    ?.get("password")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "nama",
                                                                                document.data
                                                                                    ?.get("nama")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "tanggal_lahir",
                                                                                document.data
                                                                                    ?.get("tanggal_lahir")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "jenis_kelamin",
                                                                                document.data
                                                                                    ?.get("jenis_kelamin")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "alamat",
                                                                                document.data
                                                                                    ?.get("alamat")
                                                                                    .toString()
                                                                            )
                                                                            .putExtra(
                                                                                "id",
                                                                                document.id
                                                                            )
                                                                    )
                                                                }
                                                            }

                                                            if (!check) Toast
                                                                .makeText(
                                                                    applicationContext,
                                                                    "Silahkan Periksa Email dan Password Anda",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }
                                                        .addOnFailureListener {
                                                            Toast
                                                                .makeText(
                                                                    context,
                                                                    "Silahkan Periksa Koneksi Internet Anda",
                                                                    Toast.LENGTH_LONG
                                                                )
                                                                .show()
                                                        }
                                                }
                                            }
                                    )

                                    Text(
                                        text = "Sign Up Here",
                                        Modifier
                                            .padding(top = 14.dp)
                                            .clickable {
                                                context.startActivity(
                                                    Intent(
                                                        context,
                                                        DaftarActivity::class.java
                                                    )
                                                )
                                            },
                                        color = clickhere
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}