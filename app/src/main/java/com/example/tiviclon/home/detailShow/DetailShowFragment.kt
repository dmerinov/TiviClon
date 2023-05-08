package com.example.tiviclon.home.detailShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tiviclon.databinding.FragmentDetailShowBinding
import com.example.tiviclon.model.application.Show

class DetailShowFragment(val show: Show) : Fragment(), DetailFragmentView {
    private var _binding: FragmentDetailShowBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private val viewModel: DetailShowFragmentViewModel by viewModels {
        DetailShowFragmentViewModel.Factory(
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize(show)
    }

    override fun setUpUI(showVm: Show) {
        with(binding) {
            tvTitle.text = showVm.title
            showDetail.text = showVm.description
        }
    }

    override fun setUpListeners() {
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
