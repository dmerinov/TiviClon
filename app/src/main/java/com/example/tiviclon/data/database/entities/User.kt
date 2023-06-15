package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name = "username") val name: String,
    @ColumnInfo(name = "password") val password: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int? = null
}