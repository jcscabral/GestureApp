package com.example.gestureapp.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.AppState
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.BankProductItem
import com.example.gestureapp.helpers.AppSensorManager
import com.example.gestureapp.helpers.AppGestureEvents
import com.example.gestureapp.moneyFormatter
import com.example.gestureapp.ui.components.ConfirmDialog
import com.example.gestureapp.ui.components.ProductItem
import com.example.gestureapp.ui.theme.Purple40
import com.example.gestureapp.ui.theme.md_theme_dark_secondary

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
                            AppGestureEvents.onPointerEvent(
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
    Text(
        text="Serviços",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )
    ProductsList(
        onPixButtonClick = onButtonClick,
        modifier = Modifier
            .padding(16.dp)
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = md_theme_dark_secondary)
            ,
            ) {
            Text(
                "$userName! n°${AppState.id}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                "Ação ${AppState.actionNumber}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
            ) {
                Text(
                    "Saldo: ${moneyFormatter(balance)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Purple40,
                    textAlign = TextAlign.Center
                )
            }
            HorizontalProducts(onButtonClick = onButtonClick)
            Spacer(modifier = Modifier.height(8.dp))
            if (showDialog.value) {
                ConfirmDialog(
                    onDismissRequest = { showDialog.value = false },
                    onConfirmation = {
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

