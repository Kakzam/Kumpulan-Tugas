package live.hms.myapplication.base

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import android.widget.Toast

open class BaseActivity : ComponentActivity() {

//    public val context = ContextA.current

    open fun setToast (message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    @Composable
    open fun setText(text: String) {
        Text(text = "$text!")
    }

    fun setActivity(activity: Class<*>) {
        intent = Intent(this, activity)
        startActivity(intent)
        finish()
    }

}