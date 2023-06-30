package com.example.tiviclon.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tiviclon.data.database.entities.VODetailShow

@Dao
interface DetailShowDao {

    @Query("SELECT * FROM vodetailshow join favorites on (favorites.favShowId = vodetailshow.showId) where favorites.username = :user and vodetailshow.showId = :showId")
    fun getFavShowByID(showId: Int, user: String): LiveData<VODetailShow>

    @Query("SELECT * FROM vodetailshow WHERE showId = :id")
    fun getShowByID(id: Int): VODetailShow

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg show: VODetailShow)

    /* @Query("UPDATE vodetailshow SET favorite = :favString WHERE showId = :showId")
     fun updateShow(favString: String, showId: String) */
}