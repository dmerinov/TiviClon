package com.example.tiviclon.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.tiviclon.data.database.dao.DetailShowDao
import com.example.tiviclon.data.database.dao.FavoriteDao
import com.example.tiviclon.data.database.dao.ShowDao
import com.example.tiviclon.data.database.dao.UserDao
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.data.database.entities.VOShow
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.mappers.toDetailedShowVO
import com.example.tiviclon.mappers.toShow
import com.example.tiviclon.mappers.toVOShows
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.sharedPrefs.Prefs
import kotlinx.coroutines.*
import retrofit2.await
import kotlin.random.Random

class CommonRepository(
    private val userDao: UserDao,
    private val showDao: ShowDao,
    private val favoriteDao: FavoriteDao,
    private val detailShowDao: DetailShowDao,
    private val remoteDataSource: ApiService,
    private val preferences: Prefs
) : Repository {
    override fun getShows() = getShowsFromVO()

    private fun getShowsFromVO(): LiveData<List<Show>> =
        showDao.getAllShows().map { it.map { it.toShow() } }

    override fun fetchData() {
        val job = Job()
        val uiScope =
            CoroutineScope(Dispatchers.IO + job + CoroutineExceptionHandler { _, throwable -> })
        uiScope.launch {
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
        }
    }

    override fun getFavShows(userID: String): LiveData<List<String>> =
       favoriteDao.getUserFavShows(userID).map { fav -> fav.map { it.showId } }


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

    override fun getAllUsers(): LiveData<List<User>> {
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
    override fun getUserTimestamp() = preferences.getUserTimestamp()


    override fun saveUserTimestamp(tmp: Long) {
        preferences.saveUserTimestamp(tmp)
    }
}