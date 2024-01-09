package com.example.gestureapp

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import com.example.gestureapp.data.database.AppContainer
import com.example.gestureapp.data.database.AppDataContainer

class MainApplication: Application() {

    lateinit var container: AppContainer

    companion object {
        lateinit var sensorManager: SensorManager
    }

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        sensorManager = getSystemService(
            Context.SENSOR_SERVICE) as SensorManager
    }

}