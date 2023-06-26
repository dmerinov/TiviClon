package com.example.tiviclon.repository

import androidx.lifecycle.LiveData
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show

interface Repository {

    fun getShows(): LiveData<List<Show>>

    fun fetchData()
    fun getFavShows(userID: String): LiveData<List<String>>

    suspend fun getDetailShow(showID: Int): DetailShow

    fun getLoggedUser(): String?

    fun saveLoggedUser(username: String)

    fun getLoginState(): Boolean

    fun saveLoginState(state: Boolean)

    fun saveUserTimestamp( tmp: Long)

    fun getUserTimestamp(): Long

    fun getAllUsers(): List<User>

    suspend fun deleteFavUser(userId: String, showId: String): Boolean

    suspend fun addFavUser(userId: String, showId: String): Boolean

    suspend fun addUserDB(username: String, password: String): Boolean
}