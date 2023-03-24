package com.example.tiviclon.presenters

class LoginPresenter(private val view: LoginView) {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    fun checkCredentials(username: String, password: String) {
        if (username.length > 6 && username.isNotBlank()) {
            if (password.length > 6 && password.isNotBlank()) {
                view.navigateToHomeActivity()
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
}

interface LoginView {
    fun setUpUI()
    fun setUpListeners()
    fun navigateToHomeActivity()
    fun notifyInvalidCredentials()
    fun navigateToRegister()
}