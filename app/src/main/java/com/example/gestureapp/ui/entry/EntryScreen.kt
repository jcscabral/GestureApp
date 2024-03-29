package com.example.gestureapp.ui.entry

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import kotlin.coroutines.ContinuationInterceptor

@Composable
fun SignIn(
    userName: String,
    age: String,
    gender: String,
    genderList: List<String> = DataSource.gender,
    onAgeValueChange: (String)-> Unit,
    onGenderClick: (String)-> Unit,
    onButtonClick:()->Boolean,
    modifier: Modifier =  Modifier
)
{
    val context = LocalContext.current
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
            text = "Informar idade e sexo...",
            style = MaterialTheme.typography.titleLarge
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
                onValueChange = {},
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
                keyboardActions = KeyboardActions(
                    onDone = {
                        if(!onButtonClick.invoke()){
                            Toast.makeText(
                                context, "Favor preencher todos os campos",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        if (!textFieldLoaded) {
                            focusRequester.requestFocus()
                            textFieldLoaded = true // stop cyclic recompositions
                        }
                    }
            )
            Spacer(Modifier.height(2.dp))
            for (sex in genderList){
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .selectable(
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
            Spacer(Modifier.height(2.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if(!onButtonClick.invoke()){
                        Toast.makeText(
                            context, "Favor preencher todos os campos",
                            Toast.LENGTH_SHORT).show()
                    }
                }
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
    onAgeValueChange: (String) -> Unit,
    onGenderClick: (String) -> Unit,
    onButtonClicked: () -> Boolean
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

@Preview
@Composable
fun Preview(){
    EntryScreen(
        "Favor",
        "43",
        "male",
        {},
        {},
        {true}
    )
}