package com.example.gestureapp.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
@Database(entities =
    [User::class],
    version = 1,
    //autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = false
    )
abstract class UsersDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var Instance: UsersDatabase? = null

        fun getDatabase(context: Context):UsersDatabase{
            //context.deleteDatabase("user_database") //Bradock approach TODO
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UsersDatabase::class.java, "user_database")
                    .build().also { Instance = it }
            }
        }
    }
}