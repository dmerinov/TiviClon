package com.example.tiviclon.views.detailShow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.TiviClon
import com.example.tiviclon.container.AppContainer
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.*

class DetailShowActivity() : AppCompatActivity(), IActionsFragment {

    companion object {
        const val DETAIL_SHOW = "DETAIL_SHOW"

        fun navigateToShowDetailActivity(
            context: Context,
            showId: Int
        ) {
            val intent = Intent(context, DetailShowActivity::class.java).apply {
                putExtra(DETAIL_SHOW, showId)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityDetailShowBinding
    private var collectedShow = DetailShow()
    private lateinit var appContainer: AppContainer
    private val favShows = mutableListOf<String>()

    val job = Job()
    private val uiScope =
        CoroutineScope(Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable ->

        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appContainer = TiviClon.appContainer
        setUpUI()
        setUpListeners()
        initFragments()
        setUpLivedata()
    }

    override fun hideProgressBar() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun showProgressBar() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpUI() {
        with(binding) {
            appBar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(appBar)
        }
    }

    private fun setUpLivedata() { //TODO VER CON ROBERTO MAÑANA
        val intent = Intent()
        val showId = intent.extras?.getInt(DETAIL_SHOW)

        appContainer.repository.getLoggedUser()?.let {
            appContainer.repository.getFavShows(it).observe(this) {
                uiScope.launch(Dispatchers.IO) {
                    favShows.clear()
                    favShows.addAll(it)
                }
            }
        }
        showId?.let {
            appContainer.repository.getDetailShow(showID = it).observe(this) {
                collectedShow = it.toDetailShow()
                initFragments()
            }
        }
    }

    private fun initFragments() {
        val showId = intent.extras?.getSerializable(DETAIL_SHOW) as Int
        loadFragment(DetailShowFragment(showId))
    }

    private fun setUpListeners() {
        //nothingToDo
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

    override fun goShowDetail(id: Int) {

    }

    override fun getShows(): List<Show> {
        //NOTHING TO DO
        return emptyList()
    }

    override fun getPrefsShows() = favShows.map { it.toInt() }

    override fun deletePrefShow(idShow: String) {
        val idUser = appContainer.repository.getLoggedUser()
        uiScope.launch {
            val result = appContainer.repository.deleteFavUser(idUser.toString(), idShow)
            Log.i("CONTROL_MESSAGES", "delete show from fav result: $result")
        }
    }

    override fun setPrefShow(idShow: String) {
        val idUser = appContainer.repository.getLoggedUser()
        uiScope.launch {
            val result = appContainer.repository.addFavUser(idUser.toString(), idShow)
            Log.i("CONTROL_MESSAGES", "inserted show from fav result: $result")
        }
    }

    override fun getDetailShows(
        id: Int,
        scope: CoroutineScope,
        onShowRetrieved: (DetailShow) -> Unit
    ) {
        onShowRetrieved(collectedShow)
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }
}