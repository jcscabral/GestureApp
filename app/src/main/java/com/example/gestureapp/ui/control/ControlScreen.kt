package com.example.gestureapp.ui.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gestureapp.data.database.User

@Composable
fun ItemsLazyColumn(
    data : List<User>,
    modifier: Modifier =  Modifier
) {
    LazyColumn(
        Modifier
            //.fillMaxSize()
            .padding(16.dp)
    ){
        items(data){
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier =  modifier
                    .padding(8.dp)
            ){
                Card(modifier = modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()){
                    Text("Id: ${it.id}",
                        modifier =  Modifier.padding(start = 4.dp))
                    Text("Age: ${it.age}" ,
                        modifier =  Modifier.padding(start = 4.dp))
                    Text("Section: ${it.actionNumber}",
                        modifier =  Modifier.padding(start = 4.dp))
                    Text("Registered: ${it.isRegistered}",
                        modifier =  Modifier.padding(start = 4.dp))
                    Text("Finished: ${it.isFinished}",
                        modifier =  Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}

@Composable
fun ControlScreen(
    currentUserId: Int,
    onNewUser: ()-> Unit,
    allUsers: List<User>,
    modifier: Modifier =  Modifier
){    Column {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onNewUser
        ) {
            Text(
                text = "Novo Usuário",
                fontSize = 16.sp
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        ItemsLazyColumn(
            data = allUsers
        )
    }
}