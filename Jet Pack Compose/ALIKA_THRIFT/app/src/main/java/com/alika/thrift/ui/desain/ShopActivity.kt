package com.alika.thrift.ui.desain

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alika.thrift.R
import com.alika.thrift.ui.desain.ui.theme.ALIKATHRIFTTheme
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat

class ShopActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ALIKATHRIFTTheme {
                var kaos by remember { mutableStateOf(true) }
                var blouse by remember { mutableStateOf(false) }
                var kemeja by remember { mutableStateOf(false) }
                var celana by remember { mutableStateOf(false) }

                Scaffold(
                    bottomBar = {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp, start = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(Modifier.weight(1f)) {
                                Image(
                                    painter = painterResource(id = R.drawable.bar_chat),
                                    contentDescription = "",
                                    Modifier.size(38.dp)
                                        .clickable {
                                            try {
                                                val ph = "6285384809938"

                                                startActivity(
                                                    Intent(
                                                        Intent.ACTION_VIEW,
                                                        Uri.parse("https://api.whatsapp.com/send?phone=${ph}&text=")
                                                    )
                                                )
                                            } catch (e: Exception) {
                                                Toast
                                                    .makeText(
                                                        applicationContext,
                                                        "Whatsapp belum terinstall",
                                                        Toast.LENGTH_LONG
                                                    )
                                                    .show()
                                            }
                                        }
                                )
                            }

                            Row(
                                Modifier
                                    .weight(1f)
                                    .clickable {
                                        intent =
                                            Intent(applicationContext, ShopActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.bar_home),
                                    contentDescription = "",
                                    Modifier.size(38.dp)
                                )
                            }

                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
//                                Image(
//                                    painter = painterResource(id = R.drawable.bar_shop),
//                                    contentDescription = "",
//                                    Modifier.size(38.dp)
//                                )
                            }
                        }
                    }
                ) {
                    Column(
                        Modifier
                            .padding(bottom = it.calculateBottomPadding())
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.title_shop),
                            contentDescription = "",
                            Modifier.fillMaxWidth()
                        )

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp, start = 4.dp, end = 4.dp, top = 12.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                Text(text = "Kaos", Modifier.clickable {
                                    kaos = true
                                    blouse = false
                                    kemeja = false
                                    celana = false
                                }, fontSize = 16.sp)
                            }

                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                Text(text = "Blouse", Modifier.clickable {
                                    kaos = false
                                    blouse = true
                                    kemeja = false
                                    celana = false
                                }, fontSize = 16.sp)
                            }

                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                Text(text = "Kemeja", Modifier.clickable {
                                    kaos = false
                                    blouse = false
                                    kemeja = true
                                    celana = false
                                }, fontSize = 16.sp)
                            }

                            Row(Modifier.weight(1f), horizontalArrangement = Arrangement.Center) {
                                Text(text = "Celana", Modifier.clickable {
                                    kaos = false
                                    blouse = false
                                    kemeja = false
                                    celana = true
                                }, fontSize = 16.sp)
                            }
                        }

                        if (kaos) menu("kaos")

                        if (blouse) menu("blouse")

                        if (kemeja) menu("kemeja")

                        if (celana) menu("celana")
                    }
                }
            }
        }
    }

    @Composable
    fun menu(menu: String) {
        var kaos by remember {
            mutableStateOf(true)
        }

        var dataKaos = remember {
            mutableListOf<DocumentSnapshot>()
        }

        var dataKaos2 = remember {
            mutableListOf<DocumentSnapshot>()
        }

        if (kaos) {
            FirebaseFirestore
                .getInstance()
                .collection("barang")
                .document("baju")
                .collection(menu)
                .get()
                .addOnSuccessListener {
                    dataKaos.addAll(it.documents)

                    var position = 0
                    if ((dataKaos.size%2) == 0){
                        for (data in dataKaos) {
                            Log.v("ShopActivity:", "${position} : ${data.get("gambar").toString()}")
                            if (position > (dataKaos.size / 2)) {
                                dataKaos2.add(data)
                            }
                            position++
                        }
                    } else {
                        for (data in dataKaos) {
                            Log.v("ShopActivity:", "${position} : ${data.get("gambar").toString()}")
                            if (position > ((dataKaos.size+1) / 2)) {
                                dataKaos2.add(data)
                            }
                            position++
                        }
                    }

                    kaos = false
                }
                .addOnFailureListener {
                    Toast
                        .makeText(
                            applicationContext,
                            "Koneksi internetmu sepertinya mati",
                            Toast.LENGTH_LONG
                        )
                        .show()
                }
        } else {
            Column(Modifier.fillMaxWidth()) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    itemsIndexed(dataKaos) { index: Int, item: DocumentSnapshot ->
                        run {
                            if (index < (dataKaos.size-dataKaos2.size)) {
                                Row(Modifier.fillMaxWidth()) {
                                    Column(
                                        Modifier
                                            .weight(1f)
                                            .clickable {
                                                setIntent(
                                                    item.id,
                                                    menu
                                                )
                                            }) {
                                        Image(
                                            bitmap = getGambar(item.get("gambar").toString()),
                                            contentDescription = "",
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(14.dp)
                                        )
                                        Text(
                                            text = getCurrency(item.get("harga").toString()),
                                            Modifier.padding(start = 14.dp)
                                        )
                                    }

                                    if (index < dataKaos2.size) {
                                        Column(
                                            Modifier
                                                .weight(1f)
                                                .clickable {
                                                    setIntent(
                                                        dataKaos2
                                                            .get(index).id,
                                                        menu
                                                    )
                                                }) {
                                            Image(
                                                bitmap = getGambar(
                                                    dataKaos2.get(index).get("gambar").toString()
                                                ),
                                                contentDescription = "",
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(14.dp)
                                            )
                                            Text(
                                                text = getCurrency(dataKaos2.get(index).get("harga").toString()),
                                                Modifier.padding(start = 14.dp)
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
    }

    fun getGambar(gambar: String): ImageBitmap {
        val base = Base64.decode(gambar, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(
            base,
            0,
            base.size
        ).asImageBitmap()
    }

    fun getCurrency(nominal: String): String {
        val format = NumberFormat.getInstance()
        format.maximumFractionDigits = 0
        return format.format(nominal.toInt()).replace(",", ".")
    }

    fun setIntent(id: String, menu: String) {
        try {
            intent = Intent(applicationContext, DetailActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("menu", menu)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.v("setIntent:", "ERROR : ${e.message}")
        }
    }
}