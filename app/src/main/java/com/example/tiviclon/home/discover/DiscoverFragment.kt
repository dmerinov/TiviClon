package com.example.tiviclon.home.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentDiscoverBinding

class DiscoverFragment : HomeBaseFragment(), DiscoverView {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var presenter: DiscoverPresenter

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
        _binding = FragmentDiscoverBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DiscoverPresenter(this)
        presenter.initialize()

    }

    override fun setUpListeners() {
        //nothing to do
    }

    override fun setUpUI() {
        with(binding){
            fragmentText.text = "Este es el fragmento de descubrir"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }

}