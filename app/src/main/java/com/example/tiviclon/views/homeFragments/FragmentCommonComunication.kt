package com.example.tiviclon.views.homeFragments

interface FragmentCommonComunication {
    fun updateAppBarText(text: String)
    fun getLocation(): String

    fun isUserLogged(): Boolean
}