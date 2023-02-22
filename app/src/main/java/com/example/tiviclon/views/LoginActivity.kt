package com.example.tiviclon.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
    }

    private fun setUpUI() {
        //get attributes from xml using binding
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                // TODO - implement listener
            }
        }
    }

    private fun checkCredentials(username: String, password: String) {
        //TODO - implement logic
    }

    private fun navigateToHomeActivity() {
        //TODO - simulate navigation
    }

    private fun notifyInvalidCredentials() {
        //TODO - simulate not valid toast
    }
}