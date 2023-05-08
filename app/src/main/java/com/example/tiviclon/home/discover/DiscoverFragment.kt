package com.example.tiviclon.home.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentDiscoverBinding
import com.example.tiviclon.fragment.HomeBaseFragment
import com.example.tiviclon.home.FragmentCommonComunication
import com.example.tiviclon.home.library.IActionsFragment
import com.example.tiviclon.model.application.Show

class DiscoverFragment : HomeBaseFragment(), DiscoverView {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private val viewModel: DiscoverViewModel by viewModels { DiscoverViewModel.Factory(this) }

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
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initialize()

    }

    override fun setUpListeners() {
        with(binding) {
            btNavigate.setOnClickListener {
                val activity = getFragmentContext() as IActionsFragment
                //this id is just an example.
                //the navigation should be handled on the adapter as a lambda function.
                //this example ilustrates that.
                activity.goShowDetail(Show(-1, "showTitle", "desc", ""))
            }
        }
    }

    override fun setUpUI() {
        val activity = getFragmentContext() as FragmentCommonComunication
        activity.updateAppBarText("Descubre")
        with(binding) {
            fragmentText.text = getString(R.string.navigate_button_str)
            btNavigate.text = getString(R.string.navigate_bt)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }

}