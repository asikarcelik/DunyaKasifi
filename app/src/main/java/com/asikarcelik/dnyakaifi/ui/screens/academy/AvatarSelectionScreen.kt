package com.asikarcelik.dnyakaifi.ui.screens.academy

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.asikarcelik.dnyakaifi.ui.components.ExplorerSelectionCard
import com.asikarcelik.dnyakaifi.ui.components.ExplorerSubtitle
import com.asikarcelik.dnyakaifi.ui.components.ExplorerTitle
import com.asikarcelik.dnyakaifi.ui.components.NavigationButtons
import com.asikarcelik.dnyakaifi.ui.model.Accessory
import com.asikarcelik.dnyakaifi.ui.model.EyeColor
import com.asikarcelik.dnyakaifi.ui.model.HairStyle
import com.asikarcelik.dnyakaifi.ui.model.Outfit
import com.asikarcelik.dnyakaifi.ui.viewmodel.ExplorerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AvatarSelectionScreen(
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExplorerViewModel = koinViewModel()
) {
    val explorerState by viewModel.explorerState.collectAsState()
    val scrollState = rememberScrollState()
    
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExplorerTitle(
                title = "Kaşif Avatarını Tasarla",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            ExplorerSubtitle(
                subtitle = "Keşif maceranda seni temsil edecek karakterini oluştur",
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Avatar Önizleme - Daha modern ve gerçekçi bir görünüm
            Card(
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.tertiary
                            )
                        ),
                        shape = RoundedCornerShape(32.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Arka plan efekti
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.surfaceVariant,
                                        MaterialTheme.colorScheme.surface
                                    )
                                )
                            )
                    )
                    
                    // Avatar karakteri
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Saç stili
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(
                                    when (explorerState.hairStyle) {
                                        HairStyle.CURLY -> Color(0xFF8D6E63)  // Kahverengi
                                        HairStyle.STRAIGHT -> Color(0xFF5D4037) // Koyu kahve
                                        HairStyle.WAVY -> Color(0xFFD7CCC8)   // Açık kahve
                                        HairStyle.SHORT -> Color(0xFF212121)  // Siyah
                                        HairStyle.LONG -> Color(0xFFBDBDBD)   // Gri
                                    }
                                )
                                .offset(y = (-5).dp)
                        )
                        
                        // Yüz
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(40.dp, 40.dp, 30.dp, 30.dp))
                                .background(Color(0xFFFFDAAF)) // Cilt tonu
                                .offset(y = (-20).dp),
                            contentAlignment = Alignment.Center
                        ) {
                            // Gözler
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                modifier = Modifier.offset(y = (-10).dp)
                            ) {
                                // Sol göz
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(getColorFromEyeColor(explorerState.eyeColor))
                                )
                                // Sağ göz
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .clip(CircleShape)
                                        .background(getColorFromEyeColor(explorerState.eyeColor))
                                )
                            }
                            
                            // Ağız
                            Box(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFFE53935))
                                    .offset(y = 10.dp)
                            )
                        }
                        
                        // Kıyafet
                        Box(
                            modifier = Modifier
                                .width(110.dp)
                                .height(80.dp)
                                .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                                .background(
                                    when (explorerState.outfit) {
                                        Outfit.CASUAL -> Color(0xFF90CAF9)     // Açık mavi
                                        Outfit.ADVENTURE -> Color(0xFF81C784)  // Yeşil
                                        Outfit.SPORTY -> Color(0xFFFFF176)     // Sarı
                                        Outfit.FANCY -> Color(0xFFCE93D8)      // Mor
                                    }
                                )
                                .offset(y = (-15).dp)
                        )
                        
                        // Aksesuarlar
                        when (explorerState.accessory) {
                            Accessory.GLASSES -> Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(12.dp)
                                    .offset(y = (-80).dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Color(0xFF424242))
                            )
                            Accessory.HAT -> Box(
                                modifier = Modifier
                                    .size(70.dp, 25.dp)
                                    .offset(y = (-115).dp)
                                    .clip(RoundedCornerShape(10.dp, 10.dp, 0.dp, 0.dp))
                                    .background(Color(0xFF795548))
                            )
                            Accessory.BACKPACK -> Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .offset(y = (-55).dp, x = 50.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color(0xFFFF7043))
                            )
                            Accessory.SCARF -> Box(
                                modifier = Modifier
                                    .size(80.dp, 15.dp)
                                    .offset(y = (-35).dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(Color(0xFFE91E63))
                            )
                            Accessory.NONE -> {}
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // İsim Girişi - Daha modern bir tasarım
            OutlinedTextField(
                value = explorerState.name,
                onValueChange = { viewModel.updateName(it) },
                label = { Text("Kaşif Adı") },
                placeholder = { Text("Adını buraya yaz") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Kaşif Adı",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Kategori kartları
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Saç Stili Seçimi
                    Text(
                        text = "Saç Stili",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        HairStyle.values().forEach { hairStyle ->
                            HairStyleItem(
                                hairStyle = hairStyle,
                                isSelected = explorerState.hairStyle == hairStyle,
                                onClick = { viewModel.updateHairStyle(hairStyle) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            // Göz Rengi Kart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Göz Rengi Seçimi
                    Text(
                        text = "Göz Rengi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        EyeColor.values().forEach { eyeColor ->
                            EyeColorItem(
                                eyeColor = eyeColor,
                                isSelected = explorerState.eyeColor == eyeColor,
                                onClick = { viewModel.updateEyeColor(eyeColor) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            // Kıyafet Kart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Kıyafet Seçimi
                    Text(
                        text = "Kıyafet",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Outfit.values().forEach { outfit ->
                            OutfitItem(
                                outfit = outfit,
                                isSelected = explorerState.outfit == outfit,
                                onClick = { viewModel.updateOutfit(outfit) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            // Aksesuar Kart
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    // Aksesuar Seçimi
                    Text(
                        text = "Aksesuar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Accessory.values().forEach { accessory ->
                            AccessoryItem(
                                accessory = accessory,
                                isSelected = explorerState.accessory == accessory,
                                onClick = { viewModel.updateAccessory(accessory) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Modern buton
            NavigationButtons(
                onNextClick = onNextClick,
                nextText = "İleri",
                onBackClick = null
            )
        }
    }
}

@Composable
fun HairStyleItem(
    hairStyle: HairStyle,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            else null,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (hairStyle) {
                    HairStyle.CURLY -> "Kıvırcık"
                    HairStyle.STRAIGHT -> "Düz"
                    HairStyle.WAVY -> "Dalgalı"
                    HairStyle.SHORT -> "Kısa"
                    HairStyle.LONG -> "Uzun"
                },
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EyeColorItem(
    eyeColor: EyeColor,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            else null,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(getColorFromEyeColor(eyeColor))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when (eyeColor) {
                    EyeColor.BLUE -> "Mavi"
                    EyeColor.GREEN -> "Yeşil"
                    EyeColor.BROWN -> "Kahve"
                    EyeColor.BLACK -> "Siyah"
                    EyeColor.HAZEL -> "Ela"
                },
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OutfitItem(
    outfit: Outfit,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            else null,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (outfit) {
                    Outfit.CASUAL -> "Günlük"
                    Outfit.ADVENTURE -> "Macera"
                    Outfit.SPORTY -> "Spor"
                    Outfit.FANCY -> "Şık"
                },
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AccessoryItem(
    accessory: Accessory,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surface
        ),
        border = if (isSelected) 
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            else null,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (accessory) {
                    Accessory.HAT -> "Şapka"
                    Accessory.GLASSES -> "Gözlük"
                    Accessory.BACKPACK -> "Sırt Çantası"
                    Accessory.SCARF -> "Eşarp"
                    Accessory.NONE -> "Yok"
                },
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Göz rengini Color nesnesine dönüştürür
fun getColorFromEyeColor(eyeColor: EyeColor): Color {
    return when (eyeColor) {
        EyeColor.BLUE -> Color(0xFF1E88E5)
        EyeColor.GREEN -> Color(0xFF43A047)
        EyeColor.BROWN -> Color(0xFF795548)
        EyeColor.BLACK -> Color(0xFF212121)
        EyeColor.HAZEL -> Color(0xFFBF8040)
    }
} 