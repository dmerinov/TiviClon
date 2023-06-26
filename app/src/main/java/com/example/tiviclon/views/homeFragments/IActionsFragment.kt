package com.example.tiviclon.views.homeFragments

import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import kotlinx.coroutines.CoroutineScope


interface IActionsFragment {
    fun goShowDetail(id: Int)
    fun getShows(): List<Show>

    fun getPrefsShows() : List<Int>

    fun deletePrefShow(idShow: String)

    fun setPrefShow(idShow: String)

    fun getDetailShows(onShowRetrieved: (DetailShow) -> Unit)

    fun hideProgressBar()

    fun showProgressBar()
}