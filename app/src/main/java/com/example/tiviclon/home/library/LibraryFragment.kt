package com.example.tiviclon.home.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.databinding.FragmentLibraryBinding
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.home.FragmentCommonComunication
import com.example.tiviclon.home.library.adapter.LibraryAdapter
import com.example.tiviclon.model.application.Show

class LibraryFragment : HomeBaseFragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var adapter: LibraryAdapter

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
        setUpRecyclerView()
    }

    fun setUpUI() {
        val activity = getFragmentContext() as FragmentCommonComunication
        activity.updateAppBarText("Biblioteca")

    }

    fun setUpRecyclerView() {
        adapter = LibraryAdapter(shows = getShows(), onClick = {
            val activity = getFragmentContext() as IActionsFragment
            activity.goShowDetail(it)
        }
        )
        with(binding) {
            rvShowList.layoutManager = LinearLayoutManager(getFragmentContext())
            rvShowList.adapter = adapter
        }
    }

    fun getShows(): List<Show> {
        val activity = getFragmentContext() as IActionsFragment
        return activity.getShows()
    }

    fun setUpListeners() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}

//esta interfaz está en cada fragment (si la interfaz es igual en todos los fragments se unfica) y la hereda la actividad
//padre.
//en este caso la interfaz es igual: valorar si se debe sacar a otra clase
interface IActionsFragment {
    fun goShowDetail(show: Show)
    fun getShows(): List<Show>
}