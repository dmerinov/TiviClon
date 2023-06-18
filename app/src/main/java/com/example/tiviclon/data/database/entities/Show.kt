package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VOShow(
    @PrimaryKey()
    @ColumnInfo(name = "showId") val showId: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "image") val image: String
)

