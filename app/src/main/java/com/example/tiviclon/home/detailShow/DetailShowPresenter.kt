package com.example.tiviclon.home.detailShow


class DetailShowPresenter(private val view: DetailView) {

    fun initialize(){
        view.setUpUI()
        view.setUpListeners()
    }
}