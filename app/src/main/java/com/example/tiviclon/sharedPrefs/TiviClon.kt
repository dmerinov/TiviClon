package com.example.tiviclon.sharedPrefs

import android.app.Application

class TiviClon : Application() {

    companion object {
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}