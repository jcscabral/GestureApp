package com.example.gestureapp.helpers

import android.util.Log
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.UserActionEnum

abstract class AppGestureEvents {

    companion object{

        private fun eventNumber(eventType: PointerEventType): Int{
            val eventTypeNumber = when(eventType){
                PointerEventType.Unknown -> 0
                PointerEventType.Press -> 1
                PointerEventType.Release -> 2
                PointerEventType.Move -> 3
                PointerEventType.Enter -> 4
                PointerEventType.Exit -> 5
                PointerEventType.Scroll -> 6
                else -> -1
            }
            return eventTypeNumber
        }

        fun onPointerEvent(
            userActionEnum: UserActionEnum,
            event: PointerEvent
        ) {
            val eventType = event.type //PointerEventType
            for (change in event.changes) {
                val id = AppState.id
                val sessionId = AppState.actionNumber
                val position = change.position
                val x = position.x
                val y = position.y
                val pressure = change.pressure
                val uptimeMillis = change.uptimeMillis

//                Log.i(userActionEnum.toString(),
//                    "id:$id;sessionId:$sessionId;${userActionEnum.ordinal};" +
//                            "eventType:${eventType};action:$action;digit:$digit;" +
//                            "pressure:$pressure;x:$x;y:$y;evenTime:$uptimeMillis")

                val text = "$id;$sessionId;${userActionEnum.ordinal};${eventNumber(eventType)};" +
                        "$pressure;$x;$y;$uptimeMillis"

                Recorder.buttonData(text)
            }
        }

        fun onPointerEvent(
            userActionEnum: UserActionEnum,
            event: PointerEvent,
            digit: String = ""
        ){
            PointerEventType
            val eventType  = event.type //PointerEventType
            for (change in event.changes) {
                val id = AppState.id
                val sessionId = AppState.actionNumber
                val position = change.position
                val x = position.x
                val y = position.y
                val pressure = change.pressure
                val uptimeMillis = change.uptimeMillis

                val text = "$id;$sessionId;${userActionEnum.ordinal};${eventNumber(eventType)};" +
                        "$digit;$pressure;$x;$y;$uptimeMillis"

                Recorder.keyboardData(text)
            }
        }
        /*
        With sensors. Just when it is swiping
         */
        fun onPointerEvent(
            userActionEnum: UserActionEnum,
            event: PointerEvent,
            appSensorManager: AppSensorManager
        ){

            val eventType  = event.type
            for (change in event.changes) {

                if (!appSensorManager.activated) {
                    appSensorManager.active()
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
                            "pressure:$pressure;x:$x;y:$y;evenTime:$uptimeMillis")

                val text = "$id;$sessionId;${userActionEnum.ordinal};${eventNumber(eventType)};" +
                        "$pressure;$x;$y;$uptimeMillis"
                Recorder.swipeData(text)
            }
        }

        //        fun onButtonMotionEvent(
//            userActionEnum: UserActionEnum,
//            event: MotionEvent): Boolean{
//
//            val id = AppState.id
//            val sessionId = AppState.actionNumber
//            val action = event.action
//            val size = event.size
//            val pressure = event.pressure
//            val x = event.x
//            val y = event.y
//            val eventTime = event.eventTime
//
//            Log.i(userActionEnum.toString(),
//                "id:$id;sessionId:$sessionId;action:$action;size:$size;pressure $pressure;" +
//                        "x:$x;y:$y;evenTime:$eventTime")
//
//            val text = "$id;$sessionId;$action;$size;$pressure;$x;$y;$eventTime"
//            Recorder.keyboardData(text)
//
//            return false //action == MotionEvent.ACTION_UP
//
//        }

//        fun onDigitMotionEvent(
//            userActionEnum: UserActionEnum,
//            event: MotionEvent,
//            digit: String = ""): Boolean{
//
//            val id = AppState.id
//            val sessionId = AppState.actionNumber
//            val action = event.action
//            val size = event.size
//            val pressure = event.pressure
//            val x = event.x
//            val y = event.y
//            val eventTime = event.eventTime
//
//            Log.i(userActionEnum.toString(),
//                "digit:$digit;id:$id;sessionId:$sessionId;action:$action;size:$size;pressure $pressure;" +
//                        "x:$x;y:$y;evenTime:$eventTime")
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {}
//                MotionEvent.ACTION_MOVE -> {}
//                MotionEvent.ACTION_UP -> {}
//                else -> return false
//            }
//            return action == MotionEvent.ACTION_UP
//        }
    }
}