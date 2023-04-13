package com.example.tiviclon.home.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchView {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
    }
}