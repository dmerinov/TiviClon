package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.R
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.model.application.AppUser
import com.example.tiviclon.repository.CommonRepository
import com.example.tiviclon.repository.Repository
import com.example.tiviclon.sharedPrefs.PreferencesImp


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
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = CommonRepository(
            userDao = TiviClonDatabase.getInstance(applicationContext).userDao(),
            remoteDataSource = RetrofitResource.getRetrofit(),
            preferences = PreferencesImp(context = applicationContext),
            showDao = TiviClonDatabase.getInstance(applicationContext).showDao(),
            favoriteDao = TiviClonDatabase.getInstance(applicationContext).favoriteDao(),
            detailShowDao = TiviClonDatabase.getInstance(applicationContext).VODetailShow()
        )
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
                val loggedAppUser = AppUser(username, password)
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