package com.example.tiviclon.home

import com.example.tiviclon.model.application.User

class HomePresenter(private val view: HomeView) {

    fun initialize(user: User) {
        view.setUpUI(user)
        view.setUpListeners()
    }

}