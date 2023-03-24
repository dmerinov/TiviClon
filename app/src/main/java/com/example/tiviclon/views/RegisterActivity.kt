package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityRegisterBinding
import com.example.tiviclon.presenters.RegisterPresenter
import com.example.tiviclon.presenters.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {

    companion object {
        fun navigateToRegisterActivity(context: Context){
            val intent = Intent(context, RegisterActivity::class.java).apply {
                //here you can use putExtra to pass parameters as in
                //putExtra(key, value)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityRegisterBinding
    private val presenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.initialize()
    }

    override fun setUpUI() {
        TODO("Not yet implemented")
    }

    override fun setUpListeners() {
        TODO("Not yet implemented")
    }

    override fun onValidCredentials() {
        TODO("Not yet implemented")
    }

    override fun onInvalidCredentials() {
        TODO("Not yet implemented")
    }
}