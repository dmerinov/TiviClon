package com.example.tiviclon.home

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.home.detailShow.DetailShowFragment
import com.example.tiviclon.home.discover.DiscoverFragment
import com.example.tiviclon.home.library.IDetailFragment
import com.example.tiviclon.home.library.LibraryFragment
import com.example.tiviclon.home.search.SearchFragment
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.application.User

class HomeActivity : AppCompatActivity(), IDetailFragment,FragmentCommonComunication,  HomeView {

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

    fun changeDetailFragment(title: String){
        with(binding){
            appBar.title = "${title}"
        }
        loadFragment(DetailShowFragment())
    }

    override fun setUpListeners() {
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

    override fun goShowDetail(id: Int) {
        Toast.makeText(this,"YENDO AL DETALLE DE LA SERIE $id", Toast.LENGTH_SHORT).show()
    }

    override fun getShows():List<Show> = presenter.getShows()

    override fun initFragments() {
        loadFragment(DiscoverFragment())
    }

    override fun updateAppBarText(text: String) {
        with(binding){
            appBar.title = text
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

}