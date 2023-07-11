package com.example.tiviclon.container

import android.content.Context
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.repository.CommonRepository
import com.example.tiviclon.repository.Repository
import com.example.tiviclon.sharedPrefs.PreferencesImp

class AppContainer(context: Context) {
    private val remoteDataSource = RetrofitResource.getRetrofit()
    private val userDao = TiviClonDatabase.getInstance(context).userDao()
    private val preferences = PreferencesImp(context = context)
    private val showDao = TiviClonDatabase.getInstance(context).showDao()
    private val favoriteDao = TiviClonDatabase.getInstance(context).favoriteDao()
    private val detailShowDao = TiviClonDatabase.getInstance(context).VODetailShow()

    val repository: Repository = CommonRepository(
        userDao, showDao, favoriteDao, detailShowDao, remoteDataSource, preferences
    )
}