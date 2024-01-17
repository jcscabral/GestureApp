package com.example.gestureapp.ui.pix

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun PixMoneyScreen(
    madeAttempt: Boolean,
    isMoneyWrong: Boolean,
    balance: Double,
    textField: String,
    label: String = "Valor",
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String )-> Unit,
    odifier: Modifier= Modifier

){
    val context = LocalContext.current

    CustomKeyboard(
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira o valor",
        textField = textField,
        label = if (madeAttempt && isMoneyWrong) "Valor errado" else "Valor",
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked,
    )
    if (madeAttempt && isMoneyWrong){
        Toast.makeText(
            context, "Valor incorreto",
            Toast.LENGTH_SHORT).show()
    }
//    Column(
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Saldo atual: ${moneyFormatter(balance)}"
//        )
//        Text(
//            text = "Insira o valor da transferência",
//            style = MaterialTheme.typography.titleLarge,
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {

//        }
//    }
}