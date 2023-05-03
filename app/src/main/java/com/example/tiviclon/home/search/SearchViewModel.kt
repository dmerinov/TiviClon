package com.example.tiviclon.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModel(val view: SearchView) : ViewModel() {
    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
        view.setUpRecyclerView()
    }

    fun filterList(filter: String) {
        val allShows = view.getShows()
        var filteredShows = allShows.filter {
            it.title.contains(filter)
        }
        if(filteredShows.isEmpty()){
            filteredShows = allShows
        }
        view.updateList(filteredShows)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: SearchView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(view) as T
        }
    }
}