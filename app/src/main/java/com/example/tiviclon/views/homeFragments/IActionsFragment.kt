package com.example.tiviclon.views.homeFragments

import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show

interface IActionsFragment {
    fun goShowDetail(id: Int)
    fun getShows(): List<Show>

    fun getDetailShows(idList: List<Int>): List<DetailShow>
}