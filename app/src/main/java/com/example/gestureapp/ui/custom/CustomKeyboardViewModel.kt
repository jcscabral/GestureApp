package com.example.gestureapp.ui.custom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.gestureapp.data.DataSource
import java.math.BigInteger
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.round

enum class KeyboardTypeEnum{
    MONEY, CPF, PASSWORD,
}
class CustomKeyboardViewModel(): ViewModel() {

    private var moneyMask = MoneyMask()
    var textUiState by mutableStateOf(TextUiState())

    var currentType = KeyboardTypeEnum.MONEY

    fun setMoneyType(){
        currentType = KeyboardTypeEnum.MONEY
        moneyMask.reset()
        //moneyMask = MoneyMask()
    }
    fun setCpfType(){
        currentType = KeyboardTypeEnum.CPF
    }

    fun setPasswordType(){
        currentType = KeyboardTypeEnum.PASSWORD
    }

//    fun updateUiState(itemUiState: TextUiState){
//        this.textUiState =  TextUiState(
//            textValue = itemUiState.textValue,
//            showSheet = itemUiState.showSheet
//        )
//    }

    fun onDismissRequest(){
        textUiState =  textUiState.copy(
            showSheet = false
        )

//        itemUiState =  ItemUiState(
//            textValue = itemUiState.textValue,
//            showSheet = false
//        )
    }

    fun onItemClick(text: String): Boolean{
        if (text == DataSource.keyboardOK) return false
        val currentText = if (currentType == KeyboardTypeEnum.MONEY) moneyMask.add(text) else text

        textUiState =  textUiState.copy(
            textValue = currentText
        )
        return true
//        itemUiState =  ItemUiState(
//            textValue = moneyMask.add(text),
//            showSheet = itemUiState.showSheet
//        )
    }

}

data class TextUiState(
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

    fun reset(){
        initialMoney = 0.0
    }
}

class NumberMask(fieldText: String){
    private var initialValue = ""

    fun add(){
    }

}