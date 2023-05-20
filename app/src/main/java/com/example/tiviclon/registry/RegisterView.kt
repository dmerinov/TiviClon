package com.example.tiviclon.registry

interface RegisterView{
    fun setUpUI()
    fun setUpListeners()
    fun onValidCredentials(name: String, password: String)
    fun onInvalidCredentials(error: RegisterError)
}