package com.example.tiviclon.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.model.application.User


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val name =
                    activityResult.data?.getStringExtra(RegisterActivity.REGISTER_NAME).orEmpty()
                val password =
                    activityResult.data?.getStringExtra(RegisterActivity.REGISTER_PASS).orEmpty()
                Toast.makeText(
                    this,
                    "registrado con exito: user $name, pass $password",
                    Toast.LENGTH_SHORT
                ).show()
                with(binding) {
                    etPassword.setText(password)
                    etUsername.setText(name)
                }
            } else {
                Toast.makeText(this, "fallo en registro", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpListeners()
    }

    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                checkCredentials(etUsername.text.toString(), etPassword.text.toString())
            }
            btRegister.setOnClickListener {
                onRegisterButtonClicked()
            }
            btWebsiteLink.setOnClickListener {
                onVisitWebsiteButtonClicked()
            }
        }
    }

    private fun checkCredentials(username: String, password: String) {
        if (username.length > 6 && username.isNotBlank()) {
            if (password.length > 6 && password.isNotBlank()) {
                val loggedUser = User(username, password)
                navigateToHomeActivity(loggedUser)
            } else {
                notifyInvalidCredentials()
            }
        } else {
            notifyInvalidCredentials()
        }
    }

    private fun onRegisterButtonClicked() {
        navigateToRegister()
    }

    private fun onVisitWebsiteButtonClicked() {
        navigateToWebsite()
    }

    private fun navigateToHomeActivity(loggedUser: User) {
        Toast.makeText(this, getString(R.string.valid_user_msg), Toast.LENGTH_SHORT).show()
        HomeActivity.navigateToHomeActivity(this, loggedUser)
    }

    private fun notifyInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_user_msg), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToRegister() {
        RegisterActivity.navigateToRegisterActivity(this, responseLauncher)
    }

    private fun navigateToWebsite() {
        val webIntent: Intent = Uri.parse("https://trakt.tv/").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }
}