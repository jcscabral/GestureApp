package com.example.gestureapp.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun AuthScreen(
    //id: Int,
    //userName: String,
    madeAttempt: Boolean,
    isPasswordWrong: Boolean,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String) -> Unit,
    textField: String,
    modifier: Modifier= Modifier
)
{
    val context = LocalContext.current

    CustomKeyboard(
        text = "Olá cliente, seja bem-vindo!",
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
