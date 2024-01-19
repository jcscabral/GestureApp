package com.example.gestureapp.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.keyboard.CustomKeyboard


@Composable
fun AuthScreen(
    isTest: Boolean,
    userActionEnum : UserActionEnum,
    text: String,
    textField: String,
    madeAttempt: Boolean,
    isPasswordWrong: Boolean,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String) -> Unit,
    modifier: Modifier= Modifier
)
{
   CustomKeyboard(
       isTest =  isTest,
        userActionEnum = userActionEnum,
        text = text,
        textField = textField,
        label = if (madeAttempt && isPasswordWrong) "Senha errada" else "Senha",
        isError = madeAttempt && isPasswordWrong,
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked
    )
}
