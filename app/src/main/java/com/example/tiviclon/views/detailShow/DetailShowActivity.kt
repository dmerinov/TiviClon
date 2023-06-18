package com.example.tiviclon.views.detailShow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.data.database.entities.Favorites
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.sharedPrefs.TiviClon.Companion.prefs
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
    private var db: TiviClonDatabase? = null

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

    override fun getPrefsShows(): List<Int> {
        //ask repository for detail shows
        val returnList = mutableListOf<Int>()
        val idUser = prefs.getLoggedUser()
        getBD()?.let { bd ->
            idUser?.let {
                val bdList = bd.favoriteDao().getUserFavShows(idUser)
                if (bdList.isNotEmpty()) {
                    returnList.addAll(bdList.map { it.showId.toInt() })
                }
            }
        }
        return returnList
    }

    override fun deletePrefShow(idShow: String) {
        val idUser = prefs.getLoggedUser()
        getBD()?.let { bd ->
            idUser?.let {
                bd.favoriteDao().delete(Favorites(idUser, idShow))
            }
        }
    }

    override fun setPrefShow(idShow: String) {
        val idUser = prefs.getLoggedUser()
        getBD()?.let { bd ->
            idUser?.let {
                bd.favoriteDao().insert(Favorites(idUser, idShow))
            }
        }
    }

    override fun getDetailShows(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
        }
    }

    override fun getBD() = TiviClonDatabase.getInstance(applicationContext)
}