package com.example.tiviclon.model.data.general

data class DataShows(
    val page: Int,
    val pages: Int,
    val total: String,
    val tv_shows: List<TvShow>
)