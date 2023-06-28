package com.example.tiviclon.model.application

import java.io.Serializable

data class DetailShow(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val year: String = "",
    val image: String = "",
    val coverImage: String = "",
    val genres: List<String> = emptyList(),
    val status: String = "",
    val country: String = "",
    val favoriteList: List<String> = emptyList()
) : Serializable