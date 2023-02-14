package com.example.tiviclon.presenters

class LoginPresenter(private val view: LoginView) {

    fun initialize(){
        view.setUpUI()
    }
}

interface LoginView{
    fun setUpUI()
}