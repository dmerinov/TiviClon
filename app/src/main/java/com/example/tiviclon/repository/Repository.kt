package com.example.tiviclon.repository

import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show

interface Repository {

    suspend fun getShows(): List<Show>

    suspend fun getFavShows(userID: String): List<String>

    suspend fun getDetailShow(showID: Int): DetailShow

    fun getLoggedUser(): String?

    fun saveLoggedUser(username: String)

    fun getLoginState(): Boolean

    fun saveLoginState(state: Boolean)

    suspend fun getAllUsers(): List<User>

    suspend fun deleteFavUser(userId: String, showId: String): Boolean

    suspend fun addFavUser(userId: String, showId: String): Boolean
}