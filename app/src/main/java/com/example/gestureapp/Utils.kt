package com.example.gestureapp

import android.util.Log
import android.view.MotionEvent
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.UserActionEnum
import java.text.NumberFormat
import java.util.Locale

fun moneyFormatter(money: Double): String{
    val locale = Locale("pt", "BR")
    val formatter =  NumberFormat.getCurrencyInstance(locale)
    return formatter.format(money)
}


