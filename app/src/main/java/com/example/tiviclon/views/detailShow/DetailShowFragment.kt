package com.example.tiviclon.views.detailShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.detailShow.adapter.DetailAdapter
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.discover.adapter.PopularAdapter

class DetailShowFragment(val show: DetailShow) : HomeBaseFragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var popularAdapter: DetailAdapter
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
        setUpUI(show)
        setUpListeners()
        getShows(show.genres)
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

    private fun setUpRecyclerView(shows: List<Show>) {
        popularAdapter = DetailAdapter(shows = shows, onClick = {}, getFragmentContext()?.baseContext)
        with(binding) {
            rvMoreShows.layoutManager =
                LinearLayoutManager(getFragmentContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMoreShows.adapter = popularAdapter
        }
    }

    private fun getShows(genres: List<String>){
        val activity = getFragmentContext() as IActionsFragment
        val ids = mutableListOf<Int>()
        activity.getShows {
            it.forEach { ids.add(it.id) }
        activity.getRelatedShows(genres, ids) { list ->
            setUpRecyclerView(list)
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
        _binding = null
    }
}