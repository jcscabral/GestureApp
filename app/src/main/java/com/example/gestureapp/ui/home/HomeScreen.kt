package com.example.gestureapp.ui.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import com.example.gestureapp.ui.theme.md_theme_dark_inversePrimary
import com.example.gestureapp.ui.theme.md_theme_dark_onSurfaceVariant
import com.example.gestureapp.ui.theme.md_theme_dark_outline
import com.example.gestureapp.ui.theme.md_theme_dark_secondary
import com.example.gestureapp.ui.theme.md_theme_dark_surfaceVariant
import com.example.gestureapp.ui.theme.md_theme_light_secondary

@Composable
fun ProductsList(
    appSensorManager: AppSensorManager = AppSensorProvider
        .get(UserActionEnum.HORIZONTAL_SWIPE_HOME),
    onPixButtonClick: () -> Unit,

    modifier: Modifier = Modifier
){
    val listOfServices: List<BankProductItem> = DataSource.bankServices
    val context = LocalContext.current

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
                        imageVector = ImageVector.vectorResource(it.imageId) ,
                        userActionEnum = UserActionEnum.HORIZONTAL_SWIPE_HOME_BUTTON,
                        onButtonClick = {
                            if (it.nameId == R.string.service_pix) {
                                onPixButtonClick()
                            } else {
                                Toast.makeText(
                                    context, "Opção inválida na pesquisa!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
        color = Purple40,
        modifier = Modifier.padding(top = 16.dp)
    )
    ProductsList(
        onPixButtonClick = onButtonClick,
        modifier = Modifier
            .padding(16.dp)
    )
}

@Composable
fun HomeScreen(
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
                .background(color = md_theme_dark_inversePrimary) //md_theme_dark_secondary
            ,
        ) {
            Text(
                text = "Conta n°${AppState.id}",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            )
            Icon(
                painter = painterResource(
                    R.drawable.meu_banco),
                tint = Color.White,
                contentDescription = "Bem-vindo",
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .align(Alignment.Center)
            )

            Text(
                "Ação ${AppState.actionNumber}",
                style = MaterialTheme.typography.labelLarge,
                color = md_theme_dark_onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .width(256.dp)
                    .align(Alignment.CenterHorizontally)
            ){
                Column(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ){
                    Text(
                        text = "Saldo: ${moneyFormatter(balance)}",
                        color = md_theme_dark_surfaceVariant,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            HorizontalProducts(onButtonClick = onButtonClick)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text="Histórico de Investimentos",
                style = MaterialTheme.typography.titleLarge,
                color = Purple40,
                modifier = Modifier.padding(top = 16.dp)
            )
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ){
                Image(painter = painterResource(
                    R.drawable.grafico_investimento
                ),
                    contentDescription = "Histórico dos investimentos")
            }


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
    id: Int = 1 ,
    balance: Double = 0.00 ,
){  HomeScreen(
    balance = balance ,
    showDialog = remember { mutableStateOf(false) },
    navigateExit = {},
    onButtonClick = {}
)
}

