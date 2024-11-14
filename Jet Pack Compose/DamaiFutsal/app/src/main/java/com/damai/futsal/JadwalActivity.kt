package com.damai.futsal

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.damai.futsal.ui.theme.BG_DETAIL
import com.damai.futsal.ui.theme.DamaiFutsalTheme
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class JadwalActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DamaiFutsalTheme {
                Scaffold(topBar = {
                    Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                        Image(
                            painterResource(R.drawable.header_dashboard), "",
                            Modifier
                                .height(80.dp)
                                .width(278.dp)
                                .padding(top = 8.dp, bottom = 8.dp))
                    }
                }) {
                    var load by remember { mutableStateOf(true) }
                    val data = remember {
                        mutableListOf<DocumentSnapshot>()
                    }

                    if (load){
                        FirebaseFirestore.getInstance().collection("jadwal").get().addOnSuccessListener { documentReference ->
                            data.addAll(documentReference.documents)
                            load = false
                            Toast.makeText(applicationContext, "Berhasil di download", Toast.LENGTH_LONG).show()
                        }.addOnFailureListener { exception ->
                            Toast.makeText(applicationContext, "Silahkan Periksa Koneksi Internet Anda", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "Masuk", Toast.LENGTH_LONG).show()
                        if (data.isEmpty()){
                            Text(text = "Data Tidak Ada",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(top = it.calculateTopPadding()), textAlign = TextAlign.Center)
                        } else {
                            var filter = ""
                            LazyColumn(
                                Modifier
                                    .fillMaxSize()
                                    .padding(top = it.calculateTopPadding())) {
                                itemsIndexed(data){
                                        index: Int, item: DocumentSnapshot ->
                                    run {
                                        Log.v("ZAM",item.id)
                                        if (filter.isEmpty()){
                                            filter = item.get("tanggal").toString()
                                            Text(text = hapusBelakang(item.id),Modifier.fillMaxWidth().background(color = BG_DETAIL), textAlign = TextAlign.Center)
                                            Row(Modifier.fillMaxWidth()) {
                                                Text(text = item.get("nama_tim").toString().uppercase(), Modifier.weight(1f), textAlign = TextAlign.Center)
                                                Text(text = hapusDepan(item.id).replace("|","-"),Modifier.weight(1f), textAlign = TextAlign.Center)
                                            }
                                        } else {
                                            if (filter.equals(item.get("tanggal").toString())){
                                                Row(Modifier.fillMaxWidth()) {
                                                    Text(text = item.get("nama_tim").toString().uppercase(), Modifier.weight(1f), textAlign = TextAlign.Center)
                                                    Text(text = hapusDepan(item.id).replace("|","-"),Modifier.weight(1f), textAlign = TextAlign.Center)
                                                }
                                            } else {
                                                filter = item.get("tanggal").toString()
                                                Text(text = hapusBelakang(item.id),Modifier.fillMaxWidth().background(color = BG_DETAIL), textAlign = TextAlign.Center)
                                                Row(Modifier.fillMaxWidth()) {
                                                    Text(text = item.get("nama_tim").toString().uppercase(), Modifier.weight(1f), textAlign = TextAlign.Center)
                                                    Text(text = hapusDepan(item.id).replace("|","-"),Modifier.weight(1f), textAlign = TextAlign.Center)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = 19.dp, start = 34.dp, end = 34.dp)) {


                }
            }
        }
    }

    fun hapusDepan(hapus: String) : String{
        val index = hapus.indexOf("dan")
        return hapus.substring(index + "dan".length)
    }

    fun hapusBelakang(hapus: String) : String{
        val index = hapus.indexOf("dan")
        return hapus.substring(0, index)
    }
}
