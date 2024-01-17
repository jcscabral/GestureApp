package com.example.gestureapp
import android.content.ContextWrapper
import android.content.Intent
import android.media.audiofx.EnvironmentalReverb
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontVariation
import com.example.gestureapp.ui.AppScreen
import com.example.gestureapp.ui.theme.GestureAppTheme
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            GestureAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen()
                }
            }
        }
    }

}
//@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GestureAppTheme {
        AppScreen()
    }
}