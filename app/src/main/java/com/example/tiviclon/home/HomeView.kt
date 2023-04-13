package com.example.tiviclon.home

import com.example.tiviclon.model.application.User

interface HomeView {
    fun setUpUI(user: User)
    fun setUpListeners()
    fun initFragments()
}