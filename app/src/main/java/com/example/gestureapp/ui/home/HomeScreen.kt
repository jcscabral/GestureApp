package com.example.gestureapp.ui.home

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.BankProductItem
import com.example.gestureapp.model.AppSensorManager
import com.example.gestureapp.moneyFormatter
import com.example.gestureapp.ui.components.ConfirmDialog
import com.example.gestureapp.ui.components.ProductItem


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductsList(
    onPixButtonClick: () -> Unit,
    swipeSensorManager: AppSensorManager = AppSensorProvider.get(
        UserActionEnum.HORIZONTAL_SWIPE),
    buttonSensorManager: AppSensorManager = AppSensorProvider.get(
        UserActionEnum.BUTTON_ENTER_PIX),
    modifier: Modifier = Modifier
){

    val listOfServices: List<BankProductItem> = DataSource.bankServices

    var actionCount by rememberSaveable { mutableStateOf(0) }
    var actionType by remember { mutableStateOf("") }
    var pressureEvent by remember { mutableFloatStateOf(.0f) }
    var pressure = pressureEvent.toString()
    var pointerSizeEvent by remember { mutableFloatStateOf(.0f) }
    var pointerSize = pointerSizeEvent.toString()

    var posXEvent by remember { mutableFloatStateOf(.0f) }
    var posX = posXEvent.toString()
    var posYEvent by remember { mutableFloatStateOf(.0f) }
    var posY = posYEvent.toString()

    var touchMinorEvent by remember { mutableFloatStateOf(.0f) }
    var touchMinor = touchMinorEvent.toString()
    var touchMajorEvent by remember { mutableFloatStateOf(.0f) }
    var touchMajor = touchMajorEvent.toString()

    var tsTimeEvent by remember { mutableStateOf(0L) }
    var tsTime = tsTimeEvent.toString()
    var tsDownTimeEvent by remember { mutableStateOf(0L) }
    var tsDownTime = tsDownTimeEvent.toString()


    var _actionType by remember { mutableStateOf("") }
    var _pressureEvent by remember { mutableFloatStateOf(.0f) }
    var _pressure = _pressureEvent.toString()
    var _pointerSizeEvent by remember { mutableFloatStateOf(.0f) }
    var _pointerSize = _pointerSizeEvent.toString()

    var _posXEvent by remember { mutableFloatStateOf(.0f) }
    var _posX = _posXEvent.toString()
    var _posYEvent by remember { mutableFloatStateOf(.0f) }
    var _posY = _posYEvent.toString()


    var _tsTimeEvent by remember { mutableStateOf(0L) }
    var _tsTime = _tsTimeEvent.toString()

    val filter: PointerEventType? = null

    Row(){
        Text(text = "Action: $actionType | $_actionType")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(){
        Text(text = "Pressure: $pressure | $_pressure")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(){
        Text(text = "PosX: $posX | $_posX")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(){
        Text(text = "PosY: $posY  | $_posY")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(){
        Text(text = "Size: $pointerSize | $_pointerSize")
    }
    Spacer(modifier = Modifier.size(2.dp))
    Row(){
        Text(text = "Time: $tsTime | $_tsTime")
    }
    Column(
        modifier = Modifier
            .pointerInteropFilter() {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        actionCount++
                        actionType = "DOWN"
                    }
                    MotionEvent.ACTION_UP -> {
                        actionType = "UP"
                    }
                    MotionEvent.ACTION_MOVE -> {
                        actionType = "MOVE"
                    }
                }
                Log.i("COLUMN", "ActionMove: ${MotionEvent.ACTION_MOVE}")
                pressureEvent = it.pressure
                pointerSizeEvent = it.size
                posXEvent = it.x
                posYEvent =  it.y
                touchMinorEvent = it.touchMinor
                touchMajorEvent = it.touchMajor
                tsTimeEvent = it.eventTime
                tsDownTimeEvent = it.downTime
                false
            }

    ) {

        LazyRow(
            modifier = Modifier
                .pointerInput(filter) {

                    awaitPointerEventScope {

                        while (true) {

                            //_pointerSizeEvent = size.toSize().height * size.toSize().width // Wrong!
                            val event = awaitPointerEvent()
                            // handle pointer event
                            //if (filter == null || event.type == filter) {

                            for (change in event.changes) {

                                if (!swipeSensorManager?.activated!!) {
                                    swipeSensorManager?.active()
                                }

                                val position = change.position
                                _posXEvent = position.x
                                _posYEvent = position.y
                                _pointerSizeEvent = position.getDistanceSquared()
                                _actionType = change.type.toString()
                                _pressureEvent = change.pressure
                                _tsTimeEvent = change.uptimeMillis

                                if (!change.pressed) {
                                    swipeSensorManager.active(false)
                                }


                            }
                        }
                    }
                }
        ){
            items(listOfServices){
                Column(horizontalAlignment = Alignment.CenterHorizontally){
                    ProductItem(
                        appSensorManager = if(it.nameId == R.string.service_pix) buttonSensorManager else null ,
                        nameId = it.nameId,
                        imageVector = it.imageIcon ,
                        onButtonClick =
                            if(it.nameId == R.string.service_pix){
                                onPixButtonClick
                            } else{ {} }
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
    Text(text="Serviços") //TODO think of design
    ProductsList(
        onPixButtonClick = onButtonClick,
        modifier = modifier
    )
}

//@Composable
//fun PIXTransfer(keyboardSensorManager: ComponentSensorManager,
//                modifier: Modifier =  Modifier
//){
//    // TODO implement keyboardSensorManager
//    var pixKey by remember { mutableStateOf("") }
//    val filter: PointerEventType? = null
//
//    // events variables
//    var actionType by remember { mutableStateOf("") }
//    var pressureEvent by remember { mutableFloatStateOf(.0f) }
//    var pressure = pressureEvent.toString()
//    var pointerSizeEvent by remember { mutableFloatStateOf(.0f) }
//    var pointerSize = pointerSizeEvent.toString()
//    var tsTimeEvent by remember { mutableLongStateOf(0L) }
//    var tsTime = tsTimeEvent.toString()
//    var keysPressed by remember { mutableStateOf("") }
//
//    Column(
//
//    ){
//        Spacer(modifier = Modifier.size(2.dp))
//        Text(text = "ActionType: $actionType")
//        Spacer(modifier = Modifier.size(2.dp))
//        Text(text = "Pressure: $pressure")
//        Spacer(modifier = Modifier.size(2.dp))
//        Text(text = "Keys pressed: $keysPressed")
//        Spacer(modifier = Modifier.size(2.dp))
//        Text(text = stringResource(R.string.insert_pix_key),
//            modifier = Modifier
//                .padding(bottom = 16.dp, top = 40.dp)
//                .align(alignment = Alignment.Start)
//        )
//        Text(text = "TsTime: $tsTime")
//        TextField(
//            value = pixKey,
//            label = { Text(stringResource(R.string.key_pix_label)) },
//            onValueChange = {pixKey = it} ,
//            modifier = Modifier
//                .pointerInput(filter) {
//                    awaitPointerEventScope {
//                        while (true) {
//                            val event = awaitPointerEvent()
//                            for (change in event.changes) {
//                                if (!keyboardSensorManager.activated) {
//                                    keyboardSensorManager.active()
//                                }
//
//                                actionType = change.type.toString()
//                                pressureEvent = change.pressure
//                                tsTimeEvent = change.uptimeMillis
//
//                                if (!change.pressed) {
//                                    keyboardSensorManager.active(false)
//                                }
//                                Log.i("KEYBOARD", "$actionType - $pressureEvent - $tsTimeEvent")
//                            }
//
//                        }
//                    }
//                }
//                .onKeyEvent {
//                    val keyUtf16 = it.utf16CodePoint
//                    keysPressed += keyUtf16
//                        .toChar()
//                        .toString()
//                    Log.i("KEYBOARD", "Key pressed:${keyUtf16.toString()}")
//                    false
//                }
//            ,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // can that affect the results?
//        )
//        Text(
//            text = stringResource(R.string.recipient, pixKey) ,
//        )
//
//    }
//
//}

@Composable
fun HomeScreen(
    id: Int,
    session: Int,
    balance: Double,
    showDialog: MutableState<Boolean>,
    navigateExit: ()-> Unit,
    onButtonClick: ()-> Unit
) {

    Column {
        Text(
            "Olá você! n°$id, sessão $session",
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
        id = id,
        session = 0,
        balance = balance ,
        showDialog = remember { mutableStateOf(false) },
        navigateExit = {},
        onButtonClick = {}
    )
}

