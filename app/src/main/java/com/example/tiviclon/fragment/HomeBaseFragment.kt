package com.example.tiviclon.fragment

import com.example.tiviclon.home.HomeActivity

abstract class HomeBaseFragment: BaseFragment(), IHomeBaseView {
    override fun getHomeActivity(): HomeActivity {
        return activity as HomeActivity
    }
}

interface IHomeBaseView {
    fun getHomeActivity(): HomeActivity
}