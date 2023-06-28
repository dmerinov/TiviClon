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
    id = showId, title, status, image, favorite = false
)

fun VOShow.toFavShow() = Show(
    id = showId, title, status, image, favorite = true
)

fun DetailShow.toVoDetailShow() = VODetailShow(
    showId = id,
    title = title,
    status = status,
    image = image,
    country = country,
    genres = toVOString(genres),
    description = description,
    image_thumbnail_path = coverImage,
    year = year,
    favorite = toVOString(favoriteList)
)

fun VODetailShow.toDetailShow() = DetailShow(
    id = showId,
    title = title,
    description = description,
    year = year,
    image = image,
    coverImage = image_thumbnail_path,
    genres = toVOList(genres),
    status = status,
    country = country,
    favoriteList = toVOList(favorite)
)

fun DetailedShow.toVODetailShow() = VODetailShow(
    showId = tvShow.id,
    title = tvShow.name,
    status = tvShow.status,
    image = tvShow.image_path,
    country = tvShow.country,
    genres = toVOString(tvShow.genres),
    description = tvShow.description,
    image_thumbnail_path = tvShow.image_thumbnail_path,
    year = tvShow.start_date,
    favorite = ""
)

fun toVOList(string: String): List<String> {
    return string.split(",")
}

fun toVOString(list: List<String>): String {
    var returnString = ""
    list.forEach {
        if (list.indexOf(it) == list.size - 1) {
            returnString = returnString.plus(it)
        } else {
            returnString = returnString.plus("$it,")
        }

    }
    return returnString
}
