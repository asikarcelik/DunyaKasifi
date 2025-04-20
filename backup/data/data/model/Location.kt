package com.asikarcelik.dnyakaifi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Ülkeleri temsil eden veri modeli
 */
@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val code: String, // ISO ülke kodu (TR, US, vb.)
    val name: String,
    val localName: String, // Yerel dildeki adı
    val continent: String,
    val capital: String,
    val language: String,
    val currency: String,
    val flagUrl: String,
    val landmarks: List<Landmark> = emptyList(),
    val greetingWord: String, // Merhaba, Hello, Hola, vb.
    val funFacts: List<String> = emptyList(),
    val colorCode: String, // Haritada gösterilecek renk
    val unlocked: Boolean = false
)

/**
 * Ülkelerdeki önemli yapıları ve noktaları temsil eden veri modeli
 */
data class Landmark(
    val id: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String,
    val arModelUrl: String? = null, // AR modeli varsa
    val funFacts: List<String> = emptyList(),
    val discoveryQuiz: List<QuizQuestion> = emptyList() // Keşif sırasında sorulan sorular
)

/**
 * Keşif quizleri için soru modeli
 */
data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

/**
 * Harita üzerindeki pozisyonu temsil eden basit veri sınıfı
 */
data class MapPosition(
    val latitude: Double,
    val longitude: Double,
    val zoom: Float = 5f
)

/**
 * Kıtaları temsil eden enum
 */
enum class Continent(val displayName: String) {
    AFRICA("Afrika"),
    ASIA("Asya"),
    EUROPE("Avrupa"),
    NORTH_AMERICA("Kuzey Amerika"),
    SOUTH_AMERICA("Güney Amerika"),
    OCEANIA("Okyanusya"),
    ANTARCTICA("Antarktika")
} 