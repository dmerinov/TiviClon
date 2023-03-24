package com.example.tiviclon.presenters

import com.example.tiviclon.model.application.User

class HomePresenter(private val view: HomeView) {

    fun initialize(user: User) {
        view.setUpUI(user)
    }

}

interface HomeView {
    fun setUpUI(user: User)
    fun setUpListeners()
}