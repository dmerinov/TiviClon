package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            childColumns = ["username"],
            parentColumns = ["username"]
        ),
        ForeignKey(
            entity = VOShow::class,
            childColumns = ["favShowId"],
            parentColumns = ["showId"]
        )
    ],
    primaryKeys = ["username", "favShowId"]
)
data class Favorites(
    @ColumnInfo(name = "username") val name: String,
    @ColumnInfo(name = "favShowId") val favShowId: String,
)
