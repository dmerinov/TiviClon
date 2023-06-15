package com.example.tiviclon.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey()
    @ColumnInfo(name = "username") val name: String,
    @ColumnInfo(name = "password") val password: String,
)