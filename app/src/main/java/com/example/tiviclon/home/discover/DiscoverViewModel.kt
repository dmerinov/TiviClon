package com.example.tiviclon.home.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DiscoverViewModel(val view: DiscoverView) : ViewModel() {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: DiscoverView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DiscoverViewModel(view) as T
        }
    }
}