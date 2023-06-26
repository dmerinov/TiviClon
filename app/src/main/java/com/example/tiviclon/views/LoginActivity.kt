package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.tiviclon.R
import com.example.tiviclon.TiviClon
import com.example.tiviclon.container.AppContainer
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.model.application.AppUser


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
    private lateinit var appContainer: AppContainer
    private val userList = mutableListOf<User>()
    val username = MutableLiveData<String>("-1")
    val password = MutableLiveData<String>("-1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appContainer = TiviClon.appContainer
        setUpUI()
        setUpListeners()
        setUpLivedata()
    }

    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpLivedata() {
        appContainer.repository.getAllUsers().observe(this) {
            userList.clear()
            userList.addAll(it)
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                username.value = etUsername.text.toString()
                password.value = etPassword.text.toString()
                checkCredentials(username.value.toString(), password.value.toString())
            }
        }
    }

    private fun checkCredentials(username: String, password: String) {
        if (username.length > 6 && username.isNotBlank()) {
            if (password.length > 6 && password.isNotBlank()) {
                val loggedAppUser = AppUser(username, password)
                if (userList.map { it.name }.contains(username))
                    navigateToHomeActivity(loggedAppUser)
            } else {
                notifyInvalidCredentials()
            }
        }
    }

    private fun navigateToHomeActivity(loggedAppUser: AppUser) {
        Toast.makeText(this, getString(R.string.valid_user_msg), Toast.LENGTH_SHORT).show()
        val intent = Intent()
        intent.apply {
            putExtra(LOGIN_NAME, loggedAppUser.name)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun notifyInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_user_msg), Toast.LENGTH_SHORT).show()
    }

    private fun notifyUserNotFound() {
        Toast.makeText(this, getString(R.string.user_not_found), Toast.LENGTH_SHORT).show()
    }
}