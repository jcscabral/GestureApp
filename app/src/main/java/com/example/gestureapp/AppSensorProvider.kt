package com.example.gestureapp

import androidx.lifecycle.viewmodel.CreationExtras
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.ComponentSensorManager

object AppSensorProvider {

    val sensorManager = MainApplication.sensorManager

    fun getComponentSensorManager(actionTypeEnum: UserActionEnum): ComponentSensorManager{
        return ComponentSensorManager(sensorManager, actionTypeEnum)
    }

}

