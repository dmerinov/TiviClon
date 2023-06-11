package com.example.tiviclon.sharedPrefs

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "tividtb"

    val SHARED_LOGGED_STATE = "logged_state"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveLoginState(logged: Boolean) {
        storage.edit().putBoolean(SHARED_LOGGED_STATE, logged).apply()
    }

    //this false is because if there's no instance it returns false
    fun getLoginState(): Boolean {
        return storage.getBoolean(SHARED_LOGGED_STATE, false)
    }

}