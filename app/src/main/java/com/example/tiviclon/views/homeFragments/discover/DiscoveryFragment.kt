package com.example.tiviclon.views.homeFragments.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentDiscoveryBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.discover.adapter.DiscoverAdapter
import com.example.tiviclon.views.homeFragments.discover.adapter.PopularAdapter

class DiscoveryFragment : HomeBaseFragment() {

    private var _binding: FragmentDiscoveryBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var locationAdapter: DiscoverAdapter
    private lateinit var popularAdapter: PopularAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //here you would make the dependency injection
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDiscoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
        getShows()
    }

    private fun setUpListeners() {
        with(binding) {
        }
    }


    private fun setUpUI() {
        val activity = getFragmentContext() as FragmentCommonComunication
        val location = activity.getLocation()
        activity.updateAppBarText(getString(R.string.discover))
        with(binding) {
            if (location.contains("ERR")) {
                rvShowListLocation.visibility = View.GONE
                rvTitleLocation.visibility = View.GONE

            } else {
                rvShowListLocation.visibility = View.VISIBLE
                rvTitleLocation.visibility = View.VISIBLE
                rvTitleLocation.text = buildString {
                    append(getString(R.string.tv_location))
                    append(location)
                }
            }

            rvTitlePopular.text = getString(R.string.tv_populars)
        }
    }

    private fun setUpRecyclerView(shows: List<Show>) {
        locationAdapter = DiscoverAdapter(
            shows = shows, onClick = {
                val activity = getFragmentContext() as IActionsFragment
                activity.goShowDetail(it.id)
            },
            context = getFragmentContext()?.baseContext
        )
        popularAdapter = PopularAdapter(
            shows = shows, onClick = {
                val activity = getFragmentContext() as IActionsFragment
                activity.goShowDetail(it.id)
            },
            context = getFragmentContext()?.baseContext
        )
        with(binding) {
            rvShowListLocation.layoutManager =
                LinearLayoutManager(getFragmentContext(), LinearLayoutManager.HORIZONTAL, false)
            rvShowListLocation.adapter = locationAdapter

            rvShowListPopular.layoutManager =
                LinearLayoutManager(getFragmentContext(), LinearLayoutManager.HORIZONTAL, false)
            rvShowListPopular.adapter = popularAdapter

        }
    }

    private fun getShows() {
        val activity = getFragmentContext() as IActionsFragment
        activity.getShows {
            setUpRecyclerView(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }

}