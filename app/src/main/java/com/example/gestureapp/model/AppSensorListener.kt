package com.example.gestureapp.model

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.example.gestureapp.data.ActionTypeEnum
import com.example.gestureapp.data.AppSection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//TODO append into csv file

class AppSensorListener: SensorEventListener {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val events = Channel<SensorEvent>(100)
    var actionTypeEnum : ActionTypeEnum =  ActionTypeEnum.UNKNOWN

    constructor(actionTypeEnum: ActionTypeEnum){
        this.actionTypeEnum = actionTypeEnum
    }
    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val v = event?.values?.get(0)
            Log.i("MAGNETIC", "Uuid: ${AppSection.uui} - Type: $actionTypeEnum - Values: $event")
        }
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val v = event?.values?.get(0)
            Log.i("ACCELEROMETER", "Uuid: ${AppSection.uui} - Type: $actionTypeEnum - Values: $event")
        }
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            val v = event?.values?.get(0)
            Log.i("GYROSCOPE", "Uuid: ${AppSection.uui} - Type: $actionTypeEnum - Values: $event")
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i("CHANGED", "Uuid: ${AppSection.uui} - SensorMonitor")
    }

    fun offer(event: SensorEvent) = runBlocking{events.send(event)}

    fun process() = scope.launch {
        events.consumeEach {
            Log.i("CONSUMED", "Uuid: ${AppSection.uui} - SensorMonitor")
        }
    }

}