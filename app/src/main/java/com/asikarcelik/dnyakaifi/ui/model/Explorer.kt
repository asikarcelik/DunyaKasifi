package com.asikarcelik.dnyakaifi.ui.model

// Avatar özellikleri
enum class HairStyle { CURLY, STRAIGHT, WAVY, SHORT, LONG }
enum class EyeColor { BLUE, GREEN, BROWN, BLACK, HAZEL }
enum class Outfit { CASUAL, ADVENTURE, SPORTY, FANCY }
enum class Accessory { HAT, GLASSES, BACKPACK, SCARF, NONE }

// Keşif ekipmanları
enum class ExplorationEquipment { BINOCULARS, MAGICAL_COMPASS, NOTEBOOK, CAMERA }

// Keşif araçları
enum class ExplorationVehicle { MAGIC_CARPET, AIRPLANE, ROCKET, HOT_AIR_BALLOON }

// Kaşif veri modeli
data class Explorer(
    val name: String = "",
    val hairStyle: HairStyle = HairStyle.SHORT,
    val eyeColor: EyeColor = EyeColor.BROWN,
    val outfit: Outfit = Outfit.CASUAL,
    val accessory: Accessory = Accessory.NONE,
    val equipment: ExplorationEquipment = ExplorationEquipment.BINOCULARS,
    val vehicle: ExplorationVehicle = ExplorationVehicle.MAGIC_CARPET,
    val certificateGenerated: Boolean = false
) 