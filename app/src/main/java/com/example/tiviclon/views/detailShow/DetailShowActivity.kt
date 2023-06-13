package com.example.tiviclon.views.detailShow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.mappers.toShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.await
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
    private val relatedShows = mutableListOf<Show>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showId = intent.extras?.getSerializable(DETAIL_SHOW) as Int
        setUpUI()
        setUpListeners()
        initFragments()
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

        val api = Retrofit.Builder()
            .baseUrl(RetrofitResource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val api_shows = api.getDetailedShow(showId).await()
            val collectedShow = api_shows.toDetailShow()
            loadFragment(DetailShowFragment(collectedShow))
        }

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

    override fun getPrefsShows(): List<Int> {
        //ask repository for detail shows
        return emptyList()
    }

    override fun getDetailShows(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
        }
    }

    override fun getRelatedShows(
        genres: List<String>,
        idList: List<Int>,
        onShowsRetrieved: (List<Show>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {

        }
    }
}