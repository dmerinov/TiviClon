package com.example.tiviclon.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tiviclon.data.database.entities.VODetailShow

@Dao
interface DetailShowDao {

    @Query("SELECT * FROM vodetailshow WHERE showId = :id")
    fun getShowByID(id: Int): LiveData<VODetailShow>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg show: VODetailShow)

    @Query("UPDATE vodetailshow SET favorite = :favString WHERE showId = :showId")
    fun updateShow(favString: String, showId: String)
}