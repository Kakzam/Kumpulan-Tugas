package com.kost.`in`.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.kost.`in`.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editTextEmail = binding.editTextEmail
        val editTextPassword = binding.editTextPassword
        val editTextFullName = binding.editTextFullName
        val editTextEmailRegister = binding.editTextEmailRegister
        val editTextPasswordRegister = binding.editTextPasswordRegister
        val loginForm = binding.loginForm
        val registerForm = binding.registerForm
        val buttonToggle = binding.buttonToggle
        val buttonLogin = binding.buttonLogin
        val buttonRegister = binding.buttonRegister

        buttonToggle.setOnClickListener {
            if (loginForm.visibility == View.VISIBLE) {
                loginForm.visibility = View.GONE
                registerForm.visibility = View.VISIBLE
                buttonToggle.setText("Login")
            } else {
                loginForm.visibility = View.VISIBLE
                registerForm.visibility = View.GONE
                buttonToggle.setText("Register")
            }
        }

        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isEmpty()){
                Toast
                    .makeText(
                        applicationContext,
                        "Email Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else if (password.isEmpty()){
                Toast
                    .makeText(
                        applicationContext,
                        "Password Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else {
                FirebaseFirestore.getInstance()
                    .collection("KostIn")
                    .document("user")
                    .collection("-")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        var isUserFound = false

                        for (documentSnapshot in querySnapshot) {
                            val data = documentSnapshot.data

                            val storedEmail = data["email"] as String
                            val storedPassword = data["password"] as String

                            if (email == storedEmail && password == storedPassword) {
                                isUserFound = true
                                break
                            }
                        }

                        if (isUserFound) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "Email atau password salah", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Tangani kesalahan jika terjadi
                        Toast.makeText(
                            applicationContext,
                            "Gagal mengambil data: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

        buttonRegister.setOnClickListener {
            val fullName = editTextFullName.text.toString()
            val email = editTextEmailRegister.text.toString()
            val password = editTextPasswordRegister.text.toString()

            if (fullName.isEmpty()){
                Toast
                    .makeText(
                        applicationContext,
                        "Nama Lengkap Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else if (email.isEmpty()){
                Toast
                    .makeText(
                        applicationContext,
                        "Email Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else if (password.isEmpty()){
                Toast
                    .makeText(
                        applicationContext,
                        "Password Tidak Boleh Kosong",
                        Toast.LENGTH_LONG
                    )
                    .show()
            } else {
                var data = HashMap<String, String>()
                data.put("fullname", fullName)
                data.put("email", email)
                data.put("password", password)

                FirebaseFirestore
                    .getInstance()
                    .collection("KostIn")
                    .document("user")
                    .collection("-")
                    .add(data)
                    .addOnSuccessListener {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                MainActivity::class.java
                            )
                        )
                    }
                    .addOnFailureListener {
                        Toast
                            .makeText(
                                applicationContext,
                                "Silahkan Periksa Kembali Koneksi Internet Anda",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }
            }
        }
    }
}