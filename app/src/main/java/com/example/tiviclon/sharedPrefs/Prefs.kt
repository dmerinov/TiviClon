package com.example.tiviclon.sharedPrefs

interface Prefs {
    fun saveLoginState(state: Boolean)

    fun getLoginState(): Boolean

    fun saveLoggedUser(username: String)

    fun getLoggedUser(): String?
}