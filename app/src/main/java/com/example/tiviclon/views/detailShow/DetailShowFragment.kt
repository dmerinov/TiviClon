package com.example.tiviclon.views.detailShow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiviclon.R
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.discover.adapter.PopularAdapter

class DetailShowFragment(val show: DetailShow) : HomeBaseFragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use
    private lateinit var popularAdapter: PopularAdapter
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
        setUpRecyclerView()
    }

    private fun setUpUI(showVm: DetailShow) {
        with(binding) {
            itemImg.setImageResource(showVm.image)
            //showDetail.text = showVm.description
            tvRelated.text = getString(R.string.related_string)
            tvShowDetail.text =
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque quis urna pellentesque, finibus felis sed, porttitor dui. Curabitur rhoncus enim libero, sit amet maximus orci molestie at. Sed mollis, lectus ac congue molestie, neque metus finibus felis, at scelerisque diam elit a dui. Sed volutpat nulla justo, quis molestie dui malesuada vitae. Integer id dolor ipsum. Mauris non dui sodales, congue odio id, placerat purus. Nulla quis dapibus velit. Aliquam id sem vitae quam aliquam vehicula. Quisque eu mauris ullamcorper justo consectetur aliquet ut eu lorem.\n" +
                    "\n" +
                    "Etiam non leo varius, mollis velit nec, porta lacus. Duis a ipsum porta mauris aliquet rutrum eu quis odio. Donec tincidunt congue tortor, sed rutrum tellus pulvinar et. Morbi quis est dolor. Nullam aliquam sapien massa, et fringilla dui ornare id. Morbi bibendum eu purus quis viverra. Sed in neque non mi tempus facilisis at ut turpis. Donec sit amet justo a ex mattis accumsan eu id tortor. Etiam vestibulum sit amet lectus ut mattis. Maecenas nec nulla mauris. "
        ivStockImage.setImageResource(showVm.coverImage)
        }
    }

    private fun setUpRecyclerView(){
        popularAdapter = PopularAdapter(shows = getShows(show.genres), onClick = {})
        with(binding) {
            rvMoreShows.layoutManager =
                LinearLayoutManager(getFragmentContext(), LinearLayoutManager.HORIZONTAL, false)
            rvMoreShows.adapter = popularAdapter
        }
    }

    private fun getShows(genres: List<String>): List<Show>{
        val activity = getFragmentContext() as IActionsFragment
        return activity.getRelatedShows(genres)
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