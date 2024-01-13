package com.example.gestureapp.ui.custom

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.data.CPF_SIZE
import com.example.gestureapp.data.FACTOR_TEN
import com.example.gestureapp.data.KEY_BACKSPACE
import com.example.gestureapp.data.KEY_OK
import com.example.gestureapp.data.MAX_PASSWORD_SIZE
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppSensorManager
import com.example.gestureapp.moneyFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.math.BigInteger
import kotlin.math.max
import kotlin.math.round

enum class KeyboardTypeEnum{
    MONEY, CPF, PASSWORD,
}
class CustomKeyboardViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val moneyMask = MoneyText()
    private val cpfMask = CpfText()
    private val passwordMask = PasswordText()

    private val _uiState = MutableStateFlow(KeyboardUiState())
    val uiState: StateFlow<KeyboardUiState> = _uiState.asStateFlow()

    private val loginSensorState: AppSensorManager =  AppSensorProvider.get(UserActionEnum.KEYBOARD_LOGIN)
    private val moneySensorState: AppSensorManager =  AppSensorProvider.get(UserActionEnum.KEYBOARD_PIX_MONEY)
    private val cpfSensorState: AppSensorManager =  AppSensorProvider.get(UserActionEnum.KEYBOARD_PIX_CPF)

    fun activeSensor(){
        viewModelScope.launch {
            when(_uiState.value.keyboardType) {
                KeyboardTypeEnum.MONEY -> loginSensorState.active()
                KeyboardTypeEnum.CPF -> moneySensorState.active()
                else -> cpfSensorState.active()
            }
        }
    }
    fun disableAllSensors(){
        viewModelScope.launch {
            loginSensorState.active(false)
            moneySensorState.active(false)
            cpfSensorState.active(false)
        }
    }
    private fun setKeyboardType(keyboardTypeEnum: KeyboardTypeEnum){
        _uiState.update { state ->
            state.copy(
                keyboardType = keyboardTypeEnum
            )
        }
        setTextValue("")
    }
    fun setMoneyType(){
        clear()
        setKeyboardType(KeyboardTypeEnum.MONEY)
    }
    fun setCpfType(){
        clear()
        setKeyboardType(KeyboardTypeEnum.CPF)
    }
    fun setPasswordType(){
        clear()
        setKeyboardType(KeyboardTypeEnum.PASSWORD)
    }
    fun getTextAsDouble(): Double{
        return _uiState.value.textValue
            .replace("R$","")
            .replace(".","")
            .replace(",",".")
            .trim()
            .toDouble()
    }
    private fun setTextValue(text: String){
        _uiState.update { state ->
            state.copy(
                textValue = text
            )
        }
    }
    fun clear(){
        setTextValue("")
        clearMask()
    }
    private fun clearMask(){
        if(_uiState.value.keyboardType == KeyboardTypeEnum.MONEY){
            moneyMask.reset()
        }
        else if (_uiState.value.keyboardType == KeyboardTypeEnum.CPF){
            cpfMask.reset()
        }
        else{
            passwordMask.reset()
        }
    }
    fun madeAttempt(made: Boolean =  true){
        _uiState.update {state->
            state.copy(
                madeAttempt = made
            )
        }
    }
    fun onItemClick(text: String): Boolean{

        if (text == KEY_OK) {
            madeAttempt()
            return false
        }

        madeAttempt(false)
        val formattedText = when(_uiState.value.keyboardType){
            KeyboardTypeEnum.MONEY -> moneyMask.add(text)
            KeyboardTypeEnum.CPF -> cpfMask.add(text)
            else -> passwordMask.add(text)
        }
        setTextValue(formattedText)

        return true
    }
}

data class KeyboardUiState(
    val textValue: String = "",
    val keyboardType: KeyboardTypeEnum = KeyboardTypeEnum.MONEY,
    val madeAttempt: Boolean = false
)

class MoneyText(){

    private var initialMoney =  0.00
    fun add(text: String): String {

        var initialMoneyInt: BigInteger = BigInteger.valueOf(round(initialMoney * 100).toLong())

        if(text == KEY_BACKSPACE){
            initialMoneyInt = (initialMoneyInt / FACTOR_TEN)
        }
        else{
            val digitInt = BigInteger(text)
            initialMoneyInt = (initialMoneyInt * FACTOR_TEN)
            initialMoneyInt += digitInt
        }

        initialMoney = initialMoneyInt.toDouble()/100

        return moneyFormatter(initialMoney)


    }

    fun reset(){
        initialMoney = 0.0
    }
}

fun String.addCharAtIndex(char: Char, index: Int) =
    StringBuilder(this).apply { insert(index, char) }.toString()

class CpfText(){

    private var initialValue = ""

    fun add(fieldText: String): String {
        if(fieldText == KEY_BACKSPACE){
            initialValue = initialValue.substring(
                0, max(initialValue.length -1, 0)
            )
        }
        else if(initialValue.length < CPF_SIZE ){
            initialValue+=fieldText
        }
        return format(initialValue)
    }

    private fun format(text: String): String{
        var formattedText = text

        if(text.length > 3){
            formattedText = text.addCharAtIndex('.', 3)
        }
        if(text.length > 6){
            formattedText = formattedText.addCharAtIndex('.', 7)
        }
        if(text.length > 9){
            formattedText = formattedText.addCharAtIndex('-', 11)
        }
        return formattedText
    }

    fun reset(){
        initialValue = ""
    }

}

class PasswordText(){

    private var initialValue = ""
    private val onePositionBack = 1
    fun add(text: String): String {
        if(text == KEY_BACKSPACE){
            initialValue = initialValue.substring(
                0, max(initialValue.length - onePositionBack, 0)
            )
        }
        else if(initialValue.length < MAX_PASSWORD_SIZE ){
            initialValue+=text
        }
        return initialValue
    }

    fun reset(){
        initialValue = ""
    }

}