package com.example.gestureapp.ui.pix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun PixCPFScreen(
    textValue: String,
    label: String = "CPF",
    showSheet: Boolean,
    onItemClick: (String )-> Unit,
    onDismissRequest: ()-> Unit
){
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Para quem você vai transferir?",
            style = MaterialTheme.typography.titleLarge,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomKeyboard(
                text= "",
                textField = textValue,
                label = label,
                //showSheet = showSheet,
                onButtonClicked = {}, //TODO
                onItemClick = onItemClick,
                //onDismissRequest =  onDismissRequest
            )
        }
    }
}