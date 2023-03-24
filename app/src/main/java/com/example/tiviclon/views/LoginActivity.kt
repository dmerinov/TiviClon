package com.example.tiviclon.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityLoginBinding
import com.example.tiviclon.model.application.User
import com.example.tiviclon.presenters.HomePresenter
import com.example.tiviclon.presenters.LoginPresenter
import com.example.tiviclon.presenters.LoginView

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private val presenter = LoginPresenter(this)

    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if(activityResult.resultCode == RESULT_OK){
            val name = activityResult.data?.getStringExtra(RegisterActivity.REGISTER_NAME).orEmpty()
            val password = activityResult.data?.getStringExtra(RegisterActivity.REGISTER_PASS).orEmpty()
            Toast.makeText(this, "registrado con exito: user $name, pass $password", Toast.LENGTH_SHORT).show()
            with(binding){
                etPassword.setText(password)
                etUsername.setText(name)
            }
        }else{
            Toast.makeText(this, "fallo en registro", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.initialize()
    }

    override fun setUpUI() {
        //get attributes from xml using binding
    }

    override fun setUpListeners() {
        with(binding) {
            btLogin.setOnClickListener {
                presenter.checkCredentials(etUsername.text.toString(), etPassword.text.toString())
            }
            btRegister.setOnClickListener {
                presenter.onRegisterButtonClicked()
            }
        }
    }

    override fun navigateToHomeActivity(loggedUser: User) {
        Toast.makeText(this, getString(R.string.valid_user_msg), Toast.LENGTH_SHORT).show()
        HomeActivity.navigateToHomeActivity(this,loggedUser)
    }

    override fun notifyInvalidCredentials() {
        Toast.makeText(this, getString(R.string.invalid_user_msg), Toast.LENGTH_SHORT).show()
    }
    override fun navigateToRegister() {
        RegisterActivity.navigateToRegisterActivity(this,responseLauncher)
    }
}