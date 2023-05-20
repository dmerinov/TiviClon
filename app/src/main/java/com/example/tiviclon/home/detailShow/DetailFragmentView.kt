package com.example.tiviclon.home.detailShow

import com.example.tiviclon.model.application.Show

interface DetailFragmentView {
    fun setUpUI(showVm: Show)
    fun setUpListeners()
}