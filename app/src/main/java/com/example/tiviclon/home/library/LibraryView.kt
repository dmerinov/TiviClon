package com.example.tiviclon.home.library

import com.example.tiviclon.model.application.Show

interface LibraryView {
    fun setUpUI()
    fun setUpListeners()
    fun setUpRecyclerView(shows: List<Show>)
    fun getShows() : List<Show>
}