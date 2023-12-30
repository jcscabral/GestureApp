package com.example.gestureapp

import android.app.Application
import com.example.gestureapp.data.database.AppContainer
import com.example.gestureapp.data.database.AppDataContainer

class MainApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}