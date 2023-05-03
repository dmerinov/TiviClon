package com.example.tiviclon.home.search

import com.example.tiviclon.model.application.Show

interface SearchView {
    fun setUpUI()
    fun setUpListeners()
    fun setUpRecyclerView()
    fun getShows() : List<Show>

    fun updateList(shows: List<Show>)
}