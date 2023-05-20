package com.example.tiviclon.model.dto

data class PreProcessedAPI(
    val page: Int,
    val pages: Int,
    val total: String,
    val tv_shows: List<TvShow>
)