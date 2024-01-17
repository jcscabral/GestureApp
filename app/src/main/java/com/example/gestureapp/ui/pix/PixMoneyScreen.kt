package com.example.gestureapp.ui.pix

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.util.Log
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.custom.CustomKeyboard

@Composable
fun PixMoneyScreen(
    madeAttempt: Boolean,
    isMoneyWrong: Boolean,
    balance: Double = 0.0,
    textField: String,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String )-> Unit,
    odifier: Modifier= Modifier

){
    CustomKeyboard(
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira o valor",
        textField = textField,
        label = if (madeAttempt && isMoneyWrong) "Valor errado" else "Valor",
        isError = madeAttempt && isMoneyWrong,
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked
    )

}