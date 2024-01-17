package com.example.gestureapp.helpers

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.data.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppSensorListener: SensorEventListener {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val events = Channel<SensorEvent>(100)
    private val actionTypeEnum : UserActionEnum

    constructor(actionTypeEnum: UserActionEnum){
        this.actionTypeEnum = actionTypeEnum
    }

    private fun getCsvLine(sensorType: Int, event: SensorEvent): String{
        return "${AppState.id};${AppState.actionNumber};${actionTypeEnum.ordinal};$sensorType;" +
                "${event.values[0]};${event.values[1]};${event.values[2]};${event.timestamp}"
    }
    private fun recordCsv(sensorType: Int, event: SensorEvent){

//        val eventName = when (sensorType){
//            2 -> "MAGNETIC"
//            1 -> "ACCELEROMETER"
//            4 -> "GYROSCOPE"
//            else -> "OTHER SENSOR"
//        }
//        Log.i(eventName, "action:${actionTypeEnum};id:${AppState.id};session:${AppState.actionNumber};" +
//                "X:${event.values[0]};Y:${event.values[1]};Z:${event.values[2]};Ts:${event.timestamp}")

        scope.launch {
            Recorder.sensorsData(
                getCsvLine(sensorType, event))
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            recordCsv(event.sensor.type, event)
        }
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            recordCsv(event.sensor.type, event)
        }
        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            recordCsv(event.sensor.type, event)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.i("CHANGED", "id:${AppState.id};section:${AppState.actionNumber}")
    }

//    fun offer(event: SensorEvent) = runBlocking{events.send(event)}
//
//    fun process() = scope.launch {
//        events.consumeEach {
//            Log.i("CONSUMED", "Uuid: ${AppState.actionNumber} - SensorMonitor")
//        }
//    }

}