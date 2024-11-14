package com.alika.thrift.ui.desain

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alika.thrift.R
import com.alika.thrift.ui.theme.ALIKATHRIFTTheme
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream

class BajuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALIKATHRIFTTheme {
                var deskripsi by remember { mutableStateOf("") }
                var harga by remember { mutableStateOf("") }
                var list by remember { mutableStateOf("") }
                var baseString by remember { mutableStateOf("") }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    run {
                        var gambar = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        var output = ByteArrayOutputStream()
                        gambar.compress(Bitmap.CompressFormat.PNG, 50, output)
                        var byte = output.toByteArray()
                        baseString = Base64.encodeToString(byte, Base64.DEFAULT)
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(
                            top = 8.dp,
                            start = 14.dp,
                            end = 14.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(modifier = Modifier
                        .padding(8.dp)
                        .size(100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.icon_app), contentDescription = "",
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentScale = ContentScale.Crop
                        )
                    }

                    TextField(
                        value = harga,
                        onValueChange = {
                            harga = it
                        },
                        Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = "Harga") },
                        placeholder = { Text(text = "Cth : 088852185653") }
                    )

                    TextField(
                        value = deskripsi,
                        onValueChange = {
                            deskripsi = it
                        },
                        Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Deskripsi") },
                        placeholder = { Text(text = "Cth : 088852185653") }
                    )

                    TextField(
                        value = list,
                        onValueChange = {
                            list = it
                        },
                        Modifier
                            .padding(top = 24.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Menu") },
                        placeholder = { Text(text = "Cth : Reza Arab, Lukas Pratama") }
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
                            Card(shape = RoundedCornerShape(14.dp)) {
                                Text(
                                    text = "SIMPAN",
                                    Modifier
                                        .padding(
                                            top = 8.dp,
                                            bottom = 8.dp,
                                            start = 14.dp,
                                            end = 14.dp
                                        )
                                        .clickable {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Lagi Upload",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()

                                            val data = HashMap<String, String>()
                                            data.put("gambar", baseString)
                                            data.put("harga", harga)
                                            data.put("deskripsi", deskripsi)

                                            FirebaseFirestore
                                                .getInstance()
                                                .collection("barang")
                                                .document("baju")
                                                .collection(list)
                                                .add(data)
                                                .addOnSuccessListener {
                                                    harga = ""
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Data Berhasil Upload",
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
                                        })
                            }
                        }
                    }
                }
            }
        }
    }
}