package com.example.myapp

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.myapp.ui.theme.MyAppTheme
import java.io.ByteArrayOutputStream
import java.util.Calendar

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
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
                        var textAreaValue by remember { mutableStateOf("") }
                        var textFieldValue by remember { mutableStateOf("") }
                        var selectedCategory by remember { mutableStateOf("Data Diri") }
                        var selectedLaporan by remember { mutableStateOf("Grafik") }
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

                        val mContext = this@MainActivity
                        val calendar: Calendar = Calendar.getInstance()
                        var year = calendar.get(Calendar.YEAR)
                        var month = calendar.get(Calendar.MONTH)
                        var day = calendar.get(Calendar.DAY_OF_MONTH)
                        val date = remember { mutableStateOf("") }

                        val dateS = DatePickerDialog(
                            mContext,
                            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                                date.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                            },
                            year,
                            month,
                            day
                        )

                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(start = 10.dp, top = it.calculateTopPadding(), end = 10.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                "Unggah Foto Bukti Pengaduan",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.align(Alignment.Start)
                            )

                            Spacer(modifier = Modifier.height(25.dp))

                            Column(Modifier.fillMaxWidth()) {
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
                                    onClick = { fileLauncher.launch("image/*") },
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth(10f),
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

                                TextField(label = { Text(text = "Tanggal Laporan") },
                                    value = date.value,
                                    onValueChange = { value -> date.value = value },
                                    enabled = false,
                                    modifier = Modifier
                                        .absolutePadding(
                                            top = 8.dp,
                                            bottom = 10.dp
                                        )
                                        .fillMaxWidth()
                                        .background(Color.White)
                                        .clickable {
                                            dateS.show()
                                        },
                                    singleLine = true)
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Column(
                                Modifier
                                    .fillMaxWidth()) {
                                Text("Kategori Laporan", style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                    RadioButton(
                                        selected = selectedLaporan == "Grafik",
                                        onClick = { selectedLaporan = "Grafik" }
                                    )
                                    Text("Grafik", style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.width(16.dp))

                                    RadioButton(
                                        selected = selectedLaporan == "Pengajuan",
                                        onClick = { selectedLaporan = "Pengajuan" }
                                    )
                                    Text("Pengajuan", style = MaterialTheme.typography.bodyLarge)
                                    Spacer(modifier = Modifier.width(16.dp))

                                    RadioButton(
                                        selected = selectedLaporan == "Tidak Lanjut",
                                        onClick = { selectedLaporan = "Tidak Lanjut" }
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
                                onClick = {
                                        val isValid = validateData(
                                            selectedImageBitmap,
                                            selectedCategory,
                                            textFieldValue,
                                            date.value,
                                            selectedLaporan,
                                            textAreaValue
                                        )

                                        if (isValid) {
                                            showToast("Valid Semua")
                                            Log.v("TAG", "Tahap 1")
                                            postData(
                                                selectedImageBitmap,
                                                selectedCategory,
                                                textFieldValue,
                                                date.value,
                                                selectedLaporan,
                                                textAreaValue
                                            )
                                        }
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(10f)
                            ) {
                                Text(text = "Submit Laporan")
                            }

                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                )
            }
        }
    }

    private fun validateData(
        imageBitmap: ImageBitmap?,
        category: String,
        email: String,
        date: String,
        reportCategory: String,
        description: String
    ): Boolean {
        if (imageBitmap == null) {
            showToast("Silahkan pilih gambar terlebih dahulu")
            return false
        }

        if (category.isBlank()) {
            showToast("Silahkan pilih kategori pelapor")
            return false
        }

        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Silahkan Masukkan Alamat Email terlebih dahulu")
            return false
        }

        if (date.isBlank()) {
            showToast("Silahkan pilih tanggal terlebih dahulu")
            return false
        }

        if (reportCategory.isBlank()) {
            showToast("Silahkan pilih kategori laporan terlebih dahulu")
            return false
        }

        if (description.isBlank()) {
            showToast("Silahkan isi keterangan lengkap terlebih dahulu")
            return false
        }

        return true
    }

    private fun postData(
        imageBitmap: ImageBitmap?,
        category: String,
        email: String,
        date: String,
        reportCategory: String,
        description: String
    ) {
        val url = "http://192.168.43.217/MyApp/index.php"

        val params = HashMap<String, String>()
        params["category"] = category
        params["email"] = email
        params["date"] = date
        params["reportCategory"] = reportCategory
        params["description"] = description

        val files = HashMap<String, DataPart>()
        if (imageBitmap != null) {
            val byteArray = convertBitmapToByteArray(imageBitmap.asAndroidBitmap())
            files["imageBitmap"] = DataPart("image.jpg", byteArray, "image/jpeg")
        }

        val multipartRequest = MultipartRequest(
            Request.Method.POST,
            url,
            params,
            files,
            Response.Listener { response ->
                val responseString = String(response.data)
                Log.v("PESAN", responseString)
            },
            Response.ErrorListener { error ->
            }
        )

        Volley.newRequestQueue(this@MainActivity).add(multipartRequest)
    }

    private fun convertBitmapToByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}