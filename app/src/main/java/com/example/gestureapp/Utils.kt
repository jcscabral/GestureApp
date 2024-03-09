package com.example.gestureapp

import android.util.Log
import android.view.MotionEvent
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.UserActionEnum
import java.text.DateFormat.getDateTimeInstance
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun now(): String{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val a = getDateTimeInstance()
    val formatter = SimpleDateFormat(pattern)
    return formatter.format(Calendar.getInstance().time)
}
fun moneyFormatter(money: Double): String{
    val locale = Locale("pt", "BR")
    val formatter =  NumberFormat.getCurrencyInstance(locale)
    return formatter.format(money)
}

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

