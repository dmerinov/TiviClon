package com.example.tiviclon.home.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tiviclon.databinding.FragmentSearchBinding
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.home.FragmentCommonComunication

class SearchFragment : HomeBaseFragment(), SearchView {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private val viewModel: SearchViewModel by viewModels { SearchViewModel.Factory(this) }

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
        viewModel.initialize()
    }

    override fun setUpListeners() {
        //nothingToDo
    }

    override fun setUpUI() {
        with(binding) {
            val activity = getFragmentContext() as FragmentCommonComunication
            activity.updateAppBarText("Biblioteca")
            fragmentText.text = "Este es el fragmento de busqueda"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}