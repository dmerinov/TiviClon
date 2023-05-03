package com.example.tiviclon.home.library

class LibraryPresenter(private val view:LibraryView) {

    fun initialize(){
        view.setUpUI()
        view.setUpListeners()
        view.setUpRecyclerView()
    }
}