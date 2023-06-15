package com.example.tiviclon.mappers

import com.example.tiviclon.data.database.entities.VOShow
import com.example.tiviclon.model.application.Show

fun Show.toVOShows() = VOShow(
    id, title, status, image
)
