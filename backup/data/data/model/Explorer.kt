package com.asikarcelik.dnyakaifi.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.asikarcelik.dnyakaifi.data.local.converter.ListStringConverter

/**
 * Çocuğun oluşturduğu kaşif karakterini temsil eden veri modeli
 */
@Entity(tableName = "explorers")
@TypeConverters(ListStringConverter::class)
data class Explorer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    
    // Avatar özellikleri
    val hairStyle: String,
    val hairColor: String,
    val eyeColor: String,
    val skinTone: String,
    val outfit: String,
    val accessories: List<String> = emptyList(),
    
    // Keşif ekipmanları
    val equipment: List<String> = emptyList(),
    
    // Keşif aracı
    val vehicle: String,
    
    // İlerleme
    val level: Int = 1,
    val experience: Int = 0,
    val title: String = "Çaylak Kaşif",
    
    // Kaşif pasaportu
    val visitedCountries: List<String> = emptyList(),
    val badges: List<String> = emptyList(),
    val learnedWords: Map<String, String> = emptyMap(),
    
    // Eğitim sertifikası
    val hasCertificate: Boolean = false,
    
    // Son güncelleme 
    val lastUpdated: Long = System.currentTimeMillis()
)

/**
 * Kaşif için mevcut unvanlar
 */
enum class ExplorerTitle(val titleName: String) {
    ROOKIE("Çaylak Kaşif"),
    ADVENTURER("Maceracı Kaşif"),
    DISCOVERER("Keşfedici"),
    EXPLORER("Uzman Kaşif"),
    MASTER("Usta Kaşif"),
    SUPER_EXPLORER("Süper Kaşif")
}

/**
 * Mevcut ekipman türleri
 */
enum class EquipmentType(val displayName: String) {
    BINOCULARS("Dürbün"),
    MAGIC_COMPASS("Sihirli Pusula"),
    NOTEBOOK("Keşif Defteri"),
    CAMERA("Keşif Kamerası")
}

/**
 * Mevcut taşıt türleri
 */
enum class VehicleType(val displayName: String) {
    MAGIC_CARPET("Sihirli Halı"),
    AIRPLANE("Uçak"),
    ROCKET("Roket"),
    HOT_AIR_BALLOON("Sıcak Hava Balonu")
} 