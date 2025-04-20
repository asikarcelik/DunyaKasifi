package com.asikarcelik.dnyakaifi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Oyun içindeki görevleri temsil eden veri modeli
 */
@Entity(tableName = "missions")
data class Mission(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val difficulty: MissionDifficulty,
    val type: MissionType,
    val countryCode: String? = null, // Eğer görev belirli bir ülkeye özgü ise
    val requiredItems: List<String> = emptyList(),
    val experienceReward: Int,
    val badgeReward: String? = null,
    val isCompleted: Boolean = false,
    val isActive: Boolean = false,
    val completionDate: Long? = null
)

/**
 * Görev zorluk seviyesi
 */
enum class MissionDifficulty {
    EASY, MEDIUM, HARD
}

/**
 * Görev türleri
 */
enum class MissionType {
    // Kültürel görevler
    CULTURE_DISCOVERY,
    LANGUAGE_LEARNING,
    
    // AR tabanlı görevler
    AR_EXPLORATION, 
    AR_LANDMARK_DISCOVERY,
    
    // Eğitim görevleri
    MATH_PROBLEM,
    GEOGRAPHY_QUIZ,
    WEATHER_PREDICTION,
    
    // Eğlence görevleri
    CLOUD_PAINTING,
    DRESS_UP_CHALLENGE,
    
    // Koleksiyon görevleri
    COLLECT_BADGES,
    VISIT_COUNTRIES
}

/**
 * Görev için gerekli koşulları temsil eden sınıf
 */
data class MissionRequirement(
    val requirementType: RequirementType,
    val amount: Int = 1,
    val targetId: String? = null
)

/**
 * Görev koşul türleri
 */
enum class RequirementType {
    VISIT_COUNTRY,
    COLLECT_BADGE,
    COMPLETE_MISSIONS,
    LEARN_WORDS,
    REACH_LEVEL
} 