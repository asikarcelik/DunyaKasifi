package com.asikarcelik.dnyakaifi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asikarcelik.dnyakaifi.data.model.Country
import com.asikarcelik.dnyakaifi.data.model.Explorer
import com.asikarcelik.dnyakaifi.data.model.MapPosition
import com.asikarcelik.dnyakaifi.data.model.Mission
import com.asikarcelik.dnyakaifi.data.repository.CountryRepository
import com.asikarcelik.dnyakaifi.data.repository.ExplorerRepository
import com.asikarcelik.dnyakaifi.data.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Dünya haritası ekran durumunu temsil eden veri sınıfı
 */
data class WorldMapState(
    val currentPosition: MapPosition = MapPosition(0.0, 0.0, 1f),
    val selectedCountryCode: String? = null,
    val explorerId: Long? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Dünya haritası ekranı için ViewModel
 */
@HiltViewModel
class WorldMapViewModel @Inject constructor(
    private val countryRepository: CountryRepository,
    private val explorerRepository: ExplorerRepository,
    private val missionRepository: MissionRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(WorldMapState())
    val state: StateFlow<WorldMapState> = _state.asStateFlow()
    
    // Tüm ülkeler
    val countries: StateFlow<List<Country>> = countryRepository.getAllCountries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    // Kilidini açılmış ülkeler
    val unlockedCountries: StateFlow<List<Country>> = countryRepository.getUnlockedCountries()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    // Seçilen ülkenin görevleri
    @OptIn(ExperimentalCoroutinesApi::class)
    val countryMissions: StateFlow<List<Mission>> = _state
        .flatMapLatest { state -> 
            state.selectedCountryCode?.let { 
                missionRepository.getMissionsByCountry(it)
            } ?: kotlinx.coroutines.flow.flowOf(emptyList())
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    
    // Mevcut kaşif
    @OptIn(ExperimentalCoroutinesApi::class)
    val currentExplorer: StateFlow<Explorer?> = _state
        .flatMapLatest { state ->
            state.explorerId?.let {
                explorerRepository.getExplorerById(it)
            } ?: kotlinx.coroutines.flow.flowOf(null)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
    
    // Kaşifin tamamladığı ülke sayısı ve toplam ülke sayısı
    val explorerProgress: StateFlow<Pair<Int, Int>> = combine(
        countries,
        currentExplorer
    ) { allCountries, explorer ->
        val total = allCountries.size
        val visited = explorer?.visitedCountries?.size ?: 0
        Pair(visited, total)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Pair(0, 0)
    )
    
    /**
     * Mevcut kaşifi ayarlar
     */
    fun setCurrentExplorer(explorerId: Long) {
        _state.update { it.copy(explorerId = explorerId) }
    }
    
    /**
     * Harita pozisyonunu günceller
     */
    fun updateMapPosition(position: MapPosition) {
        _state.update { it.copy(currentPosition = position) }
    }
    
    /**
     * Ülke seçer
     */
    fun selectCountry(countryCode: String) {
        _state.update { it.copy(selectedCountryCode = countryCode) }
    }
    
    /**
     * Ülke seçimini temizler
     */
    fun clearCountrySelection() {
        _state.update { it.copy(selectedCountryCode = null) }
    }
    
    /**
     * Ülkeyi ziyaret edildi olarak işaretler
     */
    fun visitCountry(countryCode: String) {
        val explorerId = _state.value.explorerId ?: return
        
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }
                
                // Ülkeyi kilitle (görünür yap)
                countryRepository.updateUnlockStatus(countryCode, true)
                
                // Kaşifin ziyaret ettiği ülkeleri güncelle
                val explorer = currentExplorer.value ?: return@launch
                val visitedCountries = explorer.visitedCountries.toMutableList()
                
                if (countryCode !in visitedCountries) {
                    visitedCountries.add(countryCode)
                    explorerRepository.updateVisitedCountries(explorerId, visitedCountries)
                }
                
                _state.update { it.copy(isLoading = false) }
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
     * Özellikle görevin tamamlanması durumunda rozet kazanımını kontrol eder
     */
    fun checkForBadges(completedMissionId: String) {
        val explorerId = _state.value.explorerId ?: return
        
        viewModelScope.launch {
            try {
                val explorer = currentExplorer.value ?: return@launch
                val mission = countryMissions.value.find { it.id == completedMissionId } ?: return@launch
                
                // Eğer görevin rozet ödülü varsa
                mission.badgeReward?.let { badge ->
                    val badges = explorer.badges.toMutableList()
                    if (badge !in badges) {
                        badges.add(badge)
                        explorerRepository.updateBadges(explorerId, badges)
                    }
                }
            } catch (e: Exception) {
                _state.update { 
                    it.copy(errorMessage = "Rozet kontrolünde hata oluştu: ${e.localizedMessage}")
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