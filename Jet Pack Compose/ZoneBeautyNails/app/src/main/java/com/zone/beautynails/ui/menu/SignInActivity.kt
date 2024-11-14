package com.zone.beautynails.ui.menu

import android.content.Context
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
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.zone.beautynails.R
import com.zone.beautynails.ui.theme.PRIMARY
import com.zone.beautynails.ui.theme.TEXT_1
import com.zone.beautynails.ui.theme.ZoneBeautyNailsTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
                val username = remember { mutableStateOf("admin") }
                val password = remember { mutableStateOf("admin") }
                val check = remember { mutableStateOf(false) }

                Column(
                    Modifier
                        .fillMaxSize()
                        .background(PRIMARY)) {

                    Image(painterResource(R.drawable.icon_depan), "", Modifier.fillMaxWidth())

                    Text(text = "Sign in to your account",
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 45.dp), color = TEXT_1, fontSize = 26.sp, textAlign = TextAlign.Center)

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

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = check.value, onCheckedChange = { check.value = it })
                        Text(text = "Remember me", color = TEXT_1, fontSize = 14.sp, textAlign = TextAlign.Center)
                    }

                    Image(painterResource(R.drawable.button_sigin), "",
                        Modifier
                            .width(380.dp)
                            .height(88.dp)
                            .padding(start = 28.dp, end = 28.dp, bottom = 16.dp, top = 12.dp)
                            .clickable {
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
                                    FirebaseFirestore
                                        .getInstance()
                                        .collection("akun")
                                        .get()
                                        .addOnSuccessListener { result ->
                                            var check = false;
                                            var type = ""
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
                                                    type = document.data.get("type").toString()
                                                    check = true
                                                }
                                            }

                                            if (check) {
                                                val sharedPref = getSharedPreferences("zone_beauty_nails", Context.MODE_PRIVATE)
                                                val editor = sharedPref.edit()
                                                editor.putString("type", type)
                                                editor.apply()

                                                intent =
                                                    Intent(
                                                        applicationContext,
                                                        HomeActivity::class.java
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
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Silahkan Periksa Koneksi Internet Anda",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        }
                                }
                            }, contentScale = ContentScale.Fit)

                    Text(text = "Forget the password?",
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp), color = TEXT_1, fontSize = 14.sp, textAlign = TextAlign.Center)

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Don't have an account?", color = TEXT_1, fontSize = 14.sp, textAlign = TextAlign.Center)

                        Text(text = "Sign up",
                            Modifier
                                .clickable {
                                    intent = Intent(applicationContext, SignUpActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }, color = TEXT_1, fontSize = 14.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}