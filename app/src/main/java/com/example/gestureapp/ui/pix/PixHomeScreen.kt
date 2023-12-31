package com.example.gestureapp.ui.pix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.model.BankProductItem
import com.example.gestureapp.moneyFormatter
import com.example.gestureapp.ui.components.ProductItem
import com.example.gestureapp.ui.custom.CustomKeyboard


@Composable
fun PixHomeScreen(
    pixSendServices: List<BankProductItem> = DataSource.pixSendServices,
    pixReceiveServices: List<BankProductItem> = DataSource.pixReceiveServices,
    onSendPixButtonClick: ()-> Unit,
    modifier: Modifier =  Modifier
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Área PIX",
            style = MaterialTheme.typography.titleLarge,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Enviar")
            LazyRow(
                modifier = Modifier
                    .padding(8.dp)
            ){
                items(pixSendServices){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        ProductItem(
                            buttonSensorManager = null , //hummm... ??
                            nameId = it.nameId,
                            imageVector = it.imageIcon ,
                            onButtonClick =
                                if(it.nameId == R.string.pix_transfer){
                                    onSendPixButtonClick
                                }
                                else{ {} }

                            , //TODO
                            modifier = modifier
                        )
                    }
                }
            }
            Spacer(modifier = modifier.height(4.dp))
            Text("Receber")
            LazyRow(
                modifier = Modifier
                    .padding(8.dp)
            ){
                items(pixReceiveServices){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        ProductItem(
                            buttonSensorManager = null , //hummm... ??
                            nameId = it.nameId,
                            imageVector = it.imageIcon ,
                            onButtonClick = {}, //TODO
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun PixMoneyScreen(
    balance: Double,
    textValue: String,
    showSheet: Boolean,
    label: String = "Valor",
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
            text = "Saldo atual: ${moneyFormatter(balance)}"
        )
        Text(
            text = "Insira o valor da transferência",
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
                text = "",
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

@Composable
fun PixReceiverScreen(
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