package com.example.tiviclon.presenters

class RegisterPresenter (private val view: RegisterView) {

    fun initialize() {
        view.setUpUI()
        view.setUpListeners()
    }

    fun checkRegistry(password: String, repeatedPassword: String, username: String) {
        if(password.length < 6 || repeatedPassword != password){
            //errorPassword
            view.onInvalidCredentials(RegisterError.PasswordError)
        }else{
            if(username.length<6){
                //errorName
                view.onInvalidCredentials(RegisterError.UserError)
            }else{
                //OK
                view.onValidCredentials()
            }
        }
    }

}

interface RegisterView{
    fun setUpUI()
    fun setUpListeners()
    fun onValidCredentials()
    fun onInvalidCredentials(error: RegisterError)
}

enum class RegisterError {
    PasswordError, UserError
}