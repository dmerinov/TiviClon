package com.example.tiviclon.home.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.databinding.FragmentSearchBinding
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.home.FragmentCommonComunication
import com.example.tiviclon.home.library.IActionsFragment
import com.example.tiviclon.home.search.adapter.SearchAdapter
import com.example.tiviclon.model.application.Show
import androidx.appcompat.widget.SearchView as androidSearchView

class SearchFragment : HomeBaseFragment(), SearchView {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private val viewModel: SearchViewModel by viewModels { SearchViewModel.Factory(this) }
    private lateinit var adapter: SearchAdapter
    private lateinit var showList: MutableList<Show>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
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
        viewModel.initialize()
    }

    override fun setUpListeners() {
        with(binding) {
            svShowSearch.setOnQueryTextListener(object :
                androidSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let {
                        viewModel.filterList(it)
                    }
                    return false
                }

            })
        }
    }

    override fun setUpRecyclerView() {
        showList = getShows().toMutableList()
        adapter = SearchAdapter(showList, onClick = {
            val activity = getFragmentContext() as IActionsFragment
            activity.goShowDetail(it)
        }
        )
        with(binding) {
            rvShowList.layoutManager = LinearLayoutManager(getFragmentContext())
            rvShowList.adapter = adapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateList(shows: List<Show>) {
        showList.clear()
        showList.addAll(shows)
        adapter.notifyDataSetChanged()
    }

    override fun setUpUI() {
        with(binding) {
            val activity = getFragmentContext() as FragmentCommonComunication
            activity.updateAppBarText("Buscar")
            svShowSearch.queryHint = "titulo de la serie"
        }
    }

    override fun getShows(): List<Show> {
        val activity = getFragmentContext() as IActionsFragment
        return activity.getShows()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}