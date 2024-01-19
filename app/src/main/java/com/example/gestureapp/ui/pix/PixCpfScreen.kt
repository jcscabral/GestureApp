package com.example.gestureapp.ui.pix

import androidx.compose.runtime.Composable
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.keyboard.CustomKeyboard


@Composable
fun PixCpfScreen(
    isTest: Boolean,
    madeAttempt: Boolean,
    isCpfWrong: Boolean,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String) -> Unit,
    textField: String
){
    CustomKeyboard(
        isTest = isTest,
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira a chave do PIX",
        textField = textField,
        label = if (madeAttempt && isCpfWrong) "CPF errado" else "Cpf",
        isError = madeAttempt && isCpfWrong,
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked,
    )
}