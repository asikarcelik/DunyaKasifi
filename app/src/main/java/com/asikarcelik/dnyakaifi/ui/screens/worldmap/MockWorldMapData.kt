package com.asikarcelik.dnyakaifi.ui.screens.worldmap

import androidx.compose.ui.graphics.Color

// ARCore ile 3D harita yapıldığında gerçek verilerle değiştirilecek mock veri sınıfı
object MockWorldMapData {
    
    // Arka plan harita renkleri
    val oceanColor = Color(0xFF3C6382)
    val landColor = Color(0xFF78E08F)
    val landBorderColor = Color(0xFF38A169)
    
    // Kıta konumları (normalize edilmiş 0.0-1.0 arası x,y değerleri)
    val continents = listOf(
        Continent(
            name = "Avrupa",
            color = Color(0xFF38A169),
            path = listOf(
                0.40f to 0.25f,
                0.45f to 0.20f,
                0.50f to 0.25f,
                0.48f to 0.35f,
                0.45f to 0.40f,
                0.40f to 0.38f,
                0.40f to 0.25f
            )
        ),
        Continent(
            name = "Asya",
            color = Color(0xFF2E8B57),
            path = listOf(
                0.50f to 0.25f,
                0.85f to 0.20f,
                0.90f to 0.30f,
                0.85f to 0.45f,
                0.75f to 0.50f,
                0.60f to 0.45f,
                0.50f to 0.40f,
                0.50f to 0.25f
            )
        ),
        Continent(
            name = "Afrika",
            color = Color(0xFFDAA520),
            path = listOf(
                0.45f to 0.40f,
                0.55f to 0.42f,
                0.58f to 0.65f,
                0.48f to 0.70f,
                0.40f to 0.60f,
                0.45f to 0.40f
            )
        ),
        Continent(
            name = "Kuzey Amerika",
            color = Color(0xFF6B8E23),
            path = listOf(
                0.15f to 0.25f,
                0.30f to 0.25f,
                0.35f to 0.35f,
                0.30f to 0.45f,
                0.20f to 0.50f,
                0.15f to 0.40f,
                0.15f to 0.25f
            )
        ),
        Continent(
            name = "Güney Amerika",
            color = Color(0xFF8FBC8F),
            path = listOf(
                0.25f to 0.50f,
                0.30f to 0.55f,
                0.28f to 0.70f,
                0.22f to 0.75f,
                0.20f to 0.60f,
                0.25f to 0.50f
            )
        ),
        Continent(
            name = "Avustralya",
            color = Color(0xFFCD853F),
            path = listOf(
                0.80f to 0.65f,
                0.90f to 0.60f,
                0.92f to 0.70f,
                0.85f to 0.80f,
                0.75f to 0.75f,
                0.80f to 0.65f
            )
        )
    )
    
    // Harita üzerindeki şehir konumları
    val popularRoutes = listOf(
        Route(
            name = "Dünya Başkentleri Turu",
            description = "Dünyanın en önemli başkentlerini keşfet!",
            waypoints = listOf(
                Waypoint("new_york", "New York", 0.2f, 0.35f),
                Waypoint("london", "Londra", 0.42f, 0.3f),
                Waypoint("paris", "Paris", 0.45f, 0.32f),
                Waypoint("rome", "Roma", 0.48f, 0.37f),
                Waypoint("istanbul", "İstanbul", 0.53f, 0.35f),
                Waypoint("cairo", "Kahire", 0.53f, 0.42f),
                Waypoint("delhi", "Delhi", 0.65f, 0.4f),
                Waypoint("beijing", "Pekin", 0.78f, 0.33f),
                Waypoint("tokyo", "Tokyo", 0.85f, 0.35f),
                Waypoint("sydney", "Sidney", 0.88f, 0.7f)
            ),
            color = Color(0xFFE74C3C) // Kırmızı
        ),
        Route(
            name = "Doğal Harikalar Rotası",
            description = "Dünyanın en etkileyici doğal oluşumlarını ziyaret et!",
            waypoints = listOf(
                Waypoint("grand_canyon", "Büyük Kanyon", 0.18f, 0.35f),
                Waypoint("amazon", "Amazon Ormanları", 0.25f, 0.58f),
                Waypoint("sahara", "Sahra Çölü", 0.45f, 0.45f),
                Waypoint("kilimanjaro", "Kilimanjaro", 0.52f, 0.55f),
                Waypoint("everest", "Everest", 0.68f, 0.38f),
                Waypoint("great_barrier", "Büyük Mercan Resifi", 0.85f, 0.65f)
            ),
            color = Color(0xFF27AE60) // Yeşil
        ),
        Route(
            name = "Antik Medeniyetler Keşfi",
            description = "İnsanlık tarihinin en önemli medeniyetlerini keşfet!",
            waypoints = listOf(
                Waypoint("machu_picchu", "Machu Picchu", 0.23f, 0.63f),
                Waypoint("chichen_itza", "Chichen Itza", 0.17f, 0.45f),
                Waypoint("stonehenge", "Stonehenge", 0.41f, 0.29f),
                Waypoint("rome", "Roma", 0.48f, 0.37f),
                Waypoint("giza", "Gize Piramitleri", 0.53f, 0.42f),
                Waypoint("petra", "Petra", 0.55f, 0.41f),
                Waypoint("taj_mahal", "Taj Mahal", 0.65f, 0.42f),
                Waypoint("angkor_wat", "Angkor Wat", 0.71f, 0.47f),
                Waypoint("great_wall", "Çin Seddi", 0.75f, 0.32f)
            ),
            color = Color(0xFF8E44AD) // Mor
        )
    )
}

// Basit kıta/bölge veri modeli
data class Continent(
    val name: String,
    val color: Color,
    val path: List<Pair<Float, Float>> // X,Y koordinat çiftleri
)

// Rota veri modeli
data class Route(
    val name: String,
    val description: String,
    val waypoints: List<Waypoint>,
    val color: Color
)

// Rota üzerindeki durak noktaları
data class Waypoint(
    val id: String,
    val name: String,
    val x: Float,  // Normalize edilmiş X koordinatı (0.0-1.0)
    val y: Float   // Normalize edilmiş Y koordinatı (0.0-1.0)
) 