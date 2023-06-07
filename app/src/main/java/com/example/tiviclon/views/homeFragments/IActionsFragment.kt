package com.example.tiviclon.views.homeFragments

import com.example.tiviclon.model.application.Show

interface IActionsFragment {
    fun goShowDetail(show: Show)
    fun getShows(): List<Show>
}