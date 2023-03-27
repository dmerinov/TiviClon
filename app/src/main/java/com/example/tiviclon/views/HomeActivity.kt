package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.model.application.User
import com.example.tiviclon.presenters.HomePresenter
import com.example.tiviclon.presenters.HomeView

class HomeActivity : AppCompatActivity(), HomeView {

    companion object {
        const val USER_INFO = "USER_INFO"
        fun navigateToHomeActivity(
            context: Context,
            user: User,
        ) {
           //TODO
        }

    }

    private lateinit var binding: ActivityHomeBinding
    private val presenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User() // TODO Change this in order to receive the data from the intent
        presenter.initialize(user)
    }

    override fun setUpUI(user: User) {
        with(binding) {
            tvGreeting.text = "Hola ${user.name}!"
        }
    }

    override fun setUpListeners() {
        //nothing to do
    }
}