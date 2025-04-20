package com.asikarcelik.dnyakaifi.data.local.converter

import androidx.room.TypeConverter
import com.asikarcelik.dnyakaifi.data.model.Landmark
import com.asikarcelik.dnyakaifi.data.model.QuizQuestion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * String listelerini JSON formatında saklamak için TypeConverter
 */
class ListStringConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value ?: emptyList<String>())
    }
    
    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}

/**
 * String-String Map'lerini JSON formatında saklamak için TypeConverter
 */
class MapStringConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromStringMap(value: Map<String, String>?): String {
        return gson.toJson(value ?: emptyMap<String, String>())
    }
    
    @TypeConverter
    fun toStringMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType) ?: emptyMap()
    }
}

/**
 * Landmark listelerini JSON formatında saklamak için TypeConverter
 */
class LandmarkListConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromLandmarkList(value: List<Landmark>?): String {
        return gson.toJson(value ?: emptyList<Landmark>())
    }
    
    @TypeConverter
    fun toLandmarkList(value: String): List<Landmark> {
        val listType = object : TypeToken<List<Landmark>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}

/**
 * QuizQuestion listelerini JSON formatında saklamak için TypeConverter
 */
class QuizQuestionListConverter {
    private val gson = Gson()
    
    @TypeConverter
    fun fromQuizQuestionList(value: List<QuizQuestion>?): String {
        return gson.toJson(value ?: emptyList<QuizQuestion>())
    }
    
    @TypeConverter
    fun toQuizQuestionList(value: String): List<QuizQuestion> {
        val listType = object : TypeToken<List<QuizQuestion>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
} 