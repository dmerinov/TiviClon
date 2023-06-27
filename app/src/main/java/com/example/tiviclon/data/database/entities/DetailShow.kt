package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VODetailShow(
    @PrimaryKey()
    @ColumnInfo(name = "showId") val showId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "genres") val genres: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "image_thumb") val image_thumbnail_path: String,
    @ColumnInfo(name = "year") val year: String,
)