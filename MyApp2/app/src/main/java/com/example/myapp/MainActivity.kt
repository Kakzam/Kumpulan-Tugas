package com.example.myapp

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapp.ui.theme.MyAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                MyAppContent()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppContent() {

    // State untuk menyimpan nilai dari textbox, text area, dan file yang dipilih
    var textAreaValue by remember { mutableStateOf("") }
    var textFieldValue by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Data Diri") }
    var selectedImageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val fileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { uri ->
            val contentResolver = this@MainActivity.contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.use {
                val source = ImageDecoder.createSource(contentResolver, uri)
                val bitmap = ImageDecoder.decodeBitmap(source)
                selectedImageBitmap = bitmap.asImageBitmap()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /* Aksi saat tombol kembali ditekan */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                        Text(text = "Form Pengaduan", style = MaterialTheme.typography.titleMedium)
                    }
                },
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 10.dp, top = it.calculateTopPadding(), end = 10.dp)
            ) {
                Text(
                    "Unggah Foto Bukti Pengaduan",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Unggah Foto Bukti Pengaduan", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(25.dp))

                    IconButton(
                        onClick = { fileLauncher.launch("image/*") },
                        modifier = Modifier
                            .size(55.dp)
                            .background(Color.Gray, shape = CircleShape)
                    ) {
                        if (selectedImageBitmap != null) {
                            Image(
                                bitmap = selectedImageBitmap!!,
                                contentDescription = "Gambar yang dipilih",
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Pilih File",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = { /* Aksi saat tombol "Submit" ditekan */ },
                        modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(10f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5454))
                    ) {
                        Text(text = "Unggah Foto")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Kategori Pelapor", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedCategory == "Data Diri",
                            onClick = { selectedCategory = "Data Diri" }
                        )
                        Text("Data Diri", style = MaterialTheme.typography.bodyLarge)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedCategory == "Anonymouse",
                            onClick = { selectedCategory = "Anonymouse" }
                        )
                        Text("Anonymouse", style = MaterialTheme.typography.bodyLarge)
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Isi Laporan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = { textFieldValue = it },
                        label = { Text("Tanggal Pelaporan") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Kategori Laporan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedCategory == "Grafik",
                            onClick = { selectedCategory = "Grafik" }
                        )
                        Text("Grafik", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = selectedCategory == "Pengajuan",
                            onClick = { selectedCategory = "Pengajuan" }
                        )
                        Text("Pengajuan", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.width(16.dp))

                        RadioButton(
                            selected = selectedCategory == "Tidak Lanjut",
                            onClick = { selectedCategory = "Tidak Lanjut" }
                        )
                        Text("Tidak Lanjut", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(Modifier.fillMaxWidth()) {
                    Text("Keterangan", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = textAreaValue,
                        onValueChange = { textAreaValue = it },
                        label = { Text("Keterangan Lengkap") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = { /* Aksi saat tombol "Submit Laporan" ditekan */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth(10f)
                ) {
                    Text(text = "Submit Laporan")
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    )
}