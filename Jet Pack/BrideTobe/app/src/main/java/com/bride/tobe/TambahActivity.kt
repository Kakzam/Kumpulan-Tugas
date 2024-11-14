package com.bride.tobe

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.bride.tobe.ui.theme.BrideTobeTheme
import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException

class TambahActivity : ComponentActivity() {

    private val REQUEST_IMAGE_PICK = 1

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var fullName by remember { mutableStateOf("") }
            var selectedOption by remember { mutableStateOf("Decoration") }
            var igLink by remember { mutableStateOf("") }
            var phoneNumber by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(true) }

            var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
            var selectedImageBase64 by remember { mutableStateOf("") }

            val getContent = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    val uri = intent?.data
                    selectedImageUri = uri
                    selectedImageBase64 = "" // Reset the base64 value

                    uri?.let {
                        lifecycleScope.launch {
                            selectedImageBase64 = convertImageToBase64(uri, this@TambahActivity.contentResolver)
                        }
                    }
                }
            }

            BrideTobeTheme {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Nama Lengkap
                    TextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Nama Lengkap") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Decoration") },
                            onClick = {
                                selectedOption = "Decoration"
                                ShowToast("Decoration") }
                        )

                        DropdownMenuItem(
                            text = { Text("Make UP") },
                            onClick = {
                                selectedOption = "Make_UP"
                                ShowToast("Make UP") }
                        )

                        DropdownMenuItem(
                            text = { Text("Invitation Card") },
                            onClick = {
                                selectedOption = "Invitation_Card"
                                ShowToast("Invitation Card") }
                        )

                        DropdownMenuItem(
                            text = { Text("Music") },
                            onClick = {
                                selectedOption = "Music"
                                ShowToast("Music") }
                        )

                        DropdownMenuItem(
                            text = { Text("Wedding Organizer") },
                            onClick = {
                                selectedOption = "Wedding_Organizer"
                                ShowToast("Wedding Organizer") }
                        )

                        DropdownMenuItem(
                            text = { Text("Wedding Attribute") },
                            onClick = {
                                selectedOption = "Wedding_Attribute"
                                ShowToast("Wedding Attribute") }
                        )

                        DropdownMenuItem(
                            text = { Text("Souvenir") },
                            onClick = {
                                selectedOption = "Souvenir"
                                ShowToast("Souvenir") }
                        )

                        DropdownMenuItem(
                            text = { Text("Henna") },
                            onClick = {
                                selectedOption = "Henna"
                                ShowToast("Henna") }
                        )
                    }

                    TextField(
                        value = igLink,
                        onValueChange = { igLink = it },
                        label = { Text("Instagram (cth : _mua_oi)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )

                    TextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text("Nomor Handphone (+62)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    Button(
                        onClick = { getContent.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Pilih Gambar")
                    }

                    selectedImageUri?.let { uri ->
                        Image(
                            painter = rememberImagePainter(uri),
                            contentDescription = "Selected Image",
                            modifier = Modifier
                                .size(200.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                        )
                    }

                    Button(
                        onClick = {
                                  if (fullName.isEmpty()){
                                      ShowToast("Nama Lengkap Tidak Boleh Kosong")
                                  } else if (igLink.isEmpty()){
                                      ShowToast("Link Ig Tidak Boleh Kosong")
                                  } else if (phoneNumber.isEmpty()){
                                      ShowToast("No Handphone Tidak Boleh Kosong")
                                  } else if (selectedImageBase64.isEmpty()){
                                      ShowToast("Silahkan Pilih Gambar")
                                  } else {
                                      ShowToast("Proses Sedang Berjalan")

                                      Log.v("Data ", "1")
                                      var data = HashMap<String, String>()
                                      data.put("nama_lengkap", fullName)
                                      data.put("link_ig", igLink)
                                      data.put("telepon", "+62" + phoneNumber)
                                      data.put("gambar", selectedImageBase64)

                                      Log.v("Data ", "2")
                                      FirebaseFirestore
                                          .getInstance()
                                          .collection("BrideTobe")
                                          .document(selectedOption)
                                          .collection("-")
                                          .add(data)
                                          .addOnSuccessListener {
                                              expanded = true
                                              fullName = ""
                                              igLink = ""
                                              phoneNumber = ""
                                              Log.v("Data ", "3")
                                              ShowToast("Data Berhasil Disimpan")
                                          }
                                          .addOnFailureListener {
                                              expanded = true
                                              Log.v("Data ", "4 : " + it.message)
                                              ShowToast("Silahkan Periksa Koneksi Internet Anda")
                                          }
                                  }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Submit")
                    }
                }
            }
        }
    }

    fun ShowToast(message: String) {
        Toast.makeText(this@TambahActivity, message, Toast.LENGTH_SHORT).show()
    }

    suspend fun convertImageToBase64(uri: Uri, contentResolver: ContentResolver): String {
        return withContext(Dispatchers.IO) {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Mengurangi kualitas gambar menjadi 50%
                val outputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)

                // Mengonversi gambar menjadi base64
                val imageBytes = outputStream.toByteArray()
                Base64.encodeToString(imageBytes, Base64.DEFAULT)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }
        }
    }
}