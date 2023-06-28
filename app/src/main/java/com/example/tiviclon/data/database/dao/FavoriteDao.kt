package com.example.tiviclon.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.VOShow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM VOShow WHERE showId in (SELECT showId FROM favorites WHERE username = :userId)")
    fun getUserFavShows(userId: String): LiveData<List<VOShow>>

    @Query("SELECT * FROM favorites WHERE showId = :showId and username = :userId")
    fun isShowFav(userId: String, showId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg fav: Favorites)

    @Delete
    fun delete(fav: Favorites)
}