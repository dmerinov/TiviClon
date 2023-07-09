package com.example.tiviclon.views.homeFragments

import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import kotlinx.coroutines.CoroutineScope


interface IActionsFragment {
    fun goShowDetail(id: Int, userId: String)
    fun getShows(): List<Show>

    fun getPrefsShows() : List<Int>

    fun getDetailShows(id: Int, scope: CoroutineScope, onShowRetrieved: (DetailShow) -> Unit)

    fun hideProgressBar()

    fun showProgressBar()
}