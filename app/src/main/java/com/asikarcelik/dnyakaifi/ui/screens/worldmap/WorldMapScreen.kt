package com.asikarcelik.dnyakaifi.ui.screens.worldmap

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import com.asikarcelik.dnyakaifi.ui.model.ExplorationVehicle
import com.asikarcelik.dnyakaifi.ui.viewmodel.ExplorerViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.pow
import kotlin.math.sqrt
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.GameType
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.CulturalDressGame
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.FamousBuildingsGame
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.MapDetectiveGame
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.LanguageLearningGame
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.PronunciationGame
import com.asikarcelik.dnyakaifi.ui.screens.worldmap.games.CountryMiniGames

// Rotadaki durakları temsil eden veri sınıfı
data class Destination(
    val id: String,
    val name: String,
    val description: String,
    val x: Float,
    val y: Float,
    val badges: List<Badge> = emptyList(),
    val tasks: List<Task> = emptyList(),
    val isVisited: Boolean = false
)

// Rozet veri sınıfı
data class Badge(
    val id: String,
    val name: String,
    val description: String,
    val color: Color,
    val isEarned: Boolean = false
)

// Görev veri sınıfı
data class Task(
    val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false
)

// Mock veri oluşturan yardımcı fonksiyon
fun createMockWorldData(): List<Destination> {
    val destinations = listOf(
        Destination(
            id = "paris",
            name = "Paris",
            description = "Sanat ve kültürün merkezi",
            x = 0.42f,
            y = 0.35f,
            badges = listOf(
                Badge(
                    id = "paris_art",
                    name = "Sanat Uzmanı",
                    description = "Paris'teki tüm müzeleri gezdin!",
                    color = Color(0xFFE91E63)
                ),
                Badge(
                    id = "paris_food",
                    name = "Şef Aşçı",
                    description = "Fransız mutfağını keşfettin!",
                    color = Color(0xFFFF9800)
                )
            ),
            tasks = listOf(
                Task(
                    id = "paris_1",
                    title = "Eyfel Kulesi'ni ziyaret et",
                    description = "Dünyanın en ünlü kulesinden şehri seyret."
                ),
                Task(
                    id = "paris_2",
                    title = "Louvre Müzesi'ni gez",
                    description = "Mona Lisa tablosunu bulmaya çalış."
                )
            )
        ),
        Destination(
            id = "tokyo",
            name = "Tokyo",
            description = "Geleceğin şehri",
            x = 0.85f,
            y = 0.38f,
            badges = listOf(
                Badge(
                    id = "tokyo_tech",
                    name = "Teknoloji Gurusu",
                    description = "Tokyo'nun ileri teknolojisini keşfettin!",
                    color = Color(0xFF3F51B5)
                ),
                Badge(
                    id = "tokyo_anime",
                    name = "Anime Fanatiği",
                    description = "Japonya'nın anime kültürünü öğrendin!",
                    color = Color(0xFFE040FB)
                )
            ),
            tasks = listOf(
                Task(
                    id = "tokyo_1",
                    title = "Shibuya kavşağında yürü",
                    description = "Dünyanın en kalabalık kavşağını deneyimle."
                ),
                Task(
                    id = "tokyo_2",
                    title = "Suşi yapımını öğren",
                    description = "Bir şeften suşi yapımının püf noktalarını öğren."
                )
            )
        ),
        Destination(
            id = "cairo",
            name = "Kahire",
            description = "Piramitlerin şehri",
            x = 0.53f,
            y = 0.45f,
            badges = listOf(
                Badge(
                    id = "cairo_pyramids",
                    name = "Piramit Kaşifi",
                    description = "Mısır piramitlerini keşfettin!",
                    color = Color(0xFFFFEB3B)
                ),
                Badge(
                    id = "cairo_history",
                    name = "Tarih Meraklısı",
                    description = "Eski Mısır tarihini öğrendin!",
                    color = Color(0xFF795548)
                )
            ),
            tasks = listOf(
                Task(
                    id = "cairo_1",
                    title = "Giza Piramitlerini keşfet",
                    description = "Antik dünyanın en etkileyici yapılarını gör."
                ),
                Task(
                    id = "cairo_2",
                    title = "Nil Nehri'nde tekne turu yap",
                    description = "Mısır'ın can damarı olan nehri keşfet."
                )
            )
        ),
        Destination(
            id = "riodejaneiro",
            name = "Rio de Janeiro",
            description = "Karnaval şehri",
            x = 0.30f,
            y = 0.65f,
            badges = listOf(
                Badge(
                    id = "rio_carnival",
                    name = "Karnaval Kraliçesi/Kralı",
                    description = "Rio Karnavalı'nda dans ettin!",
                    color = Color(0xFF4CAF50)
                ),
                Badge(
                    id = "rio_beach",
                    name = "Plaj Kâşifi",
                    description = "Copacabana plajında vakit geçirdin!",
                    color = Color(0xFF03A9F4)
                )
            ),
            tasks = listOf(
                Task(
                    id = "rio_1",
                    title = "Kurtarıcı İsa Heykeli'ne çık",
                    description = "Şehrin nefes kesen manzarasını seyret."
                ),
                Task(
                    id = "rio_2",
                    title = "Samba dansını öğren",
                    description = "Brezilya'nın geleneksel dansını deneyimle."
                )
            )
        ),
        Destination(
            id = "sydney",
            name = "Sidney",
            description = "Okyanusun incisi",
            x = 0.9f,
            y = 0.8f,
            badges = listOf(
                Badge(
                    id = "sydney_opera",
                    name = "Sanat Tutkunu",
                    description = "Sidney Opera Binası'nda bir gösteri izledin!",
                    color = Color(0xFFFF5722)
                ),
                Badge(
                    id = "sydney_marine",
                    name = "Deniz Biyoloğu",
                    description = "Büyük Mercan Resifi'ni keşfettin!",
                    color = Color(0xFF00BCD4)
                )
            ),
            tasks = listOf(
                Task(
                    id = "sydney_1",
                    title = "Sydney Opera Binası'nı gez",
                    description = "İkonik yapının mimarisini yakından incele."
                ),
                Task(
                    id = "sydney_2",
                    title = "Kanguru ile fotoğraf çektir",
                    description = "Avustralya'nın sembol hayvanıyla tanış."
                )
            )
        )
    )
    return destinations
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorldMapScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExplorerViewModel = koinViewModel()
) {
    val explorerState by viewModel.explorerState.collectAsState()
    
    // Dünya haritası durakları
    val destinations = remember { createMockWorldData() }
    
    // Seçilen destinasyon
    var selectedDestination by remember { mutableStateOf<Destination?>(null) }
    
    // Rota ve animasyon değişkenleri
    var selectedRoute by remember { mutableStateOf<Route?>(null) }
    var currentRouteIndex by remember { mutableStateOf(0) }
    var animationProgress by remember { mutableStateOf(0f) }
    
    // Animasyon için
    val infiniteTransition = rememberInfiniteTransition(label = "mapAnimation")
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )
    
    // İlk rotayı seç
    LaunchedEffect(key1 = MockWorldMapData.popularRoutes) {
        if (MockWorldMapData.popularRoutes.isNotEmpty()) {
            selectedRoute = MockWorldMapData.popularRoutes.first()
            currentRouteIndex = 0
        }
    }
    
    // Harita zoom/pan state'leri
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    
    // Etkin navigasyon durumu
    var currentNavigationState by remember { mutableStateOf<NavigationState>(NavigationState.Map) }
    
    // Önceki navigasyon durumunu sakla
    var previousNavigationState by remember { mutableStateOf<NavigationState>(NavigationState.Map) }
    
    // Navigasyon durumu değiştiğinde işlemler yap
    LaunchedEffect(currentNavigationState) {
        if (currentNavigationState == NavigationState.Map) {
            // Haritaya döndüğünde detay ekranını da temizle
            selectedDestination = null
        }
    }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Dünya Haritası") },
                navigationIcon = {
                    IconButton(onClick = {
                        // Eğer detay veya oyun ekranındaysa, önce haritaya dön
                        when (currentNavigationState) {
                            NavigationState.Destination, 
                            NavigationState.MiniGames, 
                            NavigationState.Game -> {
                                // Oyun ekranındayken doğrudan haritaya dön
                                currentNavigationState = NavigationState.Map
                                selectedDestination = null
                            }
                            // Ana haritadaysa, gerçekten geri dön
                            NavigationState.Map -> onBackClick()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Geri Dön"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            // Sadece ana haritada FAB'ları göster
            if (currentNavigationState == NavigationState.Map) {
                Column {
                    // Rotalar arasında geçiş yapmak için düğme
                    FloatingActionButton(
                        onClick = {
                            if (MockWorldMapData.popularRoutes.isNotEmpty()) {
                                currentRouteIndex = (currentRouteIndex + 1) % MockWorldMapData.popularRoutes.size
                                selectedRoute = MockWorldMapData.popularRoutes[currentRouteIndex]
                                animationProgress = 0f
                            }
                        },
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(Icons.Default.Place, contentDescription = "Rotayı Değiştir")
                    }
                    
                    // Yakınlaştırmayı sıfırla
                    FloatingActionButton(
                        onClick = {
                            scale = 1f
                            offset = Offset.Zero
                        }
                    ) {
                        Icon(Icons.Default.Info, contentDescription = "Haritayı Sıfırla")
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 3D Dünya haritası (basit simülasyon)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            scale *= zoom
                            scale = scale.coerceIn(0.5f, 3f)
                            
                            offset += pan / scale
                            // Harita sınırlarını korumak için offset'i sınırla
                            offset = Offset(
                                x = offset.x.coerceIn(-300f, 300f),
                                y = offset.y.coerceIn(-300f, 300f)
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Harita
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF3C6382)) // Okyanus rengi
                ) {
                    // Harita üzerinde rota çizgileri ve noktalar çizmek için Canvas
                    Canvas(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Harita boyutları
                        val width = size.width
                        val height = size.height
                        
                        // Rota çizgileri
                        for (i in 0 until destinations.size - 1) {
                            val start = Offset(
                                x = destinations[i].x * width + offset.x * scale,
                                y = destinations[i].y * height + offset.y * scale
                            )
                            val end = Offset(
                                x = destinations[i + 1].x * width + offset.x * scale,
                                y = destinations[i + 1].y * height + offset.y * scale
                            )
                            
                            // Kesikli çizgi
                            drawLine(
                                color = Color.White.copy(alpha = 0.6f),
                                start = start,
                                end = end,
                                strokeWidth = 3f * scale,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(10f, 10f), phase = 0f
                                )
                            )
                            
                            // Animasyonlu nokta
                            val animatedPoint = Offset(
                                x = start.x + (end.x - start.x) * animatedProgress,
                                y = start.y + (end.y - start.y) * animatedProgress
                            )
                            
                            // Aracı çiziyoruz
                            val vehicleColor = when (explorerState.vehicle) {
                                ExplorationVehicle.MAGIC_CARPET -> Color(0xFFE74C3C) // Kırmızı
                                ExplorationVehicle.AIRPLANE -> Color(0xFF3498DB) // Mavi
                                ExplorationVehicle.ROCKET -> Color(0xFF9B59B6) // Mor
                                ExplorationVehicle.HOT_AIR_BALLOON -> Color(0xFFF1C40F) // Sarı
                            }
                            
                            // Araç gölgesi
                            drawCircle(
                                color = vehicleColor.copy(alpha = 0.3f),
                                center = animatedPoint,
                                radius = 15f * scale
                            )
                            
                            // Araç
                            drawCircle(
                                color = vehicleColor,
                                center = animatedPoint,
                                radius = 8f * scale
                            )
                        }
                        
                        // Destinasyonları çiz
                        destinations.forEach { destination ->
                            val destPoint = Offset(
                                x = destination.x * width + offset.x * scale,
                                y = destination.y * height + offset.y * scale
                            )
                            
                            // Nokta çemberi
                            drawCircle(
                                color = if (destination.isVisited) Color.Green else Color(0xFFE67E22),
                                center = destPoint,
                                radius = 8f * scale
                            )
                            
                            // Beyaz kenarlık
                            drawCircle(
                                color = Color.White,
                                center = destPoint,
                                radius = 10f * scale,
                                style = Stroke(width = 2f * scale)
                            )
                            
                            // Metin için pozisyon hesapla (destinasyonun altına)
                            val textPoint = Offset(destPoint.x, destPoint.y + 20f * scale)
                            
                            // Arka plan için dikdörtgen
                            drawRect(
                                color = Color.Black.copy(alpha = 0.5f),
                                topLeft = Offset(textPoint.x - 40f * scale, textPoint.y - 8f * scale),
                                size = Size(
                                    width = 80f * scale,
                                    height = 20f * scale
                                )
                            )
                            
                            // Şehir adı
                            drawContext.canvas.nativeCanvas.drawText(
                                destination.name,
                                textPoint.x,
                                textPoint.y + 4f * scale,
                                android.graphics.Paint().apply {
                                    color = android.graphics.Color.WHITE
                                    textSize = 12f * scale
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    isFakeBoldText = true
                                }
                            )
                        }
                    }
                    
                    // Tıklama işleme - tüm harita alanı için tek bir tıklama yüzeyi
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures { clickPoint ->
                                    val width = this.size.width
                                    val height = this.size.height
                                    
                                    // Tüm destinasyonları kontrol et ve en yakın noktayı bul
                                    for (destination in destinations) {
                                        val destPoint = Offset(
                                            x = destination.x * width + offset.x * scale,
                                            y = destination.y * height + offset.y * scale
                                        )
                                        
                                        // Tıklama noktasının hedef noktadan uzaklığını kontrol et
                                        val distance = sqrt(
                                            (clickPoint.x - destPoint.x).pow(2) +
                                            (clickPoint.y - destPoint.y).pow(2)
                                        )
                                        
                                        // Eğer tıklama hedef bölgede ise
                                        if (distance < 30f * scale) {
                                            selectedDestination = destination
                                            break // İlk eşleşen hedefi seç ve döngüden çık
                                        }
                                    }
                                }
                            }
                    )
                }
            }
            
            // Seçili destinasyon detayları
            selectedDestination?.let { destination ->
                DestinationDetailsCard(
                    destination = destination,
                    onCloseClick = { 
                        selectedDestination = null
                        currentNavigationState = NavigationState.Map
                    },
                    onShowMiniGames = {
                        previousNavigationState = currentNavigationState
                        currentNavigationState = NavigationState.MiniGames
                    },
                    onGameSelected = {
                        previousNavigationState = currentNavigationState
                        currentNavigationState = NavigationState.Game
                    },
                    onBackToDestination = {
                        currentNavigationState = NavigationState.Destination
                    },
                    navigationState = currentNavigationState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            
            // Görevler kartı - sadece ana haritada göster
            if (selectedDestination == null && currentNavigationState == NavigationState.Map) {
                ActiveTasksCard(
                    tasks = destinations.flatMap { it.tasks },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

// Navigasyon durumunu izlemek için enum
enum class NavigationState {
    Map, // Ana harita ekranı
    Destination, // Destinasyon detay ekranı
    MiniGames, // Mini oyunlar seçim ekranı
    Game // Oyun ekranı
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DestinationDetailsCard(
    destination: Destination,
    onCloseClick: () -> Unit,
    onShowMiniGames: () -> Unit,
    onGameSelected: () -> Unit,
    onBackToDestination: () -> Unit,
    navigationState: NavigationState,
    modifier: Modifier = Modifier
) {
    // MiniGames ve oyun ekranlarını göstermek için state'ler
    var showMiniGames by remember { mutableStateOf(false) }
    var selectedGameType by remember { mutableStateOf<GameType?>(null) }
    
    // Navigation state değişikliklerini takip et
    LaunchedEffect(navigationState) {
        when (navigationState) {
            NavigationState.Map, NavigationState.Destination -> {
                showMiniGames = false
                selectedGameType = null
            }
            NavigationState.MiniGames -> {
                showMiniGames = true
                selectedGameType = null
            }
            NavigationState.Game -> {
                // Oyun seçildiyse, bu durumda kalır, değilse mini oyunlara geri döner
                if (selectedGameType == null) {
                    onBackToDestination()
                }
            }
        }
    }
    
    when {
        selectedGameType != null -> {
            // Seçilen oyun tipine göre ilgili oyun ekranını tam ekranda göster
            onGameSelected() // Navigasyon durumunu güncelle
            
            // Tam ekran diyalog
            Dialog(
                onDismissRequest = { 
                    selectedGameType = null
                    onBackToDestination()
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (selectedGameType) {
                        GameType.MAP_DETECTIVE -> {
                            MapDetectiveGame(
                                countryName = destination.name,
                                onComplete = { score ->
                                    // Oyun tamamlandı, puan ekleme logici
                                    selectedGameType = null
                                    onCloseClick() // Doğrudan haritaya dön
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        GameType.FAMOUS_BUILDINGS -> {
                            FamousBuildingsGame(
                                countryName = destination.name,
                                onComplete = { score ->
                                    selectedGameType = null
                                    onCloseClick() // Doğrudan haritaya dön
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        GameType.CULTURAL_DRESS -> {
                            CulturalDressGame(
                                countryName = destination.name,
                                onComplete = { score ->
                                    selectedGameType = null
                                    onCloseClick() // Doğrudan haritaya dön
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        GameType.LANGUAGE_LEARNING -> {
                            LanguageLearningGame(
                                countryName = destination.name,
                                onComplete = { score ->
                                    selectedGameType = null
                                    onCloseClick() // Doğrudan haritaya dön
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        GameType.PRONUNCIATION -> {
                            PronunciationGame(
                                countryName = destination.name,
                                onComplete = { score ->
                                    selectedGameType = null
                                    onCloseClick() // Doğrudan haritaya dön
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        else -> {
                            // Diğer oyun tipleri henüz uygulanmadı
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Bu oyun henüz hazır değil.",
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center
                                )
                                
                                Spacer(modifier = Modifier.height(24.dp))
                                
                                Button(
                                    onClick = { 
                                        selectedGameType = null
                                        onCloseClick() // Doğrudan haritaya dön
                                    }
                                ) {
                                    Text("Haritaya Dön")
                                }
                            }
                        }
                    }
                }
            }
        }
        showMiniGames -> {
            // Mini oyunlar ekranını göster
            onShowMiniGames() // Navigasyon durumunu güncelle
            
            // Tam ekran diyalog
            Dialog(
                onDismissRequest = { 
                    showMiniGames = false
                    onBackToDestination()
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Başlık ve geri butonu
                        TopAppBar(
                            title = { Text("${destination.name} Oyunları") },
                            navigationIcon = {
                                IconButton(
                                    onClick = { 
                                        showMiniGames = false
                                        onBackToDestination()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Geri"
                                    )
                                }
                            }
                        )
                        
                        // Mini oyunlar listesi
                        CountryMiniGames(
                            countryName = destination.name,
                            onGameSelected = { gameType ->
                                // Oyun seçildiğinde
                                selectedGameType = gameType
                            },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        else -> {
            // Destinasyon detay ekranı
            onBackToDestination() // Navigasyon durumunu güncelle
            
            Card(
                modifier = modifier,
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Başlık
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = destination.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        IconButton(onClick = onCloseClick) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Kapat",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    
                    Text(
                        text = destination.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Rozetler
                    Text(
                        text = "Kazanılabilecek Rozetler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(destination.badges) { badge ->
                            BadgeItem(badge = badge)
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Görevler
                    Text(
                        text = "Görevler",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    destination.tasks.forEach { task ->
                        TaskItem(task = task)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Mini oyunlar butonu
                    Button(
                        onClick = { showMiniGames = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "${destination.name} Oyunlarını Keşfet",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BadgeItem(
    badge: Badge,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(100.dp)
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (badge.isEarned) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(badge.color)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = badge.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    if (task.isCompleted) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (task.isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Column {
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (task.isCompleted) FontWeight.Normal else FontWeight.Bold,
                color = if (task.isCompleted) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun ActiveTasksCard(
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    if (tasks.isEmpty()) return
    
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Aktif Görevler",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            tasks.filter { !it.isCompleted }.take(3).forEach { task ->
                TaskItem(task = task)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
} 