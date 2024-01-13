package com.example.gestureapp.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.BankProductItem
import com.example.gestureapp.helpers.AppSensorManager
import com.example.gestureapp.helpers.AppEvents
import com.example.gestureapp.moneyFormatter
import com.example.gestureapp.ui.components.ConfirmDialog
import com.example.gestureapp.ui.components.ProductItem



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductsList(
    appSensorManager: AppSensorManager = AppSensorProvider
        .get(UserActionEnum.HORIZONTAL_SWIPE_HOME),
    onAnyButtonClick: () -> Unit = {},
    onPixButtonClick: () -> Unit,

    modifier: Modifier = Modifier
){
    val listOfServices: List<BankProductItem> = DataSource.bankServices

    Column {
        LazyRow(
            modifier = Modifier
                .pointerInput(null) {

                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            AppEvents.onPointerEvent(
                                UserActionEnum.HORIZONTAL_SWIPE_HOME,
                                event,
                                appSensorManager)
                        }
                    }
                }
        ){
            items(listOfServices){
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    ProductItem(
                        nameId = it.nameId,
                        imageVector = it.imageIcon ,
                        userActionEnum = UserActionEnum.HORIZONTAL_SWIPE_HOME_BUTTON,
                        onButtonClick =
                        if (it.nameId == R.string.service_pix) {
                            onPixButtonClick
                        } else {
                            onAnyButtonClick
                        }
                        ,
                        modifier = modifier
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalProducts(
    onButtonClick: ()-> Unit,
    modifier: Modifier = Modifier
) {
    Text(text="Serviços")
    ProductsList(
        onPixButtonClick = onButtonClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    userName: String,
    balance: Double,
    showDialog: MutableState<Boolean>,
    navigateExit: ()-> Unit,
    onButtonClick: ()-> Unit
) {

    Column {
        Text(
            "Olá $userName! n°${AppState.id}, ação ${AppState.actionNumber}",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            "Seu saldo é: ${moneyFormatter(balance)}",
            style = MaterialTheme.typography.titleLarge
        )
        HorizontalProducts(onButtonClick = onButtonClick )
        Spacer(modifier = Modifier.height(8.dp))
        if(showDialog.value){
            ConfirmDialog(
                onDismissRequest = { showDialog.value = false },
                onConfirmation = {
                    Log.i("SHOW", "ConfirmDialog cofirmed")
                    showDialog.value = false
                    navigateExit()
                },
                dialogTitle = "Sair do aplicativo",
                dialogText = "Tem certeza que quer sair do aplicativo?",
                icon = Icons.Default.Info
            )
        }
    }
}

@Preview
@Composable
fun Preview(
    id: Int = 0 ,
    balance: Double = 0.00 ,
){  HomeScreen(
    userName = "Balboa",
    balance = balance ,
    showDialog = remember { mutableStateOf(false) },
    navigateExit = {},
    onButtonClick = {}
)
}

