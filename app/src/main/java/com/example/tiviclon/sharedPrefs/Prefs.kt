package com.example.tiviclon.sharedPrefs

import android.content.Context

class Prefs(val context: Context) {

    private val SHARED_NAME = "tividb"
    private val SHARED_LOGGING_STATE = "login_state"
    private val SHARED_USER_STATE = "user_id_state"

    private val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveLoginState(state: Boolean) {
        storage.edit().putBoolean(SHARED_LOGGING_STATE, state).apply()
    }

    fun getLoginState(): Boolean {
        return storage.getBoolean(SHARED_LOGGING_STATE, false)
    }

    fun saveLoggedUser(username: String) {
        storage.edit().putString(SHARED_USER_STATE, username).apply()
    }

    fun getLoggedUser(): String? {
        return storage.getString(SHARED_USER_STATE, null)
    }
}