package com.example.tiviclon.views.detailShow

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.tiviclon.R
import com.example.tiviclon.TiviClon.Companion.appContainer
import com.example.tiviclon.databinding.FragmentShowDetailBinding
import com.example.tiviclon.mappers.toDetailShow
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.views.homeFragments.HomeBaseFragment
import com.example.tiviclon.views.homeFragments.IActionsFragment
import kotlinx.coroutines.*

class DetailShowFragment(val showId: String, val userId: String) : HomeBaseFragment() {
    private var _binding: FragmentShowDetailBinding? = null
    private val binding get() = _binding!! //this is the one that you've to use

    val job = Job()
    private val uiScope =
        CoroutineScope(Dispatchers.Main + job + CoroutineExceptionHandler { _, throwable ->
            val activity = getFragmentContext() as IActionsFragment
            activity.hideProgressBar()
        })

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
        setUpLivedata()
    }

    private fun setUpLivedata() {

        showId?.let { showId ->
            appContainer.repository.getDetailShow(showID = showId, userId = userId)
                .observe(viewLifecycleOwner) {

                    val collectedShow = it// show detail
                    if (collectedShow != null) {
                        val favourite = collectedShow.favorite // Favorite
                        favourite?.let {
                            val detailshow = collectedShow.toDetailShow(true)
                            setUpUI(detailshow)
                            setUpListeners(detailshow)
                        }
                    } else {
                        val detailshow = appContainer.repository.getDetailShow(showId)
                        setUpUI(detailshow.toDetailShow(false))
                        setUpListeners(detailshow.toDetailShow(false))
                    }

                }
        }
    }

    private fun setUpUI(showVm: DetailShow) {
        val context = getFragmentContext()?.baseContext
        var genres = showVm.genres.joinToString(", ")
        with(binding) {
            context?.let {
                Glide.with(it).load(showVm.image).into(itemImg)
            }
            tvDescription.text = getString(R.string.about)
            tvShowDetail.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(showVm.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(showVm.description)
            }

            tvTitle.text = buildString {
                append(getString(R.string.title))
                append(" ")
                append(showVm.title)
            }
            tvYear.text = buildString {
                append(getString(R.string.release_year))
                append(" ")
                append(showVm.year)
            }
            tvGenres.text = buildString {
                append(getString(R.string.genres))
                append(" ")
                append(genres)
            }
            context?.let {
                Glide.with(it).load(showVm.coverImage).into(ivStockImage)
            }

            if (showVm.favorite) {
                btFavToggle.setImageResource(R.drawable.star_fav)
            } else {
                btFavToggle.setImageResource(R.drawable.star_not_fav)
            }
        }
    }

    private fun setUpListeners(collectedShow: DetailShow) {
        with(binding) {
            btFavToggle.setOnClickListener {
                //obtener usuario y show
                uiScope.launch {
                    appContainer.repository.updateFavUser(userId, collectedShow)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //detach binding
        job.cancel()
        Log.i("JOBS_COR", "detail job is cancelled")
        _binding = null
    }
}