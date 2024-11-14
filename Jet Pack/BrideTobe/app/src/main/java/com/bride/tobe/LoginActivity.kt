package com.bride.tobe

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bride.tobe.ui.theme.BrideTobeTheme
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (email, setEmail) = remember { mutableStateOf("") }
            val (password, setPassword) = remember { mutableStateOf("") }
            val (nama, setNamaLengkap) = remember { mutableStateOf("") }
            val (isLoginVisible, setIsLoginVisible) = remember { mutableStateOf(false) }
            var tam = 0

            BrideTobeTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 16.dp, bottom= 16.dp, end = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(0.dp, Color.Gray, CircleShape)
                            .clickable {
                                if (tam > 10) {
                                    val intent = Intent(this@LoginActivity, TambahActivity::class.java)
                                    this@LoginActivity.startActivity(intent)
                                } else {
                                    tam++
                                }
                            }
                    )

                    Spacer(modifier = Modifier.padding(top = 16.dp))

                    if (isLoginVisible){
                        TextField(
                            value = nama,
                            onValueChange = setNamaLengkap,
                            label = { Text("Nama Lengkap") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )
                    }

                    TextField(
                        value = email,
                        onValueChange = setEmail,
                        label = { Text("Username/Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    TextField(
                        value = password,
                        onValueChange = setPassword,
                        label = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Button(
                        onClick = {
                            if (isLoginVisible) {
                                if (nama.isEmpty()) {
                                    ShowToast("Nama Lengkap Tidak Boleh Kosong")
                                } else if (email.isEmpty()) {
                                    ShowToast("Email Tidak Boleh Kosong")
                                } else if (password.isEmpty()) {
                                    ShowToast("Password Tidak Boleh Kosong")
                                } else {
                                    ShowToast("Sedang Melakukan Registrasi")

                                    var data = HashMap<String, String>()
                                    data.put("nama_lengkap", nama)
                                    data.put("username", email)
                                    data.put("password", password)

                                    FirebaseFirestore
                                        .getInstance()
                                        .collection("BrideTobe")
                                        .document("user")
                                        .collection("-")
                                        .add(data)
                                        .addOnSuccessListener {
                                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                            this@LoginActivity.startActivity(intent)
                                        }
                                        .addOnFailureListener {
                                            ShowToast("Silahkan Periksa Koneksi Internet Anda")
                                        }
                                }
                            } else {
                                if (email.isEmpty()) {
                                    ShowToast("Email Tidak Boleh Kosong")
                                } else if (password.isEmpty()) {
                                    ShowToast("Password Tidak Boleh Kosong")
                                } else {
                                    ShowToast("Sedang Melakukan Validasi")
                                    FirebaseFirestore.getInstance()
                                        .collection("BrideTobe")
                                        .document("user")
                                        .collection("-")
                                        .get()
                                        .addOnSuccessListener { querySnapshot ->
                                            for (document in querySnapshot) {
                                                val userData = document.data
                                                val username = userData["username"] as? String
                                                val password = userData["password"] as? String

                                                if (username == email && password == password) {
                                                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                                                    this@LoginActivity.startActivity(intent)
                                                    return@addOnSuccessListener
                                                }
                                            }

                                            ShowToast("Username atau password salah")
                                        }
                                        .addOnFailureListener { exception ->
                                            ShowToast("Terjadi kesalahan: ${exception.message}")
                                        }
                                }
                            }
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(Color.Gray)
                    ) {
                        Text(if (isLoginVisible) "Register" else "Log In")
                    }

                    val annotatedText = buildAnnotatedString {
                        append(if (isLoginVisible) "Sudah punya akun?  " else "Belum punya akun?  ")
                        pushStringAnnotation(tag = "TOGGLE_SCREEN", annotation = "Toggle Screen")
                        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                            append(if (isLoginVisible) "Log In" else "Register")
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedText,
                        onClick = { offset ->
                            annotatedText.getStringAnnotations(tag = "TOGGLE_SCREEN", start = offset, end = offset)
                                .firstOrNull()?.let { annotation ->
                                    if (annotation.item == "Toggle Screen") {
                                        setIsLoginVisible(!isLoginVisible)
                                    }
                                }
                        }
                    )
                }
            }
        }
    }

    fun ShowToast(message: String) {
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}