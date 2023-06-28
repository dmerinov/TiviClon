package com.example.tiviclon.views.homeFragments.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.*
import androidx.appcompat.widget.SearchView as androidSearchView

class SearchFragment(val userId: String) : HomeBaseFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var adapter: SearchAdapter
    private var showList: MutableList<Show> = mutableListOf()
    private var initialList: MutableList<Show> = mutableListOf()
    val job = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + job)

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
        if (showList.isEmpty()) {
            uiScope.launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                }
                //asyncOperation
                async {
                    getShows()
                }.await()
                withContext(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }
            }
        }
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

    override fun onResume() {
        super.onResume()

    }

    fun filterList(filter: String) {
        val allShows = initialList
        var filteredShows = allShows.filter {
            it.title.contains(filter)
        }
        if (filteredShows.isEmpty()) {
            filteredShows = allShows
        }
        updateList(filteredShows)
    }

    private fun setUpRecyclerView() {
        adapter = SearchAdapter(
            showList, onClick = {
                val activity = getFragmentContext() as IActionsFragment
                activity.goShowDetail(it.id, userId)
            },
            onLongClick = {
                Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
            },
            context = getFragmentContext()?.baseContext
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

    private fun getShows() {
        val activity = getFragmentContext() as IActionsFragment
        showList.clear()
        showList.addAll(activity.getShows())
        initialList.addAll(showList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
        job.cancel()
        Log.i("JOBS_COR", "job is cancelled")
    }
}