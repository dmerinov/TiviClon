package com.example.tiviclon.model.application

import java.io.Serializable

data class DetailShow(
    val id: Int,
    val title: String,
    val description: String,
    val year: String,
    val image: String,
    val coverImage: String,
    val genres: List<String> = emptyList()
) : Serializable