package com.example.tiviclon.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tiviclon.data.database.converters.Converters
import com.example.tiviclon.data.database.dao.DetailShowDao
import com.example.tiviclon.data.database.dao.FavoriteDao
import com.example.tiviclon.data.database.dao.ShowDao
import com.example.tiviclon.data.database.dao.UserDao
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.data.database.entities.VODetailShow
import com.example.tiviclon.data.database.entities.VOShow

@Database(
    entities = [User::class, VOShow::class, Favorites::class, VODetailShow::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class TiviClonDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun showDao(): ShowDao
    abstract fun favoriteDao(): FavoriteDao

    abstract fun VODetailShow(): DetailShowDao

    companion object {
        private var INSTANCE: TiviClonDatabase? = null

        fun getInstance(context: Context): TiviClonDatabase? {
            if (INSTANCE == null) {
                synchronized(TiviClonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TiviClonDatabase::class.java, "user.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}