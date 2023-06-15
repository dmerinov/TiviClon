package com.example.tiviclon.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tiviclon.data.database.dao.ShowDao
import com.example.tiviclon.data.database.dao.UserDao
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.data.database.entities.VOShow

@Database(entities = [User::class, VOShow::class], version = 1)
abstract class TiviClonDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun showDao(): ShowDao
}