package com.example.tiviclon.data.database.dao

import androidx.room.*
import com.example.tiviclon.data.database.entities.Favorites

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE username = :userId")
    fun getUserFavShows(userId: String): List<Favorites>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg fav: Favorites)

    @Delete
    fun delete(fav: Favorites)
}