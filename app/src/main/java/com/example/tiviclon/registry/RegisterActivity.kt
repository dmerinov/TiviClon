package com.example.tiviclon.registry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    companion object {

        const val REGISTER_NAME = "REGISTER_NAME"
        const val REGISTER_PASS = "REGISTER_PASS"
        fun navigateToRegisterActivity(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {

            val intent = Intent(context, RegisterActivity::class.java).apply {
                //here you can use putExtra to pass parameters as in
                //putExtra(key, value)
            }
            responseLauncher.launch(intent)
        }
    }

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialize()
    }

    fun setUpUI() {
        //Nothing to do
    }

    fun setUpListeners() {
        with(binding) {
            btRegister.setOnClickListener {
                checkRegistry(
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString(),
                    etUsername.text.toString()
                )
            }
        }
    }

    fun initialize() {
        setUpUI()
        setUpListeners()
    }

    fun checkRegistry(password: String, repeatedPassword: String, username: String) {
        if (password.length < 6 || repeatedPassword != password) {
            //errorPassword
            onInvalidCredentials(RegisterError.PasswordError)
        } else {
            if (username.length < 6) {
                //errorName
                onInvalidCredentials(RegisterError.UserError)
            } else {
                //OK
                onValidCredentials(username, password)
            }
        }
    }


    fun onValidCredentials(name: String, password: String) {
        val intent = Intent()
        intent.apply {
            putExtra(REGISTER_NAME, name)
            putExtra(REGISTER_PASS, password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    fun onInvalidCredentials(error: RegisterError) {
        when (error) {
            RegisterError.PasswordError -> Toast.makeText(
                this,
                "comprueba la contraseña",
                Toast.LENGTH_SHORT
            ).show()
            RegisterError.UserError -> Toast.makeText(
                this,
                "comprueba el usuario",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

enum class RegisterError {
    PasswordError, UserError
}