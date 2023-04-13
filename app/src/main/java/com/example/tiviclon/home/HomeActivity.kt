package com.example.tiviclon.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.home.discover.DiscoverFragment
import com.example.tiviclon.home.library.LibraryFragment
import com.example.tiviclon.home.search.SearchFragment
import com.example.tiviclon.model.application.User

class HomeActivity : AppCompatActivity(), HomeView {

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
    private val presenter = HomePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.getSerializable(USER_INFO) as User
        presenter.initialize(user)
    }

    override fun setUpUI(user: User) {
        with(binding){
            appBar.title = "Buenas, ${user.name}"
            appBar.setTitleTextColor(Color.WHITE)
        }
    }

    override fun setUpListeners() {
        with(binding) {
            bottomNavBar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_favorites -> {
                        loadFragment(DiscoverFragment())
                        true
                    }
                    R.id.action_music -> {
                        loadFragment(LibraryFragment())
                        true
                    }
                    R.id.action_schedules -> {
                        loadFragment(SearchFragment())
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    override fun initFragments() {
        loadFragment(DiscoverFragment())
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

}