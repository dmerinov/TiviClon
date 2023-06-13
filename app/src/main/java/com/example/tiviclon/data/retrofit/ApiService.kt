package com.example.tiviclon.data.retrofit

import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.data.detail.DetailedShow
import com.example.tiviclon.model.data.general.DataShows
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    fun getShows(
        @Query("page") page: Int
    ): Call<DataShows>

    @GET("show-details")
    fun getDetailedShow(
        @Query("q") id: Int
    ): Call<DetailedShow>

}