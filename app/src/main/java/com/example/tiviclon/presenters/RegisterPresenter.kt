package com.example.tiviclon.presenters

class RegisterPresenter (private val view: RegisterView) {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

}

interface RegisterView{
    fun setUpUI()
    fun setUpListeners()
    fun onValidCredentials()
    fun onInvalidCredentials()
}