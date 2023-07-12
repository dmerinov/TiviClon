package com.example.tiviclon.views.homeFragments.library.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.TiviClon
import com.example.tiviclon.model.application.Show

class LibraryViewModel(userId: String) : ViewModel() {
    private lateinit var showList: LiveData<List<Show>>

    init {
        showList = TiviClon.appContainer.repository.getFavShows(userId)
    }

    fun getShowList() = showList

    class Factory(private val userId: String) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LibraryViewModel(userId) as T
        }
    }
}