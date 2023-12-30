package com.example.gestureapp.ui.entry

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestureapp.AppViewModelProvider
import kotlinx.coroutines.launch
import com.example.gestureapp.data.DataSource


@Composable
fun SignIn(
    userUiState: UserUiState,
    //loginViewModel: LoginViewModel,
    genderList: List<String>,
    ageOnValueChange: (String)-> Unit,
    genderOnClick: (String)-> Unit,
    buttonOnClick:()->Unit,
    modifier: Modifier =  Modifier
)
{
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SEU BANCO",
            style = MaterialTheme.typography.titleLarge,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = userUiState.userName,
                onValueChange = { },
                label = {Text(text = "Usuário")},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                singleLine = true
            )
            OutlinedTextField(
                value = userUiState.age,
                onValueChange = ageOnValueChange,
                label = {Text(text = "Idade")},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done

                    ),
                singleLine = true

            )
            Text(
                text = userUiState.age
            )
            // loginViewModel.genderOption.forEach { item ->
            for (gender  in genderList){
                Row(
                    modifier = Modifier.selectable(
                        selected = userUiState.gender == gender,
                        onClick = {
                            genderOnClick(gender)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = userUiState.gender == gender,
                        onClick = {
                            genderOnClick(gender)
                        }
                    )
                    Text(gender)
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = buttonOnClick
            ) {
                Text(
                    text = "Salvar",
                    fontSize = 16.sp
                )
            }
        }
    }

}

@Composable
fun LogIn(
    //viewModel: LoginViewModel
    userName: String,
    password: String = ""
)
{
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "SEU BANCO",
            style = MaterialTheme.typography.titleLarge,
        )
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
        }
    }
}


@Composable
fun EntryScreen(
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory),
    isStarted: Boolean,
    isRegistered: Boolean,
    onUserFinished: ()-> Unit,
    //onNewUser: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val userState by loginViewModel.userUiState.collectAsState()
//    if(!userState.isStarted){
//        ControlArea(
//            loginViewModel = loginViewModel,
//            //onUserFinished = onUserFinished,
//            onNewUser =  onNewUser
//        )
//    }
    if (!userState.isRegistered){
        SignIn(
            userUiState = userState,
            genderList = DataSource().gender,
            ageOnValueChange = { loginViewModel.setAge(it) },
            genderOnClick = { loginViewModel.setGender(it) },
            buttonOnClick = {
                coroutineScope.launch {
                    if (loginViewModel.saveUser()){
                        //TODO start
                    }
                    else{
                        //TODO show error
                    }
                }
            }
        )
    }
    else{
        LogIn(
            userState.userName
        )
    }
}