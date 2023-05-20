package com.example.tiviclon.registry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegisterViewModel(val view: RegisterView) : ViewModel() {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    fun checkRegistry(password: String, repeatedPassword: String, username: String) {
        if (password.length < 6 || repeatedPassword != password) {
            //errorPassword
            view.onInvalidCredentials(RegisterError.PasswordError)
        } else {
            if (username.length < 6) {
                //errorName
                view.onInvalidCredentials(RegisterError.UserError)
            } else {
                //OK
                view.onValidCredentials(username, password)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val view: RegisterView) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegisterViewModel(view) as T
        }
    }
}

enum class RegisterError {
    PasswordError, UserError
}