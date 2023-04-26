package com.example.tiviclon.home.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LibraryViewModel(val view: LibraryView) : ViewModel() {

    fun initialize(){
        view.setUpUI()
        view.setUpListeners()
        view.setUpRecyclerView(view.getShows())
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: LibraryView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LibraryViewModel(view) as T
        }
    }
}

