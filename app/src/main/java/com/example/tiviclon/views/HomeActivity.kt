package com.example.tiviclon.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.model.application.User

class HomeActivity : AppCompatActivity() {

    companion object {
        const val USER_INFO = "USER_INFO"
        fun navigateToHomeActivity(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra(USER_INFO) as User
        setUpUI(user)
    }

    private fun setUpUI(user: User) {
        with(binding) {
            tvGreeting.text = "Hola ${user.name}!"
        }
    }

    fun setUpListeners() {
        //nothing to do
    }
}