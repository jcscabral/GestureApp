package com.example.gestureapp

import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppSensorManager

object AppSensorProvider {

    private val sensorManager = MainApplication.sensorManager

    fun get(userActionEnum: UserActionEnum): AppSensorManager {
        return AppSensorManager(sensorManager, userActionEnum)
    }

}

