package com.example.tiviclon.model.application

import java.io.Serializable

data class Show(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
) : Serializable
