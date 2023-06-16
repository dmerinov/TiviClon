package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo

data class Favorites(
    // @PrimaryKey() las dos
    //@ForeignKey([User::class])
    @ColumnInfo(name = "username") val name: String,
    @ColumnInfo(name = "showId") val showId: String,
)
