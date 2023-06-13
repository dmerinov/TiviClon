package com.example.tiviclon.views.detailShow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.getMockDetailShows
import com.example.tiviclon.getMockShows
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.IActionsFragment

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val showId = intent.extras?.getSerializable(DETAIL_SHOW) as Int
        val show = getShow(showId)
        setUpUI(show.title)
        setUpListeners()
        initFragments()
    }

    private fun setUpUI(title: String) {
        with(binding) {
            appBar.title = title
            appBar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(appBar)
        }
    }

    private fun initFragments() {
        val showId = intent.extras?.getSerializable(DETAIL_SHOW) as Int
        val show = getShow(showId)
        loadFragment(DetailShowFragment(show))
    }

    private fun setUpListeners() {
        //nothingToDo
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

    private fun getShow(id: Int): DetailShow {
        //petition to retrofit or room (room preferably)
        return getMockDetailShows().filter { it.id == id }[0]
    }

    override fun goShowDetail(id: Int) {

    }

    override fun getShows(): List<Show> = emptyList()

    override fun getDetailShows(idList: List<Int>): List<DetailShow> = emptyList()

    override fun getRelatedShows(genres: List<String>): List<Show> = getMockShows()

}