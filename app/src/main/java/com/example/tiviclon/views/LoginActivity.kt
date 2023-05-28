package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.model.application.User


class LoginActivity : AppCompatActivity() {

    companion object {
        const val LOGIN_NAME = "LOGIN_NAME"
        const val LOGIN_PASS = "LOGIN_PASS"
        fun navigateToLoginActivity(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, LoginActivity::class.java)
            responseLauncher.launch(intent)
        }

    }

    private lateinit var binding: ActivityLoginBinding

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

    private fun navigateToHomeActivity(loggedUser: User) {
        Toast.makeText(this, getString(R.string.valid_user_msg), Toast.LENGTH_SHORT).show()
        val intent = Intent()
        intent.apply {
            putExtra(LOGIN_NAME, loggedUser.name)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun notifyInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_user_msg), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToWebsite() {
        val webIntent: Intent = Uri.parse("https://trakt.tv/").let { webpage ->
            Intent(Intent.ACTION_VIEW, webpage)
        }
        startActivity(webIntent)
    }
}