package com.example.tiviclon.home.detailShow

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityDetailShowBinding
import com.example.tiviclon.model.application.Show

class DetailShowActivity : AppCompatActivity(), DetailShowActivityView {

    companion object {
        const val DETAIL_SHOW = "DETAIL_SHOW"

        fun navigateToShowDetailActivity(
            context: Context,
            show: Show
        ){
            val intent = Intent(context, DetailShowActivity::class.java).apply{
                putExtra(DETAIL_SHOW, show)
            }
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityDetailShowBinding
    private val viewModel: DetailShowViewModel by viewModels { DetailShowViewModel.Factory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val show = intent.extras?.getSerializable(DETAIL_SHOW) as Show
        viewModel.initialize(show)
    }

    override fun setUpUI(title: String) {
        with(binding){
            appBar.title = title
            appBar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(appBar)
        }
    }

    override fun initFragments() {
        loadFragment(DetailShowFragment(viewModel.getShow()))
    }

    override fun setUpListeners() {
        //nothingToDo
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

}