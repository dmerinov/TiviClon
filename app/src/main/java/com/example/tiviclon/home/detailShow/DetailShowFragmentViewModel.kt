package com.example.tiviclon.home.detailShow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.model.application.Show

class DetailShowFragmentViewModel(val view: DetailFragmentView) : ViewModel() {

    fun initialize(show: Show) {
        view.setUpUI(show)
        view.setUpListeners()
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: DetailFragmentView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailShowFragmentViewModel(view) as T
        }
    }
}