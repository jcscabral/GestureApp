package com.example.gestureapp.model
import android.hardware.Sensor
import android.hardware.SensorManager

class AppSensorManager{

    private lateinit var magneticSensor: Sensor
    private lateinit var acceleromterSensor: Sensor
    private lateinit var gyroscopeSensor: Sensor
    private var appSensorListeser: AppSensorListener
    private var sensorManager: SensorManager
    private var activated = false

    constructor(sensorManager: SensorManager){

        this.sensorManager = sensorManager
        this.appSensorListeser = AppSensorListener()
        this.initSensors()
        //this.register()
    }

    private fun initSensors(){
        this.magneticSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)!!
        this.gyroscopeSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        this.acceleromterSensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
    }

    private fun register(){

        this.sensorManager?.registerListener(this.appSensorListeser, this.magneticSensor, SensorManager.SENSOR_DELAY_NORMAL)
        this.sensorManager?.registerListener(this.appSensorListeser, this.gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        this.sensorManager?.registerListener(this.appSensorListeser, this.acceleromterSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun unregister(){
        this.sensorManager?.unregisterListener(this.appSensorListeser)
    }

    public fun active(activated: Boolean =  true){
        if (activated){
            this.register()
        }
        else{
            this.unregister()
        }
        this.activated = activated
    }




}