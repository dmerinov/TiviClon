package com.example.tiviclon.data.database.dao

import androidx.room.*
import com.example.tiviclon.data.database.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg user: User)

    @Delete
    fun delete(user: User)

}