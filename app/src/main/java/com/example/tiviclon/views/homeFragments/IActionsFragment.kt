package com.example.tiviclon.views.homeFragments

import com.example.tiviclon.data.database.TiviClonDatabase
import com.example.tiviclon.model.application.Show


interface IActionsFragment {
    fun goShowDetail(id: Int)
    fun getShows(onShowsRetrieved: (List<Show>) -> Unit)

    fun getPrefsShows() : List<Int>

    fun deletePrefShow(idShow: String)

    fun setPrefShow(idShow:String)

    fun getDetailShows(id: Int)

    fun getBD(): TiviClonDatabase?
}