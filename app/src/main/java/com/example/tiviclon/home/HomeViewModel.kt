package com.example.tiviclon.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.application.User

class HomeViewModel(val view: HomeView) : ViewModel() {

    fun initialize(user: User) {
        view.setUpUI(user)
        view.setUpListeners()
        view.initFragments()
    }

    fun getShows(): List<Show> {
        val shows: MutableList<Show> = ArrayList()
        shows.add(Show(1, "Spiderman", "Marvel", "Peter Parker"))
        shows.add(Show(2, "Daredevil", "Marvel", "Matthew Michael Murdock"))
        shows.add(Show(3, "Wolverine", "Marvel", "James Howlett"))
        shows.add(Show(4, "Batman", "DC", "Bruce Wayne"))
        shows.add(Show(5, "Thor", "Marvel", "Thor Odinson"))
        shows.add(Show(6, "Flash", "DC", "Jay Garrick"))
        shows.add(Show(7, "Green Lantern", "DC", "Alan Scott"))
        shows.add(Show(8, "Wonder Woman", "DC", "Princess Diana"))
        return shows
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: HomeView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(view) as T
        }
    }
}