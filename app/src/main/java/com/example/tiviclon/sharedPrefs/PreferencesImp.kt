package com.example.tiviclon.sharedPrefs

import android.content.Context

class PreferencesImp(val context: Context) : Prefs {

    private val SHARED_NAME = "tividb"
    private val SHARED_LOGGING_STATE = "login_state"
    private val SHARED_USER_STATE = "user_id_state"
    private val SHARED_TIMESTAMP_STATE = "shared_timestamp_state"

    private val storage = context.getSharedPreferences(SHARED_NAME, 0)

    override fun saveLoginState(state: Boolean) {
        storage.edit().putBoolean(SHARED_LOGGING_STATE, state).apply()
    }

    override fun getLoginState(): Boolean {
        return storage.getBoolean(SHARED_LOGGING_STATE, false)
    }

    override fun saveLoggedUser(username: String) {
        storage.edit().putString(SHARED_USER_STATE, username).apply()
    }

    override fun getLoggedUser(): String? {
        return storage.getString(SHARED_USER_STATE, null)
    }

    override fun saveUserTimestamp(tmp: Long) {
        storage.edit().putLong(SHARED_TIMESTAMP_STATE,tmp).apply()
    }

    override fun getUserTimestamp(): Long {
        return storage.getLong(SHARED_TIMESTAMP_STATE, -1)
    }
}