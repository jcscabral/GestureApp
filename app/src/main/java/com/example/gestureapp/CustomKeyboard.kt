package com.example.gestureapp

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.utils.moneyMask

@Composable
fun CustomKeyboard() {

    var textValue = remember { mutableStateOf("0") }
    var showSheet = remember { mutableStateOf(true) }

    Column {

        // Disable default keyboard
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            //  UI: just to user check his typing
            TextField(
                value = textValue.value, //textFieldValueState,
                label = { Text(text = "Valor") },
                readOnly = true,
                onValueChange = {
                },
                modifier = Modifier
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                showSheet.value = true
                                Log.i("POINTER_INPUT", "Clicked")
                            }
                        }
                    }
                ,
                singleLine = true //,
                //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // can that affect the results?
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        // Here we actually get the events
        ModalBottomLayout(
            showSheet,
            textValue
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomLayout(
    showSheet: MutableState<Boolean>,
    textValue: MutableState<String>
){
    val digitList = listOf(
        "1", "2", "3", "\u232b", "4", "5", "6", "Done", "7", "8", "9", " ", " ", "0", " ", " "
    )
    // Keep SheetValue.Expanded
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = {
            false
        }
    )

    Column(
    ){
        if(showSheet.value) {
            ModalBottomSheet(
                onDismissRequest = {
                    showSheet.value = false
                },
                scrimColor = Color.Transparent,
                tonalElevation = 5.dp,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                sheetState = sheetState,
                containerColor = Color.Black
            ) {
                Surface(
                    color = Color.Black
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BottomActionSheetView(
                            digitList = digitList,
                            onItemClick = {
                                    data ->
                                textValue.value = moneyMask(data)
                            }
                        )
                        Spacer(modifier = Modifier.padding(vertical = 24.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun BottomActionSheetView(
    digitList: List<String>,
    onItemClick: (data: String)-> Unit
){
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
        state =  gridState,
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ){
        itemsIndexed(
            items = digitList,
            key = { index: Int, _: String ->
                index
            }
        ){ _: Int, item ->
            GridListItemView(
                data = item,
                onItemClick = onItemClick
            )
        }
    }
}


@Composable
fun GridListItemView(
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
        ,
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.DarkGray,
            contentColor = if (data != "Done") Color.White else Color.Cyan,
            disabledContainerColor = Color.Gray,
            disabledContentColor =  Color.White
        ),
        onClick = {onItemClick(data)}
    ){
        Text(
            text = data,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
