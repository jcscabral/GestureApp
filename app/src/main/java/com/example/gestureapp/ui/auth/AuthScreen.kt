package com.example.gestureapp.ui.auth

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun AuthScreen(
    //id: Int,
    //userName: String,
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
    val context = LocalContext.current

    CustomKeyboard(
        userActionEnum = userActionEnum,
        text = text,
        textField = textField,
        label = if (madeAttempt && isPasswordWrong) "Senha errada" else "Senha",
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked,
    )
    if (madeAttempt && isPasswordWrong){
        Toast.makeText(
            context, "Senha incorreta",
            Toast.LENGTH_SHORT).show()
    }
}
