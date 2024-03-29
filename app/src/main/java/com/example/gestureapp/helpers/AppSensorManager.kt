package com.example.gestureapp.helpers
import android.hardware.Sensor
import android.hardware.SensorManager
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppSensorListener

class AppSensorManager{

    private lateinit var magneticSensor: Sensor
    private lateinit var acceleromterSensor: Sensor
    private lateinit var gyroscopeSensor: Sensor
    private var sensorManager: SensorManager
    private var actionTypeEnum : UserActionEnum
    private var appSensorListener : AppSensorListener
    var activated = false

    constructor(sensorManager: SensorManager, actionTypeEnum: UserActionEnum){
        this.actionTypeEnum = actionTypeEnum
        this.appSensorListener = AppSensorListener(actionTypeEnum)
        this.sensorManager = sensorManager
        this.initSensors()
    }

    private fun initSensors(){
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        acceleromterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
    }

    private fun register(){
        sensorManager.registerListener(
            appSensorListener,
            magneticSensor,
            SensorManager.SENSOR_DELAY_NORMAL) // 20000 ms 5Hz
        sensorManager.registerListener(
            appSensorListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(
            appSensorListener,
            acceleromterSensor,
            SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun unregister(){
        sensorManager?.unregisterListener(appSensorListener)
    }

    fun active(activated: Boolean =  true){
        if (activated){
            this.register()
        }
        else{
            this.unregister()
        }
        this.activated = activated
    }

}