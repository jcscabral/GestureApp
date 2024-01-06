package com.example.gestureapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

val formatter = SimpleDateFormat("yyyy-MM-dd")
val DATE = formatter.format(Calendar.getInstance().time)

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //val userName: String,
    val age: Int,
    val gender: String,
    val isStarted: Boolean = false,
    val isRegistered: Boolean = false,
    val isLogged: Boolean = false,
    val isTrain: Boolean = true,
    val isFinished: Boolean = false,
    val date: String = DATE
)
