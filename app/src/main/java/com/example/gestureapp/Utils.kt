package com.example.gestureapp

import java.text.NumberFormat
import java.util.Locale

fun moneyFormatter(money: Double): String{
    val locale = Locale("pt", "BR")
    val formatter =  NumberFormat.getCurrencyInstance(locale)
    return formatter.format(money)
}