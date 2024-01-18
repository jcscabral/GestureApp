package com.example.gestureapp.ui.pix

import android.widget.Toast
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppGestureEvents
import com.example.gestureapp.helpers.AppSensorManager
import com.example.gestureapp.model.BankProductItem
import com.example.gestureapp.ui.components.ProductItem


@Composable
fun PixHomeScreen(
    appSensorManager: AppSensorManager = AppSensorProvider.get(
        UserActionEnum.HORIZONTAL_SWIPE_PIX_RECEIVE),
    onSendPixButtonClick: ()-> Unit,
    modifier: Modifier =  Modifier
) {
    val pixSendServices: List<BankProductItem> = DataSource.pixSendServices
    val pixReceiveServices: List<BankProductItem> = DataSource.pixReceiveServices
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Área PIX",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enviar",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium)
            LazyRow(
                modifier = Modifier
                    .pointerInput(null) {

                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                AppGestureEvents.onPointerEvent(
                                    UserActionEnum.HORIZONTAL_SWIPE_PIX_RECEIVE,
                                    event,
                                    appSensorManager)
                            }
                        }
                    }
            ){
                items(pixSendServices){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        ProductItem(
                            nameId = it.nameId,
                            imageVector = ImageVector.vectorResource(it.imageId) ,
                            userActionEnum = UserActionEnum.HORIZONTAL_SWIPE_PIX_SEND,
                            onButtonClick = {
                                if (it.nameId == R.string.pix_transfer) {
                                    onSendPixButtonClick()
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
            Spacer(modifier = modifier.height(4.dp))
            Text(text = "Receber",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.titleMedium)
            LazyRow(
                modifier = Modifier
                    .padding(8.dp)
            ){
                items(pixReceiveServices){
                    Column(horizontalAlignment = Alignment.CenterHorizontally){
                        ProductItem(
                            nameId = it.nameId,
                            imageVector = ImageVector.vectorResource(it.imageId) ,
                            userActionEnum = UserActionEnum.HORIZONTAL_SWIPE_PIX_RECEIVE,
                            onButtonClick = {
                                Toast.makeText(
                                    context, "Opção inválida na pesquisa!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            modifier = modifier
                        )
                    }
                }
            }
        }
    }
}
