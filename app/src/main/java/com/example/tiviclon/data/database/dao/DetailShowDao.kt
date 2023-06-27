package com.example.tiviclon.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.VODetailShow

@Dao
interface DetailShowDao {

    @Query("SELECT * FROM vodetailshow WHERE showId = :id")
    fun getShowByID(id: Int): VODetailShow

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg show: VODetailShow)
}