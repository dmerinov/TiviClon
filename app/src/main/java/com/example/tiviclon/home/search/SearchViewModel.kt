package com.example.tiviclon.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.home.library.LibraryView
import com.example.tiviclon.home.library.LibraryViewModel

class SearchViewModel(val view: SearchView) : ViewModel() {
    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: SearchView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(view) as T
        }
    }
}