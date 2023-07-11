package com.example.tiviclon.model.application

import java.io.Serializable

data class Show(
    val id: Int,
    val title: String,
    val status: String,
    val image: String,
    val favorite: Boolean
) : Serializable