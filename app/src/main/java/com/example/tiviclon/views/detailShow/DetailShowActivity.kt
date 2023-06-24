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
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.repository.CommonRepository
import com.example.tiviclon.repository.Repository
import com.example.tiviclon.sharedPrefs.Prefs
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailShowActivity : AppCompatActivity(), IActionsFragment {

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
    private lateinit var collectedShow: DetailShow
    private lateinit var repository: Repository

    val job = Job()
    private val uiScope =
        CoroutineScope(Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable ->

        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = CommonRepository(
            userDao = TiviClonDatabase.getInstance(applicationContext).userDao(),
            remoteDataSource = RetrofitResource.getRetrofit(),
            preferences = Prefs(context = applicationContext),
            showDao = TiviClonDatabase.getInstance(applicationContext).showDao(),
            favoriteDao = TiviClonDatabase.getInstance(applicationContext).favoriteDao(),
            detailShowDao = TiviClonDatabase.getInstance(applicationContext).VODetailShow()
        )
        setUpUI()
        setUpListeners()
        initFragments()
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

    override fun getShows(onShowsRetrieved: (List<Show>) -> Unit) {
        //nothing to do
    }

    override fun getPrefsShows(onShowsRetrieved: (List<Int>) -> Unit) {
        //ask repository for detail shows
        val returnList = mutableListOf<Int>()
        val idUser = repository.getLoggedUser()

        idUser?.let {
            uiScope.launch {
                returnList.addAll(repository.getFavShows(idUser).map {
                    it.toInt()
                })
                onShowsRetrieved(returnList)
            }
        }
    }

    override fun deletePrefShow(idShow: String) {
        val idUser = repository.getLoggedUser()
        uiScope.launch {
            val result = repository.deleteFavUser(idUser.toString(), idShow)
            Log.i("CONTROL_MESSAGES", "delete show from fav result: $result")
        }
    }

    override fun setPrefShow(idShow: String) {
        val idUser = repository.getLoggedUser()
        uiScope.launch {
            val result = repository.addFavUser(idUser.toString(), idShow)
            Log.i("CONTROL_MESSAGES", "inserted show from fav result: $result")
        }
    }

    override fun getDetailShows(
        id: Int,
        scope: CoroutineScope,
        onShowRetrieved: (DetailShow) -> Unit
    ) {
        val api = Retrofit.Builder()
            .baseUrl(RetrofitResource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


        scope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                showProgressBar()
            }

            collectedShow = repository.getDetailShow(id)

            withContext(Dispatchers.Main) {
                collectedShow?.let {
                    onShowRetrieved(it)
                }
                hideProgressBar()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }
}