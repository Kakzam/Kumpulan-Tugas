package com.uti.nyobawo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.uti.nyobawo.ui.theme.NyobaWoTheme

class LazyGrid : ComponentActivity() {
    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LazyGridList(this)

        }
    }
}

@ExperimentalStdlibApi
@Composable
fun LazyGridList(context: Context) {
    val itemNumber = (1..8).toList()
    val imageList = listOf(
        R.drawable.decor,
        R.drawable.makeup,
        R.drawable.invitation,
        R.drawable.music,
        R.drawable.weddingorganizer,
        R.drawable.attribut,
        R.drawable.souvenir,
        R.drawable.henna
    )
    val itemNameList = listOf(
        "Decoration",
        "Make UP",
        "Invitation Card",
        "Music",
        "Wedding Organizer",
        "wedding Attribute",
        "Souvenir",
        "Henna"
    )
    val myColor = Color(0xFFBFB4A0)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Welcome to BrideTobe \n" +
                    "Make Your Dream Wedding Come True",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 30.dp),
            textAlign = TextAlign.Center
        )
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(horizontal = 20.dp)) {
            items(itemNumber.size) { index ->
                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .height(250.dp)
                        .padding(10.dp).
                        shadow(15.dp)
                        .clickable {
                            Toast.makeText(context, itemNameList[index], Toast.LENGTH_SHORT).show()

                        },
                    colors = CardDefaults.cardColors(containerColor = myColor)


                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = imageList[index]),
                            contentDescription = "",
                            modifier = Modifier
                                .width(200.dp)
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = itemNameList[index],
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

