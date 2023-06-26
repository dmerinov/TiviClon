package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.tiviclon.TiviClon
import com.example.tiviclon.container.AppContainer
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.databinding.ActivityRegisterBinding
import kotlinx.coroutines.*

class RegisterActivity : AppCompatActivity() {
    companion object {
        const val RESULT_OK_REGISTER = -2
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
    private lateinit var appContainer: AppContainer
    private val userList = mutableListOf<User>()
    val username = MutableLiveData<String>("-1")
    val password = MutableLiveData<String>("-1")
    val passwordVerification = MutableLiveData<String>("-1")
    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appContainer = TiviClon.appContainer

        setUpUI()
        setUpListeners()
        setUpLivedata()
    }

    private fun checkRegistry(password: String, repeatedPassword: String, username: String) {
        if (password.length < 6 || repeatedPassword != password) {
            //errorPassword
            onInvalidCredentials(RegisterError.PasswordError)
        } else {
            if (username.length < 6) {
                //errorName
                onInvalidCredentials(RegisterError.UserError)
            } else {
                //OK
                //lets check the database and if theres anybody named that way it will be invalid
                if (userList.none { it.name == username }) {
                    onValidCredentials(username, password)
                } else {
                    onInvalidCredentials(RegisterError.ExistingUserError)
                }
            }
        }
    }

    private fun setUpUI() {
        //Nothing to do
    }

    private fun setUpLivedata() {
        appContainer.repository.getAllUsers().observe(this) {
            userList.clear()
            userList.addAll(it)
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btRegister.setOnClickListener {
                username.value = etUsername.text.toString()
                password.value = etPassword.text.toString()
                passwordVerification.value = etRepeatPassword.text.toString()
                checkRegistry(
                    password.value.toString(),
                    passwordVerification.value.toString(),
                    username.value.toString()
                )
            }
        }
    }

    private fun onValidCredentials(name: String, password: String) {

        scope.launch(Dispatchers.IO) {
            appContainer.repository.addUserDB(name, password)
            val intent = Intent()
            setResult(RESULT_OK_REGISTER, intent)
            withContext(Dispatchers.Main) {
                finish()
            }
        }

    }

    private fun onInvalidCredentials(error: RegisterError) {
        scope.launch {
            withContext(Dispatchers.Main) {
                when (error) {
                    RegisterError.PasswordError -> Toast.makeText(
                        this@RegisterActivity,
                        "comprueba la contraseña",
                        Toast.LENGTH_SHORT
                    ).show()
                    RegisterError.UserError -> Toast.makeText(
                        this@RegisterActivity,
                        "comprueba el usuario",
                        Toast.LENGTH_SHORT
                    ).show()
                    RegisterError.ExistingUserError -> Toast.makeText(
                        this@RegisterActivity,
                        "el usuario ya existe",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

enum class RegisterError {
    PasswordError, UserError, ExistingUserError
}