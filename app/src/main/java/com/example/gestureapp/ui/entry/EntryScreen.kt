package com.example.gestureapp.ui.entry

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource

@Composable
fun SignIn(
    userName: String,
    age: String,
    gender: String,
    genderList: List<String> = DataSource.gender,
    onAgeValueChange: (String)-> Unit,
    onGenderClick: (String)-> Unit,
    onButtonClick:()->Unit,
    modifier: Modifier =  Modifier
)
{
    val focusRequester = remember { FocusRequester() }
    var textFieldLoaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registrar",
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
                label = {Text(text = stringResource(R.string.label_usuario))},
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
                value = age,
                onValueChange = onAgeValueChange,
                label = {Text(text = stringResource(R.string.label_idade))},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        if (!textFieldLoaded) {
                            focusRequester.requestFocus() // IMPORTANT
                            textFieldLoaded = true // stop cyclic recompositions
                        }
                    }
            )
            for (sex in genderList){
                Row(
                    modifier = Modifier.selectable(
                        selected = gender == sex,
                        onClick = {
                            onGenderClick(sex)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = gender == sex,
                        onClick = {
                            onGenderClick(sex)
                        }
                    )
                    Text(sex)
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClick
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
fun EntryScreen(
    userName: String,
    age: String,
    gender: String,
    //isRegistered: Boolean,
    onAgeValueChange: (String) -> Unit,
    onGenderClick: (String) -> Unit,
    onButtonClicked: () -> Unit
) {
        SignIn(
            userName = userName,
            age = age,
            gender = gender ,
            onAgeValueChange = onAgeValueChange,
            onGenderClick = onGenderClick,
            onButtonClick = onButtonClicked
        )
}