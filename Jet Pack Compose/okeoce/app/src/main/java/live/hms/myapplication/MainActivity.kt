package live.hms.myapplication

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import live.hms.myapplication.base.BaseActivity
import live.hms.myapplication.ui.theme.MyApplicationTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                TextButton(onClick = {
                    setToast("Message")
                }) {
                    setText("Coba")
                }
            }

            val time = object : CountDownTimer(2000, 1000) {
                override fun onFinish() {

//                    setLog("Token : " + Preference.getToken().toString())
//                    if (!Preference.getToken().isNullOrEmpty()) setActivity(MainActivity::class.java)
//                    else setActivity(WelcomeActivity::class.java)
                }

                override fun onTick(countDownTimer: Long) {}

            }
            time.start()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Row(Modifier.fillMaxSize()) {
            TextButton(onClick = {

            }) {
                Text(text = "Coba")
                Modifier.background(Color.Cyan)
            }

            TextButton(onClick = {

            }) {
                Text(text = "Coba")
                Modifier.background(Color.Cyan)
                Alignment.BottomCenter
            }
        }
    }
}