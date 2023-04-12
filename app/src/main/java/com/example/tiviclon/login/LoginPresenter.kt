package com.example.tiviclon.login

import com.example.tiviclon.model.application.User

class LoginPresenter(private val view: LoginView) {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    fun checkCredentials(username: String, password: String) {
        if (username.length > 6 && username.isNotBlank()) {
            if (password.length > 6 && password.isNotBlank()) {
                val loggedUser = User(username, password)
                view.navigateToHomeActivity(loggedUser)
            } else {
                view.notifyInvalidCredentials()
            }
        } else {
            view.notifyInvalidCredentials()
        }
    }

    fun onRegisterButtonClicked() {
        view.navigateToRegister()
    }

    fun onVisitWebsiteButtonClicked() {
        view.navigateToWebsite()
    }
}