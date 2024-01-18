package com.example.gestureapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val age: Int,
    val gender: String,
    val actionNumber: Int,
    val isStarted: Boolean = false,
    val isRegistered: Boolean = false,
    val isTest: Boolean = false,
    val isLogged: Boolean = false,
    val isFinished: Boolean = false,
    val initDateTime: String? = null,
    val endDateTime: String? = null
)
