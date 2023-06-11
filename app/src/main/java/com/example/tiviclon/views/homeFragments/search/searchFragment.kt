package com.example.tiviclon.views.homeFragments.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.databinding.FragmentSearchBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.search.adapter.SearchAdapter
import androidx.appcompat.widget.SearchView as androidSearchView

class SearchFragment : HomeBaseFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var adapter: SearchAdapter
    private lateinit var showList: MutableList<Show>

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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
        setUpRecyclerView()
    }

    fun setUpListeners() {
        with(binding) {
            svShowSearch.setOnQueryTextListener(object :
                androidSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let {
                        filterList(it)
                    }
                    return false
                }

            })
        }
    }

    fun filterList(filter: String) {
        val allShows = getShows()
        var filteredShows = allShows.filter {
            it.title.contains(filter)
        }
        if (filteredShows.isEmpty()) {
            filteredShows = allShows
        }
        updateList(filteredShows)
    }

    fun setUpRecyclerView() {
        showList = getShows().toMutableList()
        adapter = SearchAdapter(showList, onClick = {
            val activity = getFragmentContext() as IActionsFragment
            activity.goShowDetail(it.id)
        },
            onLongClick = {
                Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            }
        )
        with(binding) {
            rvShowList.layoutManager = LinearLayoutManager(getFragmentContext())
            rvShowList.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(shows: List<Show>) {
        showList.clear()
        showList.addAll(shows)
        adapter.notifyDataSetChanged()
    }

    fun setUpUI() {
        with(binding) {
            val activity = getFragmentContext() as FragmentCommonComunication
            activity.updateAppBarText("Buscar")
            svShowSearch.queryHint = "titulo de la serie"
        }
    }

    private fun getShows(): List<Show> {
        val activity = getFragmentContext() as IActionsFragment
        return activity.getShows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}