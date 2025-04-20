package com.asikarcelik.dnyakaifi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asikarcelik.dnyakaifi.data.model.EquipmentType
import com.asikarcelik.dnyakaifi.data.model.Explorer
import com.asikarcelik.dnyakaifi.data.model.VehicleType
import com.asikarcelik.dnyakaifi.data.repository.ExplorerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Karakter oluşturma ekranının durumunu temsil eden veri sınıfı
 */
data class CharacterCreationState(
    val name: String = "",
    val age: Int = 6,
    val hairStyle: String = "Normal",
    val hairColor: String = "Kahverengi",
    val eyeColor: String = "Kahverengi",
    val skinTone: String = "Orta",
    val outfit: String = "Standart",
    val accessories: List<String> = emptyList(),
    val selectedEquipment: List<String> = emptyList(),
    val vehicle: String = VehicleType.MAGIC_CARPET.displayName,
    val isLoading: Boolean = false,
    val isComplete: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Karakter oluşturma ekranı için ViewModel
 */
@HiltViewModel
class CharacterCreationViewModel @Inject constructor(
    private val explorerRepository: ExplorerRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(CharacterCreationState())
    val state: StateFlow<CharacterCreationState> = _state.asStateFlow()
    
    // Mevcut saç modelleri
    val availableHairStyles = listOf("Normal", "Dalgalı", "Kıvırcık", "Düz", "Örgülü")
    
    // Mevcut saç renkleri
    val availableHairColors = listOf("Siyah", "Kahverengi", "Sarı", "Kızıl", "Turuncu", "Mavi", "Mor", "Yeşil")
    
    // Mevcut göz renkleri
    val availableEyeColors = listOf("Kahverengi", "Mavi", "Yeşil", "Siyah", "Gri", "Ela")
    
    // Mevcut ten tonları
    val availableSkinTones = listOf("Açık", "Orta", "Koyu")
    
    // Mevcut kıyafetler
    val availableOutfits = listOf("Standart", "Keşif", "Macera", "Astronot", "Denizci", "Dağcı")
    
    // Mevcut aksesuarlar
    val availableAccessories = listOf("Gözlük", "Şapka", "Bere", "Atkı", "Eldiven", "Sırt Çantası", "Kemer")
    
    // Mevcut ekipmanlar
    val availableEquipments = EquipmentType.values().map { it.displayName }
    
    // Mevcut taşıtlar
    val availableVehicles = VehicleType.values().map { it.displayName }
    
    /**
     * İsim güncellemesi yapar
     */
    fun updateName(name: String) {
        _state.update { it.copy(name = name) }
    }
    
    /**
     * Yaş güncellemesi yapar
     */
    fun updateAge(age: Int) {
        if (age in 4..12) {
            _state.update { it.copy(age = age) }
        }
    }
    
    /**
     * Saç stili güncellemesi yapar
     */
    fun updateHairStyle(hairStyle: String) {
        _state.update { it.copy(hairStyle = hairStyle) }
    }
    
    /**
     * Saç rengi güncellemesi yapar
     */
    fun updateHairColor(hairColor: String) {
        _state.update { it.copy(hairColor = hairColor) }
    }
    
    /**
     * Göz rengi güncellemesi yapar
     */
    fun updateEyeColor(eyeColor: String) {
        _state.update { it.copy(eyeColor = eyeColor) }
    }
    
    /**
     * Ten tonu güncellemesi yapar
     */
    fun updateSkinTone(skinTone: String) {
        _state.update { it.copy(skinTone = skinTone) }
    }
    
    /**
     * Kıyafet güncellemesi yapar
     */
    fun updateOutfit(outfit: String) {
        _state.update { it.copy(outfit = outfit) }
    }
    
    /**
     * Aksesuar ekler veya çıkarır
     */
    fun toggleAccessory(accessory: String) {
        _state.update {
            val currentAccessories = it.accessories.toMutableList()
            if (accessory in currentAccessories) {
                currentAccessories.remove(accessory)
            } else {
                currentAccessories.add(accessory)
            }
            it.copy(accessories = currentAccessories)
        }
    }
    
    /**
     * Ekipman ekler veya çıkarır
     */
    fun toggleEquipment(equipment: String) {
        _state.update {
            val currentEquipment = it.selectedEquipment.toMutableList()
            if (equipment in currentEquipment) {
                currentEquipment.remove(equipment)
            } else if (currentEquipment.size < 2) {
                // En fazla 2 ekipman seçilebilir
                currentEquipment.add(equipment)
            }
            it.copy(selectedEquipment = currentEquipment)
        }
    }
    
    /**
     * Taşıt güncellemesi yapar
     */
    fun updateVehicle(vehicle: String) {
        _state.update { it.copy(vehicle = vehicle) }
    }
    
    /**
     * Karakteri kaydeder
     */
    fun saveCharacter() {
        val currentState = _state.value
        
        if (currentState.name.isBlank()) {
            _state.update { it.copy(errorMessage = "Lütfen kaşifinize bir isim verin.") }
            return
        }
        
        if (currentState.selectedEquipment.isEmpty()) {
            _state.update { it.copy(errorMessage = "Lütfen en az bir ekipman seçin.") }
            return
        }
        
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        
        viewModelScope.launch {
            try {
                val explorer = Explorer(
                    name = currentState.name,
                    age = currentState.age,
                    hairStyle = currentState.hairStyle,
                    hairColor = currentState.hairColor,
                    eyeColor = currentState.eyeColor,
                    skinTone = currentState.skinTone,
                    outfit = currentState.outfit,
                    accessories = currentState.accessories,
                    equipment = currentState.selectedEquipment,
                    vehicle = currentState.vehicle
                )
                
                explorerRepository.createExplorer(explorer)
                _state.update { it.copy(isLoading = false, isComplete = true) }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(
                        isLoading = false,
                        errorMessage = "Bir hata oluştu: ${e.localizedMessage}"
                    )
                }
            }
        }
    }
    
    /**
     * Hata mesajını temizler
     */
    fun clearErrorMessage() {
        _state.update { it.copy(errorMessage = null) }
    }
} 