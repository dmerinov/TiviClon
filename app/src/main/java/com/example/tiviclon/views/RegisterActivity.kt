package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.database.entities.User
import com.example.tiviclon.databinding.ActivityRegisterBinding
import com.example.tiviclon.mappers.toAppUser
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

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    })

    private lateinit var binding: ActivityRegisterBinding
    private var db: TiviClonDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpListeners()
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
                getBD()?.let {
                    scope.launch(Dispatchers.IO) {
                        val bdUsers = it.userDao().getAllUsers().map { it.toAppUser() }
                        if (bdUsers.none { it.name == username }){
                            onValidCredentials(username, password)
                        }else{
                            onInvalidCredentials(RegisterError.ExistingUserError)
                        }
                    }
                }
            }
        }
    }

    private fun getBD() = TiviClonDatabase.getInstance(applicationContext)

    private fun setUpUI() {
        //Nothing to do
    }

    private fun setUpListeners() {
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

    private fun onValidCredentials(name: String, password: String) {
        getBD()?.let {
            scope.launch(Dispatchers.IO) {
                it.userDao().insert(User(name, password))
                val intent = Intent()
                intent.apply {
                }
                setResult(RESULT_OK_REGISTER, intent)
                withContext(Dispatchers.Main){
                    finish()
                }
            }
        }

    }

    private fun onInvalidCredentials(error: RegisterError) {
        scope.launch {
            withContext(Dispatchers.Main){
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
    PasswordError, UserError,ExistingUserError
}