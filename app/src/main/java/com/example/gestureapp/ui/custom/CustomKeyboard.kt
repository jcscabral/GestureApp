package com.example.gestureapp.ui.custom

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.data.DataSource

@Composable
fun CustomKeyboard(
    //customKeyboardViewModel: CustomKeyboardViewModel = CustomKeyboardViewModel()
    textValue: String,
    label: String,
    showSheet: Boolean,
    onItemClick: (String )-> Unit,
    onDismissRequest: ()-> Unit
) {

    Column {
        // Disable default keyboard, anyways
        CompositionLocalProvider(
            LocalTextInputService provides null
        ) {
            //  UI: just to user check his typing
            OutlinedTextField(
                //value = customKeyboardViewModel.textUiState.textValue,
                value = textValue,
                label = { Text(text = label) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                readOnly = true,
                onValueChange = {
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .pointerInput(Unit) {
//                        awaitPointerEventScope {
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
//                        }
                    }
                ,
                singleLine = true
            )
        }
        Spacer(modifier = Modifier.padding(8.dp))
        // Here we actually get the events
        ModalBottomLayout(
            //customKeyboardViewModel
            showSheet = showSheet,
            onItemClick = onItemClick ,
            onDismissRequest = onDismissRequest
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomLayout(
    digitList: List<String> =  DataSource.keyboardDigits,
    showSheet: Boolean,
    onItemClick: (String )-> Unit,
    onDismissRequest: ()-> Unit
){
    // Keep SheetValue.Expanded
    val sheetState = rememberModalBottomSheetState(
        confirmValueChange = {
            false
        }
    )
    //val coroutineScope = rememberCoroutineScope()

    Column(
    ){
        //if(customKeyboardViewModel.textUiState.showSheet) {
        if(showSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    //coroutineScope.launch{
                        onDismissRequest
                    //customKeyboardViewModel.onDismissRequest()
                    //}
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
                                //coroutineScope.launch {
                                    //customKeyboardViewModel.onItemClick(it)
                                    onItemClick(it)
                                //}
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
