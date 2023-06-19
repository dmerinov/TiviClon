package com.example.tiviclon.data.database.converters

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        val result = value?.split(" ")
        return value?.let { result }
    }

    @TypeConverter
    fun dateToTimestamp(list: List<String>?): String? {
        var result: String? = null

        if (list != null) {
            result = ""
            for (element in list) {
                result.plus(" $element")
            }
        }

        return result?.let { result }
    }
}
