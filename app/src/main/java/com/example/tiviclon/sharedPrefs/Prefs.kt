package com.example.tiviclon.sharedPrefs

import android.content.Context

class Prefs(val context: Context) {

    val SHARED_NAME = "tividb"
    val SHARED_LOGGING_STATE = "login_state"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveLoginState(state: Boolean) {
        storage.edit().putBoolean(SHARED_LOGGING_STATE, state).apply()
    }

    fun getLoginState(): Boolean {
        return storage.getBoolean(SHARED_LOGGING_STATE, false)
    }
}