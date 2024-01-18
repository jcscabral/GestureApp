package com.example.gestureapp.ui.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource

@Composable
fun OptionUseScreen(
    useOption: String,
    onOptionClicked: (String)-> Unit,
    onButtonClicked: ()-> Unit

) {
    val useOptionList: List<String> = DataSource.useOption

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Olá participante!",
            //textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Escolha uma opção de uso:",
            //textAlign = TextAlign.Left,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier
            .height(16.dp)
        )
        Row(){

            for (use in useOptionList){
                Row(
                    modifier = Modifier.selectable(
                        selected = useOption == use ,
                        onClick = {
                            onOptionClicked(use)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(
                        selected = useOption == use,
                        enabled = true,
                        onClick = {
                            onOptionClicked(use)
                        }
                    )
                    Text(use)
                }
            }

        }
        Spacer(modifier = Modifier
            .height(16.dp)
        )
        Row(){
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.button_escolher),
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier
            .height(16.dp)
            .padding(start = 6.dp, top = 12.dp, end = 6.dp)
        )
        Text(
            text =
            if (useOption == useOptionList.first()){
                stringResource(id = R.string.text_option_train)
            }
            else{
                stringResource(id = R.string.text_option_test)
            },
            fontSize = 18.sp,
            modifier = Modifier.padding(4.dp)
        )
    }
}