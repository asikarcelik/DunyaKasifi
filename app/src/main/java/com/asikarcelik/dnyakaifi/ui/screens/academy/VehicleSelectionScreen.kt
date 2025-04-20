package com.asikarcelik.dnyakaifi.ui.screens.academy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.asikarcelik.dnyakaifi.ui.components.ExplorerSelectionCard
import com.asikarcelik.dnyakaifi.ui.components.ExplorerSubtitle
import com.asikarcelik.dnyakaifi.ui.components.ExplorerTitle
import com.asikarcelik.dnyakaifi.ui.components.NavigationButtons
import com.asikarcelik.dnyakaifi.ui.model.ExplorationVehicle
import com.asikarcelik.dnyakaifi.ui.viewmodel.ExplorerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun VehicleSelectionScreen(
    onNextClick: () -> Unit,
    onBackClick: () -> Unit,
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
                title = "Keşif Aracı Seç",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            ExplorerSubtitle(
                subtitle = "Dünyayı keşfetmek için kullanacağın aracı seç",
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Sihirli Halı
            VehicleCard(
                vehicle = ExplorationVehicle.MAGIC_CARPET,
                title = "Sihirli Halı",
                description = "Rüzgar gibi hızlı ve sessiz, dar geçitlerden kolayca geçebilir!",
                icon = Icons.Default.Attractions,
                isSelected = explorerState.vehicle == ExplorationVehicle.MAGIC_CARPET,
                onClick = { viewModel.selectVehicle(ExplorationVehicle.MAGIC_CARPET) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Uçak
            VehicleCard(
                vehicle = ExplorationVehicle.AIRPLANE,
                title = "Uçak",
                description = "Uzun mesafeleri hızla kat et ve gökyüzünden muhteşem manzarayı izle!",
                icon = Icons.Default.AirplanemodeActive,
                isSelected = explorerState.vehicle == ExplorationVehicle.AIRPLANE,
                onClick = { viewModel.selectVehicle(ExplorationVehicle.AIRPLANE) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Roket
            VehicleCard(
                vehicle = ExplorationVehicle.ROCKET,
                title = "Roket",
                description = "Uzaya bile çıkabilir, çok hızlıdır ve muhteşem bir manzara sunar!",
                icon = Icons.Default.Speed,
                isSelected = explorerState.vehicle == ExplorationVehicle.ROCKET,
                onClick = { viewModel.selectVehicle(ExplorationVehicle.ROCKET) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sıcak Hava Balonu
            VehicleCard(
                vehicle = ExplorationVehicle.HOT_AIR_BALLOON,
                title = "Sıcak Hava Balonu",
                description = "Yavaş ama keyifli bir yolculuk, her detayı görmeni sağlar!",
                icon = Icons.Default.Cloud,
                isSelected = explorerState.vehicle == ExplorationVehicle.HOT_AIR_BALLOON,
                onClick = { viewModel.selectVehicle(ExplorationVehicle.HOT_AIR_BALLOON) }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            NavigationButtons(
                onNextClick = onNextClick,
                onBackClick = onBackClick,
                nextText = "İleri"
            )
        }
    }
}

@Composable
private fun VehicleCard(
    vehicle: ExplorationVehicle,
    title: String,
    description: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ExplorerSelectionCard(
        title = title,
        isSelected = isSelected,
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            androidx.compose.material3.Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
        }
    }
} 