package com.example.gestureapp.ui.components

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gestureapp.model.AppSensorManager


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductItem(
    appSensorManager: AppSensorManager? =  null,
    nameId: Int,
    imageVector: ImageVector,
    onButtonClick: ()-> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxWidth()
    ){

        Column {

            var numberOfTouches by remember { mutableIntStateOf(0) }
            var touches = numberOfTouches.toString()

            // events variables
            var actionType by remember { mutableStateOf("") }
            var pressureEvent by remember { mutableFloatStateOf(.0f) }
            var pressure = pressureEvent.toString()
            var pointerSizeEvent by remember { mutableFloatStateOf(.0f) }
            var pointerSize = pointerSizeEvent.toString()
            var tsTimeEvent by remember { mutableLongStateOf(0L) }
            var tsTime = tsTimeEvent.toString()
            var keysPressed by remember { mutableStateOf("") }

            Button(
                onClick = {
                    Log.i("ONBUTTONCLICK", "nameId: ${nameId}")
                    touches+=1
                    onButtonClick()
                },
                modifier = Modifier
                    .pointerInteropFilter() {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                actionType = "DOWN"
                            }
                            MotionEvent.ACTION_UP -> {
                                actionType = "UP"
                            }
                            MotionEvent.ACTION_MOVE -> {
                                actionType = "MOVE"
                            }
                        }
                        //Log.i("COLUMN", "ActionMove: ${MotionEvent.ACTION_MOVE}")
                        pressureEvent = it.pressure
                        pointerSizeEvent = it.size
                        val posXEvent = it.xT
                        val posYEvent =  it.y
//                        touchMinorEvent = it.touchMinor
//                        touchMajorEvent = it.touchMajor
                        tsTimeEvent = it.eventTime
//                        tsDownTimeEvent = it.downTime

                        Log.i("PRODUCT_ITEM_ACTION", "size: $pointerSize pressure $pressure ")
                        true
                    }


                ,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Icon(
                    imageVector,
                    modifier = Modifier.size(64.dp),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = nameId),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = touches,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}