package com.example.gestureapp.ui.pix

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun PixCpfScreen(
    //id: Int,
    //userName: String,
    madeAttempt: Boolean,
    isCpfWrong: Boolean,
    onButtonClicked: ()-> Unit,
    onKeyboardClicked: (String) -> Unit,
    textField: String
){
    val context = LocalContext.current

    CustomKeyboard(
        userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
        text = "Insira a chave do PIX",
        textField = textField,
        label = if (madeAttempt && isCpfWrong) "CPF errado" else "Cpf",
        onButtonClicked = onButtonClicked,
        onItemClick = onKeyboardClicked,
    )
    if (madeAttempt && isCpfWrong){
        Toast.makeText(
            context, "CPF incorreta",
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
//            text = "Para quem você vai transferir?",
//            style = MaterialTheme.typography.titleLarge,
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            CustomKeyboard(
//                userActionEnum = UserActionEnum.KEYBOARD_PIX_CPF,
//                text= "",
//                textField = textValue,
//                label = label,
//                //showSheet = showSheet,
//                onButtonClicked = {}, //TODO
//                onItemClick = onItemClick,
//                //onDismissRequest =  onDismissRequest
//            )
//        }
//    }
}