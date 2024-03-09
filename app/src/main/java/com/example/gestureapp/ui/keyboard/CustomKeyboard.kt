package com.example.gestureapp.ui.keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppGestureEvents
import com.example.gestureapp.ui.theme.GestureAppTheme
import com.example.gestureapp.ui.theme.CyanKeyboard

@Composable
fun CustomKeyboard(
    isTest: Boolean ,
    userActionEnum: UserActionEnum,
    text: String,
    textField: String,
    label: String,
    isError: Boolean =  false,
    onButtonClicked: () -> Unit,
    onItemClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier =  Modifier
                    .padding(16.dp)
            )
            OutlinedTextField(
                value = textField,
                label = { Text(text = label) },
                isError = isError,
                readOnly = true,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                singleLine = true
            )
            Column(
                modifier =  Modifier
                    .padding(16.dp)
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth()
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                if(isTest){
                                    while (true) {
                                        val event = awaitPointerEvent()
                                        AppGestureEvents.onPointerEvent(
                                            userActionEnum,
                                            event
                                        )
                                    }
                                }
                            }
                        }
                    ,
                    onClick = onButtonClicked
                ) {
                    Text(
                        text = stringResource(R.string.button_confirmar),
                        fontSize = 16.sp
                    )
                }
            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyboardGrid(
                    isTest,
                    userActionEnum,
                    onItemClick = onItemClick

                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardGrid(
    isTest: Boolean,
    userActionEnum: UserActionEnum,
    digitList: List<String> =  DataSource.keyboardDigits,
    onItemClick: (data: String)-> Unit
){
    val gridState = rememberLazyGridState()

    Surface(
        color = Color.Black,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
                state = gridState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                itemsIndexed(
                    items = digitList,
                    key = { index: Int, _: String ->
                        index
                    }
                ) { _: Int, item ->
                    ButtonDigit(
                        isTest,
                        userActionEnum,
                        data = item,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonDigit(
    isTest: Boolean ,
    userActionEnum: UserActionEnum,
    data: String,
    onItemClick: (data: String)-> Unit
){

    TextButton(
        enabled = data != "",
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(4.dp)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    if(isTest){
                        while (true) {
                            val event = awaitPointerEvent()
                            AppGestureEvents.onPointerEvent(
                                userActionEnum,
                                event,
                                data
                            )
                        }
                    }
                }
            }
        ,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.DarkGray,
            contentColor = if (data != "OK") Color.White else CyanKeyboard,
            disabledContainerColor = Color.Gray,
            disabledContentColor =  Color.White
        ),
        onClick = {
            if(data != ""){
                onItemClick(data)}
        }
    ){
        Text(
            text = data,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CustomKeyboardPreview() {
    GestureAppTheme {
        CustomKeyboard(
            isTest = false,
            userActionEnum =  UserActionEnum.KEYBOARD_PIX_CPF,
            text = "Olá",
            textField = "",
            label = "label",
            onButtonClicked= {},
            onItemClick = {}
        )
    }
}