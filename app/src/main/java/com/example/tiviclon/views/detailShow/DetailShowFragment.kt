package com.example.tiviclon.views.detailShow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tiviclon.R
import com.example.tiviclon.data.retrofit.ApiService
import com.example.tiviclon.data.retrofit.RetrofitResource
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.detailShow.adapter.DetailAdapter
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.discover.adapter.PopularAdapter
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

class DetailShowFragment(val showId: Int) : HomeBaseFragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
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

        uiScope.launch(Dispatchers.IO) {
            binding.progressBar.visibility = View.VISIBLE
            val api_shows = api.getDetailedShow(showId).await()
            val collectedShow = api_shows.toDetailShow()
            withContext(Dispatchers.Main){
                setUpUI(collectedShow)
                binding.progressBar.visibility = View.GONE
            }
        }
        setUpListeners()
    }

    private fun setUpUI(showVm: DetailShow) {
        val context = getFragmentContext()?.baseContext
        with(binding) {
            context?.let {
                Glide.with(it).load(showVm.image).into(itemImg)
            }
            //showDetail.text = showVm.description
            tvRelated.text = getString(R.string.related_string)
            tvShowDetail.text = showVm.description
            context?.let {
                Glide.with(it).load(showVm.coverImage).into(ivStockImage)
            }

        }
    }

    private fun setUpListeners() {
        with(binding) {
            btFavToggle.setOnClickListener {
                //change with star_fav or star_not_fav depending on the room state of the instance
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        job.cancel()
        Log.i("JOBS_COR", "detail job is cancelled")
        _binding = null
    }
}