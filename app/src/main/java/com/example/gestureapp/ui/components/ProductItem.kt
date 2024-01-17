package com.example.gestureapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestureapp.data.UserActionEnum
import com.example.gestureapp.helpers.AppGestureEvents
import com.example.gestureapp.ui.theme.Purple40
import com.example.gestureapp.ui.theme.md_theme_dark_secondary


@JvmOverloads
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProductItem(
    nameId: Int,
    imageVector: ImageVector,
    userActionEnum: UserActionEnum,
    onButtonClick: ()-> Unit,
    modifier: Modifier = Modifier
){

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            AppGestureEvents.onPointerEvent(
                                userActionEnum,
                                event
                            )
                        }
                    }
                }
//                .pointerInteropFilter() {
//                    AppGestureEvents.onButtonMotionEvent(userActionEnum, it)
//                }


        ) {

            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_dark_secondary),
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
                modifier = Modifier.size(128.dp)
            ) {
                Icon(
                    imageVector,
                    tint = Purple40,
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .padding(2.dp)
                )
            }
            Text(
                text = stringResource(id = nameId),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)

            )
        }
    }
}

@Preview
@Composable
fun Preview(){
    ProductItem(
        nameId = 0,
        imageVector = Icons.Default.Info ,
        userActionEnum =  UserActionEnum.HORIZONTAL_SWIPE_HOME,
        onButtonClick = {}
    )
}