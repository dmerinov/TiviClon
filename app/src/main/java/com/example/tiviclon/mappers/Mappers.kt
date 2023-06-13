package com.example.tiviclon.mappers

import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.data.detail.DetailedShow
import com.example.tiviclon.model.data.general.TvShow

fun TvShow.toShow() = Show(
    id = id,
    title = name,
    status = status,
    image = image_thumbnail_path
)

fun DetailedShow.toDetailShow() = DetailShow(
    id = tvShow.id,
    title = tvShow.name,
    description = tvShow.description,
    year = tvShow.start_date,
    image = tvShow.image_thumbnail_path,
    coverImage = tvShow.pictures[0],
    genres = tvShow.genres
)

fun DetailedShow.toShow() = Show(
    id = tvShow.id,
    title = tvShow.name,
    image = tvShow.image_thumbnail_path,
    status = tvShow.status
)