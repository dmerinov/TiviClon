package com.example.tiviclon.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiviclon.model.application.User

class LoginViewModel(val view: LoginView) : ViewModel() {

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

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: LoginView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(view) as T
        }
    }
}