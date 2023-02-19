package com.example.tiviclon.views

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                presenter.checkCredentials(etUsername.text.toString(), etPassword.text.toString())
            }
        }
    }

    override fun navigateToHomeActivity() {
        Toast.makeText(this, getString(R.string.valid_user_msg), Toast.LENGTH_SHORT).show()
    }

    override fun notifyInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_user_msg), Toast.LENGTH_SHORT).show()
    }
}