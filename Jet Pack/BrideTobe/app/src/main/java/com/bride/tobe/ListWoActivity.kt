package com.bride.tobe

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.bride.tobe.ui.theme.BrideTobeTheme
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class ListWoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var dataList by remember { mutableStateOf<List<DocumentSnapshot>>(emptyList()) }
            val receivedData = intent.getStringExtra("data")
            if (receivedData != null) {
                FirebaseFirestore.getInstance()
                    .collection("BrideTobe")
                    .document(receivedData)
                    .collection("-")
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        dataList = querySnapshot.documents
                    }
                    .addOnFailureListener { exception ->
                        ShowToast("Terjadi kesalahan: ${exception.message}")
                    }
            }

            BrideTobeTheme {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp))
                {
                    items(dataList) {
                        item: DocumentSnapshot ->
                        Card(modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp), colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), elevation = CardDefaults. cardElevation(defaultElevation = 10.dp, pressedElevation = 20.dp)) {
                            //buat Row
                            Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)) {

                                val decodedBytes = Base64.decode(item.getString("gambar")!!, Base64.DEFAULT)
                                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

                                Image(
                                    painter = rememberImagePainter(bitmap),
                                    contentDescription = "Foto Profile Instagram",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                )

                                Column(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)) {
                                    Text(text = item.getString("nama_lengkap") ?: "")

                                    Button(
                                        onClick = {
                                                  openInstagramProfile(item.getString("link_ig")!!)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Open Instagram",
                                            color = Color.White
                                        )
                                    }

                                    Button(
                                        onClick = {
                                                  copyLink(item.getString("link_ig")!!, this@ListWoActivity, 0)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Copy Instagram",
                                            color = Color.White
                                        )
                                    }

                                    Button(
                                        onClick = {
                                                  openWhatsApp(item.getString("telepon")!!,this@ListWoActivity)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Open Whatsapp",
                                            color = Color.White
                                        )
                                    }

                                    Button(
                                        onClick = {
                                            copyLink(item.getString("telepon")!!, this@ListWoActivity, 0)
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = "Copy Whatsapp",
                                            color = Color.White
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

    fun openInstagramProfile(username: String) {
        val uri = Uri.parse("https://www.instagram.com/$username")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.setPackage("com.instagram.android")

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            intent.setPackage(null)
            startActivity(intent)
        }
    }


    fun openWhatsApp(number: String, context: Context) {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_VIEW)

        try {
            val url = "https://api.whatsapp.com/send?phone=$number"
            intent.setPackage("com.whatsapp")
            intent.data = Uri.parse(url)
            if (intent.resolveActivity(packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Aplikasi WhatsApp tidak terpasang", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


    fun copyLink(number: String, context: Context, pilih: Int) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (pilih > 0) {
            val clipData = ClipData.newPlainText("WhatsApp Number", "https://api.whatsapp.com/send?phone=" + number)
            clipboardManager.setPrimaryClip(clipData)
            ShowToast("Nomor WhatsApp berhasil disalin")
        } else {
            val clipData = ClipData.newPlainText("Instagram Id", "https://www.instagram.com/" +number)
            clipboardManager.setPrimaryClip(clipData)
            ShowToast("Instagram berhasil disalin")
        }
    }

    fun ShowToast(message: String) {
        Toast.makeText(this@ListWoActivity, message, Toast.LENGTH_SHORT).show()
    }
}