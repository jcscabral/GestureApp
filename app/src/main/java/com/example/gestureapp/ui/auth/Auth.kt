package com.example.gestureapp.ui.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun AuthScreen(
    id: Int,
    userName: String,
    useOption: String,
    useOptionList: List<String> = DataSource.useOption,
    isPasswordWrong: Boolean,
    isLogged: Boolean ,
    onTrainClicked:(String)->Unit,
    onLoginClick: (String) -> Unit,
    keyboardText: String,
    showSheet: Boolean,
    onDismissRequest: ()-> Unit
)
{
    //var passwordText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Acesso cliente n°${id}",
            style = MaterialTheme.typography.titleLarge,
        )
        if (!isPasswordWrong){
            Text(
                text = ""
            )
        }
        else{
            Text(
                text =  "Senha errada, favor tente novamente"
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = userName,
                onValueChange = { },
                label = { Text(text = "Usuário") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            CustomKeyboard(
                textValue = keyboardText,
                label = if(isPasswordWrong) "Tente novamente" else  "Senha",
                showSheet = showSheet,
                onItemClick = onLoginClick ,
                onDismissRequest =  onDismissRequest
            )
//            OutlinedTextField(
//                value = keyboardText,
////                onValueChange = {
////                    passwordText =  it
////                },
//                label = {
//                    if(isPasswordWrong){
//                        Text(text = "Tente novamente")
//                        //TODO show visually w modifier
//                    }
//                    else{
//                        Text(text = "Senha")
//                    }
//                },
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
//                )
//                ,
////                keyboardOptions = KeyboardOptions.Default.copy(
////                    keyboardType = KeyboardType.Number,
////                    imeAction = ImeAction.Done
////                ),
//                modifier = Modifier.fillMaxWidth(),
//                singleLine = true
//            )
            if(!isLogged){
                for (use in useOptionList){
                    Row(
                        modifier = Modifier.selectable(
                            selected = useOption == use ,
                            onClick = {
                                onTrainClicked(use)
                            }
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        RadioButton(
                            selected = useOption == use,
                            onClick = {
                                onTrainClicked(use)
                            }
                        )
                        Text(use)
                    }
                }
            }
//            Button(
//                modifier = Modifier.fillMaxWidth(),
//                onClick = {
//                    onLoginClick(keyboardText)
//                }
//            ) {
//                Text(
//                    text = stringResource(R.string.button_entrar),
//                    fontSize = 16.sp
//                )

            //}
        }
    }
}
