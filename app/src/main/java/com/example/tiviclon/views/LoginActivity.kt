package com.example.tiviclon.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.presenters.LoginPresenter
import com.example.tiviclon.presenters.LoginView

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private val presenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.initialize()
    }

    override fun setUpUI() {
        //get attributes from xml using binding
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                //pass the credentials to the presenter to check if they are valid
            }
        }
    }

    override fun navigateToHomeActivity() {
        //make a toast emulating a successful login
    }

    override fun notifyInvalidCredentials() {
        //make a toast emulating a wrong login
    }
}