package com.butter.sweet

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.butter.sweet.ui.theme.ButterSweetTheme
import com.butter.sweet.ui.theme.pink
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.util.*

class InputActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterSweetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    TampilanProfile()
                }
            }
        }
    }

    @Composable
    fun TampilanProfile() {
        val context = LocalContext.current
        var update by remember { mutableStateOf(true) }

        var deskripsi by remember { mutableStateOf("") }
        var harga by remember { mutableStateOf("") }
        var col by remember { mutableStateOf("") }
        var nama by remember { mutableStateOf("") }
        var baseString by remember { mutableStateOf("") }

        var bitmapImage by remember {
            mutableStateOf(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_user
                )?.toBitmap(100, 100)
            )
        }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            run {
                var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                bitmapImage = bitmap

                var s = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, s)
                var byte = s.toByteArray()
                baseString = android.util.Base64.encodeToString(byte, android.util.Base64.DEFAULT)
            }
        }

        Column(
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 14.dp,
                    end = 14.dp
                )
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                Image(
                    bitmap = bitmapImage!!.asImageBitmap(), contentDescription = "",
                    modifier = Modifier
                        .wrapContentSize()
                        .clickable {
                            if (update) launcher.launch("image/*")
                        },
                    contentScale = ContentScale.Crop
                )
            }

            Text(text = "Change profile picture")

            TextField(
                value = harga,
                onValueChange = {
                    harga = it
                },
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                enabled = update,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Harga") },
                placeholder = { Text(text = "Cth : 088852185653") }
            )

            TextField(
                value = nama,
                onValueChange = {
                    nama = it
                },
                Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                enabled = update,
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters),
                label = { Text(text = "Nama Makanan") },
                placeholder = { Text(text = "Cth : Reza Arab, Lukas Pratama") }
            )

            TextField(
                value = col,
                onValueChange = {
                    col = it
                },
                Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth(),
                enabled = update,
                label = { Text(text = "Menu") },
                placeholder = { Text(text = "Cth : Reza Arab, Lukas Pratama") }
            )

            TextField(
                value = deskripsi,
                onValueChange = {
                    deskripsi = it
                },
                Modifier
                    .padding(top = 14.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                enabled = update,
                label = { Text(text = "Deskripsi") },
                placeholder = { Text(text = "Cth : Jl. Nasution. RT.10, RW.20") }
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Column(
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(shape = RoundedCornerShape(14.dp), backgroundColor = pink) {
                        Text(
                            text = "SIMPAN",
                            Modifier
                                .padding(top = 8.dp, bottom = 8.dp, start = 14.dp, end = 14.dp)
                                .clickable {
                                    if (deskripsi.isEmpty()) {
                                        Toast
                                            .makeText(
                                                applicationContext,
                                                "deskripsi kosong",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else if (harga.isEmpty()) {
                                        Toast
                                            .makeText(
                                                applicationContext,
                                                "harga kosong",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else if (nama.isEmpty()) {
                                        Toast
                                            .makeText(
                                                applicationContext,
                                                "nama makanan kosong",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else {
                                        Toast
                                            .makeText(
                                                applicationContext,
                                                "Proses Sedang Berjalan",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()

                                        val data = HashMap<String, String>()
                                        data.put("image", baseString)
                                        data.put("deskripsi", deskripsi)
                                        data.put("harga", harga)
                                        data.put("nama", nama)

                                        FirebaseFirestore
                                            .getInstance()
                                            .collection("ButterSweet")
                                            .document("-")
                                            .collection(col)
                                            .add(data)
                                            .addOnSuccessListener {
                                                harga = ""
                                                nama = ""
                                                deskripsi = ""
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Data Berhasil Disimpan",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                            .addOnFailureListener {
                                                Log.v("InputActivity", it.message.toString())
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        it.message,
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                    }
                                })
                    }
                }
            }
        }
    }
}