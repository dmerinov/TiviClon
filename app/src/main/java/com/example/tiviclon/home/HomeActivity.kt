package com.example.tiviclon.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.home.detailShow.DetailShowActivity
import com.example.tiviclon.home.discover.DiscoverFragment
import com.example.tiviclon.home.library.IActionsFragment
import com.example.tiviclon.home.library.LibraryFragment
import com.example.tiviclon.home.search.SearchFragment
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.application.User

class HomeActivity : AppCompatActivity(), IActionsFragment, FragmentCommonComunication {

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

        val user = intent.extras?.getSerializable(USER_INFO) as User
        initialize(user)
    }

    fun initialize(user: User) {
        setUpUI(user)
        setUpListeners()
        initFragments()
    }

    override fun getShows(): List<Show> {
        val shows: MutableList<Show> = ArrayList()
        shows.add(Show(1, "Spiderman", "Marvel", "Peter Parker"))
        shows.add(Show(2, "Daredevil", "Marvel", "Matthew Michael Murdock"))
        shows.add(Show(3, "Wolverine", "Marvel", "James Howlett"))
        shows.add(Show(4, "Batman", "DC", "Bruce Wayne"))
        shows.add(Show(5, "Thor", "Marvel", "Thor Odinson"))
        shows.add(Show(6, "Flash", "DC", "Jay Garrick"))
        shows.add(Show(7, "Green Lantern", "DC", "Alan Scott"))
        shows.add(Show(8, "Wonder Woman", "DC", "Princess Diana"))
        return shows
    }

    fun goToDetail(show: Show){
        navigateToDetail(show)
    }

    fun setUpUI(user: User) {
        with(binding) {
            appBar.title = "Buenas, ${user.name}"
            appBar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(appBar)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.actionExit) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.top_bar_menu, menu)
        return true
    }

    fun setUpListeners() {
        with(binding) {
            bottomNavBar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_discover -> {
                        loadFragment(DiscoverFragment())
                        true
                    }
                    R.id.action_library -> {
                        loadFragment(LibraryFragment())
                        true
                    }
                    R.id.action_search -> {
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

    override fun goShowDetail(show: Show) {
        Toast.makeText(this, "YENDO AL DETALLE DE LA SERIE ${show.id}", Toast.LENGTH_SHORT).show()
        goToDetail(show)
    }

    fun initFragments() {
        loadFragment(DiscoverFragment())
    }

    override fun updateAppBarText(text: String) {
        with(binding) {
            appBar.title = text
        }
    }

    fun navigateToDetail(show: Show) {
        DetailShowActivity.navigateToShowDetailActivity(this, show)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

}