package com.example.tiviclon.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tiviclon.data.database.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Insert
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

}