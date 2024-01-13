package com.example.gestureapp.helpers

import android.util.Log
import android.view.MotionEvent
import androidx.compose.ui.input.pointer.PointerEvent
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.UserActionEnum

abstract class AppEvents {

    companion object{
        fun onButtonMotionEvent(
            userActionEnum: UserActionEnum,
            event: MotionEvent): Boolean{

            val id = AppState.id
            val sessionId = AppState.actionNumber
            val action = event.action
            val size = event.size
            val pressure = event.pressure
            val x = event.x
            val y = event.y
            val eventTime = event.eventTime

            Log.i(userActionEnum.toString(),
                "id:$id;sessionId:$sessionId;action:$action;size:$size;pressure $pressure;" +
                        "x:$x;y:$y;evenTime:$eventTime")

            return action == MotionEvent.ACTION_UP

        }

        fun onDigitMotionEvent(
            userActionEnum: UserActionEnum,
            event: MotionEvent,
            digit: String = ""): Boolean{

            val id = AppState.id
            val sessionId = AppState.actionNumber
            val action = event.action
            val size = event.size
            val pressure = event.pressure
            val x = event.x
            val y = event.y
            val eventTime = event.eventTime

            Log.i(userActionEnum.toString(),
                "digit:$digit;id:$id;sessionId:$sessionId;action:$action;size:$size;pressure $pressure;" +
                        "x:$x;y:$y;evenTime:$eventTime")

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {}
                MotionEvent.ACTION_MOVE -> {}
                MotionEvent.ACTION_UP -> {}
                else -> return false
            }
            return true
            //return action == MotionEvent.ACTION_UP

        }

        fun onDigitPointerEvent(
            userActionEnum: UserActionEnum,
            event: PointerEvent,
            digit: String
        ){
            val eventType  = event.type
            for (change in event.changes) {

                val id = AppState.id
                val sessionId = AppState.actionNumber
                val position = change.position
                val action = change.type.toString()
                val x = position.x
                val y = position.y
                val pressure = change.pressure
                val uptimeMillis = change.uptimeMillis

                Log.i(userActionEnum.toString(),
                    "digit:$digit;id:$id;sessionId:$sessionId;eventType:$eventType;action:$action;" +
                            "pressure:$pressure;x:$x;y:$y;evenTime:$uptimeMillis")
            }

        }

        fun onPointerEvent(
            userActionEnum: UserActionEnum,
            event: PointerEvent,
            appSensorManager: AppSensorManager
        ){

            val eventType  = event.type

            for (change in event.changes) {

                if (!appSensorManager?.activated!!) {
                    appSensorManager?.active()
                }
                val id = AppState.id
                val sessionId = AppState.actionNumber
                val position = change.position
                val action = change.type.toString()
                val x = position.x
                val y = position.y
                val pressure = change.pressure
                val uptimeMillis = change.uptimeMillis

                if (!change.pressed) {
                    appSensorManager.active(false)
                }

                Log.i(userActionEnum.toString(),
                    "id:$id;sessionId:$sessionId;eventType:$eventType;action:$action;" +
                            "pressure $pressure;x:$x;y:$y;evenTime:$uptimeMillis")
            }

        }

    }
}