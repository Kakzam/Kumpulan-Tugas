package com.butter.sweet

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.butter.sweet.ui.theme.ButterSweetTheme
import com.butter.sweet.ui.theme.pink
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayOutputStream
import java.text.NumberFormat
import java.util.*

class UtamaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ButterSweetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    var search by remember { mutableStateOf("") }
                    var utama by remember { mutableStateOf(intent.getBooleanExtra("utama", true)) }
                    var keranjang by remember {
                        mutableStateOf(
                            intent.getBooleanExtra(
                                "keranjang",
                                false
                            )
                        )
                    }
                    var profile by remember { mutableStateOf(false) }

                    Scaffold(topBar = {
                        if (utama) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 4.dp, bottom = 4.dp, end = 8.dp, start = 8.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextField(value = search,
                                    onValueChange = {
                                        search = it
                                    },
                                    Modifier.weight(4f),
                                    label = { Text(text = "Cari Makanan..") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Search"
                                        )
                                    })

                                if (!intent.getStringExtra("image").toString().equals("Empty")) {
                                    Card(
                                        shape = CircleShape, modifier = Modifier
                                            .padding(8.dp)
                                            .size(50.dp)
                                    ) {
                                        val d = android.util.Base64.decode(
                                            intent.getStringExtra("image"),
                                            android.util.Base64.DEFAULT
                                        )
                                        Image(
                                            bitmap = BitmapFactory.decodeByteArray(d, 0, d.size)
                                                .asImageBitmap(),
                                            contentDescription = "",
                                            Modifier
                                                .fillMaxSize()
                                                .padding(4.dp)
                                        )
                                    }
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_user),
                                        contentDescription = "",
                                        Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            }
                        }
                    }, bottomBar = {
                        BottomNavigation(Modifier.fillMaxWidth(), backgroundColor = pink) {
                            BottomNavigationItem(icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_utama),
                                    contentDescription = ""
                                )
                            }, label = { Text(text = "Home") }, selected = utama, onClick = {
                                utama = true
                                keranjang = false
                                profile = false
                            })

                            BottomNavigationItem(icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_keranjang),
                                    contentDescription = ""
                                )
                            },
                                label = { Text(text = "Keranjang") },
                                selected = keranjang,
                                onClick = {
                                    utama = false
                                    keranjang = true
                                    profile = false
                                })

                            BottomNavigationItem(icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_baseline_profile),
                                    contentDescription = ""
                                )
                            },
                                label = { Text(text = "Profile") },
                                selected = profile,
                                onClick = {
                                    utama = false
                                    keranjang = false
                                    profile = true
                                })
                        }
                    }) {
                        if (utama) TampilanUtama(it)
                        if (keranjang) TampilanKeranjang(it)
                        if (profile) TampilanProfile(it)
                    }
                }
            }
        }
    }

    @Composable
    fun TampilanUtama(it: PaddingValues) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(
                    top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()
                )
                .verticalScroll(rememberScrollState())
        ) {

            Image(
                painter = painterResource(id = R.drawable.icon_utama_hello),
                contentDescription = "gambar makanan",
                Modifier
                    .fillMaxWidth()
                    .height(59.dp)
            )

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ) {

                Column(
                    Modifier
                        .padding(4.dp)
                        .clickable {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MenuActivity::class.java
                                )
                                    .putExtra(
                                        "image",
                                        intent
                                            .getStringExtra("image")
                                            .toString()
                                    )
                                    .putExtra(
                                        "phone",
                                        intent
                                            .getStringExtra("phone")
                                            .toString()
                                    )
                                    .putExtra(
                                        "email",
                                        intent
                                            .getStringExtra("email")
                                            .toString()
                                    )
                                    .putExtra(
                                        "password",
                                        intent
                                            .getStringExtra("password")
                                            .toString()
                                    )
                                    .putExtra(
                                        "nama",
                                        intent
                                            .getStringExtra("nama")
                                            .toString()
                                    )
                                    .putExtra(
                                        "tanggal_lahir",
                                        intent
                                            .getStringExtra("tanggal_lahir")
                                            .toString()
                                    )
                                    .putExtra(
                                        "jenis_kelamin",
                                        intent
                                            .getStringExtra("jenis_kelamin")
                                            .toString()
                                    )
                                    .putExtra(
                                        "alamat",
                                        intent
                                            .getStringExtra("alamat")
                                            .toString()
                                    )
                                    .putExtra(
                                        "id",
                                        intent
                                            .getStringExtra("id")
                                            .toString()
                                    )
                                    .putExtra(
                                        "menu",
                                        "terbaru"
                                    )
                            )
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_utama_terbaru),
                        contentDescription = "gambar makanan"
                    )
                    Text(text = "TERBARU")
                }

                Column(
                    Modifier
                        .padding(4.dp)
                        .clickable {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MenuActivity::class.java
                                )
                                    .putExtra(
                                        "image",
                                        intent
                                            .getStringExtra("image")
                                            .toString()
                                    )
                                    .putExtra(
                                        "phone",
                                        intent
                                            .getStringExtra("phone")
                                            .toString()
                                    )
                                    .putExtra(
                                        "email",
                                        intent
                                            .getStringExtra("email")
                                            .toString()
                                    )
                                    .putExtra(
                                        "password",
                                        intent
                                            .getStringExtra("password")
                                            .toString()
                                    )
                                    .putExtra(
                                        "nama",
                                        intent
                                            .getStringExtra("nama")
                                            .toString()
                                    )
                                    .putExtra(
                                        "tanggal_lahir",
                                        intent
                                            .getStringExtra("tanggal_lahir")
                                            .toString()
                                    )
                                    .putExtra(
                                        "jenis_kelamin",
                                        intent
                                            .getStringExtra("jenis_kelamin")
                                            .toString()
                                    )
                                    .putExtra(
                                        "alamat",
                                        intent
                                            .getStringExtra("alamat")
                                            .toString()
                                    )
                                    .putExtra(
                                        "id",
                                        intent
                                            .getStringExtra("id")
                                            .toString()
                                    )
                                    .putExtra(
                                        "menu",
                                        "terlaris"
                                    )
                            )
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_utama_terlaris),
                        contentDescription = "gambar makanan"
                    )
                    Text(text = "TERLARIS")
                }

                Column(
                    Modifier
                        .padding(4.dp)
                        .clickable {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MenuActivity::class.java
                                )
                                    .putExtra(
                                        "image",
                                        intent
                                            .getStringExtra("image")
                                            .toString()
                                    )
                                    .putExtra(
                                        "phone",
                                        intent
                                            .getStringExtra("phone")
                                            .toString()
                                    )
                                    .putExtra(
                                        "email",
                                        intent
                                            .getStringExtra("email")
                                            .toString()
                                    )
                                    .putExtra(
                                        "password",
                                        intent
                                            .getStringExtra("password")
                                            .toString()
                                    )
                                    .putExtra(
                                        "nama",
                                        intent
                                            .getStringExtra("nama")
                                            .toString()
                                    )
                                    .putExtra(
                                        "tanggal_lahir",
                                        intent
                                            .getStringExtra("tanggal_lahir")
                                            .toString()
                                    )
                                    .putExtra(
                                        "jenis_kelamin",
                                        intent
                                            .getStringExtra("jenis_kelamin")
                                            .toString()
                                    )
                                    .putExtra(
                                        "alamat",
                                        intent
                                            .getStringExtra("alamat")
                                            .toString()
                                    )
                                    .putExtra(
                                        "id",
                                        intent
                                            .getStringExtra("id")
                                            .toString()
                                    )
                                    .putExtra(
                                        "menu",
                                        "promo"
                                    )
                            )
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_utama_promo),
                        contentDescription = "gambar makanan"
                    )
                    Text(text = "PROMO")
                }

                Column(
                    Modifier
                        .padding(4.dp)
                        .clickable {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    MenuActivity::class.java
                                )
                                    .putExtra(
                                        "image",
                                        intent
                                            .getStringExtra("image")
                                            .toString()
                                    )
                                    .putExtra(
                                        "phone",
                                        intent
                                            .getStringExtra("phone")
                                            .toString()
                                    )
                                    .putExtra(
                                        "email",
                                        intent
                                            .getStringExtra("email")
                                            .toString()
                                    )
                                    .putExtra(
                                        "password",
                                        intent
                                            .getStringExtra("password")
                                            .toString()
                                    )
                                    .putExtra(
                                        "nama",
                                        intent
                                            .getStringExtra("nama")
                                            .toString()
                                    )
                                    .putExtra(
                                        "tanggal_lahir",
                                        intent
                                            .getStringExtra("tanggal_lahir")
                                            .toString()
                                    )
                                    .putExtra(
                                        "jenis_kelamin",
                                        intent
                                            .getStringExtra("jenis_kelamin")
                                            .toString()
                                    )
                                    .putExtra(
                                        "alamat",
                                        intent
                                            .getStringExtra("alamat")
                                            .toString()
                                    )
                                    .putExtra(
                                        "id",
                                        intent
                                            .getStringExtra("id")
                                            .toString()
                                    )
                                    .putExtra(
                                        "menu",
                                        "koreanxtaiwan"
                                    )
                            )
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_utama_koreanxtaiwan),
                        contentDescription = "gambar makanan"
                    )
                    Text(text = "KOREAN X TAIWAN")
                }
            }

            Image(
                painter = painterResource(id = R.drawable.icon_utama_big),
                contentDescription = "gambar makanan",
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = it.calculateBottomPadding())
                    .clickable {
                        startActivity(
                            Intent(
                                applicationContext,
                                MenuActivity::class.java
                            )
                                .putExtra(
                                    "image",
                                    intent
                                        .getStringExtra("image")
                                        .toString()
                                )
                                .putExtra(
                                    "phone",
                                    intent
                                        .getStringExtra("phone")
                                        .toString()
                                )
                                .putExtra(
                                    "email",
                                    intent
                                        .getStringExtra("email")
                                        .toString()
                                )
                                .putExtra(
                                    "password",
                                    intent
                                        .getStringExtra("password")
                                        .toString()
                                )
                                .putExtra(
                                    "nama",
                                    intent
                                        .getStringExtra("nama")
                                        .toString()
                                )
                                .putExtra(
                                    "tanggal_lahir",
                                    intent
                                        .getStringExtra("tanggal_lahir")
                                        .toString()
                                )
                                .putExtra(
                                    "jenis_kelamin",
                                    intent
                                        .getStringExtra("jenis_kelamin")
                                        .toString()
                                )
                                .putExtra(
                                    "alamat",
                                    intent
                                        .getStringExtra("alamat")
                                        .toString()
                                )
                                .putExtra(
                                    "id",
                                    intent
                                        .getStringExtra("id")
                                        .toString()
                                )
                                .putExtra(
                                    "menu",
                                    "spesial"
                                )
                        )
                    }
            )
        }
    }

    @Composable
    fun TampilanKeranjang(it: PaddingValues) {

        var data = remember { mutableListOf<DocumentSnapshot>() }
        var qty_ = remember { mutableListOf<Int>() }
        var load by remember { mutableStateOf(true) }
        var total by remember { mutableStateOf(0) }

        if (load) {
            FirebaseFirestore
                .getInstance()
                .collection("ButterSweet")
                .document("-")
                .collection("user")
                .document(intent.getStringExtra("id").toString())
                .collection("keranjang")
                .get()
                .addOnSuccessListener {
                    load = false
                    var p = 0
                    for (d in it.documents) {
                        qty_.add(p, 0)
                        p += 1
                    }
                    data.addAll(it.documents)
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
        } else {
            Column(Modifier.padding(bottom = it.calculateBottomPadding())) {
                Row(Modifier.fillMaxWidth()) {
                    Card(
                        Modifier
                            .weight(0.6f)
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = pink
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val format = NumberFormat.getInstance()
                            format.maximumFractionDigits = 0
                            Text(
                                text = "Bayar\nRp ${format.format(total).replace(",", ".")}",
                                Modifier.padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 4.dp,
                                    bottom = 4.dp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Card(
                        Modifier
                            .weight(0.4f)
                            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                            .clickable {
                                var message = ""
                                var p = 0
                                for (d in data) {
                                    if (qty_.get(p) > 0) {
                                        message += "*${
                                            d
                                                .get("nama")
                                                .toString()
                                        }* qty ${qty_.get(p)}\nTotal: ${
                                            qty_.get(p) * d
                                                .get("harga")
                                                .toString()
                                                .toInt()
                                        }\n\n"
                                    }
                                    p += 1
                                }
                                message += "\n\nBayar: ${total}"

                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://api.whatsapp.com/send?phone=6289615842054&text=${message}")
                                        )
                                    )
                                } catch (e: Exception) {
                                    Toast
                                        .makeText(
                                            applicationContext,
                                            "Silahkan download Whatsapp",
                                            Toast.LENGTH_LONG
                                        )
                                        .show()
                                }
                            },
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = pink
                    ) {
                        Column(
                            Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "PESAN",
                                Modifier.padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    top = 4.dp,
                                    bottom = 4.dp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                LazyColumn() {
                    itemsIndexed(data) { index, item ->
                        run {
                            Card(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, end = 12.dp, start = 12.dp),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 12.dp,
                                            end = 12.dp,
                                            top = 8.dp,
                                            bottom = 8.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val d = android.util.Base64.decode(
                                        item.get("gambar").toString(),
                                        android.util.Base64.DEFAULT
                                    )

                                    Image(
                                        bitmap = BitmapFactory.decodeByteArray(
                                            d,
                                            0,
                                            d.size
                                        ).asImageBitmap(),
                                        contentDescription = "",
                                        Modifier
                                            .width(90.dp)
                                            .height(90.dp)
                                            .padding(4.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )

                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(4.dp), horizontalAlignment = Alignment.End
                                    ) {
                                        Text(
                                            text = item.get("nama").toString(),
                                            Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = item.get("deskripsi").toString(),
                                            Modifier.fillMaxWidth()
                                        )
                                        Text(
                                            text = item.get("harga").toString(),
                                            Modifier.fillMaxWidth()
                                        )
                                        var total_qty by remember { mutableStateOf(0) }

                                        Row() {
                                            var qty by remember { mutableStateOf(0) }

                                            Card(
                                                Modifier
                                                    .padding(
                                                        top = 4.dp,
                                                        bottom = 4.dp,
                                                        start = 10.dp,
                                                        end = 10.dp
                                                    )
                                                    .clickable {
                                                        qty += 1
                                                        qty_.set(index, qty)
                                                        total += item
                                                            .get("harga")
                                                            .toString()
                                                            .toInt()
                                                        total_qty += item
                                                            .get("harga")
                                                            .toString()
                                                            .toInt()
                                                    },
                                                shape = RoundedCornerShape(8.dp),
                                                backgroundColor = pink
                                            ) {
                                                Text(
                                                    text = "+",
                                                    Modifier.padding(
                                                        top = 8.dp,
                                                        bottom = 8.dp,
                                                        end = 12.dp,
                                                        start = 12.dp
                                                    )
                                                )
                                            }

                                            Text(
                                                text = qty.toString(),
                                                Modifier.padding(
                                                    top = 8.dp,
                                                    bottom = 8.dp,
                                                    end = 12.dp,
                                                    start = 12.dp
                                                )
                                            )

                                            Card(
                                                Modifier
                                                    .padding(
                                                        top = 4.dp,
                                                        bottom = 4.dp,
                                                        start = 10.dp,
                                                        end = 10.dp
                                                    )
                                                    .clickable {
                                                        if (qty > 0) {
                                                            qty -= 1
                                                            qty_.set(index, qty)
                                                            total -= item
                                                                .get("harga")
                                                                .toString()
                                                                .toInt()
                                                            total_qty -= item
                                                                .get("harga")
                                                                .toString()
                                                                .toInt()
                                                        }
                                                    },
                                                shape = RoundedCornerShape(8.dp),
                                                backgroundColor = pink
                                            ) {
                                                Text(
                                                    text = "-",
                                                    Modifier.padding(
                                                        top = 8.dp,
                                                        bottom = 8.dp,
                                                        end = 12.dp,
                                                        start = 12.dp
                                                    )
                                                )
                                            }

                                            Image(
                                                painter = painterResource(id = R.drawable.ic_trash),
                                                contentDescription = "",
                                                Modifier.clickable {
                                                    FirebaseFirestore
                                                        .getInstance()
                                                        .collection("ButterSweet")
                                                        .document("-")
                                                        .collection("user")
                                                        .document(
                                                            intent.getStringExtra("id").toString()
                                                        )
                                                        .collection("keranjang")
                                                        .document(item.id)
                                                        .delete()
                                                        .addOnSuccessListener {
                                                            Toast.makeText(
                                                                applicationContext,
                                                                "${
                                                                    item.get("nama").toString()
                                                                } berhasil dihapus",
                                                                Toast.LENGTH_LONG
                                                            ).show()
                                                            data.removeAt(index)
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
                                            )
                                        }

                                        val format = NumberFormat.getInstance()
                                        format.maximumFractionDigits = 0
                                        Text(
                                            text = "Total: ${
                                                format.format(total_qty).replace(",", ".")
                                            }", Modifier.fillMaxWidth()
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

    @Composable
    fun TampilanProfile(it: PaddingValues) {
        val context = LocalContext.current
        var update by remember { mutableStateOf(false) }
        var phone by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var nama by remember { mutableStateOf("") }
        var tanggal_lahir by remember { mutableStateOf("") }
        var jenis_kelamin by remember { mutableStateOf(true) }
        var alamat by remember { mutableStateOf("") }
        var first by remember { mutableStateOf(true) }
        var baseString by remember { mutableStateOf("") }
        var bitmapImage by remember {
            mutableStateOf(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.ic_user
                )?.toBitmap(100, 100)
            )
        }

        if (first) {
            if (!intent.getStringExtra("image").equals("Empty")) {
                baseString = intent.getStringExtra("image").toString()
                val d = android.util.Base64.decode(baseString, android.util.Base64.DEFAULT)
                bitmapImage = BitmapFactory.decodeByteArray(d, 0, d.size)
            }

            phone = intent.getStringExtra("phone").toString()
            email = intent.getStringExtra("email").toString()
            password = intent.getStringExtra("password").toString()
            nama = intent.getStringExtra("nama").toString()
            tanggal_lahir = intent.getStringExtra("tanggal_lahir").toString()
            jenis_kelamin = intent.getStringExtra("jenis_kelamin").toString() == "true"
            alamat = intent.getStringExtra("alamat").toString()
            first = false
        }

        val calendar: Calendar = Calendar.getInstance()
        val date = DatePickerDialog(
            context,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                tanggal_lahir = "$mDayOfMonth/${mMonth + 1}/$mYear"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        painterResource(id = R.drawable.ic_user)

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
                    bottom = it.calculateBottomPadding(),
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
                value = phone,
                onValueChange = {
                    phone = it
                },
                Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                enabled = update,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Phone") },
                placeholder = { Text(text = "Cth : 088852185653") },
                maxLines = 15
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
                label = { Text(text = "Nama Lengkap") },
                placeholder = { Text(text = "Cth : Reza Arab, Lukas Pratama") },
                maxLines = 25
            )

            TextField(value = tanggal_lahir,
                onValueChange = {},
                Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth()
                    .clickable { if (update) date.show() },
                enabled = false,
                label = { Text(text = "Tanggal Lahir") },
                placeholder = { Text(text = "Pilih Tanggal") })

            if (update) {
                Text(
                    text = "Jenis Kelamin", Modifier.padding(top = 14.dp)
                )

                Row(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = jenis_kelamin, onClick = {
                            jenis_kelamin = true
                        })

                        Text(text = "Laki - Laki")
                    }

                    Row(
                        Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = !jenis_kelamin, onClick = {
                            jenis_kelamin = false
                        })
                        Text(text = "Perempuan")
                    }
                }
            } else {
                var jenis = ""
                if (jenis_kelamin) jenis = "Laki-Laki"
                else jenis = "Perempuan"
                TextField(value = jenis,
                    onValueChange = {},
                    Modifier
                        .padding(top = 14.dp)
                        .fillMaxWidth(),
                    enabled = update,
                    label = { Text(text = "Jenis Kelamin") })
            }

            TextField(
                value = alamat,
                onValueChange = {
                    alamat = it
                },
                Modifier
                    .padding(top = 14.dp)
                    .height(100.dp)
                    .fillMaxWidth(),
                enabled = update,
                label = { Text(text = "Alamat") },
                placeholder = { Text(text = "Cth : Jl. Nasution. RT.10, RW.20") },
                maxLines = 100
            )

            TextField(value = email,
                onValueChange = {
                    email = it
                },
                Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth(),
                enabled = update,
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Cth : reza.sigit@teknokrat.ac.id") })

            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                Modifier
                    .padding(top = 14.dp)
                    .fillMaxWidth(),
                enabled = update,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Cth : 123QWE#kl, rayo!n_1") },
                maxLines = 100
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
                        if (!update) {
                            Text(
                                text = "UPDATE",
                                Modifier
                                    .padding(top = 8.dp, bottom = 8.dp, start = 14.dp, end = 14.dp)
                                    .clickable {
                                        update = true
                                    })
                        } else {
                            Text(
                                text = "SIMPAN",
                                Modifier
                                    .padding(top = 8.dp, bottom = 8.dp, start = 14.dp, end = 14.dp)
                                    .clickable {
                                        if (phone.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "No Telepon Tidak Boleh Kosong",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (phone.length < 11) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "No Telepon Salah",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (nama.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Nama Lengkap Tidak Boleh Kosong",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (nama.length < 5) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Nama Lengkap Kurang dari 5 Kata",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (tanggal_lahir.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Tanggal Lahir Tidak Boleh Kosong",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (alamat.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Alamat Tidak Boleh Kosong",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (alamat.length < 12) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Alamat Tidak Sesuai",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (email.length < 10) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Email Tidak Sesuai",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (email.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Email Tidak Boleh Kosong",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (password.length < 7) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Password Kurang dari 7 kata",
                                                    Toast.LENGTH_LONG
                                                )
                                                .show()
                                        } else if (password.isEmpty()) {
                                            Toast
                                                .makeText(
                                                    applicationContext,
                                                    "Password Tidak Boleh Kosong",
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
                                            data.put("phone", phone)
                                            data.put("email", email)
                                            data.put("password", password)
                                            data.put("nama", nama)
                                            data.put("tanggal_lahir", tanggal_lahir)
                                            data.put("jenis_kelamin", jenis_kelamin.toString())
                                            data.put("alamat", alamat)

                                            FirebaseFirestore
                                                .getInstance()
                                                .collection("ButterSweet")
                                                .document("-")
                                                .collection("user")
                                                .document(
                                                    intent
                                                        .getStringExtra("id")
                                                        .toString()
                                                )
                                                .set(data)
                                                .addOnSuccessListener {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Data Berhasil Disimpan",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                    update = false
                                                }
                                                .addOnFailureListener {
                                                    Toast
                                                        .makeText(
                                                            applicationContext,
                                                            "Gambar anda terlalu besar(Minimal 200Kb), atau silahkan periksa koneksi internet anda.",
                                                            Toast.LENGTH_LONG
                                                        )
                                                        .show()
                                                }
                                        }
                                    })
                        }
                    }
                }

                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_logout),
                        contentDescription = "",
                        Modifier.clickable {
                            startActivity(
                                Intent(
                                    context,
                                    LoginActivity::class.java
                                )
                            )
                        })
                }
            }
        }
    }
}