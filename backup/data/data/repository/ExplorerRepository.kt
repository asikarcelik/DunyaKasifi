package com.asikarcelik.dnyakaifi.data.repository

import com.asikarcelik.dnyakaifi.data.local.dao.ExplorerDao
import com.asikarcelik.dnyakaifi.data.model.Explorer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Kaşif verilerini yöneten repository sınıfı
 */
@Singleton
class ExplorerRepository @Inject constructor(
    private val explorerDao: ExplorerDao
) {
    /**
     * Tüm kaşifleri akış olarak getirir
     */
    fun getAllExplorers(): Flow<List<Explorer>> = explorerDao.getAllExplorers()
    
    /**
     * ID'ye göre bir kaşifi akış olarak getirir
     */
    fun getExplorerById(explorerId: Long): Flow<Explorer> = explorerDao.getExplorerById(explorerId)
    
    /**
     * Yeni bir kaşif ekler
     * @return eklenen kaşifin ID'si
     */
    suspend fun createExplorer(explorer: Explorer): Long {
        return explorerDao.insertExplorer(explorer)
    }
    
    /**
     * Mevcut bir kaşifi günceller
     */
    suspend fun updateExplorer(explorer: Explorer) {
        explorerDao.updateExplorer(explorer)
    }
    
    /**
     * Bir kaşifi siler
     */
    suspend fun deleteExplorer(explorer: Explorer) {
        explorerDao.deleteExplorer(explorer)
    }
    
    /**
     * Kaşifin seviye ve deneyim bilgilerini günceller
     */
    suspend fun updateExplorerProgress(explorerId: Long, level: Int, experience: Int, title: String) {
        explorerDao.updateExplorerProgress(explorerId, level, experience, title)
    }
    
    /**
     * Kaşifin ziyaret ettiği ülkeleri günceller
     */
    suspend fun updateVisitedCountries(explorerId: Long, visitedCountries: List<String>) {
        explorerDao.updateVisitedCountries(explorerId, visitedCountries)
    }
    
    /**
     * Kaşifin kazandığı rozetleri günceller
     */
    suspend fun updateBadges(explorerId: Long, badges: List<String>) {
        explorerDao.updateBadges(explorerId, badges)
    }
    
    /**
     * Kaşif sertifikası durumunu günceller
     */
    suspend fun updateCertificateStatus(explorerId: Long, hasCertificate: Boolean) {
        explorerDao.updateCertificateStatus(explorerId, hasCertificate)
    }
} 