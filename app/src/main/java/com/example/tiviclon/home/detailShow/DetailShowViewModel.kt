package com.example.tiviclon.home.detailShow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailShowViewModel(val view: DetailView) : ViewModel() {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: DetailView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailShowViewModel(view) as T
        }
    }
}