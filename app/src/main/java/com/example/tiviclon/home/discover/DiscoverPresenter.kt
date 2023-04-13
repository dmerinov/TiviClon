package com.example.tiviclon.home.discover

class DiscoverPresenter(private val view: DiscoverView) {

    fun initialize(){
        view.setUpUI()
        view.setUpListeners()
    }
}