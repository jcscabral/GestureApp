package com.example.gestureapp.ui.pix

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.keyboard.CustomKeyboard

@Composable
fun PixMoneyScreen(
    isTest: Boolean,
    madeAttempt: Boolean,
    isMoneyWrong: Boolean,
    balance: Double = 0.0,
    textField: String,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String )-> Unit,
    odifier: Modifier= Modifier

){
    CustomKeyboard(
        isTest =  isTest,
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira o valor",
        textField = textField,
        label = if (madeAttempt && isMoneyWrong) "Valor errado" else "Valor",
        isError = madeAttempt && isMoneyWrong,
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked
    )

}