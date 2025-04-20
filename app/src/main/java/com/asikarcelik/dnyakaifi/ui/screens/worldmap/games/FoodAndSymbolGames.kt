package com.asikarcelik.dnyakaifi.ui.screens.worldmap.games

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asikarcelik.dnyakaifi.R
import kotlinx.coroutines.delay

/**
 * Yöresel Yemek Toplama Oyunu
 */
@Composable
fun LocalFoodGame(
    countryName: String,
    onComplete: (score: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var score by remember { mutableStateOf(0) }
    var timeLeft by remember { mutableStateOf(60) }
    var gamePhase by remember { mutableStateOf(GamePhase.COLLECTION) }
    
    val foodItems = remember {
        when (countryName) {
            "İtalya" -> listOf(
                FoodItem("Pizza", true),
                FoodItem("Makarna", true),
                FoodItem("Risotto", true),
                FoodItem("Tiramisu", true),
                FoodItem("Burger", false),
                FoodItem("Sushi", false),
                FoodItem("Taco", false),
                FoodItem("Croissant", false)
            )
            "Japonya" -> listOf(
                FoodItem("Sushi", true),
                FoodItem("Ramen", true),
                FoodItem("Tempura", true),
                FoodItem("Miso Çorbası", true),
                FoodItem("Pizza", false),
                FoodItem("Paella", false),
                FoodItem("Burger", false),
                FoodItem("Köfte", false)
            )
            "Fransa" -> listOf(
                FoodItem("Croissant", true),
                FoodItem("Ratatouille", true),
                FoodItem("Macaron", true),
                FoodItem("Boeuf Bourguignon", true),
                FoodItem("Sushi", false),
                FoodItem("Taco", false),
                FoodItem("Pizza", false),
                FoodItem("Pad Thai", false)
            )
            else -> listOf(
                FoodItem("Yemek 1", true),
                FoodItem("Yemek 2", true),
                FoodItem("Yemek 3", true),
                FoodItem("Yemek 4", true),
                FoodItem("Yemek 5", false),
                FoodItem("Yemek 6", false),
                FoodItem("Yemek 7", false),
                FoodItem("Yemek 8", false)
            )
        }
    }
    
    val collectedFoods = remember { mutableStateListOf<FoodItem>() }
    
    LaunchedEffect(key1 = gamePhase) {
        if (gamePhase == GamePhase.COLLECTION) {
            while (timeLeft > 0 && gamePhase == GamePhase.COLLECTION) {
                delay(1000)
                timeLeft--
            }
            if (timeLeft == 0) {
                gamePhase = GamePhase.MATCHING
            }
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.padding(end = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Puan: $score",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
                
                if (gamePhase == GamePhase.COLLECTION) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Kalan Süre: $timeLeft",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
            
            AnimatedContent(
                targetState = gamePhase,
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = fadeIn() + scaleIn(),
                        initialContentExit = fadeOut() + scaleOut(),
                        sizeTransform = null
                    )
                },
                label = "game_phase"
            ) { phase ->
                when (phase) {
                    GamePhase.COLLECTION -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$countryName Yerel Yemekleri",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            Text(
                                text = "Yöresel yemekleri toplama zamanı!",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            // İlerleme göstergesi
                            LinearProgressIndicator(
                                progress = { timeLeft / 60f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp)
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                            
                            // Yemek kartları
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(foodItems) { food ->
                                    val isCollected = collectedFoods.contains(food)
                                    
                                    FoodCard(
                                        food = food,
                                        isCollected = isCollected,
                                        onClick = {
                                            if (!isCollected) {
                                                collectedFoods.add(food)
                                            } else {
                                                collectedFoods.remove(food)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    GamePhase.MATCHING -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Yöresel Yemekler Testi",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            Text(
                                text = "Hangileri $countryName'ya ait yöresel yemeklerdir?",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                            
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                itemsIndexed(collectedFoods.toList()) { _, food ->
                                    var isMarkedAsLocal by remember { mutableStateOf(true) }
                                    
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isMarkedAsLocal)
                                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                                            else
                                                MaterialTheme.colorScheme.surface
                                        ),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = food.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.SemiBold,
                                                color = if (isMarkedAsLocal)
                                                    MaterialTheme.colorScheme.onPrimaryContainer
                                                else
                                                    MaterialTheme.colorScheme.onSurface
                                            )
                                            
                                            Box(
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(
                                                        if (isMarkedAsLocal) 
                                                            MaterialTheme.colorScheme.primary
                                                        else 
                                                            MaterialTheme.colorScheme.surfaceVariant
                                                    )
                                                    .clickable {
                                                        isMarkedAsLocal = !isMarkedAsLocal
                                                        // Puanı güncelle
                                                        if (isMarkedAsLocal && food.isLocalFood) {
                                                            score += 15 // Doğru yöresel yemek
                                                        } else if (isMarkedAsLocal && !food.isLocalFood) {
                                                            score -= 10 // Yanlış yemek
                                                        }
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                if (isMarkedAsLocal) {
                                                    Icon(
                                                        imageVector = Icons.Default.Check,
                                                        contentDescription = null,
                                                        tint = Color.White
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            
                            Button(
                                onClick = { onComplete(score) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Oyunu Tamamla",
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class FoodItem(
    val name: String,
    val isLocalFood: Boolean
)

enum class GamePhase {
    COLLECTION,
    MATCHING
}

@Composable
fun FoodCard(
    food: FoodItem,
    isCollected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.8f)
            .clickable(onClick = onClick)
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isCollected) 8.dp else 2.dp
        ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCollected) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = when {
                        isCollected -> Icons.Default.Restaurant
                        else -> Icons.Default.RestaurantMenu
                    },
                    contentDescription = null,
                    tint = if (isCollected) Color.White else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(40.dp)
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = food.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = if (isCollected) Color.White else MaterialTheme.colorScheme.onSurface
                )
            }
            
            if (isCollected) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Kültürel Sembol Eşleştirme Oyunu
 */
@Composable
fun CulturalSymbolsGame(
    countryName: String,
    onComplete: (score: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var score by remember { mutableStateOf(0) }
    var cardsFlipped by remember { mutableStateOf(0) }
    
    // Semboller ve açıklamalar
    val symbols = remember {
        when (countryName) {
            "Japonya" -> listOf(
                SymbolPair("Torii", "Kutsal geçit kapısı"),
                SymbolPair("Sakura", "Kiraz çiçeği"),
                SymbolPair("Fuji", "Japonya'nın en yüksek dağı"),
                SymbolPair("Origami", "Kağıt katlama sanatı")
            )
            "Hindistan" -> listOf(
                SymbolPair("Taj Mahal", "Hint mimarisinin şaheseri"),
                SymbolPair("Lotus", "Saflık ve güzellik sembolü"),
                SymbolPair("Fil", "Bilgelik ve güç"),
                SymbolPair("Rangoli", "Renkli desenler")
            )
            else -> listOf(
                SymbolPair("Sembol 1", "Açıklama 1"),
                SymbolPair("Sembol 2", "Açıklama 2"),
                SymbolPair("Sembol 3", "Açıklama 3"),
                SymbolPair("Sembol 4", "Açıklama 4")
            )
        }
    }
    
    // Oyun kartları
    val allCards = remember { 
        val symbolCards = symbols.map { SymbolCard(it.symbol, it.symbol, false) }
        val descriptionCards = symbols.map { SymbolCard(it.description, it.symbol, true) }
        (symbolCards + descriptionCards).shuffled()
    }
    
    // Kart durumunu izle
    val cardStates = remember { mutableStateListOf(*Array(allCards.size) { CardState() }) }
    
    // Seçilen kartlar
    var firstSelectedIndex by remember { mutableStateOf<Int?>(null) }
    var secondSelectedIndex by remember { mutableStateOf<Int?>(null) }
    
    // Eşleşme kontrolü
    LaunchedEffect(firstSelectedIndex, secondSelectedIndex) {
        if (firstSelectedIndex != null && secondSelectedIndex != null) {
            delay(1000) // Eşleşme kontrolü için bekleme süresi
            
            val first = allCards[firstSelectedIndex!!]
            val second = allCards[secondSelectedIndex!!]
            
            if (first.matchKey == second.matchKey && first.isDescription != second.isDescription) {
                // Eşleşme bulundu
                cardStates[firstSelectedIndex!!] = cardStates[firstSelectedIndex!!].copy(isMatched = true)
                cardStates[secondSelectedIndex!!] = cardStates[secondSelectedIndex!!].copy(isMatched = true)
                score += 25
                cardsFlipped += 2
            } else {
                // Eşleşme bulunamadı
                cardStates[firstSelectedIndex!!] = cardStates[firstSelectedIndex!!].copy(isFlipped = false)
                cardStates[secondSelectedIndex!!] = cardStates[secondSelectedIndex!!].copy(isFlipped = false)
                score -= 5
            }
            
            firstSelectedIndex = null
            secondSelectedIndex = null
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Kültürel Semboller",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "$countryName kültürel sembollerini eşleştir!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Skor ve ilerleme
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Puan: $score",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
                
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$cardsFlipped / ${allCards.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            // Kartlar
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                items(allCards.size) { index ->
                    val card = allCards[index]
                    val state = cardStates[index]
                    
                    SymbolMatchCard(
                        card = card,
                        state = state,
                        onClick = {
                            if (!state.isFlipped && !state.isMatched &&
                                firstSelectedIndex != index && secondSelectedIndex != index) {
                                // Kart çevrildi
                                cardStates[index] = state.copy(isFlipped = true)
                                
                                // İlk veya ikinci seçilen kart
                                if (firstSelectedIndex == null) {
                                    firstSelectedIndex = index
                                } else if (secondSelectedIndex == null) {
                                    secondSelectedIndex = index
                                }
                            }
                        }
                    )
                }
            }
            
            // Tüm kartlar eşleştirildiğinde tamamla butonu göster
            if (cardsFlipped == allCards.size) {
                Button(
                    onClick = { onComplete(score) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Oyunu Tamamla",
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

data class SymbolPair(val symbol: String, val description: String)

data class SymbolCard(
    val text: String,
    val matchKey: String,
    val isDescription: Boolean
)

data class CardState(
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
)

@Composable
fun SymbolMatchCard(
    card: SymbolCard,
    state: CardState,
    onClick: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (state.isFlipped) 180f else 0f,
        label = "card_rotation",
        animationSpec = tween(400)
    )
    
    val isFlipped = rotation >= 90f
    
    Box(
        modifier = Modifier
            .aspectRatio(0.75f)
            .clip(RoundedCornerShape(16.dp))
            .clickable(enabled = !state.isMatched, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Ön yüz (kapalı)
        if (!isFlipped) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "?",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            // Arka yüz (açık)
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .rotate(rotation),
                colors = CardDefaults.cardColors(
                    containerColor = if (state.isMatched) 
                        Color(0xFF4CAF50).copy(alpha = 0.2f)
                    else 
                        MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .border(
                            width = 2.dp,
                            color = if (state.isMatched) 
                                Color(0xFF4CAF50)
                            else 
                                MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .scale(scaleX = -1f, scaleY = 1f)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (!card.isDescription) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = if (state.isMatched) 
                                    Color(0xFF4CAF50) 
                                else 
                                    MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        
                        Text(
                            text = card.text,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = if (state.isMatched) 
                                Color(0xFF4CAF50)
                            else 
                                MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = if (card.isDescription) 
                                FontWeight.Normal 
                            else 
                                FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
} 