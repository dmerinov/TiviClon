package com.example.tiviclon.home

import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.application.User

class HomePresenter(private val view: HomeView) {

    fun initialize(user: User) {
        view.setUpUI(user)
        view.setUpListeners()
        view.initFragments()
    }

    fun getShows(): List<Show>{
        val shows:MutableList<Show> = ArrayList()
        shows.add(Show("Spiderman", "Marvel", "Peter Parker"))
        shows.add(Show("Daredevil", "Marvel", "Matthew Michael Murdock"))
        shows.add(Show("Wolverine", "Marvel", "James Howlett"))
        shows.add(Show("Batman", "DC", "Bruce Wayne",))
        shows.add(Show("Thor", "Marvel", "Thor Odinson"))
        shows.add(Show("Flash", "DC", "Jay Garrick"))
        shows.add(Show("Green Lantern", "DC", "Alan Scott"))
        shows.add(Show("Wonder Woman", "DC", "Princess Diana"))
        return shows
    }

}