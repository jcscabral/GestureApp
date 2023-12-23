package com.example.gestureapp.utils

import java.math.BigInteger
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.round

var initialMoney =  0.00

fun moneyMask(fieldText: String): String {

    val factorTen = BigInteger("10")
    var initialMoneyInt: BigInteger = BigInteger.valueOf(round(initialMoney * 100).toLong())

    if(fieldText =="\u232b"){
        initialMoneyInt = (initialMoneyInt / factorTen)
    }
    else{
        val digitInt = BigInteger(fieldText)//.toInt()
        initialMoneyInt = (initialMoneyInt * factorTen)
        initialMoneyInt += digitInt
    }

    initialMoney = initialMoneyInt.toDouble()/100

    val locale = Locale("pt", "BR")
    val formatter =  NumberFormat.getCurrencyInstance(locale)
    return formatter.format(initialMoney)

}
