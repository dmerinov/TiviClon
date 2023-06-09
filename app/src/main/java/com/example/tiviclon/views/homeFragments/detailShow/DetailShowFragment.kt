package com.example.tiviclon.views.homeFragments.detailShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.model.application.Show

class DetailShowFragment(val show: Show) : Fragment(){
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentShowDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI(show)
        setUpListeners()
    }

    private fun setUpUI(showVm: Show) {
        with(binding) {
            itemImg.setImageResource(showVm.image)
            showDetail.text= showVm.description
        }
    }

    private fun setUpListeners() {
        //nothing to do
        with(binding) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}