package com.asikarcelik.dnyakaifi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asikarcelik.dnyakaifi.ui.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExplorerViewModel : ViewModel() {

    private val _explorerState = MutableStateFlow(Explorer())
    val explorerState: StateFlow<Explorer> = _explorerState.asStateFlow()

    // Avatar güncelleme fonksiyonları
    fun updateName(name: String) {
        _explorerState.update { it.copy(name = name) }
    }

    fun updateHairStyle(hairStyle: HairStyle) {
        _explorerState.update { it.copy(hairStyle = hairStyle) }
    }

    fun updateEyeColor(eyeColor: EyeColor) {
        _explorerState.update { it.copy(eyeColor = eyeColor) }
    }

    fun updateOutfit(outfit: Outfit) {
        _explorerState.update { it.copy(outfit = outfit) }
    }

    fun updateAccessory(accessory: Accessory) {
        _explorerState.update { it.copy(accessory = accessory) }
    }

    // Keşif ekipmanı seçme
    fun selectEquipment(equipment: ExplorationEquipment) {
        _explorerState.update { it.copy(equipment = equipment) }
    }

    // Keşif aracı seçme
    fun selectVehicle(vehicle: ExplorationVehicle) {
        _explorerState.update { it.copy(vehicle = vehicle) }
    }

    // Sertifika oluşturma
    fun generateCertificate() {
        _explorerState.update { it.copy(certificateGenerated = true) }
    }

    // Tüm explorer durumunu sıfırlama
    fun resetExplorer() {
        _explorerState.value = Explorer()
    }
} 