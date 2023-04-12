package com.example.tiviclon.login

import com.example.tiviclon.model.application.User

interface LoginView {
    fun setUpUI()
    fun setUpListeners()
    fun navigateToHomeActivity(loggedUser: User)
    fun notifyInvalidCredentials()
    fun navigateToRegister()
    fun navigateToWebsite()
}