package com.example.tiviclon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tiviclon.data.database.dao.UserDao
import com.example.tiviclon.data.database.entities.User

@Database(entities = [User::class], version = 1)
abstract class TiviClonDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}