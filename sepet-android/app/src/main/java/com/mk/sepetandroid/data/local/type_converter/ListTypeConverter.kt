package com.mk.sepetandroid.data.local.type_converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListTypeConverter {
    private val typeToken = object : TypeToken<List<String>>() {}.type
    @TypeConverter
    fun fromStringArrayList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): List<String> {
        return try {
            Gson().fromJson(value,typeToken)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}