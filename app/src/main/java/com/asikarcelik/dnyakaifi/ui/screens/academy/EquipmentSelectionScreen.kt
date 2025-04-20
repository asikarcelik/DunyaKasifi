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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ZoomIn
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
import com.asikarcelik.dnyakaifi.ui.model.ExplorationEquipment
import com.asikarcelik.dnyakaifi.ui.viewmodel.ExplorerViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EquipmentSelectionScreen(
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
                title = "Keşif Ekipmanı Seç",
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            ExplorerSubtitle(
                subtitle = "Maceranda sana yardımcı olacak ekipmanı seç",
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            // Dürbün
            EquipmentCard(
                equipment = ExplorationEquipment.BINOCULARS,
                title = "Dürbün",
                description = "Uzaktaki harika yerleri ve nesneleri görmeni sağlar!",
                icon = Icons.Default.ZoomIn,
                isSelected = explorerState.equipment == ExplorationEquipment.BINOCULARS,
                onClick = { viewModel.selectEquipment(ExplorationEquipment.BINOCULARS) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Sihirli Pusula
            EquipmentCard(
                equipment = ExplorationEquipment.MAGICAL_COMPASS,
                title = "Sihirli Pusula",
                description = "Her zaman doğru yönü gösterir ve yeni yerler keşfetmeni sağlar!",
                icon = Icons.Default.Explore,
                isSelected = explorerState.equipment == ExplorationEquipment.MAGICAL_COMPASS,
                onClick = { viewModel.selectEquipment(ExplorationEquipment.MAGICAL_COMPASS) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Not Defteri
            EquipmentCard(
                equipment = ExplorationEquipment.NOTEBOOK,
                title = "Not Defteri",
                description = "Keşiflerini ve öğrendiklerini kaydetmeni sağlar!",
                icon = Icons.Default.MenuBook,
                isSelected = explorerState.equipment == ExplorationEquipment.NOTEBOOK,
                onClick = { viewModel.selectEquipment(ExplorationEquipment.NOTEBOOK) }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Fotoğraf Makinesi
            EquipmentCard(
                equipment = ExplorationEquipment.CAMERA,
                title = "Fotoğraf Makinesi",
                description = "Gördüğün harika manzaraları ve anıları ölümsüzleştirir!",
                icon = Icons.Default.CameraAlt,
                isSelected = explorerState.equipment == ExplorationEquipment.CAMERA,
                onClick = { viewModel.selectEquipment(ExplorationEquipment.CAMERA) }
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
private fun EquipmentCard(
    equipment: ExplorationEquipment,
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