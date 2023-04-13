package com.example.tiviclon.home.search

class SearchPresenter(private val view: SearchView) {

    fun initialize(){
        view.setUpUI()
        view.setUpListeners()
    }
}