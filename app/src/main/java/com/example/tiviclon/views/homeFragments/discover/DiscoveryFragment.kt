package com.example.tiviclon.views.homeFragments.discover

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentDiscoveryBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment

class DiscoveryFragment : HomeBaseFragment() {

    private var _binding: FragmentDiscoveryBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use

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
        _binding = FragmentDiscoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpListeners()
    }

    private fun setUpListeners() {
        with(binding) {
            btNavigate.setOnClickListener {
                val activity = getFragmentContext() as IActionsFragment
                //this id is just an example.
                //the navigation should be handled on the adapter as a lambda function.
                //this example ilustrates that.
                activity.goShowDetail(Show(-1, "buttonShowTitle", "buttonShowDescription", ""))
            }
        }
    }

    private fun setUpUI() {
        val activity = getFragmentContext() as FragmentCommonComunication
        activity.updateAppBarText(getString(R.string.discover))
        with(binding) {
            fragmentText.text = activity.getLocation()
            btNavigate.text = getString(R.string.navigate_bt)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }

}