package com.example.gestureapp
import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gestureapp.data.AppSection
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.ComponentSensorManager
import com.example.gestureapp.ui.AppScreen
import com.example.gestureapp.ui.theme.GestureAppTheme
import java.util.UUID


class MainActivity : ComponentActivity(){

    lateinit var swipeSensorManager: ComponentSensorManager
    lateinit var buttonSensorManager: ComponentSensorManager
    lateinit var keyboardSensorManager: ComponentSensorManager

    //val uuid: UUID =  AppSection.sectionId

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        this.swipeSensorManager = ComponentSensorManager(
            getSystemService(
                Context.SENSOR_SERVICE) as SensorManager,
            UserActionEnum.HORIZONTAL_SWIPE)

        this.buttonSensorManager = ComponentSensorManager(
            getSystemService(
                Context.SENSOR_SERVICE) as SensorManager,
            UserActionEnum.BUTTON_PRESS)

        this.keyboardSensorManager = ComponentSensorManager(
            getSystemService(
                Context.SENSOR_SERVICE) as SensorManager,
            UserActionEnum.KEYBOARD_TYPING)

        setContent {
            GestureAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreen(
                        swipeSensorManager ,
                        buttonSensorManager,
                        keyboardSensorManager
                    )
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
//@Composable
//fun AppPreview() {
//    GestureAppTheme {
//        HomeScreen()
//    }
//}