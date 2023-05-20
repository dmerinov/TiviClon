package com.example.tiviclon.registry

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), RegisterView {
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
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModel.Factory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initialize()
    }

    override fun setUpUI() {
        //Nothing to do
    }

    override fun setUpListeners() {
        with(binding) {
            btRegister.setOnClickListener {
                viewModel.checkRegistry(
                    etPassword.text.toString(),
                    etRepeatPassword.text.toString(),
                    etUsername.text.toString()
                )
            }
        }
    }

    override fun onValidCredentials(name: String, password: String) {
        val intent = Intent()
        intent.apply {
            putExtra(REGISTER_NAME, name)
            putExtra(REGISTER_PASS, password)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onInvalidCredentials(error: RegisterError) {
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