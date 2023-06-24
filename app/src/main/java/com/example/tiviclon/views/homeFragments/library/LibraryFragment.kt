package com.example.tiviclon.views.homeFragments.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.databinding.FragmentLibraryBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.library.adapter.LibraryAdapter

class LibraryFragment : HomeBaseFragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var adapter: LibraryAdapter
    private val libraryShows = mutableListOf<Show>()
    private val allShows = mutableListOf<Show>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
        getShows()
    }

    fun setUpUI() {
        val activity = getFragmentContext() as FragmentCommonComunication
        activity.updateAppBarText("Biblioteca")
        if (activity.isUserLogged()) {
            with(binding) {
                rvShowList.visibility = View.VISIBLE
                tvLoggingWarning.visibility = View.GONE
            }
        } else {
            with(binding) {
                rvShowList.visibility = View.GONE
                tvLoggingWarning.visibility = View.VISIBLE
            }
        }
    }

    private fun setUpRecyclerView(shows: List<Show>) {
        adapter = LibraryAdapter(
            shows = libraryShows, onClick = {
                val activity = getFragmentContext() as IActionsFragment
                activity.goShowDetail(it.id)
            },
            onLongClick = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            },
            context = getFragmentContext()?.baseContext
        )
        with(binding) {
            rvShowList.layoutManager = LinearLayoutManager(getFragmentContext())
            rvShowList.adapter = adapter
        }

        val activity = getFragmentContext() as IActionsFragment
        activity.getPrefsShows { prefShows ->
            libraryShows.clear()
            libraryShows.addAll(allShows.filter { show ->
                prefShows.contains(show.id)
            })
            adapter.notifyDataSetChanged()
        }
    }

    private fun getShows() {
        val activity = getFragmentContext() as IActionsFragment
        activity.getShows {
            allShows.addAll(it)
            activity.getPrefsShows { prefsShows ->
                libraryShows.addAll(it.filter { prefsShows.contains(it.id) })
            }
            setUpRecyclerView(libraryShows)
        }
    }

    private fun setUpListeners() {
        with(binding) {
            fabReload.setOnClickListener {
                val activity = getFragmentContext() as IActionsFragment
                activity.getPrefsShows { prefList ->
                    libraryShows.clear()
                    libraryShows.addAll(allShows.filter { show ->
                        prefList.contains(show.id)
                    })
                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = getFragmentContext() as IActionsFragment
        activity.getPrefsShows { prefShows ->
            libraryShows.clear()
            libraryShows.addAll(allShows.filter { show ->
                prefShows.contains(show.id)
            })
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}