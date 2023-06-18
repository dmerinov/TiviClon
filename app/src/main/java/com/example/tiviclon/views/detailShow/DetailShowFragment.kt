package com.example.tiviclon.views.detailShow

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tiviclon.R
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class DetailShowFragment(val showId: Int) : HomeBaseFragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use

    private val job = Job()
    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            hideProgressBar()
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val api = Retrofit.Builder()
            .baseUrl(RetrofitResource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        scope.launch(Dispatchers.IO) {
            binding.progressBar.visibility = View.VISIBLE
            val api_shows = api.getDetailedShow(showId).await()
            withContext(Dispatchers.Main) {
                setUpUI(api_shows.toDetailShow())
                binding.progressBar.visibility = View.GONE
            }
        }
        setUpListeners()
    }

    private fun setUpUI(showVm: DetailShow) {
        val context = getFragmentContext()?.baseContext
        var genres = showVm.genres.joinToString(", ")
        with(binding) {
            context?.let {
                Glide.with(it).load(showVm.image).into(itemImg)
            }
            tvDescription.text = getString(R.string.about)
            tvShowDetail.text = showVm.description

            tvTitle.text = buildString {
                append(getString(R.string.title))
                append(" ")
                append(showVm.title)
            }
            tvYear.text = buildString {
                append(getString(R.string.release_year))
                append(" ")
                append(showVm.year)
            }
            tvGenres.text = buildString {
                append(getString(R.string.genres))
                append(" ")
                append(genres)
            }
            btFavToggle
            context?.let {
                Glide.with(it).load(showVm.coverImage).into(ivStockImage)
            }
            if (isShowFav()) {
                btFavToggle.setImageResource(R.drawable.star_fav)
            } else {
                btFavToggle.setImageResource(R.drawable.star_not_fav)
            }
        }
    }

    private fun setUpListeners() {
        with(binding) {
            btFavToggle.setOnClickListener {
                val activity = getFragmentContext() as IActionsFragment
                if (isShowFav()) {
                    activity.deletePrefShow(showId.toString())
                    btFavToggle.setImageResource(R.drawable.star_not_fav)
                } else {
                    activity.setPrefShow(showId.toString())
                    btFavToggle.setImageResource(R.drawable.star_fav)

                }
            }
        }
    }

    private fun hideProgressBar() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun isShowFav(): Boolean {
        val activity = getFragmentContext() as IActionsFragment
        return activity.getPrefsShows().contains(showId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        job.cancel()
        Log.i("JOBS_COR", "detail job is cancelled")
        _binding = null
    }
}