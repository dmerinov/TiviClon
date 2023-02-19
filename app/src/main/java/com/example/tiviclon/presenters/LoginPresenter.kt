package com.example.tiviclon.presenters

class LoginPresenter(private val view: LoginView) {

    fun initialize() {
        view.setUpUI()
    }

    fun checkCredentials(username: String, password: String) {
        //TODO fill the method so it checks the credentials and notify the user
    }
}

interface LoginView {
    fun setUpUI()
    fun navigateToHomeActivity()
    fun notifyInvalidCredentials()
}