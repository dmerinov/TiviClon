package com.example.tiviclon

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.container.AppContainer

class TiviClon : Application() {

    companion object {
        lateinit var appContainer: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }
}