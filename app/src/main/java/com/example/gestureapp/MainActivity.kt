package com.example.gestureapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gestureapp.ui.AppScreen
import com.example.gestureapp.ui.theme.GestureAppTheme


class MainActivity : ComponentActivity(){

//    lateinit var swipeSensorManager: AppSensorManager
//    lateinit var buttonSensorManager: AppSensorManager
//    lateinit var keyboardSensorManager: AppSensorManager

    //val uuid: UUID =  AppSection.sectionId

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

//        this.swipeSensorManager = AppSensorManager(
//            getSystemService(
//                Context.SENSOR_SERVICE) as SensorManager,
//            UserActionEnum.HORIZONTAL_SWIPE)
//
//        this.buttonSensorManager = AppSensorManager(
//            getSystemService(
//                Context.SENSOR_SERVICE) as SensorManager,
//            UserActionEnum.BUTTON_PRESS)
//
//        this.keyboardSensorManager = AppSensorManager(
//            getSystemService(
//                Context.SENSOR_SERVICE) as SensorManager,
//            UserActionEnum.KEYBOARD_TYPING)

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

//    override fun onResume() {
//        super.onResume()
//        this.appSensorManager.active()//TODO
//    }
//    override fun onPause() {
//        super.onPause()
//        this.swipeSensorManager.active(false)
//        this.buttonSensorManager.active(false)
//        this.keyboardSensorManager.active(false)
//    }
//    override fun onStop() {
//        super.onStop()
//        this.swipeSensorManager.active(false)
//        this.buttonSensorManager.active(false)
//        this.keyboardSensorManager.active(false)
//    }
}
//@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GestureAppTheme {
        AppScreen()
    }
}