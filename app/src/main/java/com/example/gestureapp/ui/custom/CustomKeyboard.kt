package com.example.gestureapp.ui.custom

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
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.AppSensorProvider
import com.example.gestureapp.R
import com.example.gestureapp.data.DataSource
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.model.AppSensorManager
import com.example.gestureapp.ui.theme.GestureAppTheme


@Composable
fun CustomKeyboard(
    text: String,
    textField: String,
    label: String,
    onButtonClicked: () -> Unit,
    onItemClick: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = text
            )
            OutlinedTextField(
                value = textField,
                label = { Text(text = label) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                readOnly = true,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp)
                    .pointerInput(Unit) {//TODO
                        awaitPointerEventScope {
//                            while (true) {
//                                val event = awaitPointerEvent()
//                                customKeyboardViewModel.updateUiState(
//                                    customKeyboardViewModel.itemUiState.copy(
//                                        textValue = customKeyboardViewModel.itemUiState.textValue,
//                                        showSheet = true
//                                    )
//                                )
//                                Log.i("POINTER_INPUT", "Clicked")
//                            }
                        }
                    },
                singleLine = true
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onButtonClicked
            ) {
                Text(
                    text = stringResource(R.string.button_entrar),
                    fontSize = 16.sp
                )
            }
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                KeyboardGrid(
                    onItemClick = onItemClick

                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(8.dp))
}

@Composable
fun KeyboardGrid(
    digitList: List<String> =  DataSource.keyboardDigits,
    onItemClick: (data: String)-> Unit
){
    val gridState = rememberLazyGridState()

    Surface(
        color = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
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
            .pointerInput(Unit) {//TODO
                awaitPointerEventScope {

                }
            }
        ,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.DarkGray,
            contentColor = if (data != "OK") Color.White else Color.Cyan,
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
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CustomKeyboardPreview() {
    GestureAppTheme {
        CustomKeyboard(
            text = "Olá",
            textField = "",
            label = "label",
            onButtonClicked= {},
            onItemClick = {}
        )
    }
}