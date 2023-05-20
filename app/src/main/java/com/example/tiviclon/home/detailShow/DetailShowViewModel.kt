package com.example.tiviclon.home.detailShow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.model.application.Show

class DetailShowViewModel(val view: DetailShowActivityView) : ViewModel() {

    private lateinit var showVm : Show
    fun initialize(show: Show) {
        showVm = show
        view.setUpUI(show.title)
        view.setUpListeners()
        view.initFragments()
    }

    fun getShow() = showVm

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: DetailShowActivityView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailShowViewModel(view) as T
        }
    }
}