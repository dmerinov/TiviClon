package com.example.tiviclon.repository

import android.util.Log
import com.example.tiviclon.data.database.dao.DetailShowDao
import com.example.tiviclon.data.database.dao.FavoriteDao
import com.example.tiviclon.data.database.dao.ShowDao
import com.example.tiviclon.data.database.dao.UserDao
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.mappers.toDetailedShowVO
import com.example.tiviclon.mappers.toShow
import com.example.tiviclon.mappers.toVOShows
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.sharedPrefs.Prefs
import retrofit2.await

class CommonRepository(
    private val userDao: UserDao,
    private val showDao: ShowDao,
    private val favoriteDao: FavoriteDao,
    private val detailShowDao: DetailShowDao,
    private val remoteDataSource: ApiService,
    private val preferences: Prefs
) : Repository {

    override suspend fun getShows(): List<Show> {
        val retrievedShows = mutableListOf<Show>()
        val apiShows = remoteDataSource.getShows(1).await()
        apiShows.tv_shows.forEach {
            Log.d("RESPONSE_COR", it.toString())
        }
        val appShows = apiShows.tv_shows.map {
            it.toShow()
        }
        appShows.forEach {
            showDao.insert(it.toVOShows())
        }
        val dbShows = showDao.getAllShows()
        Log.i("DATABASE_SHOWS", dbShows.toString())
        retrievedShows.addAll(dbShows.map { it.toShow() })
        return retrievedShows
    }

    override suspend fun getFavShows(userID: String): List<String> {
        val userFavs = mutableListOf<String>()
        userFavs.addAll(favoriteDao.getUserFavShows(userID).map { fav -> fav.showId })
        return userFavs
    }

    override suspend fun getDetailShow(showID: Int): DetailShow {
        var collectedShow: DetailShow
        try {
            collectedShow = detailShowDao.getShowByID(showID).toDetailShow()
        } catch (e: java.lang.Exception) {
            collectedShow =
                remoteDataSource.getDetailedShow(showID).await().toDetailShow()
            detailShowDao.insert(collectedShow.toDetailedShowVO())
        }
        return collectedShow
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
        return userDao.getAllUsers()
    }

    override suspend fun deleteFavUser(userId: String, showId: String): Boolean {
        val success: Boolean = try {
            favoriteDao.delete(Favorites(userId, showId))
            true
        } catch (e: java.lang.Exception) {
            false
        }
        return success
    }

    override suspend fun addFavUser(userId: String, showId: String): Boolean {
        val success: Boolean = try {
            favoriteDao.insert(Favorites(userId, showId))
            true
        } catch (e: java.lang.Exception) {
            false
        }
        return success
    }

    override suspend fun addUserDB(username: String, password: String): Boolean {
        val success: Boolean = try {
            userDao.insert(User(username, password))
            true
        } catch (e: java.lang.Exception) {
            false
        }
        return success
    }
}