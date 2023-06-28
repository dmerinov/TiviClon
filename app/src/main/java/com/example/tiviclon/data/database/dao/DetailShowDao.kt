package com.example.tiviclon.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.VODetailShow
import com.example.tiviclon.model.application.DetailShow

@Dao
interface DetailShowDao {

    @Query("SELECT * FROM vodetailshow WHERE showId = :id")
    fun getShowByID(id: Int): LiveData<VODetailShow>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg show: VODetailShow)
}