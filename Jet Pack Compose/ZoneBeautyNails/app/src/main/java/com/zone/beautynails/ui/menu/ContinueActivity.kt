package com.zone.beautynails.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zone.beautynails.R
import com.zone.beautynails.ui.theme.PRIMARY
import com.zone.beautynails.ui.theme.ZoneBeautyNailsTheme

class ContinueActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZoneBeautyNailsTheme {
                Column(Modifier.fillMaxSize().background(PRIMARY), Arrangement.Center) {
                    Image(painterResource(R.drawable.icon_depan), "", Modifier.fillMaxWidth())
                    Image(painterResource(R.drawable.button_continue), "", Modifier.fillMaxWidth().padding(start = 28.dp, end = 28.dp).clickable {
                        intent = Intent(applicationContext, SignInActivity::class.java)
                        startActivity(intent)
                        finish()
                    })
                }
            }
        }
    }
}