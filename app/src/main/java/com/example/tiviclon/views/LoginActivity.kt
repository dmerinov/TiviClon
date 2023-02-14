package com.example.tiviclon.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiviclon.R
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
        binding.tvGreeting.text = "Hello ASEE students"
    }
}