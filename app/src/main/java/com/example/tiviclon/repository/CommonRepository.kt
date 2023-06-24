package com.example.tiviclon.repository

import android.util.Log
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.mappers.toDetailedShowVO
import com.example.tiviclon.mappers.toShow
import com.example.tiviclon.mappers.toVOShows
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.sharedPrefs.Prefs
import retrofit2.await

class CommonRepository(
    private val roomDatabase: TiviClonDatabase?,
    private val remoteDataSource: RetrofitResource,
    private val preferences: Prefs
) : Repository {

    override suspend fun getShows(): List<Show> {
        val retrievedShows = mutableListOf<Show>()
        val api = remoteDataSource.getRetrofit()
        val apiShows = api.getShows(1).await()
        apiShows.tv_shows.forEach {
            Log.d("RESPONSE_COR", it.toString())
        }
        val appShows = apiShows.tv_shows.map {
            it.toShow()
        }
        roomDatabase?.let { database ->
            appShows.forEach {
                database.showDao().insert(it.toVOShows())
            }
            val dbShows = database.showDao().getAllShows()
            Log.i("DATABASE_SHOWS", dbShows.toString())
            retrievedShows.addAll(dbShows.map { it.toShow() })
        }
        return retrievedShows
    }

    override suspend fun getFavShows(userID: String): List<String> {
        val userFavs = mutableListOf<String>()
        roomDatabase?.let {
            userFavs.addAll(it.favoriteDao().getUserFavShows(userID).map { fav ->
                fav.showId
            })
        }
        return userFavs
    }

    override suspend fun getDetailShow(showID: Int): DetailShow {
        val collectedShow = roomDatabase?.VODetailShow()?.getShowByID(showID)
        var apiShow = DetailShow()
        collectedShow?.let {
            apiShow = it.toDetailShow()
        } ?: run {
            apiShow = remoteDataSource.getRetrofit().getDetailedShow(showID).await().toDetailShow()
            roomDatabase?.let { db ->
                apiShow.let {
                    db.VODetailShow().insert(it.toDetailedShowVO())
                }
            }
        }
        return apiShow
    }

    override fun getLoggedUser(): String? =
        preferences.getLoggedUser()

    override fun saveLoggedUser(username: String) {
        preferences.saveLoggedUser(username)
    }

    override fun getLoginState(): Boolean =
        preferences.getLoginState()

    override fun saveLoginState(state: Boolean) {
        preferences.saveLoginState(state)
    }

    override suspend fun getAllUsers(): List<User> {
        return roomDatabase?.let {
            it.userDao().getAllUsers()
        } ?: kotlin.run {
            emptyList()
        }
    }

    override suspend fun deleteFavUser(userId: String, showId: String): Boolean {
        var success = false
        roomDatabase?.let {
            it.favoriteDao().delete(Favorites(userId, showId))
            success = true
        }
        return success
    }

    override suspend fun addFavUser(userId: String, showId: String): Boolean {
        var success = false
        roomDatabase?.let {
            it.favoriteDao().insert(Favorites(userId, showId))
            success = true
        }
        return success
    }
}