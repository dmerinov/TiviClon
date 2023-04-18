package com.example.tiviclon.home.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.databinding.FragmentLibraryBinding
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.home.library.adapter.LibraryAdapter
import com.example.tiviclon.model.application.Show

class LibraryFragment : HomeBaseFragment(), LibraryView {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var presenter: LibraryPresenter
    private lateinit var adapter: LibraryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLibraryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LibraryPresenter(this)
        presenter.initialize()
    }

    override fun setUpUI() {
        with(binding){
            fragmentText.text = "Este es el fragmento de tu biblioteca de series"
        }
    }

    override fun setUpRecyclerView(){
        adapter = LibraryAdapter(getShows())
        with(binding){
            rvShowList.layoutManager = LinearLayoutManager(getFragmentContext())
            rvShowList.adapter = adapter
        }
    }
    private fun getShows(): MutableList<Show>{
        val shows:MutableList<Show> = ArrayList()
        shows.add(Show("Spiderman", "Marvel", "Peter Parker"))
        shows.add(Show("Daredevil", "Marvel", "Matthew Michael Murdock"))
        shows.add(Show("Wolverine", "Marvel", "James Howlett"))
        shows.add(Show("Batman", "DC", "Bruce Wayne",))
        shows.add(Show("Thor", "Marvel", "Thor Odinson"))
        shows.add(Show("Flash", "DC", "Jay Garrick"))
        shows.add(Show("Green Lantern", "DC", "Alan Scott"))
        shows.add(Show("Wonder Woman", "DC", "Princess Diana"))
        return shows
    }

    override fun setUpListeners() {
        //nothing to do
        with(binding){
            fragmentText.setOnClickListener {
                //------------------------TUTORIA---------------------
                val activity = getFragmentContext() as IDetailFragment
                activity.goShowDetail(/*serie*/)
                //-----------------------TUTORIA-----------------------
                //navegar al siguiente f
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}


//esta interfaz está en cada fragment (si la interfaz es igual en todos los fragments se unfica) y la hereda la actividad
//padre.
interface IDetailFragment{
    fun goShowDetail()
}