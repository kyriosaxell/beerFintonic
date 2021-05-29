package com.example.fintonicbeer.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        val g = Gson()
        return g.toJson(list)
    }

//    @TypeConverter
//    fun stringListToJson(value: List<String>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}