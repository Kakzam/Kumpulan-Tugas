package com.uti.nyobawo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uti.nyobawo.ui.theme.NyobaWoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NyobaWoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // buat variable dengan tipe data class karyawan
                    val daftarWO = arrayListOf<WeddingOrganizer>()

                    //isi daftar karyawan
                    daftarWO.add(WeddingOrganizer("Andi", "https://instagram.com/abieproduction?igshid=NTc4MTIwNjQ2YQ==", R.drawable.pic_wo1))

                    //tampilkan daftar karyawan dengan list (LazyColumn)
                    LazyColumn(modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)){

                        //"listkaryawan"
                        items(daftarWO){
                                data ->
                            listWO(model = data)
                        }
                    }
                }
            }
        }
    }
}

//  buat class data karyawan
data class WeddingOrganizer (val nama: String, val link: String, val foto: Int)

//buat method untuk daftar karyawan
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun listWO (model : WeddingOrganizer) {
    //buat variable context
    val context = LocalContext.current

    //buat Card
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable {
            Toast.makeText(context,model.nama, Toast.LENGTH_LONG).show()
        }, colors = CardDefaults.cardColors(containerColor = Color.White, contentColor = Color.Black), elevation = CardDefaults. cardElevation(defaultElevation = 10.dp, pressedElevation = 20.dp)) {
        //buat Row
        Row( verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
            //buat image
            Image(painter = painterResource(id = model.foto), contentDescription = "Foto Profile Instagram", contentScale = ContentScale.Crop,modifier = Modifier
                .size(100.dp)
                .clip(
                    CircleShape
                ))
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {

                //buat nama
                Text(text = model.nama)
                //buat jabatan
                Text(text = model.link)

            }

        }

    }

}


