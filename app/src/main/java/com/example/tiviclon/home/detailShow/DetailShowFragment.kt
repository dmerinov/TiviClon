package com.example.tiviclon.home.detailShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiviclon.databinding.FragmentDetailShowBinding

class DetailShowFragment : Fragment(), DetailView {
    private var _binding: FragmentDetailShowBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var presenter: DetailShowPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailShowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = DetailShowPresenter(this)
        presenter.initialize()
    }

    override fun setUpUI() {
        with(binding){
            showDetail.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut viverra lectus lectus, ultrices tempus odio fermentum sed. Sed quis orci auctor, fringilla felis a, dictum tortor. Vivamus cursus elementum venenatis. Nullam condimentum bibendum risus, ut sodales leo. Nulla tellus magna, congue ac orci et, maximus dignissim leo. Etiam neque justo, fermentum vel bibendum in, condimentum lacinia massa. Phasellus nulla nulla, dignissim in urna eget, suscipit finibus arcu. Donec auctor feugiat tellus eu maximus. Aenean nec efficitur nunc. Maecenas nec sapien sem. "
        }
    }

    override fun setUpListeners() {
        //nothing to do
        with(binding){

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        _binding = null
    }
}
