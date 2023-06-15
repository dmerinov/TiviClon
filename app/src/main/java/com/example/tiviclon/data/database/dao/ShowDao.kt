package com.example.tiviclon.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.tiviclon.data.database.entities.VOShow

@Dao
interface ShowDao {

    @Query("SELECT * FROM voshow")
    fun getAllShows(): List<VOShow>

    @Insert(onConflict = IGNORE)
    fun insert(vararg show: VOShow)

    @Delete
    fun delete(show: VOShow)
}