package com.example.gestureapp.ui.pix

import android.widget.Toast
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.custom.CustomKeyboard
import com.example.gestureapp.ui.theme.md_theme_dark_onError


@Composable
fun PixCpfScreen(
    madeAttempt: Boolean,
    isCpfWrong: Boolean,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String) -> Unit,
    textField: String
){
    CustomKeyboard(
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira a chave do PIX",
        textField = textField,
        label = if (madeAttempt && isCpfWrong) "CPF errado" else "Cpf",
        isError = madeAttempt && isCpfWrong,
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked,
    )
}