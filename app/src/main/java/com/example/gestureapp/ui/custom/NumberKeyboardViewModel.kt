package com.example.gestureapp.ui.custom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.math.BigInteger
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.round

class CustomKeyboardViewModel(): ViewModel() {

    var moneyMask = MoneyMask()
    var itemUiState by mutableStateOf(ItemUiState())

    fun updateUiState(itemUiState: ItemUiState){
        this.itemUiState =  ItemUiState(
            textValue = itemUiState.textValue,
            showSheet = itemUiState.showSheet
        )
    }

    fun onDismissRequest(){
        itemUiState =  ItemUiState(
            textValue = itemUiState.textValue,
            showSheet = false
        )
    }

    fun onItemClick(text: String){
        itemUiState =  ItemUiState(
            textValue = moneyMask.add(text),
            showSheet = itemUiState.showSheet
        )
    }

}

data class ItemUiState(
    val textValue: String =  "",
    val showSheet: Boolean = true
)

class MoneyMask(){

    private var initialMoney =  0.00
    fun add(fieldText: String): String {

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
}