package com.example.tiviclon.mappers

import com.example.tiviclon.data.database.entities.VODetailShow
import com.example.tiviclon.data.database.entities.VOShow
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.model.data.detail.DetailedShow

fun Show.toVOShows() = VOShow(
    id, title, status, image
)

fun VOShow.toShow() = Show(
    id = showId, title, status, image
)

fun DetailShow.toDetailedShowVO() = VODetailShow(
    showId = id,
    title = title,
    status = status,
    image = image,
    country = country,
    genres = genres,
    description = description,
    image_thumbnail_path = coverImage,
    year = year
)

fun VODetailShow.toDetailShow() = DetailShow(
    id = showId,
    title = title,
    description = description,
    year = year,
    image = image,
    coverImage = image_thumbnail_path,
    genres = genres,
    status = status,
    country = country
)

fun DetailedShow.toVODetailShow() = VODetailShow(
    showId = tvShow.id,
    title = tvShow.name,
    status = tvShow.status,
    image = tvShow.image_path,
    country = tvShow.country,
    genres = tvShow.genres,
    description = tvShow.description,
    image_thumbnail_path = tvShow.image_thumbnail_path,
    year = tvShow.start_date

)
